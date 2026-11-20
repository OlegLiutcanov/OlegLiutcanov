-- Why do the snipers die, and what keeps them up? Level 15 Witch, 18-point lab levelling tree, snipers gem level 5.
-- Compares sniper supports (Meat Shield I, Last Gasp) and the minion life / revive notables near his tree.
-- Usage: ./run.sh jobs/sniper_survival.lua
dofile(os.getenv("LAB") .. "/harness.lua")
dofile(os.getenv("LAB") .. "/tools/treelib.lua")
dofile(os.getenv("LAB") .. "/tools/domino.lua")
local TREE = dofile(os.getenv("LAB") .. "/jobs/leveling_tree.lua")
local SCEPTRE = "+100 to Spirit\nAllies in your Presence deal 2 to 5 added Attack Physical Damage"
local spec = D.character(15, { questLevel = 15, enemy = { boss = "None", level = 17 }, customMods = SCEPTRE, conditions = false })
T.apply(spec, TREE, 18, 3)
local function clear() local l = build.skillsTab.socketGroupList; for k = #l, 1, -1 do table.remove(l, k) end end
local function measure(sups)
  clear()
  local t = { "Skeletal Sniper 5/0  2" }; for _, s in ipairs(sups) do t[#t + 1] = s .. " 1/0  1" end
  local i = H.group(table.concat(t, "\n"), true)
  local g = build.skillsTab.socketGroupList[i]
  local src = g.displaySkillList[1].activeEffect.srcInstance
  local geid = g.displaySkillList[1].activeEffect.grantedEffect.id
  src.skillMinionSkillStatSetIndexLookup = { [geid] = { [2] = 3 } }; src.skillMinionSkillStatSetIndexLookupCalcs = { [geid] = { [2] = 3 } }
  H.minionSkill(i, 2)
  local o = build.calcsTab.mainOutput; local m = o.Minion
  local r = { life = m.Life, boom = m.AverageDamage, radius = m.AreaOfEffectRadiusMetres, revive = o.MinionRevivalSpeed, fire = m.FireResist, cold = m.ColdResist, light = m.LightningResist, chaos = m.ChaosResist, armour = m.Armour, evasion = m.Evasion, taken = m.DamageTakenMult or nil, phys = m.PhysicalMaximumHitTaken, fireHit = m.FireMaximumHitTaken, regen = m.LifeRegenRecovery }
  D.tidy(); return r
end
local function show(tag, r)
  print(string.format("%-44s life %4.0f | biggest phys hit %5s fire hit %5s | res F/C/L/Ch %s/%s/%s/%s | regen %s/s | revive %.1fs | explosion %4.0f r %.2f",
    tag, r.life or 0, D.fmt(r.phys), D.fmt(r.fireHit), tostring(r.fire), tostring(r.cold), tostring(r.light), tostring(r.chaos), D.fmt(r.regen, 1), r.revive or 0, r.boom or 0, r.radius or 0))
end
show("Magnified Area I + Bidding I", measure({ "Magnified Area I", "Bidding I" }))
show("Magnified Area I + Meat Shield I", measure({ "Magnified Area I", "Meat Shield I" }))
show("Magnified Area I + Last Gasp", measure({ "Magnified Area I", "Last Gasp" }))
local base = measure({ "Magnified Area I", "Meat Shield I" })
print("--- tree nodes (from the 18-point tree), with Magnified Area I + Meat Shield I")
for _, name in ipairs({ "Living Death", "Necrotised Flesh", "Vile Mending", "Crystalline Flesh", "Restless Dead", "Gravedigger", "Licking Wounds", "Growing Swarm" }) do
  T.apply(spec, TREE, 18, 3)
  local node
  for id, n in pairs(spec.nodes) do if n.dn == name and not n.ascendancyName and n.path and (not node or n.pathDist < node.pathDist) then node = n end end
  if node and not node.alloc then
    local cost = node.pathDist
    spec:AllocNode(node)
    for nid, n2 in pairs(spec.allocNodes) do if n2.isAttribute and not spec.hashOverrides[nid] then spec:SwitchAttributeNode(nid, 3) end end
    spec:BuildAllDependsAndPaths(); H.recalc()
    local r = measure({ "Magnified Area I", "Meat Shield I" })
    show(string.format("+%s (%d pts)", name, cost), r)
    print("        " .. table.concat(node.sd, " / "))
  elseif node then print(name, "already taken") end
end
T.apply(spec, TREE, 18, 3)
