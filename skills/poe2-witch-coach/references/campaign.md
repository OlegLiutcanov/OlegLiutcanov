# Fast campaign plan: minion Witch (patch 0.5.5h, November 2026)

**Summary.** Level as a minion Witch: a sceptre gives 100 Spirit and two free Skeletal Warriors as bodyguards. Spend the Spirit on Skeletal Snipers, add Raging Spirits after the King in the Mists, and cast Flame Wall to summon them. Take every permanent reward. Skip quests that only give gems or orbs. Infernalist ascendancy. Switch to Domino Army (`build.md`) when he wants to, from about level 26 at the earliest; the lab says it pays off later.

Marks: **[checked]** = poe2db, PoB2 or patch notes. **[opinion]** = guides or players. **[test]** = needs a check in game.

Borrowed: the sniper + Raging Spirits setup and the ascendancy order come from Maxroll's "Minion Army Infernalist" guide (0.5.5), a self-reported 4:45 minion campaign run (GuyThatDies, Mobalytics) and poe-vault's minion levelling guide. The levelling tree order, the support picks and the kill times come from PoB2 runs in Oleg's lab.

## Time expectations

- Racers report 4.5 to 5 hours on 0.5 **[opinion, self-reported]**. A first run is far longer: old articles say 20 to 25 hours **[opinion, written for 0.3]**. Don't promise a number.

## Character creation

- **Class:** Witch.
- **League:** the current main softcore trade league (in November 2026: "Runes of Aldur"). Avoid "Hardcore" (one death ends the character there) and "SSF" (solo self-found: no trading). All 0.5 leagues end when 1.0 arrives on 11 December 2026.

## Minute one to the first sceptre

1. The first zombie drops a wand **(Withered Wand: grants Chaos Bolt [checked])**. Equip it.
2. The chest in the middle of the first zone has the first uncut skill gem **[opinion: Maxroll]**. An uncut gem is a blank gem you turn into any skill of its tier or lower. Make it **Unearth** (raises bone minions from corpses; needs no Spirit).
3. Next uncut skill gem: **Flame Wall** (Tier 1 fire spell [checked]). It is needed for Raging Spirits later. Snipers' arrows that fly through it also gain added fire damage: poe2db says "Any Projectiles fired through the wall by you and Allies deal added Fire damage" **[checked text; whether minion arrows count: test]**.
4. **Get a sceptre.** In Clearfell town, open Una's shop and type `sceptre` in the search box **[opinion: Maxroll says Una sells sceptres; stock is random]**. Buy any sceptre whose text says "Grants Skill: Skeletal Warrior". Rattling Sceptre has no requirements; Lupine Sceptre needs level 12 and 24 Intelligence **[checked]**. Every sceptre base has 100 Spirit **[checked]**. Shops restock when you level up **[opinion]**, so look again after each level until you have one. Zarka in the Act 2 town (Ardura Caravan) also sells sceptres **[opinion: Maxroll]**.
   - **Without a sceptre the Witch has zero Spirit** until the King in the Mists **[checked: PoB2 base Spirit is 0]**, so no Skeletal Sniper can be summoned. Keep using Unearth, Chaos Bolt and Flame Wall.
   - The King's +30 Spirit pays for **Raging Spirits** (30) but not for a sniper (41 or more at gem level 4) **[checked]**. Snipers need the sceptre.
   - If both weapons won't fit together, keep the sceptre.
5. With the sceptre on: the next uncut skill gem becomes **Skeletal Sniper** (Tier 1 [checked]). It is not in the Witch's own tab of the gem-cutting window; Maxroll says to look in the Sorceress tab **[opinion]**.
6. **[test] Ask Oleg to hover the sceptre's Skeletal Warrior skill and report its level.** Sources disagree on whether the item fixes that level or it rises with the character. The answer decides how often he should replace the sceptre.

## The army and its Spirit costs [checked: poe2db / PoB2]

