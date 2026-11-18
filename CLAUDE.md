# Oleg's Path of Exile 2 build lab

Oleg is new to ARPGs. This folder is his build lab: Path of Building 2 (PoB2) runs here without a window, so ideas
get numbers before he spends time in game. Last full check: 2026-11-17, game patch **0.5.5f** (0.5.5h on 17 Nov: bug fixes only). Version 1.0 lands
**11 December 2026** (passive tree overhaul, support gem rebalance, Infernalist rework: re-check everything then).

## Standing rules

- Start of each session: check the live patch (official patch notes forum) and say which one the advice is for.
  If a skill called poe2-guide is listed, load it first.
- poe2db.tw is the truth for skill, support, passive and item text. PoB2 is the truth for calculated numbers.
  Guides and videos are opinions, but learn from them.
- Builds must be Oleg's own. Study existing builds, borrow what is proven, and say what was borrowed.
- Before building on any skill, confirm it can be obtained in the live game. poe2db lists unreleased skills too.
  Warning signs: no Tier, a "From" line naming a [DNT] placeholder, missing from PoB2's data, no published build.
  (This went wrong once with Abyssal Pact.)
- Check a support's "Excluded Type" line. One that excludes Triggered cannot go in a trigger gem (Minion Pact).
- Sort every claim into "checked" (poe2db says it or PoB2 calculated it) or "needs a test in game". Never blur
  them. For each test say what to equip, what to press, what he should see.
- Run every idea next to a plain reference on the same character.
- Plain words. Lead with the answer. Short sentences. Explain a game word the first time. Numbers only when they
  change a decision. Read his instructions for what he means, not word for word.
- Free and local tools first. Never delete his files. Ask before overwriting.
- Toward the game: read-only. No keys or clicks sent to it, no reading its memory, no editing its files.

## Where the lab runs

- This repo was set up in a **Linux cloud session**, not on Oleg's Windows PC. From there the lab cannot see
  `C:\Users\joker\POE2_Tree_Helper`, the game's `logs\Client.txt`, or his own PoB2 app. The poe2tree MCP tools
  were not connected. To use those, run the session on the PC or share the folder with the session.
- On Windows the simplest route is WSL: `wsl bash setup.sh` in this folder (untested there).
- The engine folders (`pob2/`, `luajit/`, `luautf8/`, `lib/`, `.cache/`) are not in git. On a fresh machine run:

```bash
./setup.sh                       # clones PoB2, builds the pinned LuaJIT + lua-utf8, runs the smoke test (about 2 min)
./run.sh tests/smoke.lua         # 4 numbers must match (2191, 2988, 6573, 493 at PoB2 commit 601768bd)
./run.sh jobs/job1_route.lua     # shortest tree route + numbers at 20/40/60/80 points
./run.sh jobs/job2_checkpoints.lua            # campaign checkpoints 10/26/41/52/65/90
JOB2_LIB=1 ./run.sh jobs/export_build.lua     # rewrites builds/Domino Army.xml and the tree json
./run.sh tests/check_build.lua   # loads the saved build back into PoB2
python3 -I tools/poe2db.py Minion_Instability # a poe2db page as plain text (cached); --find "text" to search in it
```

## Files

- `harness.lua` Oleg's test harness (H.new, H.group, H.minionSkill, H.recalc). Use it for every test.
- `tools/treelib.lua` exact shortest route through chosen nodes (T.steiner), ordering, T.apply.
- `tools/domino.lua` the Domino Army test character (D.character, D.groups, D.measure) and the hand-made
  "lap model" (D.lap). Anything from D.lap must be reported as "worked out by hand on top of PoB2".
- `jobs/extension.lua` points 41 to 80. `jobs/explore_extension.lua` is the search that produced it (about 40 min).
- `reports/` text output of job 1 and job 2. `builds/` the PoB2 build, an import code, and the tree as json.

## PoB2 quirks learned the hard way

- LuaJIT must be the commit pinned in `pob2/Dockerfile`. PoB2's latest *release* is v0.23.1 (July 2026, before
  0.5.5); the lab uses the dev branch, which has the 0.5.5 data.
- Default target is a pinnacle boss with 50% fire/cold resistance: the 493 in the smoke test is half the raw hit.
- Call `D.tidy()` (wipeGlobalCache + collectgarbage) inside loops and reuse one build: a new build per test leaks
  until the machine kills the process at about 13 GB. HeadlessWrapper clears `arg`; copy it first.
- Item-granted skills (sceptre skeletons) get the highest level whose **level and Intelligence** requirement the
  character meets. PoB2 resets their minion count to 1, so the lab uses the bare "Skeletal Warrior" gem plus
  "+100 to Spirit" as a stand-in for the sceptre.
