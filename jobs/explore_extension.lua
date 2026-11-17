-- Explorer: after the 40-point route, which notables give the most per point?
-- It tries every notable within reach, measures it in PoB2, takes the best "gain per point", and repeats until 80 points.
-- The score is worked out by hand on top of PoB2 numbers (see D.bossScore): damage one warrior does per death, divided by
-- the seconds one death-and-revive lap takes. It is a guide for picking nodes, not a promise of in-game damage.
-- Usage: ./run.sh jobs/explore_extension.lua [maxPoints] [maxDistance]
local ARGV = arg -- PoB2's headless wrapper clears "arg"
dofile(os.getenv("LAB") .. "/harness.lua")
dofile(os.getenv("LAB") .. "/tools/treelib.lua")
dofile(os.getenv("LAB") .. "/tools/domino.lua")
io.stdout:setvbuf("no")
local MAXPTS = tonumber(ARGV and ARGV[1]) or 80
local MAXDIST = tonumber(ARGV and ARGV[2]) or 10

-- Left out on purpose (each one works against the plan or is not for this build):
local SKIP = {
  ["Vile Mending"] = "Oleg skips it: minion life regeneration fights the burn",
  ["Regenerative Flesh"] = "minions get life back, so they die slower",
  ["Crystalline Flesh"] = "minion fire resistance slows the burn",
  ["Silent Guardian"] = "minion fire resistance slows the burn",
  ["Living Death"] = "the far one gives minion resistances, which slows the burn",
  ["Unstable Bond"] = "Volatility hurts the player",
  ["Sacrificial Blood"] = "only works if spells cost life; not for a hands-off build",
  ["Blood Transfusion"] = "makes triggered spells cost life with nobody at the wheel",
  ["Lord of the Wilds"] = "keystone with a big drawback",
}
-- Picks already settled by earlier runs of this explorer (saves re-measuring them). Empty this list for a full re-run.
local PRE = { "Raw Destruction", "Empowering Remains", "Emboldening Lead" }

-- "+100 to Spirit" stands in for the sceptre (every Skeletal Warrior sceptre base has 100 Spirit)
local spec = D.setup{ level = 90, warriors = 2, trigger = { gemLevel = 20, spells = { "Comet 20/0", "Detonate Dead 20/0" } }, customMods = "+100 to Spirit" }
local ids = {}; for _, n in ipairs(D.NOTABLES) do ids[#ids + 1] = n.id end
local set = T.steiner(spec, ids, function(node) return (node.dn == "Spell and Minion Damage") and 1 or 0 end)
local order = T.order(spec, set, ids)

local function attrFix()
  for nid, n2 in pairs(spec.allocNodes) do if n2.isAttribute and not spec.hashOverrides[nid] then spec:SwitchAttributeNode(nid, 3) end end
  spec:BuildAllDependsAndPaths(); H.recalc()
end
-- score = geometric mean of the boss lap (Comet) and the map lap (Detonate Dead), so a pick has to help both
local function score() local r = D.measure(); local b, m = D.bossScore(r), D.mapScore(r); return math.sqrt(b * m), m, r, b end

T.apply(spec, order, #order, 3)
for _, name in ipairs(PRE) do
  local best
  for id, node in pairs(spec.nodes) do
    if node.dn == name and not node.alloc and not node.ascendancyName and node.path and (not best or node.pathDist < best.pathDist) then best = node end
  end
  for i = #best.path, 1, -1 do order[#order + 1] = best.path[i].id end
  T.apply(spec, order, #order, 3)
  print(string.format("PRE  %-22s -> %d points", name, #order))
end
local pool
local picks = {}
while #order < MAXPTS do
  T.apply(spec, order, #order, 3)
  local base, baseMap = score()
  local cands = {}
  if not pool then
    local names = {}
    for _, n in ipairs(dofile(os.getenv("LAB") .. "/jobs/extension_pool.lua")) do names[n] = true end
    for id, node in pairs(spec.nodes) do
      if not node.alloc and not node.ascendancyName and node.type == "Notable" and names[node.dn] and node.path and node.pathDist and node.pathDist <= MAXDIST and not node.unlockConstraint and not SKIP[node.dn] then cands[#cands + 1] = id end
    end
  else
    for id in pairs(pool) do local node = spec.nodes[id]; if not node.alloc and node.path and node.pathDist <= MAXDIST then cands[#cands + 1] = id end end
  end
  table.sort(cands)
  local best, newPool = nil, {}
  for _, id in ipairs(cands) do
    T.apply(spec, order, #order, 3)
    local node = spec.nodes[id]
    local cost = node.pathDist
    local path = {}
    for i = #node.path, 1, -1 do path[#path + 1] = node.path[i].id end
    if #order + cost <= MAXPTS then
      spec:AllocNode(node); attrFix()
      local s, sMap = score()
      local gain = (s / base - 1) * 100
      local gainMap = (sMap / baseMap - 1) * 100
      if gain > 0.05 or gainMap > 0.05 then newPool[id] = true end
      local c = { id = id, dn = node.dn, cost = cost, gain = gain, gainMap = gainMap, per = gain / cost, path = path, text = table.concat(node.sd, " / ") }
      if gain > 0.05 and (not best or c.per > best.per) then best = c end
    else
      newPool[id] = true
    end
    if wipeGlobalCache then wipeGlobalCache() end
    collectgarbage()
  end
  pool = newPool
  if not best then print("no candidate left that fits"); break end
  for _, id in ipairs(best.path) do order[#order + 1] = id end
  picks[#picks + 1] = best
  print(string.format("PICK %-22s cost %2d  score %+6.1f%% (%.1f%%/pt)  map-only %+6.1f%%  -> %d points | %s", best.dn, best.cost, best.gain, best.per, best.gainMap, #order, best.text))
end
T.apply(spec, order, #order, 3)
local _, _, r = score()
print(string.format("END at %d points: life %.0f boom %.0f comet %.0f dd %.0f revive %.2fs", #order, r.life, r.explosion, r.Comet.hit, r["Detonate Dead"].hit, r.reviveTime))
io.write("ORDER = { "); for i = 41, #order do io.write(order[i], ", ") end print("}")
for i = 41, #order do print(i, T.describe(spec, order[i])) end