- Skeletal Warrior (from the sceptre): the first 2 cost nothing. Each extra one costs, by skill level: 60 (1), 44 (2), 33 (3), 27 (4), 25 (5-6), 23 (7-9), 22 (10-12), 21 (13-16), 20 (17-20).
- Skeletal Sniper: 90 Spirit at gem level 1, 65 at 2, 50 at 3, 41 at 4, 37 at 5, 35 at 7-9, 33 at 11, 31 at 13.
- Raging Spirits (Tier 4 spirit gem, 30 Spirit): fire spells he casts himself summon flaming skulls that last 5 seconds; at most 10 at once and 2 per cast **[checked]**.
- What PoB2 says (no gear, a normal monster of his level): this army kills one in about 0.5 to 2 seconds from level 3 to level 62 **[checked: PoB2]**. Self-cast spells can't keep up: with no gear, mana regeneration pays for only 20 to 35% of nonstop casting **[checked: PoB2]**.
- On paper, extra warriors give about 30% more damage per Spirit than snipers **[checked: PoB2]**. Snipers are still the safer pick, because a sniper is a gem he levels himself, while the sceptre's warrior level may be stuck (see the test above). If the test shows warriors rise with his level, extra warriors are the better buy.

**Supports** (PoB2, extra damage per support for one minion; pick in this order):

| When | On snipers | Notes |
|---|---|---|
| From the start | **Fire Attunement** (about +35%), **Rapid Attacks I** (+15%) | [checked: PoB2] |
| Act 2 (tier 2 supports drop from about area level 16 [opinion]) | **Brutality II replaces Fire Attunement** (+33 to 38%). Never use both: Brutality boosts physical damage "at the expense of all other Damage Types", which wipes out Fire Attunement's extra fire | [checked: poe2db text, PoB2] |
| Act 3, third socket (tier 3 from about area level 33 [opinion]) | add **Feeding Frenzy I** (+36 to 43%; minions also take 20% more damage) | [checked] |
| About level 55 (tier 5 [opinion]) | Minion Mastery (+1 level) replaces Rapid Attacks I | [checked text] |

