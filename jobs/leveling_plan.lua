-- Fast-campaign check: a fresh Witch levelling with minions. No gear except a sceptre (100 Spirit, Skeletal Warriors).
-- At each level: passive points and quest Spirit she would have, the levelling tree (jobs/leveling_tree.lua), gem levels
-- capped by level and Intelligence, supports that can drop by then. Target: a normal monster of her level.
-- Usage: ./run.sh jobs/leveling_plan.lua
local LAB = os.getenv("LAB")
dofile(LAB .. "/harness.lua")
dofile(LAB .. "/tools/treelib.lua")
dofile(LAB .. "/tools/domino.lua")
io.stdout:setvbuf("no")
local TREE = dofile(LAB .. "/jobs/leveling_tree.lua")
local LEVELS = { 3, 6, 10, 14, 18, 22, 28, 33, 41, 46, 52, 58, 62 }
local TIER_LEVEL = { [1] = 1, [2] = 16, [3] = 33, [4] = 45, [5] = 55 } -- support tier -> area level it starts dropping (unconfirmed)
local WARRIOR_SUPPORTS = { { "Fire Attunement", 1 }, { "Rapid Attacks I", 1 }, { "Brutality I", 1, "Brutality" }, { "Brutality II", 2, "Brutality" }, { "Feeding Frenzy I", 3 }, { "Heavy Swing", 4 }, { "Minion Mastery", 5 } }
local SNIPER_SUPPORTS = { { "Fire Attunement", 1 }, { "Rapid Attacks I", 1 }, { "Brutality I", 1, "Brutality" }, { "Brutality II", 2, "Brutality" }, { "Feeding Frenzy I", 3 }, { "Minion Mastery", 5 } }
local function questPoints(level) local n = 0; for _, q in ipairs(data.questRewards) do if q.questPoints and q.AreaLevel <= level then n = n + q.questPoints end end; return n end
local function clear() local list = build.skillsTab.socketGroupList; for i = #list, 1, -1 do table.remove(list, i) end end
local function minion(text) -- dps of ONE minion's normal attack, its Spirit cost, its life
  clear(); D.army = H.group(text, true); H.minionSkill(D.army, 1)
  local o = build.calcsTab.mainOutput
  local r = { dps = o.Minion.TotalDPS or 0, life = o.Minion.Life, cost = o.SpiritReserved or 0 }
  D.tidy(); return r
end
local function best(gem, level, list, sockets, charLevel) -- greedy: add the support that helps most, one socket at a time
  local chosen, fam = {}, {}
  local function text() local t = { string.format("%s %d/0  1", gem, level) }; for _, s in ipairs(chosen) do t[#t + 1] = s .. " 1/0  1" end; return table.concat(t, "\n") end
  local cur = minion(text())
  for _ = 1, sockets do
    local pick
    for _, c in ipairs(list) do
      local f = c[3] or c[1]
      if charLevel >= TIER_LEVEL[c[2]] and not fam[f] then
        chosen[#chosen + 1] = c[1]; local r = minion(text()); table.remove(chosen)
        if r.dps > cur.dps * 1.01 and (not pick or r.dps > pick.r.dps) then pick = { c = c, r = r } end
      end
    end
    if not pick then break end
    chosen[#chosen + 1] = pick.c[1]; fam[pick.c[3] or pick.c[1]] = true; cur = pick.r
  end
  return cur, chosen
end
print("level | points | Int | Spirit | A: all warriors | B: 2 warriors + snipers | one Raging Spirit | normal monster life")
for _, level in ipairs(LEVELS) do
  local spec = D.character(level, { questLevel = level, enemy = { boss = "None", level = level }, customMods = "+100 to Spirit", conditions = false })
  local points = math.min(level - 1 + questPoints(level), #TREE)
  T.apply(spec, TREE, points, 3)
  local out = build.calcsTab.mainOutput
  local int, spirit = out.Int, out.Spirit
  local wl = D.usableGemLevel("Skeletal Warrior", level, int)
  local sl = D.usableGemLevel("Skeletal Sniper", level, int)
  local wSockets = wl >= 15 and 4 or wl >= 10 and 3 or 2
  local sSockets = level >= 33 and 3 or 2 -- assumption: a third socket from a Lesser Jeweller's Orb by Act 3
  local w, wSup = best("Skeletal Warrior", wl, WARRIOR_SUPPORTS, wSockets, level)
  local wc = minion(string.format("Skeletal Warrior %d/0  3", wl)).cost -- cost of the one paid warrior
  local s, sSup = best("Skeletal Sniper", sl, SNIPER_SUPPORTS, sSockets, level)
  local rs = level >= 10 and minion(string.format("Raging Spirits %d/0  1", D.usableGemLevel("Raging Spirits", level, int))) or nil
  local rsCost = rs and 30 or 0
  local nA = 2 + math.floor((spirit - rsCost) / wc + 1e-9)
  local nB = math.floor((spirit - rsCost) / s.cost + 1e-9)
  local A, B = nA * w.dps, 2 * w.dps + nB * s.dps
  local monster = data.monsterLifeTable[level]
  print(string.format("%5d | %3d | %3d | %3d | %2d x %4.0f = %5.0f (kills in %4.1fs) [L%d: %s] | 2 + %d snipers x %4.0f = %5.0f (%4.1fs) [L%d, %d Spirit each: %s] | %s | %d",
    level, points, int, spirit, nA, w.dps, A, monster / A, wl, table.concat(wSup, " + "), nB, s.dps, B, monster / B, sl, s.cost, table.concat(sSup, " + "),
    rs and string.format("%.0f dps, 5 up = %.0f", rs.dps, 5 * rs.dps) or "-", monster))
end
