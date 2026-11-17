local _print = print; print = function() end
dofile("HeadlessWrapper.lua"); print = _print
H = {}
function H.recalc()
  build.buildFlag = true; build.modFlag = true
  runCallback("OnFrame"); build.calcsTab:BuildOutput(); runCallback("OnFrame")
end
function H.new(class, level)
  newBuild()
  build.spec:SelectClass(build.spec.tree.classNameMap[class])
  build.characterLevel = level; build.characterLevelAutoMode = false
  build.controls.characterLevel:SetText(level); H.recalc()
end
function H.group(text, main)            -- "Name level/quality  count", one gem per line
  build.skillsTab:PasteSocketGroup(text)
  local i = #build.skillsTab.socketGroupList
  if main then
    build.mainSocketGroup = i; build.calcsTab.input.skill_number = i
    local g = build.skillsTab.socketGroupList[i]; g.mainActiveSkill = 1; g.mainActiveSkillCalcs = 1
  end
  H.recalc(); return i
end
function H.minionSkill(i, n)            -- pick which minion skill is calculated
  local g = build.skillsTab.socketGroupList[i]
  local s = g.displaySkillList[1].activeEffect.srcInstance
  s.skillMinionSkill = n; s.skillMinionSkillCalcs = n; H.recalc()
end
-- custom mods stand in for tree/gear:
-- build.configTab.input.customMods = "Minions have 200% increased maximum Life"; build.configTab:BuildModList()
-- results: build.calcsTab.mainOutput (player) and .Minion (minion); minion skill list: build.calcsTab.mainEnv.minion.activeSkillList