- **If the main damage is Command: Gas Arrow** (the sniper's button skill: a gas cloud that explodes when an ignited enemy touches it or a Detonator skill hits it [checked: poe2db]), area beats damage. At level 15 the explosion already deals 2 to 3 times a normal monster's life **[checked: PoB2, jobs/gas_arrow_packs.lua]**. Snipers: **Magnified Area I + Bidding I** (Bidding: 20% more command damage, 20% faster command cooldown). Never Concentrated Area: it halves the area for 30% more damage that is wasted. For big packs, a 3rd sniper beats Raging Spirits (+50% clouds). Next tree notable: **Growing Swarm** (minion area + cooldown). Lab pack score vs Fire Attunement + Concentrated Area: Magnified + Bidding +278%, 3 snipers +468%, Growing Swarm a further +32% **[hand, on top of PoB2]**.
- Brutality and Infernal Legion each need +5 Strength **[checked]**; the Witch starts with 7. If a support's text turns red, put one attribute node into Strength.
- PoB2 says **Multishot I cuts a sniper's single-target damage by about half** **[checked: PoB2]**. Guides use it early for packs **[opinion]**. Skip it.
- Support sockets: a skill starts with 2 **[opinion]**. A Lesser Jeweller's Orb sets 3, a Greater sets 4 **[checked: poe2db]**. The first Lesser is guaranteed in Act 3: click the basket at the Orok campfire in the Sandswept Marsh **[checked: poe2db quest table]**.

## Passive tree for levelling (damage first) [checked: PoB2 search]

Every "+5 to any Attribute" node goes to **Intelligence**. Gem levels need Intelligence as well as character level: about 21 at character level 10, 41 at 22, 57 at 31, 74 at 41 and 92 at 52 **[checked: poe2db gem tables]**.

Order by passive point: 1 Spell and Minion Damage (the small node next to the start) · 2-4 Attribute (Int) · **5 Raw Destruction** · 6 Spell and Minion Damage · 7 Minion Damage · **8 Power of the Dead** · 9-11 Attribute · 12-13 Spell and Minion Damage · **14 Emboldening Lead** (only while he himself has hit something recently) · 15 Spell and Minion Damage · **16 Empowering Remains** · 17-21 Attribute · 22 Minion Damage · **23 Deadly Swarm** · 24-25 Attribute · 26-28 Minion Life nodes · 29 Minion Damage · **30 Relentless Fallen** · 31-33 Attribute · 34-35 Minion Damage · 36 Command Skill Damage · **37 At your Command** · 38-39 Minion Attack and Cast Speed · **40 Restless Dead** · 41-43 Minion Damage · **44 Growing Swarm**.

- 33 of these 44 points are also on the Domino Army route. Switching later means refunding about 11 points (Deadly Swarm, At your Command, Command Skill Damage, some small nodes).
- He gets 1 point per level after level 1 plus 24 from quest books (12 books x 2) **[checked: PoB2 / poe2db]**. The book points are called "Weapon Set Passive Skill Points" but are spent like normal points **[checked: poe2db]**.

## Ascendancy (Infernalist)

Eight ascendancy points in all, two per trial; six can be earned before maps **[checked: poe2db Ascendancy table]**. Choosing Infernalist can't be changed later; its nodes can be respecced **[opinion: poe2db community note]**.

- **Points 1-2: Trial of the Sekhemas**, Act 2, area level 22 **[checked]**. The key, Balbala's Barya, comes from the quest "Ascent to Power" (Balbala, Traitor's Passage) **[checked]**. Guides say go at about level 24 to 28 **[opinion]**. The trial fails if **Honour** runs out; Honour drops when he gets hit **[opinion]**. Teach him that first.
- **Points 3-4: Trial of Chaos**, Act 3 (Temple of Chaos, area level 38), with the Chimeral Inscribed Ultimatum from the Chimeral Wetlands (4 trials) **[checked]**. Or two floors of the Sekhemas with a Djinn Barya (level 38).
- **Points 5-6: Sekhemas, three floors** (level 60). In the interludes, **Elzarah, the Cobra Lord** (Sel Khari Sanctuary) drops a one-time guaranteed Djinn Barya for it **[checked: poe2db]**.
- Points 7-8: endgame (Sekhemas four floors or the Trial of Chaos at level 80) **[checked]**.
- Picks (borrowed from Maxroll and poe-vault): 1st **Loyal Hellhound** (via the small Minion Life node; a reviving fire hound that ignites enemies near it). 2nd **Altered Flesh** (some physical, lightning and cold damage taken as other types). 3rd **Beidat's Will** (reserves 25% of life; +1 Spirit per 25 maximum life). Guides take Beidat's Hand last; the lab leaves the last two points open because it reserves another 25% of life.

## The route and the permanent rewards [checked unless marked]

Do every reward below; they last forever. Skip side quests that only give gems or orbs. Two side zones hold Spirit and must not be skipped: **Freythorn** (Act 1) and **Azak Bog** (Act 3).

**Act 1** (area levels 1 to 15; final boss Count Geonor; guides say be level 14 for him [opinion])
- Clearfell: Beira gives +10% cold resistance.
- Hunting Grounds: Crowbell gives +2 passive points.
- **Freythorn** (side zone, area level 11): the King in the Mists gives **+30 Spirit** [checked] **and an Uncut Spirit Gem (level 4)** [poe2db's Act 1 page and Maxroll say so; poe2db's Freythorn page lists only the Spirit]. Make the gem **Raging Spirits**.
- Ogham Farmlands: Una's Lute gives +2 points. Ogham Manor: Candlemass gives +20 life.

**Act 2** (16 to 31; final boss Jamanra; no zones at area level 24 to 27, so keep up with the levels)
- Keth: Kabala +2 points. Deshar: The Final Letter +2 points. Spires of Deshar: Sisters of Garukhan +10% lightning resistance.
- Valley of the Titans, Medallion (a choice): either option gives +1 charm slot. Pick "30% increased Charm Effect Duration" [lab pick].
- Trial of the Sekhemas here (above). An Uncut Spirit Gem is in the Lost City [opinion: Maxroll].

