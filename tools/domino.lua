-- The "Domino Army" test character. Load after harness.lua and treelib.lua.
-- D.setup{...} builds the character in PoB2, D.measure() reads the numbers the lab cares about.
D = {}

-- The six notables (PoB2 node ids, tree 0_5). Living Death here is the Witch one: +15% minion life, 15% faster revive.
D.NOTABLES = {
  { id = 17973, name = "Living Death" },
  { id = 59541, name = "Necrotised Flesh" },
  { id = 41905, name = "Gravedigger" },
  { id = 38972, name = "Restless Dead" },
  { id = 3688,  name = "Dynamism" },
  { id = 30523, name = "Dead can Dance" },
}

-- PoB2 keeps a cache of every calculation and LuaJIT's collector does not keep up with it by itself:
-- without this, a loop of a few hundred recalculations grows past 13 GB and the machine kills it.
function D.tidy()
  if wipeGlobalCache then wipeGlobalCache() end
  collectgarbage(); collectgarbage()
end

local function setCondition(name)
  build.configTab.modList:NewMod("Condition:" .. name, "FLAG", true, "Config")
end

-- Step 1: the bare character.
-- opts: customMods (string), conditions (default true: "a minion died recently" and "triggered a skill recently"),
--       ascend (default "Infernalist"), questLevel (only count quest rewards from areas up to this level; nil = all),
--       enemy = { boss = "None"|"Boss"|"Pinnacle", level = n, corpseLife = n } (nil = PoB2 default: pinnacle boss)
function D.character(level, opts)
  opts = opts or {}
  H.new("Witch", level)
  local spec = build.spec
  local asc = opts.ascend == nil and "Infernalist" or opts.ascend
  if asc then
    for id, a in pairs(spec.tree.classes[spec.curClassId].classes) do
      if a.name == asc then spec:SelectAscendClass(id) end
    end
  end
  local input = build.configTab.input
  input.customMods = opts.customMods or ""
  if opts.conditions ~= false then input.conditionMinionsDiedRecently = true end
  if opts.questLevel then
    for _, quest in ipairs(data.questRewards) do
      if quest.useConfig ~= false and quest.Stat then
        input["quest" .. quest.Description .. quest.Area .. quest.Info] = quest.AreaLevel <= opts.questLevel
      end
    end
  end
  if opts.enemy then
    input.enemyIsBoss = opts.enemy.boss or "None"
    if opts.enemy.level then input.enemyLevel = opts.enemy.level end
    if opts.enemy.corpseLife then input.detonateDeadCorpseLife = opts.enemy.corpseLife end
  end
  build.configTab:BuildModList()
  if opts.conditions ~= false then setCondition("TriggeredSkillRecently") end
  D.opts = { level = level, otherReserved = 0 }
  D.army, D.triggers = nil, {}
  H.recalc()
  D.tidy()
  return spec
end

