-- Explorer: the best passive order for LEVELLING with a plain skeleton army (damage first).
-- Reference: level 41 Witch, sceptre Skeletal Warriors level 11 with Brutality II + Rage I + Feeding Frenzy I, no gear.
-- Score = number of warriors x one warrior's attack damage a second (PoB2). Greedy: best gain per point each step.
-- Usage: ./run.sh jobs/explore_leveling_tree.lua [maxPoints] [maxDistance]
local ARGV = arg
local LAB = os.getenv("LAB")
dofile(LAB .. "/harness.lua")
dofile(LAB .. "/tools/treelib.lua")
dofile(LAB .. "/tools/domino.lua")
io.stdout:setvbuf("no")
local MAXPTS = tonumber(ARGV and ARGV[1]) or 60
local MAXDIST = tonumber(ARGV and ARGV[2]) or 7
local SKIP = { ["Gigantic Following"] = true, ["Lord of the Wilds"] = true, ["Necromantic Talisman"] = true, ["Sacrificial Blood"] = true, ["Blood Transfusion"] = true }

local spec = D.character(41, { questLevel = 41, enemy = { boss = "None", level = 41 }, customMods = "+100 to Spirit", conditions = false })
D.groups{ warriorLevel = 11, warriors = 2, armySupports = { "Brutality II 1/0", "Rage I 1/0", "Feeding Frenzy I 1/0" } }
local function score()
  H.minionSkill(D.army, 1)
  local out = build.calcsTab.mainOutput
  local dps = out.Minion.TotalDPS or 0
  local g = build.skillsTab.socketGroupList[D.army]
  g.gemList[1].count = 3; H.recalc()
  local cost = build.calcsTab.mainOutput.SpiritReserved
  g.gemList[1].count = 2; H.recalc()
  local N = 2 + math.floor(out.Spirit / math.max(cost, 1) + 1e-9)
  return dps * N, dps, N, out.Minion.Life, out.Int
end
local order = {}
local function attrFix()
  for nid, n2 in pairs(spec.allocNodes) do if n2.isAttribute and not spec.hashOverrides[nid] then spec:SwitchAttributeNode(nid, 3) end end
  spec:BuildAllDependsAndPaths(); H.recalc()
end
local pool
while #order < MAXPTS do
  T.apply(spec, order, #order, 3)
  local base = score()
  local cands = {}
  for id, node in pairs(spec.nodes) do
    if not node.alloc and not node.ascendancyName and node.type == "Notable" and node.path and node.pathDist and node.pathDist <= MAXDIST and not node.unlockConstraint and not SKIP[node.dn] and (not pool or pool[id] ~= false) then cands[#cands + 1] = id end
  end
  table.sort(cands)
  pool = pool or {}
  local best
  for _, id in ipairs(cands) do
    T.apply(spec, order, #order, 3)
    local node = spec.nodes[id]
    local cost = node.pathDist
    local path = {}
    for i = #node.path, 1, -1 do path[#path + 1] = node.path[i].id end
    if #order + cost <= MAXPTS then
      spec:AllocNode(node); attrFix()
      local s = score()
      local gain = (s / base - 1) * 100
      if gain <= 0.05 and cost <= 3 then pool[id] = false end -- useless and near: stop re-testing it
      local c = { dn = node.dn, cost = cost, gain = gain, per = gain / cost, path = path, text = table.concat(node.sd, " / ") }
      if gain > 0.05 and (not best or c.per > best.per) then best = c end
    end
    D.tidy()
  end
  if not best then print("no candidate left that fits"); break end
  for _, id in ipairs(best.path) do order[#order + 1] = id end
  print(string.format("PICK %-22s cost %2d  army damage %+6.1f%% (%.1f%%/pt) -> %d points | %s", best.dn, best.cost, best.gain, best.per, #order, best.text))
end
T.apply(spec, order, #order, 3)
local s, dps, N, life, int = score()
print(string.format("END at %d points: %d warriors x %.0f = %.0f dps, warrior life %.0f, Int %d", #order, N, dps, s, life, int))
io.write("ORDER = { "); for _, id in ipairs(order) do io.write(id, ", ") end print("}")
for i, id in ipairs(order) do print(i, T.describe(spec, id)) end