**Act 3** (33 to 45; final boss Doryani)
- Jungle Ruins: Mighty Silverfist +2 points. Jiquani's Machinarium: Blackjaw +10% fire resistance. Aggorat: Blood Sacrifice +2 points.
- **Azak Bog** (side zone): Ignagduk gives **+30 Spirit** (and a level 10 Uncut Spirit Gem [opinion: Maxroll]).
- **Venom Crypts, Venom Draught: a permanent choice that can't be changed.** Options: 25% stun threshold, 30% ailment threshold, or 25% mana regeneration. Pick **25% increased Mana Regeneration Rate** [lab pick: mana is what runs out for this Witch, now and later in Domino Army].
- Sandswept Marsh: the Orok campfire basket gives a Lesser Jeweller's Orb. Trial of Chaos here.

**Act 4** (46 to 53; final boss Tavakai; the islands can be done in any order, Ngakanu and Arastas last [opinion])
- Quest "Dark Mists" (Mist-shrouded Tome) +2 points. Trial of the Ancestors (Tattoo of Hinekora) +2 points.
- Eye of Hinekora, Silent Hall: 5% increased maximum mana. Tribal Medicine (choice): pick "30% increased Global Armour, Evasion and Energy Shield" [lab pick: simplest].
- Halls of the Dead, three tests: each offers +5 to an attribute or +5% to one resistance. Take the resistance unless Intelligence is short for the next gem level [lab pick].
- Abandoned Prison, Goddess of Justice (choice): pick 30% increased Life Recovery from Flasks [lab pick].
- Isle of Kin, Blind Beast: the reward is disputed. poe2db says a potion with +10% lightning resistance; Maxroll says a Blank Greater Rune.

**Interludes 1 to 3** (area levels about 54 to 64; scaling unconfirmed)
- Wolvenhold: Oswin +2 points. Save about 20,000 gold for the Ferryman's one-time Greater Runes in Interlude 1 [opinion: Maxroll].
- Khari Crossing: Akthi and Anundr +2 points; Molten Shrine 5% increased maximum life.
- Qimah, Seven Pillars (choice): pick +5% to all Elemental Resistances [lab pick].
- Kriar Village: **Lythara gives +40 Spirit** (on the main path).
- Howling Caves: Abominable Yeti +2 points.

**Epilogue:** Kingsmarch, Siege of Oriath (talk to the Hooded One) +2 points.

Total Spirit from quests: 30 + 30 + 40 = 100, plus 100 from the sceptre **[checked]**.

## Speed habits

- **Hold the dodge key to sprint** **[checked: official 0.5.0 notes]**.
- Teleport between checkpoints inside a zone **[checked: official announcement]**.
- Kill what is in the way, not every pack. Stay within about 3 to 4 levels of the area level; if he falls further behind, clear one zone fully **[opinion: no official rule found]**.
- A death respawns the zone's monsters **[opinion: Maxroll]**. Whether a campaign death costs experience is unconfirmed **[test: note the experience bar before and after a death]**.
- First purchase: **boots with movement speed** (10% possible from item level 1, 15% from 16, 20% from 33 [checked: poe2db]).
- In vendor search boxes type `nion sk|movem`. It highlights "+ to Level of all Minion Skills" and movement speed [opinion: from a speed guide].
- Each act from Act 2 lowers all elemental resistances by 10%, down to -60% in the endgame **[checked: PoB2]**. Fix resistances at each act's town (see `gear.md`).
- A loot filter hides junk drops; guides call it essential [opinion]. FilterBlade.xyz is free; check it supports the current patch.

## Bosses and danger points

- Act 1 single-target is weak until Raging Spirits and the second sniper arrive [opinion]. Put Flame Wall between the snipers and the boss.
- Jamanra (end of Act 2): guides say be about level 29 to 31 and farm if needed [opinion].
- If a boss keeps killing him, the fix order is: resistances to the act's need, more life, then minion damage. Ask what killed him (the hit's colour or element shows on the death screen).
