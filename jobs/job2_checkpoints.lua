-- Job 2: the campaign checkpoints (levels 10, 26, 41, 52, 65, 90) with the planned tree applied.
-- At each level the character only has what it could really have: passive points, quest Spirit, gem levels it meets
-- the requirements for, support gems that can drop by then, and as many support sockets as the sceptre skill has.
-- Usage: ./run.sh jobs/job2_checkpoints.lua            (prints the tables)
-- Numbers marked "lap model" are worked out by hand on top of PoB2 output (see tools/domino.lua).
local LAB = os.getenv("LAB")
local ARGV = arg -- PoB2's headless wrapper clears "arg", so keep our own copy
dofile(LAB .. "/harness.lua")
dofile(LAB .. "/tools/treelib.lua")
dofile(LAB .. "/tools/domino.lua")
io.stdout:setvbuf("no")
local f = D.fmt

local LEVELS = { 10, 26, 41, 52, 65, 90 }
-- Infernalist points by level (lab suggestion; 2 points per trial): Minion Life > Loyal Hellhound, Life > Altered Flesh,
-- Life > Beidat's Will. The last 2 points are left open (Beidat's Hand and Gaze each reserve another 25% of life).
local ASC = {
  { level = 22, ids = { 39470, 17754 }, name = "Loyal Hellhound" },
  { level = 38, ids = { 7793, 18348 }, name = "Altered Flesh" },
  { level = 60, ids = { 8854, 46644 }, name = "Beidat's Will" },
}
-- Area level from which an uncut support gem of each tier can drop (poe2wiki table, read through a search snippet).
local SUPPORT_TIER_LEVEL = { [1] = 1, [2] = 16, [3] = 33, [4] = 45, [5] = 55 }
-- Support sockets on a sceptre-granted skill by skill level (poe2db table for item skills): 2 / 3 / 4 / 5.
local function sockets(skillLevel) return skillLevel >= 20 and 5 or skillLevel >= 15 and 4 or skillLevel >= 10 and 3 or 2 end

local function questPoints(level)
  local n = 0
  for _, q in ipairs(data.questRewards) do if q.questPoints and q.AreaLevel <= level then n = n + q.questPoints end end
  return n
end

