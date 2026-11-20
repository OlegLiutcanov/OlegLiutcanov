-- Oleg's level 15 sniper Witch (Act 2, patch 0.5.5h): how to clear big packs faster with Gas Arrow explosions.
-- Model: level 15 Witch, the first 18 points of jobs/leveling_tree.lua, Spirit 130 (sceptre 100 + King in the Mists 30),
-- sceptre stand-in "+100 to Spirit" plus "Allies in your Presence deal 2 to 5 added Attack Physical Damage" (his roll
-- is unknown), Skeletal Sniper gem level 5, target = a normal monster of area level 17 (no resistances).
-- PoB2 gives: explosion damage, its radius, the cooldown. The "pack score" is worked out by hand on top:
--   clouds per minute x explosion area x min(1, explosion damage / monster life)
-- i.e. how much ground per minute gets wiped. Usage: ./run.sh jobs/gas_arrow_packs.lua
local LAB = os.getenv("LAB")
dofile(LAB .. "/harness.lua")
dofile(LAB .. "/tools/treelib.lua")
dofile(LAB .. "/tools/domino.lua")
io.stdout:setvbuf("no")
local f = D.fmt

local LEVEL, AREA_LEVEL, POINTS = 15, 17, 18
local SCEPTRE = "+100 to Spirit\nAllies in your Presence deal 2 to 5 added Attack Physical Damage"
local TREE = dofile(LAB .. "/jobs/leveling_tree.lua")

local spec = D.character(LEVEL, { questLevel = LEVEL, enemy = { boss = "None", level = AREA_LEVEL }, customMods = SCEPTRE, conditions = false })
T.apply(spec, TREE, POINTS, 3)
local MONSTER = data.monsterLifeTable[AREA_LEVEL]

local function clear() local l = build.skillsTab.socketGroupList; for k = #l, 1, -1 do table.remove(l, k) end end

