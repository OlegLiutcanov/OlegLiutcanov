-- Job 1: the shortest passive tree route through the six notables, what it costs, and what the warriors gain.
-- Reference character for every number here: level 90 Witch (Infernalist, no ascendancy points, no gear),
-- Skeletal Warrior level 20 + Minion Instability + Infernal Legion I + Feeding Frenzy I,
-- Cast on Minion Death 20 holding Comet 20 / Detonate Dead 20. Only the tree changes from row to row.
-- Usage: ./run.sh jobs/job1_route.lua
local LAB = os.getenv("LAB")
dofile(LAB .. "/harness.lua")
dofile(LAB .. "/tools/treelib.lua")
dofile(LAB .. "/tools/domino.lua")
io.stdout:setvbuf("no")
local f = D.fmt

local SPELLS = { "Comet 20/0", "Detonate Dead 20/0" }
local spec = D.setup{ level = 90, warriors = 2, trigger = { gemLevel = 20, spells = SPELLS } }
local ids, isNotable = {}, {}
for _, n in ipairs(D.NOTABLES) do ids[#ids + 1] = n.id; isNotable[n.id] = true end

-- 1. the exact cheapest set of nodes (ties broken toward the "Spell and Minion Damage" start)
local set, cost = T.steiner(spec, ids, function(node) return (node.dn == "Spell and Minion Damage") and 1 or 0 end)
local route = T.order(spec, set, ids)
print(string.format("SHORTEST ROUTE: %d points for all six notables (exact; PoB2 tree %s)", cost, spec.treeVersion))

-- 3. the suggested extension (points 41+), found by jobs/explore_extension.lua
local ok, ext = pcall(dofile, LAB .. "/jobs/extension.lua")
local full = { unpack(route) }
if ok and ext then for _, id in ipairs(ext) do full[#full + 1] = id end end

-- 4. point by point
print("\nPOINT-BY-POINT (put every \"+5 to any Attribute\" into Intelligence)")
for i, id in ipairs(full) do
  local node = spec.nodes[id]
  local tag = isNotable[id] and "  <== NOTABLE" or (node.type == "Notable" and "  <== notable (extension)" or "")
  if i == #route + 1 then print("  ---- the six notables are done; from here on it is the lab's suggested extension ----") end
  print(string.format("  %2d  %-34s %s%s", i, node.dn, table.concat(node.sd or {}, " / "), tag))
end

-- 5. the numbers at each mark
-- "boss" = PoB2's default target (pinnacle boss, 50% fire and cold resistance); "plain" = a monster with no resistances
local function measureAt(n)
  T.apply(spec, full, n, 3)
  return D.measure()
end
local function plainAt(n)
  build.configTab.input.enemyIsBoss = "None"; build.configTab:BuildModList()
  build.configTab.modList:NewMod("Condition:TriggeredSkillRecently", "FLAG", true, "Config")
  local r = measureAt(n)
  build.configTab.input.enemyIsBoss = "Pinnacle"; build.configTab:BuildModList()
  build.configTab.modList:NewMod("Condition:TriggeredSkillRecently", "FLAG", true, "Config")
  return r
end
print("\nMARKS (level 90 reference character, only the tree changes)")
print("  points | warrior life | explosion vs boss / vs no-resistance monster | revive delay | Comet hit | Detonate Dead hit | Int")
local marks = { 0, 20, 40, 60, 80 }
for _, n in ipairs(marks) do
  if n <= #full then
    local r = measureAt(n)
    local p = plainAt(n)
    print(string.format("  %6d | %12s | %8s / %-8s | %9ss | %9s | %9s | %d", n, f(r.life), f(r.explosion), f(p.explosion), f(r.reviveTime, 1), f(r.Comet.hit), f(r["Detonate Dead"].hit), r.int))
  end
end

-- 6. when each notable arrives
print("\nWHEN EACH NOTABLE ARRIVES")
for i, id in ipairs(full) do
  if isNotable[id] then
    local r = measureAt(i)
    print(string.format("  point %2d  %-18s -> warrior life %s, explosion %s, revive %ss", i, spec.nodes[id].dn, f(r.life), f(r.explosion), f(r.reviveTime, 1)))
  end
end

-- 7. compare with simply clicking the notables in order in PoB2 (done last: it renames attribute nodes)
spec:ResetNodes(); spec:BuildAllDependsAndPaths()
for _, id in ipairs(ids) do spec:AllocNode(spec.nodes[id]) end
print(string.format("\nFOR COMPARISON: clicking the six notables in order in PoB2 costs %d points; the exact route costs %d", (spec:CountAllocNodes()), cost))
JOB1 = { route = route, full = full }