-- the route: 40 points for the six notables, then the suggested extension
local ROUTE
do
  local spec = D.character(90)
  local ids = {}; for _, n in ipairs(D.NOTABLES) do ids[#ids + 1] = n.id end
  local set = T.steiner(spec, ids, function(node) return (node.dn == "Spell and Minion Damage") and 1 or 0 end)
  ROUTE = T.order(spec, set, ids)
  local ok, ext = pcall(dofile, LAB .. "/jobs/extension.lua")
  if ok and ext then for _, id in ipairs(ext) do ROUTE[#ROUTE + 1] = id end end
end

local DOMINO_SUPPORTS = { -- in the order the plan adds them
  { "Minion Instability 1/0", 2 }, { "Infernal Legion I 1/0", 2 }, { "Feeding Frenzy I 1/0", 3 }, { "Crazed Minions 1/0", 4 },
}
local PLAIN_SUPPORTS = { -- candidates for the plain reference: the same skeletons just fighting
  { "Brutality I 1/0", 1, "Brutality" }, { "Rage I 1/0", 1, "Rage" }, { "Brutality II 1/0", 2, "Brutality" }, { "Armour Break I 1/0", 2, "Armour Break" },
  { "Feeding Frenzy I 1/0", 3, "Feeding Frenzy" }, { "Heavy Swing 1/0", 4, "Heavy Swing" }, { "Impale 1/0", 4, "Impale" }, { "Rage II 1/0", 4, "Rage" },
  { "Brutality III 1/0", 5, "Brutality" }, { "Rage III 1/0", 5, "Rage" }, { "Minion Mastery 1/0", 5, "Mastery" },
}

local function base(level, opts)
  opts = opts or {}
  local enemyLevel = math.min(level, 80)
  local spec = D.character(level, {
    customMods = "+100 to Spirit", -- the sceptre (every Skeletal Warrior sceptre base has 100 Spirit)
    questLevel = level,
    enemy = { boss = opts.boss or "None", level = enemyLevel, corpseLife = data.monsterLifeTable[enemyLevel] },
  })
  local asc, ascNames = {}, {}
  for _, a in ipairs(ASC) do if level >= a.level then for _, id in ipairs(a.ids) do asc[#asc + 1] = id end; ascNames[#ascNames + 1] = a.name end end
  local points = math.min(level - 1 + questPoints(level), #(opts.route or ROUTE))
  T.apply(spec, opts.route or ROUTE, points, 3, asc)
  local out = build.calcsTab.mainOutput
  return { level = level, enemyLevel = enemyLevel, points = points, pointsHave = level - 1 + questPoints(level), int = out.Int, ascNames = ascNames, monsterLife = data.monsterLifeTable[enemyLevel] }
end

local function plainReference(level, c)
  -- same character, the warriors with ordinary damage supports, all Spirit spent on warriors
  local wl = c.warriorLevel
  local n = sockets(wl)
  local chosen, families = {}, {}
  local function dps(list)
    D.groups{ warriorLevel = wl, warriors = 2, armySupports = list }
    local r = D.measure()
    local N = 2 + math.max(0, math.floor(r.spirit / r.warriorCost + 1e-9))
    return (r.attackDPS or 0) * N, N, r
  end
  for _ = 1, n do
    local best
    for _, cand in ipairs(PLAIN_SUPPORTS) do
      if level >= SUPPORT_TIER_LEVEL[cand[2]] and not families[cand[3]] then
        local list = { unpack(chosen) }; list[#list + 1] = cand[1]
        local d = dps(list)
        if not best or d > best.d then best = { d = d, cand = cand } end
      end
    end
    if not best then break end
    chosen[#chosen + 1] = best.cand[1]; families[best.cand[3]] = true
  end
  local d, N = dps(chosen)
  return d, N, chosen
end

local function checkpoint(level, opts, reuse)
  opts = opts or {}
  local c = reuse or base(level, opts) -- reuse: keep the character and tree, only change the gems
  -- gem levels the character can use
  c.warriorLevel = D.usableGemLevel("Skeletal Warrior", level, c.int)
  c.sockets = sockets(c.warriorLevel) + (opts.extraSockets or 0)
  local supports = {}
  for _, s in ipairs(opts.supports or DOMINO_SUPPORTS) do
    if #supports < c.sockets and level >= SUPPORT_TIER_LEVEL[s[2]] then supports[#supports + 1] = s[1] end
  end
  c.supports = supports
  c.loop = false
  for _, s in ipairs(supports) do if s:find("Minion Instability") then c.loop = true end end
  local trigger
  if level >= 26 and opts.trigger ~= false then
    c.comdLevel = D.usableGemLevel("Cast on Minion Death", level, c.int)
    local spells = {}
    c.ddLevel = opts.ddLevel or D.usableGemLevel("Detonate Dead", level, c.int)
    spells[#spells + 1] = string.format("Detonate Dead %d/0", c.ddLevel)
    if level >= 41 then
      c.cometLevel = D.usableGemLevel("Comet", level, c.int)
      spells[#spells + 1] = string.format("Comet %d/0", c.cometLevel)
    end
    trigger = { gemLevel = c.comdLevel, spells = spells }
  end
  D.groups{ warriorLevel = c.warriorLevel, warriors = 2, armySupports = supports, trigger = trigger, triggerSupports = opts.triggerSupports }
  local r = D.measure()
  c.r = r
  -- the measuring build holds one trigger gem per spell; the real plan is priced inside D.lap
  if c.loop then
    c.map1 = trigger and D.lap(r, "Detonate Dead", 1, true) or D.lap(r, nil, 0, true)
    c.boss1 = (trigger and c.cometLevel) and D.lap(r, "Comet", 1, false) or D.lap(r, nil, 0, false)
    if trigger then c.map2 = D.lap(r, "Detonate Dead", 2, true) end
    if trigger and c.cometLevel then c.boss2 = D.lap(r, "Comet", 2, false) end
  end
  return c
end

local function line(c)
  local r = c.r
  print(string.format("LEVEL %d  | passive points %d (have %d) | Int %d | Spirit %d | ascendancy: %s", c.level, c.points, c.pointsHave, c.int, r.spirit, #c.ascNames > 0 and table.concat(c.ascNames, ", ") or "none"))
  print(string.format("  warriors: skill level %d, %d support sockets: %s", c.warriorLevel, c.sockets, #c.supports > 0 and table.concat(c.supports, " + ") or "(no loop supports can drop yet)"))
  print(string.format("  warrior life %s | explosion %s = %s%% of a normal level-%d monster's life (%s) | extra warrior costs %s Spirit",
    f(r.life), f(r.explosion), r.explosion and f(100 * r.explosion / c.monsterLife, 1) or "-", c.enemyLevel, f(c.monsterLife), f(r.warriorCost)))
  if c.loop then
    print(string.format("  one lap: burn to Low Life %ss + revive %ss = %ss | mana %s, regen %s/s", f(r.timeToLowLife, 1), f(r.reviveTime, 1), f(r.cycle, 1), f(r.mana), f(r.manaRegen, 1)))
    if c.comdLevel then
      local dd = r["Detonate Dead"]
      print(string.format("  Cast on Minion Death level %d (+%d%% Energy): Detonate Dead level %d hits %s for %s mana%s",
        c.comdLevel, 3 * (c.comdLevel - 1), c.ddLevel, f(dd.hit), f(dd.mana),
        c.cometLevel and string.format("; Comet level %d hits %s for %s mana", c.cometLevel, f(r.Comet.hit), f(r.Comet.mana)) or ""))
    end
    local function show(tag, L)
      if not L then return end
      print(string.format("  %-34s %2d warriors, %4.1f casts wanted, mana pays %4.1f -> per lap: explosions %s + spell %s + sword hits %s = %s damage a second (lap model)",
        tag, L.N, L.wanted, L.paid, f(L.boom), f(L.spell), f(L.attacks), f(L.dps)))
    end
    show("map pack, 1 trigger gem (DD):", c.map1)
    show("map pack, 2 trigger gems (DD):", c.map2)
    show(c.cometLevel and "lone boss, 1 trigger gem (Comet):" or "lone boss (no boss spell yet):", c.boss1)
    show("lone boss, 2 trigger gems (Comet):", c.boss2)
  end
end

-- The lab's support pick, in socket order: the loop first, then Feeding Frenzy, then Essence Harvest (mana back on
-- every death), then Minion Mastery (+1 skill level). Crazed Minions is left out (see CLAUDE.md).
local LAB_SUPPORTS = {
  { "Minion Instability 1/0", 2 }, { "Infernal Legion I 1/0", 2 }, { "Feeding Frenzy I 1/0", 3 }, { "Essence Harvest 1/0", 3 }, { "Minion Mastery 1/0", 5 },
}
local function variant(c, opts)
  local copy = { level = c.level, enemyLevel = c.enemyLevel, points = c.points, pointsHave = c.pointsHave, int = c.int, ascNames = c.ascNames, monsterLife = c.monsterLife }
  return checkpoint(c.level, opts, copy)
end
local function short(tag, v)
  local function s(L) return L and string.format("%s (%d warriors, mana pays %.1f of %.1f casts)", f(L.dps), L.N, L.paid, L.wanted) or "-" end
  print(string.format("  %s: %s", tag, table.concat(v.supports, " + ")))
  print(string.format("      map pack 1 gem %s | 2 gems %s", s(v.map1), s(v.map2)))
  print(string.format("      lone boss 1 gem %s | 2 gems %s", s(v.boss1), s(v.boss2)))
end

local only = ARGV and ARGV[1] and tonumber(ARGV[1])
local results = {}
for _, level in ipairs(os.getenv("JOB2_LIB") and {} or LEVELS) do -- JOB2_LIB=1: only load the functions
  if not only or only == level then
    local c = checkpoint(level)
    line(c)
    if c.loop and level >= 33 then
      short("lab pick", variant(c, { supports = LAB_SUPPORTS }))
      if c.sockets < 5 then short("lab pick with one more socket (Jeweller's Orb on the sceptre)", variant(c, { supports = LAB_SUPPORTS, extraSockets = 1 })) end
    end
    local d, N, chosen = plainReference(level, c) -- re-uses the checkpoint's character
    print(string.format("  plain reference: %d warriors with %s just attacking = %s damage a second (PoB2)", N, table.concat(chosen, " + "), f(d)))
    results[#results + 1] = c
    D.tidy()
    print()
  end
end
JOB2 = { checkpoint = checkpoint, base = base, line = line, plainReference = plainReference, variant = variant, ROUTE = ROUTE, LAB_SUPPORTS = LAB_SUPPORTS }