- Some start nodes differ per class: node 17973 is "Rapid Recharge" for a Sorceress and "Living Death" for a Witch.
- PoB2 does not model meta-gem Energy, the revive timer chain, Crazed Minions' bonus, or Explosive Demise (0).

## Build state: "Domino Army" (zero-button Witch, Infernalist)

**The plan.** Skeletal Warriors from a sceptre (first 2 free; each extra costs 27 Spirit at skill level 4, 23 at 8,
21 at 13, 20 at 17+) + Minion Instability + Infernal Legion I + Feeding Frenzy I. Cast on Minion Death (Tier 8,
30 Spirit) holding Detonate Dead for packs, Comet for bosses. One Skeletal Cleric from 52. Skip Vile Mending.

**Tree (job 1, done).** All six notables cost exactly **40 points** (clicking them in order in PoB2 costs 41).
Order: Living Death (point 10) > Necrotised Flesh (15) > Gravedigger (16) > Restless Dead (24) > Dynamism (32) >
Dead can Dance (40). Gravedigger moved ahead of Restless Dead because it is 1 point away once Necrotised Flesh is
taken. Every "+5 to any Attribute" goes to Intelligence. Points 41 to 80 are the lab's suggestion: Raw Destruction,
Empowering Remains, Emboldening Lead, Power of the Dead, then spell and minion damage clusters.

| points | warrior life | explosion (boss / no resistance) | revive delay |
|---|---|---|---|
| 0 | 2191 | 214 / 427 | 12.0 s |
| 20 | 3834 | 411 / 822 | 8.1 s |
| 40 | 4382 | 512 / 1025 | 6.9 s |
| 60 | 4382 | 1128 / 2256 | 6.9 s |
| 80 | 4601 | 1364 / 2727 | 6.9 s |

(Level 90 reference, warriors level 20, no gear. Points 41+ assume "minions deal increased damage" scales the
explosion, as PoB2 calculates. Needs a test.)

**Campaign checkpoints (job 2, done).** Damage a second, lap model, no gear, normal monsters of that level.
"Plain" = the same skeletons with ordinary damage supports, just fighting.

| level | loop on a pack | loop on a lone boss | plain |
|---|---|---|---|
| 10 | loop not possible yet | - | 53 |
| 26 | 177 | 160 | 187 |
| 41 | 1079 | 922 | 1320 |
| 52 | 2439 | 1832 | 2910 |
| 65 | 5889 (7651 with 2 trigger gems) | 3418 | 7149 |
| 90 | 23453 (32552 with lab supports + 2 gems) | 6806 | 16887 |

**What the lab says should change**

1. The loop is a pack-clearer. On a lone boss the same skeletons just fighting do about 1.5 to 2.5 times more.
   Boss rule: take Infernal Legion out and put a damage support in. Keep the loop for packs.
2. Level 10 has no loop: Tier 2 supports drop from about area level 16, the trigger gem from 26 at the earliest
   (it needs a level 8+ Uncut Spirit Gem; a guaranteed one may only come in Act 3). Level as plain skeletons.
3. Comet needs **300 Energy** (poe2db Energy rule: "+1 second to Total Cast Time" counts double). That is about
   4 to 6 skeleton deaths per Comet. A second Comet gem is not worth its Spirit.
4. Mana limits the trigger half: each triggered cast costs full mana in PoB2 (Comet 173, Detonate Dead 99 at 20).
   Essence Harvest (Tier 3, "Recoup 8% of your maximum Mana" per warrior death) fixes it; use it when running two
   trigger gems.
5. Crazed Minions: its bonus lasts 4 seconds after a revive and the burn takes 5.4, so the explosion misses it.
   Use Minion Mastery (Tier 5, +1 level) in that socket.
6. Sockets: a sceptre skill has 2 support sockets until skill level 10 (character level 36), 3 until 15 (level
   64), 4 until 20. A Jeweller's Orb on the sceptre adds one earlier.
7. Skeletal Cleric: its heal fights the burn and its revive may push the shared revive timer. Test before buying.
8. Detonate Dead needs an enemy corpse (dead skeletons do not count without the Sacrifice gem).
9. The army is gone for the whole revive delay, about half of every lap. The player has no cover then.

**Checked** (poe2db or PoB2): every gem and notable in the plan exists and is obtainable; Low Life is 35%;
Infernal Legion I burns 10% of max life a second (12% with Feeding Frenzy); Minion Instability deals 15% of max
life; the revive timer is shared by all minions; Cast on Minion Death gives 50 base Energy per death and each
gem keeps its own Energy; Minion Pact excludes Triggered; Infernal Legion II gives minions +20% fire resistance
(worse for the loop); Infernal Legion III cannot be obtained.

**Needs a test in game** (in this order)