-- Step 2: the gems.
-- opts: warriorLevel, warriorQuality, warriors (count), armySupports (list of "Name level/quality"),
--       trigger = { gemLevel, spells = {"Comet 20/0", ...} }, triggerSupports (list),
--       extraGroups (list of socket group texts), otherReserved (Spirit used by extraGroups, for the lap model)
function D.groups(opts)
  -- start from an empty Skills tab, so one character can be re-equipped many times (a new PoB2 build per test leaks memory)
  local list = build.skillsTab.socketGroupList
  for i = #list, 1, -1 do table.remove(list, i) end
  build.mainSocketGroup = 1; build.calcsTab.input.skill_number = 1
  D.opts = { level = D.opts.level, otherReserved = 0 }
  for k, v in pairs(opts) do D.opts[k] = v end
  local lines = { string.format("Skeletal Warrior %d/%d  %d", opts.warriorLevel or 20, opts.warriorQuality or 0, opts.warriors or 2) }
  for _, s in ipairs(opts.armySupports or { "Minion Instability 1/0", "Infernal Legion I 1/0", "Feeding Frenzy I 1/0" }) do
    lines[#lines + 1] = s .. "  1"
  end
  D.army = H.group(table.concat(lines, "\n"), true)
  D.triggers = {}
  if opts.trigger then
    for _, spell in ipairs(opts.trigger.spells) do
      local t = { string.format("Cast on Minion Death %d/0  1", opts.trigger.gemLevel or 20), spell .. "  1" }
      for _, s in ipairs(opts.triggerSupports or {}) do t[#t + 1] = s .. "  1" end
      D.triggers[#D.triggers + 1] = { group = H.group(table.concat(t, "\n"), false), spell = spell }
    end
  end
  if opts.extraGroups then for _, g in ipairs(opts.extraGroups) do H.group(g, false) end end
end

-- Both steps at once (the fixed reference character used by job 1).
function D.setup(opts)
  local spec = D.character(opts.level or 90, opts)
  D.groups(opts)
  return spec
end

-- Highest level of a gem the character can use: PoB2's own rule for item-granted skills
-- (character level and Intelligence both have to meet the gem level's requirement).
function D.usableGemLevel(name, charLevel, int, cap)
  local gemData
  for _, g in pairs(data.gems) do if g.name == name and g.grantedEffect and g.grantedEffect.levels[1] then gemData = g; break end end
  assert(gemData, "gem not in PoB2: " .. name)
  for level = cap or 20, 1, -1 do
    local ld = gemData.grantedEffect.levels[level]
    local req = ld and ld.levelRequirement
    if req and req <= charLevel and calcLib.getGemStatRequirement(req, gemData.reqInt, false) <= int then return level, req end
  end
  return 1, 0
end

local function selectGroup(i, skillIndex)
  build.mainSocketGroup = i; build.calcsTab.input.skill_number = i
  local g = build.skillsTab.socketGroupList[i]
  g.mainActiveSkill = skillIndex or 1; g.mainActiveSkillCalcs = skillIndex or 1
  H.recalc()
  return g
end

-- Reads everything in one go. All damage numbers are against PoB2's default target
-- (a pinnacle boss with 50% fire and cold resistance) unless the key ends in "Raw".
function D.measure()
  local r = {}
  selectGroup(D.army, 1)
  local out = build.calcsTab.mainOutput
  local m = out.Minion
  r.life = m.Life
  r.burnPerSec = m.TotalBuildDegen or 0
  r.reviveTime = out.MinionRevivalSpeed
  r.spirit, r.spiritReserved, r.spiritFree = out.Spirit, out.SpiritReserved, out.SpiritUnreserved
  r.playerLife, r.playerES, r.playerMana = out.Life, out.EnergyShield, out.Mana
  r.mana, r.manaRegen = out.ManaUnreserved or out.Mana, out.ManaRegenRecovery or out.ManaRegen or 0
  r.manaMax = out.Mana
  r.int, r.reqInt, r.str, r.reqStr = out.Int, out.ReqInt, out.Str, out.ReqStr
  r.minionFireRes = m.FireResist
  for n, s in ipairs(build.calcsTab.mainEnv.minion.activeSkillList) do
    local name = s.activeEffect.grantedEffect.name
    if name == "Minion Instability" then
      H.minionSkill(D.army, n)
      r.explosion = build.calcsTab.mainOutput.Minion.AverageHit
    elseif name == "Basic Attack" then
      H.minionSkill(D.army, n)
      r.attackDPS = build.calcsTab.mainOutput.Minion.TotalDPS
    end
  end
  H.minionSkill(D.army, 1)
  -- seconds for the burn alone to take a full-life minion down to Low Life (35% of life)
  r.timeToLowLife = r.burnPerSec > 0 and (0.65 * r.life / r.burnPerSec) or nil
  r.cycle = r.timeToLowLife and (r.timeToLowLife + r.reviveTime) or nil
  for _, t in ipairs(D.triggers) do
    selectGroup(t.group, 2)
    local o = build.calcsTab.mainOutput
    local key = t.spell:match("^(.-) %d")
    r[key] = { hit = o.AverageHit, mana = o.ManaCost, castTime = o.Time }
  end
  selectGroup(D.army, 1)
  -- Spirit prices, read from PoB2 so reservation efficiency nodes are included
  local g = build.skillsTab.socketGroupList[D.army]
  local keep = g.gemList[1].count
  g.gemList[1].count = 2; H.recalc()
  local at2 = build.calcsTab.mainOutput.SpiritReserved
  g.gemList[1].count = 3; H.recalc()
  r.warriorCost = build.calcsTab.mainOutput.SpiritReserved - at2
  r.triggerCost = (#D.triggers > 0) and ((at2 - (D.opts.otherReserved or 0)) / #D.triggers) or 30
  r.otherReserved = D.opts.otherReserved or 0
  g.gemList[1].count = keep; H.recalc()
  D.tidy()
  return r
end

function D.fmt(x, d) if x == nil then return "-" end return string.format("%." .. (d or 0) .. "f", x) end

-- ---- Hand model on top of PoB2 numbers (say "worked out by hand" whenever you quote it) ----
-- PoB2 gives: warrior life, explosion hit, burn per second, revive delay, spell hit, mana cost, mana, mana regen, Spirit costs.
-- The model below only strings those together into "one lap": the whole army burns down to Low Life (35%), explodes,
-- and comes back after the shared revive delay.
--
-- Energy one dead minion gives Cast on Minion Death: 50 base (poe2db), scaled by the gem's "increased Energy gained"
-- (3% per gem level above 1) and by tree nodes such as Dynamism (+15%).
-- D.POWER is the "modified by the Minion's Power" factor. NOT confirmed for Skeletal Warriors: 1 means no change.
D.POWER = 1
function D.energyPerDeath(gemLevel, treeIncPercent)
  return 50 * D.POWER * (1 + (3 * ((gemLevel or 20) - 1) + (treeIncPercent or 0)) / 100)
end
-- Maximum Energy is 10 per 0.1 s of base cast time (poe2db): Comet 1.0 s -> 100, Detonate Dead 0.8 s -> 80.
-- poe2db Energy keyword: "modifiers to Total use time are treated as though they were double the value".
-- Comet has 1.00 s cast time and "+1 second to Total Cast Time": 100 + 2 x 100 = 300 Energy (hand arithmetic from that rule).
D.MAX_ENERGY = { Comet = 300, ["Detonate Dead"] = 80 }
function D.treeEnergyInc()
  local inc = 0
  for _, node in pairs(build.spec.allocNodes) do
    for _, line in ipairs(node.sd or {}) do
      local n = line:match("Meta Skills gain (%d+)%% increased Energy$")
      if n then inc = inc + tonumber(n) end
    end
  end
  return inc
end
-- One lap for a given trigger spell.
--   gems  = how many Cast on Minion Death gems hold that spell (each has its own Energy bar)
--   pool  = true: the mana pool is full at the start of the lap (a map pack); false: long fight, only regeneration counts
-- Returns a table: N warriors, lap seconds, casts wanted / paid for by mana, damage per lap split by source, dps.
function D.lap(r, spell, gems, pool, N)
  gems = gems or 1
  local t = spell and r[spell] or nil
  local gemLevel = D.opts.trigger and D.opts.trigger.gemLevel or 20
  local e = D.energyPerDeath(gemLevel, D.treeEnergyInc())
  N = N or (2 + math.max(0, math.floor((r.spirit - gems * r.triggerCost - (r.otherReserved or 0)) / r.warriorCost + 1e-9)))
  local L = { N = N, lap = r.cycle, energy = e }
  L.wanted = t and (N * e / D.MAX_ENERGY[spell] * gems) or 0
  L.spellName = spell
  L.manaLap = r.manaRegen * r.cycle + (pool and r.mana or 0)
  -- Essence Harvest (poe2db): "Recoup 8% of your maximum Mana when a Persistent Reviving Minion created by Supported Skill dies"
  for _, sup in ipairs(D.opts.armySupports or {}) do
    if sup:find("Essence Harvest") then L.harvest = N * 0.08 * r.manaMax; L.manaLap = L.manaLap + L.harvest end
  end
  L.paid = t and math.min(L.wanted, L.manaLap / math.max(t.mana, 1)) or 0
  L.boom = N * (r.explosion or 0)
  L.spell = L.paid * (t and t.hit or 0)
  -- the warriors also swing their swords while they are alive (the burn-down part of the lap)
  L.attacks = N * (r.attackDPS or 0) * r.timeToLowLife
  L.dps = (L.boom + L.spell + L.attacks) / r.cycle
  return L
end
function D.bossScore(r) return D.lap(r, "Comet", 1, false).dps end
function D.mapScore(r) return D.lap(r, "Detonate Dead", 1, true).dps end
