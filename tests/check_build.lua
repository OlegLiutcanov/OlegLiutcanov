-- Loads builds/Domino Army.xml back into PoB2 and prints what it sees (proves the file opens).
local LAB = os.getenv("LAB")
dofile(LAB .. "/harness.lua")
local fh = assert(io.open(LAB .. "/builds/Domino Army.xml")); local xml = fh:read("*a"); fh:close()
loadBuildFromXML(xml, "Domino Army")
H.recalc()
print("class", build.spec.curClassName, build.spec.curAscendClassName, "level", build.characterLevel)
for i, s in ipairs(build.treeTab.specList) do
  local used, asc = s:CountAllocNodes()
  print(string.format("tree %d: %-34s %3d points + %d ascendancy%s", i, s.title or "Default", used, asc, i == build.treeTab.activeSpec and "   (open by default)" or ""))
end
for i, g in ipairs(build.skillsTab.socketGroupList) do
  local names = {}
  for _, gem in ipairs(g.gemList) do names[#names + 1] = string.format("%s %d%s", gem.nameSpec, gem.level, (gem.count or 1) > 1 and (" x" .. gem.count) or "") end
  print(string.format("skills %d: %-26s %s", i, g.label or "", table.concat(names, " + ")))
end
local o = build.calcsTab.mainOutput
print("Spirit", o.Spirit, "reserved", o.SpiritReserved, "Int", o.Int, "Life", o.Life, "Mana", o.Mana)
print("warrior life", o.Minion and math.floor(o.Minion.Life + 0.5))