-- one sniper group; returns explosion damage, radius (m), cooldown (s), basic attack dps, Spirit per sniper
local function sniper(supports, count, extraGroups)
  clear()
  for _, g in ipairs(extraGroups or {}) do H.group(g, false) end
  local t = { string.format("Skeletal Sniper 5/0  %d", count or 2) }
  for _, s in ipairs(supports) do t[#t + 1] = s .. " 1/0  1" end
  local i = H.group(table.concat(t, "\n"), true)
  local g = build.skillsTab.socketGroupList[i]
  local src = g.displaySkillList[1].activeEffect.srcInstance
  local geid = g.displaySkillList[1].activeEffect.grantedEffect.id
  src.skillMinionSkillStatSetIndexLookup = { [geid] = { [2] = 3 } }      -- Gas Arrow, "Explosion" part
  src.skillMinionSkillStatSetIndexLookupCalcs = { [geid] = { [2] = 3 } }
  H.minionSkill(i, 2)
  local m = build.calcsTab.mainOutput.Minion
  local r = { boom = m.AverageDamage or 0, radius = m.AreaOfEffectRadiusMetres or 0, cooldown = m.Cooldown or 6 }
  H.minionSkill(i, 1)
  r.attack = build.calcsTab.mainOutput.Minion.TotalDPS or 0
  local out = build.calcsTab.mainOutput
  r.spirit, r.reserved = out.Spirit, out.SpiritReserved
  r.count = count or 2
  D.tidy()
  return r
end

local function score(r)
  local clouds = r.count * 60 / r.cooldown
  local area = math.pi * r.radius ^ 2
  local kill = math.min(1, r.boom / MONSTER)
  return clouds * area * kill, clouds, area, kill
end

local function line(tag, r, base)
  local s, clouds, area, kill = score(r)
  print(string.format("  %-46s explosion %4.0f (%s a normal monster) | radius %.2f m | cooldown %.1fs | %4.1f clouds/min | pack score %5.0f%s",
    tag, r.boom, kill >= 1 and "kills" or string.format("%.0f%% of", 100 * kill), r.radius, r.cooldown, clouds, s,
    base and string.format(" (%+.0f%%)", (s / score(base) - 1) * 100) or ""))
  return s
end

print(string.format("Level %d Witch, %d passive points (lab levelling tree), Spirit %s; normal monster at area level %d has %d life.\n",
  LEVEL, POINTS, f(build.calcsTab.mainOutput.Spirit), AREA_LEVEL, MONSTER))

print("SNIPER SUPPORTS (2 snipers, Raging Spirits kept)")
local RS = { "Raging Spirits 4/0  1\nRapid Attacks I 1/0  1", "Flame Wall 5/0  1\nMagnified Area I 1/0  1\nSpell Cascade 1/0  1" }
local base = sniper({ "Fire Attunement", "Concentrated Area" }, 2, RS)
line("now: Fire Attunement + Concentrated Area", base)
local variants = {
  { "Fire Attunement + Magnified Area I", { "Fire Attunement", "Magnified Area I" } },
  { "Fire Attunement + Bidding I", { "Fire Attunement", "Bidding I" } },
  { "Bidding I + Magnified Area I", { "Bidding I", "Magnified Area I" } },
  { "Concentrated Area + Bidding I", { "Concentrated Area", "Bidding I" } },
  { "Fire Attunement only", { "Fire Attunement" } },
  { "Brutality I + Magnified Area I (trap: no fire)", { "Brutality I", "Magnified Area I" } },
}
local best, bestScore = nil, -1
for _, v in ipairs(variants) do
  local r = sniper(v[2], 2, RS)
  local s = line(v[1], r, base)
  if s > bestScore and not v[1]:find("trap") then best, bestScore = v, s end
end

print("\nSPIRIT: third sniper instead of Raging Spirits (best supports above: " .. best[1] .. ")")
local two = sniper(best[2], 2, RS)
line("2 snipers + Raging Spirits", two, base)
local three = sniper(best[2], 3, { RS[2] })
line("3 snipers, no Raging Spirits", three, base)
print(string.format("  Spirit: 2 snipers + Raging Spirits = %s of %s; 3 snipers = %s of %s", f(two.reserved), f(two.spirit), f(three.reserved), f(three.spirit)))

-- Raging Spirits for comparison (single target)
clear()
H.group(RS[2], false)
local ri = H.group(RS[1], true)
H.minionSkill(ri, 1)
local rsd = build.calcsTab.mainOutput.Minion.TotalDPS or 0
D.tidy()
print(string.format("  one Raging Spirit: %.0f damage a second to one target (5 alive = %.0f). A gas cloud hits everything inside it.", rsd, 5 * rsd))

print("\nTREE: next notables from his tree (point cost from where the 18-point tree ends)")
T.apply(spec, TREE, POINTS, 3)
local ref = sniper(best[2], 2, RS)
for _, name in ipairs({ "Growing Swarm", "At your Command", "Power of the Dead", "Deadly Swarm", "Emboldening Lead" }) do
  T.apply(spec, TREE, POINTS, 3)
  local node
  for id, n in pairs(spec.nodes) do
    if n.dn == name and not n.ascendancyName and n.path and (not node or n.pathDist < node.pathDist) then node = n end
  end
  if node and node.alloc then
    print(string.format("  %-18s already taken", name))
  elseif node then
    local cost = node.pathDist
    spec:AllocNode(node)
    for nid, n2 in pairs(spec.allocNodes) do if n2.isAttribute and not spec.hashOverrides[nid] then spec:SwitchAttributeNode(nid, 3) end end
    spec:BuildAllDependsAndPaths(); H.recalc()
    local r = sniper(best[2], 2, RS)
    local s = score(r)
    print(string.format("  %-18s %2d points: explosion %4.0f, radius %.2f m, cooldown %.1fs -> pack score %+.0f%% | %s",
      name, cost, r.boom, r.radius, r.cooldown, (s / score(ref) - 1) * 100, table.concat(node.sd, " / ")))
  end
end
T.apply(spec, TREE, POINTS, 3)
