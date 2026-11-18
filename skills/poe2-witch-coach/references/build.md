# Domino Army (Oleg's own build, patch 0.5.5h)

**Summary.** A zero-button Witch. Skeletal Warriors from the sceptre burn themselves down (Infernal Legion), explode when they reach Low Life (Minion Instability), and each death charges a trigger gem (Cast on Minion Death) that casts a spell for free. It is strong on packs late in the game, weaker on lone bosses, and several key interactions still need a test in game. The lab says: level with the plain army (`campaign.md`) and switch when he wants the zero-button style, ideally after the tests below.

Marks: **[checked]** = poe2db, PoB2 or patch notes. **[hand]** = worked out by hand on top of PoB2 numbers. **[test]** = needs a check in game.

## How a fight goes

1. The warriors walk into a pack. Infernal Legion I makes them burn for 10% of their own maximum life each second and ignite enemies near them **[checked]**.
2. At 35% life ("Low Life") each warrior explodes for 15% of its maximum life as fire damage (Minion Instability) **[checked]**.
3. Each death gives Energy to Cast on Minion Death. When the Energy bar is full, it casts the spell inside it **[checked text]**; whether an explosion death counts **[test 1]**.
4. All the warriors come back after the shared revive timer **[checked: one timer for all]**; how long it really is **[test 2]**.

## Gems

- **Army (on the sceptre's Skeletal Warrior skill):** Minion Instability + Infernal Legion I + Feeding Frenzy I. 4th socket: Minion Mastery, not Crazed Minions. Crazed Minions' bonus lasts 4 seconds after a revive, and the burn takes about 5.4 seconds, so the explosion misses it **[hand]**. 5th socket, or when running two trigger gems: Essence Harvest ("Recoup 8% of your maximum Mana" per warrior death **[checked]**).
- **Trigger: Cast on Minion Death** (Tier 8, 30 Spirit) **[checked]**. Inside it: **Detonate Dead** for packs (explodes an enemy corpse; dead skeletons are not corpses **[checked]**) and **Comet** for bosses.
  - Detonate Dead needs 80 Energy, about 1 to 2 warrior deaths **[hand]**.
  - Comet needs **300 Energy** (1 second cast time, plus its "+1 second to Total Cast Time", which counts double for Energy **[checked: poe2db Energy rule]**). That is about 4 to 6 deaths **[hand]**. A second Comet gem is not worth its Spirit.
  - Energy per death: 50 base, plus 3% per gem level, plus tree bonuses **[checked]**; changed by "the Minion's Power" **[test 6]**.
- **Mana is the limit.** PoB2 charges full mana for each triggered cast (Comet 173 and Detonate Dead 99 at gem level 20) **[checked: PoB2]**; whether the game does **[test 4]**.
- Minion Pact can't go inside the trigger gem (it excludes Triggered) **[checked]**. Infernal Legion II gives minions +20% fire resistance, which slows the burn **[checked]**. Infernal Legion III can't be obtained **[checked]**.
- **Boss rule:** on a lone boss, take Infernal Legion out and put a damage support in. The same skeletons just fighting do 1.5 to 2.5 times more there **[hand]**.

## Tree: six notables, 40 points [checked: exact shortest route in PoB2]

Every "+5 to any Attribute" goes to Intelligence. The notables arrive at these points:

1. **Living Death** (point 10): minions have 15% increased maximum life and revive 15% faster. This is the Witch version of the node by her start; a second "Living Death" far away gives minion resistances, which is bad for the burn.
2. **Necrotised Flesh** (15): minions have 40% increased maximum life and 10% reduced life recovery.
3. **Gravedigger** (16): revive 15% faster, recover 2% of your life when a minion revives.
4. **Restless Dead** (24): revive 25% faster.
5. **Dynamism** (32): 40% increased damage if he triggered a skill recently; meta skills gain 15% increased Energy.
6. **Dead can Dance** (40): minions have 25% increased maximum life and evasion.

Skip Vile Mending: its regeneration fights the burn. Points 41 to 80 (lab suggestion): Raw Destruction, Empowering Remains, Emboldening Lead, Power of the Dead, then spell and minion damage clusters.

Warriors at level 90 with skill level 20 and no gear **[checked: PoB2]**:

| points | warrior life | explosion vs a boss / vs a normal monster | revive delay |
|---|---|---|---|
| 20 | 3834 | 411 / 822 | 8.1 s |
| 40 | 4382 | 512 / 1025 | 6.9 s |
| 80 | 4601 | 1364 / 2727 | 6.9 s |

## Campaign checkpoints [hand, on top of PoB2; no gear, normal monsters]

Damage per second. "Plain" = the same skeletons with ordinary damage supports.

| level | loop on a pack | loop on a lone boss | plain |
|---|---|---|---|
| 26 | 177 | 160 | 187 |
| 41 | 1079 | 922 | 1320 |
| 52 | 2439 | 1832 | 2910 |
| 65 | 5889 | 3418 | 7149 |
| 90 | 23453 (32552 with the lab supports and 2 trigger gems) | 6806 | 16887 |

Reading: the loop only pulls ahead on packs late: from about level 65 with two trigger gems, clearly by level 90. During the campaign plain skeletons are as good or better. The army is gone for the whole revive delay, about half of every lap, so the player has no cover then.

When the loop can start: Minion Instability and Infernal Legion are Tier 2 supports (from about area level 16 [opinion]). Cast on Minion Death needs a level 8 or higher Uncut Spirit Gem and character level 26 **[checked]**. A sceptre skill has 2 support sockets until skill level 10, 3 until 15, then 4 **[checked]**; a Jeweller's Orb adds one earlier.

## Tests in game (equip / press / see)

1. **Does an explosion count as a kill for Cast on Minion Death?** Equip the sceptre with Minion Instability and Infernal Legion I on Skeletal Warrior, and Cast on Minion Death holding Detonate Dead. Walk into a pack and do nothing. **See:** the Energy shown on the Cast on Minion Death buff rises each time a warrior explodes. If it never rises, the loop does not work.
2. **Revive time.** Same setup. Start a stopwatch when the last warrior explodes and stop it when they reappear. PoB2 data says 12 seconds base; an official note from 0.2.0 says 7.5 **[conflict]**. At 7.5 the loop is about 27% stronger **[hand]**.
3. **Does Detonate Dead fire by itself?** In a pack, watch for enemy corpses exploding with no button pressed.
4. **Do triggered spells cost mana?** Watch the mana globe each time the trigger fires. What happens at empty mana?
5. **Sceptre skill level.** Hover the Skeletal Warrior skill and compare its level with the character level (and with the Intelligence requirement).
6. **Energy per death.** With Comet inside, count the warrior deaths until it fires (expect about 4 to 6).
7. **One-hit deaths.** Does a warrior killed in one hit (never at Low Life) explode? A May 2026 forum report says no.
8. **Skeletal Cleric.** Does its heal keep warriors above Low Life (which would stop the loop)? Try it before spending Spirit on it.

## Dead ends

- Abyssal Pact: not obtainable in 0.5 (the unique staff The Watcher in the Dark is a 1.0 preview item).
- Explosive Demise (Skeletal Arsonist) needs a button press, and PoB2 can't calculate it.
- Pyromantic Pact (Infernalist) would remove mana costs, but it can kill a hands-off character.