1. Does an Instability explosion count as "Killed" for Cast on Minion Death? Watch the Energy bar.
2. Stopwatch: all warriors dead to all warriors back. PoB2 and poe2db data say 12 s base, the official 0.2.0e note
   says 7.5 s. The loop is about 27% stronger if it is 7.5 s.
3. Does Detonate Dead fire from the trigger gem and find an enemy corpse by itself?
4. Does a triggered spell cost mana, and what happens when mana is short?
5. Hover the sceptre's Skeletal Warrior skill: is its level capped by Intelligence as PoB2 says?
6. Energy per warrior death ("modified by the Minion's Power": 50, or 44 if it follows the warrior's 88% life).
7. Does a warrior killed in one hit skip the explosion? (One May 2026 forum report says yes.)
8. Does the Cleric's heal keep warriors above Low Life?

**Dead ends.** Abyssal Pact (unreleased; The Watcher in the Dark is a 1.0 preview item). Explosive Demise needs a
button and PoB2 returns 0. Pyromantic Pact would remove mana costs but can kill a hands-off character.

## Fast campaign plan (new Witch, fresh account, asked 2026-11-17)

Goal: fewest played hours. Level as a **minion Witch**, then switch on Domino Army (or not) at maps.
Full numbers: `reports/leveling_plan.txt` (`./run.sh jobs/leveling_plan.lua`), tree order: `jobs/leveling_tree.lua`.

- **Setup (borrowed** from Maxroll Minion Army Infernalist 0.5.5, GuyThatDies' 4:45 minion run, poe-vault**):**
  a sceptre (100 Spirit, 2 free Skeletal Warriors as bodyguards), Spirit into Skeletal Snipers (Fire Attunement +
  Rapid Attacks I, Feeding Frenzy I from Act 3), Raging Spirits (30 Spirit) after the King in the Mists, a fire
  spell (Flame Wall or Volcano) to summon them. Infernalist: Loyal Hellhound, Altered Flesh, Beidat's Will.
- **Lab (PoB2, no gear):** this army kills a normal monster of its own level in about 1 second from level 3 to 62.
  On paper extra warriors are about 30% stronger per Spirit than snipers, but the sceptre's warrior level is fixed
  by the item, so snipers (a gem he levels himself) are the dependable choice. One Raging Spirit is about one
  sniper's damage. Self-cast spells run out of mana (regen pays 20 to 35% of nonstop casting with no gear).
- **Tree, damage first:** Raw Destruction (5 points), Power of the Dead (8), Emboldening Lead (14), Deadly Swarm
  (23), Relentless Fallen (30). All attribute nodes to Intelligence (gem level needs 21 Int at level 10, 41 at
  22, 57 at 31, 74 at 41, 92 at 52). All of it sits on the Domino tree, so nothing is wasted.
- **Without a sceptre there is no Spirit at all** (PoB2 base is 0). The Witch starts with a wand; a Rattling Sceptre
  must drop or be bought from Una in Clearfell (random stock, unconfirmed: check her shop after each level).
- **Route facts (poe2db):** Act 1 area levels 1-15 (Geonor), Act 2 16-31 (Jamanra), Act 3 33-45 (Doryani), Act 4
  46-53 (Tavakai), three interludes 54-64, epilogue. Trial of the Sekhemas: Act 2, area level 22. Trial of Chaos:
  Act 3, area level 38. Take every permanent reward (12 point books, Spirit x3, resistances, life); skip quests
  that only give gems or orbs. A death resets the zone.
- **Gear rules:** movement speed boots (10% from item level 1, 15% at 16, 20% at 33), "+ to Level of all Minion
  Skills" on sceptre (item level 2) or amulet (5), resistance runes and rings as each act adds -10%.
- Opinion only, not confirmed: racers finish in 4.5 to 5 hours, first-timers about 20 to 25 (old articles); stay
  within 3 to 4 levels of the area; no experience loss on death in the campaign; hold dodge to sprint.
- The fastest timed runs use Essence Drain + Contagion with Bonestorm for bosses (4:32). Harder to play.

## Jobs

1. Tree route: done (see above, `reports/job1_route.txt`, `builds/`).
2. Campaign checkpoints: done (`reports/job2_checkpoints.txt`).
3. When Oleg pastes an item (Ctrl+C in game) or a PoB code: load it, answer equip or skip in two lines.
4. On or after 11 December 2026: check everything against the 1.0 patch notes. Rebuild with `./setup.sh` first so
   PoB2 has the 1.0 tree, re-run the smoke test, job 1 and job 2, and report what broke and what opened up.

Not done yet: the tree file is a plain list of node ids, not Oleg's tree-helper format (no saved tree was
readable from the cloud). The fact-check of existing builds and of campaign unlock levels did not finish; the
support tier drop levels (16 / 33 / 45 / 55) come from a poe2wiki search snippet only.
