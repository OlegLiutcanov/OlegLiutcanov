-- Smoke test: level 90 Witch, no gear, no tree. Expected numbers are from PoB2 commit 601768bd.
dofile(os.getenv("LAB") .. "/harness.lua")
local fails = 0
local function check(name, got, want)
  local ok = math.floor(got + 0.5) == want
  if not ok then fails = fails + 1 end
  print(string.format("%-46s got %-8s want %-6d %s", name, tostring(math.floor(got + 0.5)), want, ok and "OK" or "DIFFERENT"))
end

H.new("Witch", 90)
H.group("Skeletal Warrior 20/0  1", true)
check("Skeletal Warrior 20: minion Life", build.calcsTab.mainOutput.Minion.Life, 2191)

H.new("Witch", 90)
H.group("Skeletal Brute 20/0  1", true)
check("Skeletal Brute 20: minion Life", build.calcsTab.mainOutput.Minion.Life, 2988)

H.new("Witch", 90)
build.configTab.input.customMods = "Minions have 200% increased maximum Life"; build.configTab:BuildModList()
local i = H.group("Skeletal Warrior 20/0  1\nMinion Instability 1/0  1", true)
check("Warrior + Instability, +200% life: Life", build.calcsTab.mainOutput.Minion.Life, 6573)
-- find the Minion Instability explosion among the minion's skills
local hit
for n, s in ipairs(build.calcsTab.mainEnv.minion.activeSkillList) do
  local nm = s.activeEffect.grantedEffect.name
  if nm:find("Instability") then
    H.minionSkill(i, n)
    hit = build.calcsTab.mainOutput.Minion.AverageHit
    print(string.format("  (minion skill %d = %s)", n, nm))
  end
end
check("Minion Instability hit", hit or -1, 493)
print(fails == 0 and "SMOKE TEST PASSED" or ("SMOKE TEST: " .. fails .. " number(s) differ"))
os.exit(fails == 0 and 0 or 1)
