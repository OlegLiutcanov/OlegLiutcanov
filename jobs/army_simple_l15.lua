-- Level 15: his sniper + Gas Arrow army against an all-warrior army (no command button). Usage: ./run.sh jobs/army_simple_l15.lua
dofile(os.getenv("LAB") .. "/harness.lua")
dofile(os.getenv("LAB") .. "/tools/treelib.lua")
dofile(os.getenv("LAB") .. "/tools/domino.lua")
local TREE = dofile(os.getenv("LAB") .. "/jobs/leveling_tree.lua")
local SCEPTRE = "+100 to Spirit\nAllies in your Presence deal 2 to 5 added Attack Physical Damage"
local spec = D.character(15, { questLevel = 15, enemy = { boss = "None", level = 17 }, customMods = SCEPTRE, conditions = false })
T.apply(spec, TREE, 18, 3)
local function clear() local l = build.skillsTab.socketGroupList; for k = #l, 1, -1 do table.remove(l, k) end end
local function one(text)
  clear(); local i = H.group(text, true); H.minionSkill(i, 1)
  local o = build.calcsTab.mainOutput
  local r = { dps = o.Minion.TotalDPS or 0, life = o.Minion.Life, reserved = o.SpiritReserved }
  local g = build.skillsTab.socketGroupList[i]; local c = g.gemList[1].count
  g.gemList[1].count = c + 1; H.recalc(); r.extra = build.calcsTab.mainOutput.SpiritReserved - r.reserved
  D.tidy(); return r
end
local rs = one("Raging Spirits 4/0  1\nRapid Attacks I 1/0  1")
print(string.format("Raging Spirit: %.0f dps each, about 5 alive = %.0f", rs.dps, 5 * rs.dps))
local sn = one("Skeletal Sniper 5/0  2\nMagnified Area I 1/0  1\nMeat Shield I 1/0  1")
print(string.format("Sniper (Magnified + Meat Shield): arrows %.0f dps each, life %.0f, 37 Spirit each", sn.dps, sn.life))
local snFA = one("Skeletal Sniper 5/0  2\nFire Attunement 1/0  1\nRapid Attacks I 1/0  1")
print(string.format("Sniper (Fire Attunement + Rapid Attacks): arrows %.0f dps each", snFA.dps))
for _, wl in ipairs({ 3, 4, 5 }) do
  local w = one(string.format("Skeletal Warrior %d/0  2\nFire Attunement 1/0  1\nRapid Attacks I 1/0  1", wl))
  local free = 130 - 30
  local n = 2 + math.floor(free / w.extra + 1e-9)
  print(string.format("Warriors at skill level %d: %.0f dps each, life %.0f, extra costs %d Spirit -> with Raging Spirits: %d warriors = %.0f dps, + spirits %.0f = %.0f total",
    wl, w.dps, w.life, w.extra, n, n * w.dps, 5 * rs.dps, n * w.dps + 5 * rs.dps))
end
local w2 = one("Skeletal Warrior 4/0  2\nFire Attunement 1/0  1\nRapid Attacks I 1/0  1")
print(string.format("Current army for comparison: 2 warriors (lvl 4) %.0f + 2 snipers' arrows %.0f + spirits %.0f = %.0f (plus gas explosions on packs)", 2 * w2.dps, 2 * sn.dps, 5 * rs.dps, 2 * w2.dps + 2 * sn.dps + 5 * rs.dps))
