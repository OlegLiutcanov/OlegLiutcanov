-- Writes the plan out as files:
--   builds/Domino Army.xml           open in Path of Building 2 (copy into "Documents\Path of Building (PoE2)\Builds")
--   builds/domino-army.tree.json     the route as a plain list of node ids, in the order to take them
-- The build is the level 90 checkpoint from job 2 (gem levels the character can really use, no gear).
-- Usage: JOB2_LIB=1 ./run.sh jobs/export_build.lua
local LAB = os.getenv("LAB")
dofile(LAB .. "/jobs/job2_checkpoints.lua") -- with JOB2_LIB=1 this only loads the functions
local ROUTE = JOB2.ROUTE

local c = JOB2.checkpoint(90)
local r = c.r
local spec = build.spec

-- warriors: 2 free + what the Spirit left by the two trigger gems (Detonate Dead, Comet) pays for
local N = D.lap(r, "Comet", 2, false).N
build.skillsTab.socketGroupList[D.army].gemList[1].count = N
build.skillsTab.socketGroupList[D.army].label = "Army (from the sceptre)"
for _, t in ipairs(D.triggers) do build.skillsTab.socketGroupList[t.group].label = "Trigger: " .. t.spell:match("^(.-) %d") end
H.recalc()

-- ascendancy nodes on the active tree (job 2 put them there); collect them for the other trees
local asc = {}
for id, node in pairs(spec.allocNodes) do if node.ascendancyName and node.type ~= "AscendClassStart" then asc[#asc + 1] = id end end
table.sort(asc)

-- one tree per mark
local marks = {}
for _, n in ipairs({ 20, 40, 60, 80 }) do if n <= #ROUTE then marks[#marks + 1] = n end end
local titles = { [20] = "20 points", [40] = "40 points - all six notables", [60] = "60 points - lab extension", [80] = "80 points - lab extension" }
spec.title = titles[marks[1]]
T.apply(spec, ROUTE, marks[1], 3, asc)
for i = 2, #marks do
  local s2 = build.treeTab:CopyTree(1, titles[marks[i]])
  T.apply(s2, ROUTE, marks[i], 3, asc)
end
build.treeTab:SetActiveSpec(#marks)
H.recalc()

local lines = {
  "DOMINO ARMY - zero-button Witch (Infernalist). Made by the build lab with Path of Building 2 (headless).",
  "",
  "What is in this file",
  "- Tree tab: pick a tree from the drop-down: " .. table.concat((function() local t = {} for _, n in ipairs(marks) do t[#t + 1] = titles[n] end return t end)(), " | "),
  "- Every '+5 to any Attribute' node is set to Intelligence: the level of your skeletons, of Cast on Minion Death and of the",
  "  triggered spell is capped by your Intelligence as well as by your character level.",
  "- Skills tab: the army, one trigger gem with Detonate Dead (packs) and one with Comet (bosses).",
  "- No gear. '+100 to Spirit' in Configuration > Custom Modifiers stands in for the sceptre (every Skeletal Warrior sceptre has 100 Spirit).",
  "- The Skeletal Warrior gem here stands in for the sceptre's skill. Its level is the highest this character meets the",
  "  requirements for. PoB2 cannot count paid sceptre warriors on a real sceptre item (it resets the count to 1), so it is a gem here.",
  "",
  "Not tested in game yet: see CLAUDE.md in the lab folder.",
}
build.notesTab.controls.edit:SetText(table.concat(lines, "\n"))
build.notesTab.lastContent = build.notesTab.controls.edit.buf

os.execute('mkdir -p "' .. LAB .. '/builds"')
local xml = build:SaveDB("Domino Army")
local fh = assert(io.open(LAB .. "/builds/Domino Army.xml", "w")); fh:write(xml); fh:close()
print("wrote builds/Domino Army.xml", #xml .. " bytes", "trees: " .. #build.treeTab.specList, "warriors: " .. N)

-- the neutral tree file
local function esc(s) return (s:gsub('\\', '\\\\'):gsub('"', '\\"')) end
local out = {}
out[#out + 1] = '{'
out[#out + 1] = '  "name": "Domino Army",'
out[#out + 1] = '  "game_patch": "0.5.5f",'
out[#out + 1] = '  "tree_version": "' .. spec.treeVersion .. '",'
out[#out + 1] = '  "class": "Witch",'
out[#out + 1] = '  "ascendancy": "Infernalist",'
out[#out + 1] = '  "note": "Node ids are the passive skill ids used by Path of Building 2 tree data 0_5. Take the nodes in this order. Every attribute node is set to Intelligence. Some nodes near the start differ per class: witch_variant_id is the id poe2db shows for the Witch version of that node.",'
out[#out + 1] = '  "six_notables_done_at_point": 40,'
out[#out + 1] = '  "ascendancy_nodes": [' .. table.concat(asc, ", ") .. '],'
out[#out + 1] = '  "node_ids_in_order": [' .. table.concat(ROUTE, ", ") .. '],'
out[#out + 1] = '  "nodes": ['
local last = build.treeTab.specList[#marks] -- names as the Witch sees them (some start nodes differ per class)
for i, id in ipairs(ROUTE) do
  local node = last.nodes[id]
  local raw = spec.tree.nodes[id]
  local variant = raw.options and raw.options.Witch and raw.options.Witch.id
  out[#out + 1] = string.format('    { "point": %d, "id": %d, "name": "%s", "kind": "%s", "stats": "%s"%s%s }%s', i, id,
    esc(raw.isAttribute and "Attribute" or node.dn), raw.isAttribute and "Attribute" or (node.type or ""),
    esc(raw.isAttribute and "+5 to Intelligence" or table.concat(node.sd or {}, " / ")),
    raw.isAttribute and ', "attribute": "Intelligence"' or "",
    variant and string.format(', "witch_variant_id": %d', variant) or "", i < #ROUTE and "," or "")
end
out[#out + 1] = '  ]'
out[#out + 1] = '}'
fh = assert(io.open(LAB .. "/builds/domino-army.tree.json", "w")); fh:write(table.concat(out, "\n") .. "\n"); fh:close()
print("wrote builds/domino-army.tree.json", #ROUTE .. " nodes")
