-- Which levelling skill is strongest for a fresh Witch? Raw PoB2 numbers, no gear, no tree, no supports,
-- against a normal monster of the character's level. Gem level = the highest the character level allows.
-- Usage: ./run.sh jobs/leveling_compare.lua
local LAB = os.getenv("LAB")
dofile(LAB .. "/harness.lua")
dofile(LAB .. "/tools/treelib.lua")
dofile(LAB .. "/tools/domino.lua")
io.stdout:setvbuf("no")
local f = D.fmt
local LEVELS = { 3, 6, 10, 14, 22, 31, 41, 52, 62 }
local SPELLS = { -- name, tier
  { "Contagion", 1 }, { "Essence Drain", 3 }, { "Spark", 1 }, { "Flame Wall", 1 }, { "Frost Bomb", 1 }, { "Ice Nova", 1 }, { "Fireball", 3 }, { "Living Bomb", 3 },
  { "Bone Cage", 3 }, { "Orb of Storms", 3 }, { "Bonestorm", 5 }, { "Arc", 5 }, { "Ember Fusillade", 5 }, { "Frostbolt", 5 }, { "Snap", 5 }, { "Detonate Dead", 7 }, { "Incinerate", 9 }, { "Dark Effigy", 9 }, { "Hexblast", 11 }, { "Comet", 11 }, { "Firestorm", 11 },
}
local MINIONS = { { "Skeletal Warrior", 0, free = 2 }, { "Skeletal Sniper", 1 }, { "Skeletal Arsonist", 3 }, { "Raging Spirits", 4 }, { "Skeletal Frost Mage", 5 }, { "Raise Zombie", 5 }, { "Unearth", 1 }, { "Skeletal Reaver", 9 }, { "Skeletal Storm Mage", 11 }, { "Skeletal Brute", 13 } }
local REQ = { 0, 3, 6, 10, 14, 18, 22, 26, 31, 36, 41, 46, 52, 58, 64, 66, 72, 78, 84, 90 }
local function gemLevel(level) local g = 1; for i, r in ipairs(REQ) do if r <= level then g = i end end; return g end

for _, level in ipairs(LEVELS) do
  local gl = gemLevel(level)
  D.character(level, { questLevel = level, enemy = { boss = "None", level = level, corpseLife = data.monsterLifeTable[level] }, customMods = "+100 to Spirit", conditions = false })
  local base = build.calcsTab.mainOutput
  local monster = data.monsterLifeTable[level]
  print(string.format("\nLEVEL %d (gems level %d)  normal monster life %d | mana %d regen %.1f/s | Spirit %d (sceptre 100 + quests)", level, gl, monster, base.Mana, base.ManaRegenRecovery or 0, base.Spirit))
  local rows = {}
  for _, s in ipairs(SPELLS) do
    if gl >= s[2] then
      local list = build.skillsTab.socketGroupList
      for i = #list, 1, -1 do table.remove(list, i) end
      local ok = pcall(function() H.group(string.format("%s %d/0  1", s[1], gl), true) end)
      local o = build.calcsTab.mainOutput
      if ok and o then
        local dps = math.max(o.CombinedDPS or 0, o.TotalDPS or 0, o.TotalDotDPS or 0, o.TotalDot or 0)
        local cps = o.Speed or 0
        local sustain = (o.ManaCost and o.ManaCost > 0 and cps > 0) and math.min(1, (base.ManaRegenRecovery or 0) / (o.ManaCost * cps)) or 1
        rows[#rows + 1] = { name = s[1], dps = dps, note = string.format("hit %s, dot %s, %.2f casts/s, %s mana, regen pays %d%% of nonstop casting", f(o.AverageHit), f(math.max(o.TotalDot or 0, o.TotalDotDPS or 0)), cps, f(o.ManaCost), sustain * 100) }
      end
      D.tidy()
    end
  end
  for _, m in ipairs(MINIONS) do
    if gl >= m[2] then
      local list = build.skillsTab.socketGroupList
      for i = #list, 1, -1 do table.remove(list, i) end
      local ok = pcall(function() D.army = H.group(string.format("%s %d/0  1", m[1], gl), true) end)
      local o = build.calcsTab.mainOutput
      if ok and o and o.Minion then
        local best, bestName = 0, ""
        for n, sk in ipairs(build.calcsTab.mainEnv.minion.activeSkillList) do
          H.minionSkill(D.army, n)
          local d = build.calcsTab.mainOutput.Minion.TotalDPS or 0
          if d > best then best, bestName = d, sk.activeEffect.grantedEffect.name end
        end
        -- how many fit in the Spirit
        local g = build.skillsTab.socketGroupList[D.army]
        local free = m.free or 0
        g.gemList[1].count = free + 1; H.recalc()
        local cost = build.calcsTab.mainOutput.SpiritReserved or 0
        local N = cost > 0 and (free + math.floor(base.Spirit / cost + 1e-9)) or nil
        rows[#rows + 1] = { name = m[1] .. (N and (" x" .. N) or ""), dps = best * (N or 1), minion = true,
          note = string.format("%s each with %s, life %s%s", f(best), bestName, f(build.calcsTab.mainOutput.Minion.Life), N and string.format(", %s Spirit each", f(cost)) or " (count not known to PoB2: shown for ONE)") }
      end
      D.tidy()
    end
  end
  table.sort(rows, function(a, b) return a.dps > b.dps end)
  for _, r in ipairs(rows) do
    print(string.format("  %-26s %7s dps = a normal monster dies in %5.1fs | %s", r.name, f(r.dps), r.dps > 0 and monster / r.dps or 999, r.note))
  end
end
