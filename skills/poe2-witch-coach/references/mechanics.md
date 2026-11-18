# Mechanics, in the order Oleg needs them

**Summary.** Teach one of these when the game is about to make it matter, not before. Each entry has a plain explanation you can reuse, and why it matters for his Witch. Marks: **[checked]** = poe2db or PoB2 confirmed it in the lab; **[general]** = long-standing game rule, worth confirming on poe2db if it matters for a decision.

## First hour (Act 1)

**Skill gems and uncut gems.** Skills come from gems. An uncut gem is a blank: he picks which skill it becomes, from any skill of that gem's tier or lower, and it can't be undone. A gem's level sets its power. Each level needs a character level *and* some Intelligence: gem level 4 needs level 10 and 21 Int, level 7 needs 22 and 41, level 9 needs 31 and 57, level 11 needs 41 and 74, level 13 needs 52 and 92 **[checked: poe2db]**. Why it matters: that is why every attribute node goes to Intelligence.

**Support gems.** A support sits in a skill's support socket and changes that skill. Only one support of each "Category" fits in one skill **[checked: poe2db]**. A skill starts with 2 sockets **[opinion]**; a Lesser Jeweller's Orb sets 3, a Greater sets 4 **[checked]**. Why: the levelling sniper wants Fire Attunement and Rapid Attacks I first.

**Spirit.** A second pool beside mana, used only by always-on skills (minions, auras, the trigger gem). Each always-on skill reserves part of it while it is on. The Witch starts with 0; the sceptre gives 100, and three campaign bosses give 30, 30 and 40 **[checked]**. Why: Spirit decides how many minions he can keep. Taking the sceptre off removes its 100 Spirit, and the minions it was paying for disappear.

**Minions.** Allies the skills summon. *Persistent* minions stay until they die (snipers, warriors). *Reviving* ones come back by themselves after a short delay, and all reviving minions share one timer that each new death pushes back **[checked: poe2db keyword and official 0.2.0 notes]**. *Temporary* ones vanish after a few seconds (Raging Spirits: 5 seconds **[checked]**). Why: the army is his damage and his shield.

**Passive points and attribute nodes.** One point per level after level 1, plus 24 from quest books **[checked]**. Small nodes give small bonuses; *notables* (bigger named nodes) give big ones; *keystones* change a rule. "+5 to any Attribute" nodes let him choose Strength, Dexterity or Intelligence **[checked: PoB2]**. Respecs cost gold **[opinion]**.

**Resistances.** Fire, cold and lightning ("elemental") resistance each cut that damage type by the percent shown. The cap is 75% **[checked: PoB2]**. From Act 2 each act lowers all three by 10%, down to -60% in the endgame **[checked: PoB2]**. A boss hit that does 1000 fire damage does 250 at 75% resistance and 1600 at -60% **[general]**. Why: uncapped resistances are the most common reason new players die. Chaos resistance is separate and is not lowered by acts **[general]**.

## Act 2

**Ascendancy and Honour.** The Trial of the Sekhemas (Act 2) gives the first 2 ascendancy points and makes the Witch an Infernalist **[checked]**. In the trial a second bar called Honour drops when he is hit; at zero the run fails **[opinion]**. Why: teach him to let the minions tank and to keep moving.

**Increased versus more.** All "increased" bonuses of one kind are added together first; each "more" bonus then multiplies on top **[checked: this is how PoB2 calculates]**. Three "+20% increased" nodes make +60% (1.6 times). Two "20% more" supports make 1.2 x 1.2 = 1.44 times, and stack on top of the increased. Why: supports and gem levels (more) usually beat one more small node (increased) once he already has a lot of increased.

**Items: rarity, item level, mods.** White (normal), blue (magic, up to 2 mods), yellow (rare, up to 6), orange (unique, fixed mods) **[general]**. An item's *item level* (where it dropped) decides which mods it can have; its *required level* decides when he can wear it **[general]**. "+1 to Level of all Minion Skills" can appear on a sceptre from item level 2 and on an amulet from item level 5 **[checked: poe2db]**.

## Act 3

**Low Life and ignite.** Low Life means 35% of maximum life or less **[checked: poe2db]**. Ignite is fire damage over time **[general]**. Why: Domino Army works by burning its own skeletons to Low Life, where they explode.

**Mana.** Spells cost mana; mana regenerates about 4% of its maximum each second **[checked: PoB2]**. With no gear, regeneration pays for only 20 to 35% of nonstop casting **[checked: PoB2]**. Minions attack for free. Why: this is why a minion Witch levels faster than a spell Witch with no gear, and why the Venom Crypts mana choice is the lab pick.

**Weapon sets.** The 24 points from quest books are "Weapon Set Passive Skill Points": they can differ between his two weapon sets, but they are spent like normal points **[checked: poe2db]**.

## Act 4 and the interludes

**Meta gems and Energy** (only for Domino Army). A meta gem holds other skills. Cast on Minion Death gains Energy when his persistent minions die and casts the spells inside it when the Energy is full **[checked]**. How much Energy a spell needs depends on its cast time, and "+ to Total Cast Time" counts double: Detonate Dead needs 80, Comet 300 **[checked rule, hand arithmetic]**.

**Defences.** Life: when it hits zero he dies. Energy shield (ES): a blue layer that takes damage first and recharges when he isn't hit for a while **[general]**. Armour cuts physical hits; evasion makes hits miss **[general]**. Why: a Witch has little of any of this, so resistances and life come first.

## After the campaign

**Endgame words.** Maps, waystones, the atlas, map tiers, pinnacle bosses: these change between patches, and a lot at 1.0. Look up the current patch before teaching them; `endgame.md` says how.

**Crafting currency** (confirm names and effects on poe2db's currency pages before he spends anything) **[general]**: Orb of Transmutation turns a white item blue with one mod. Orb of Augmentation adds a mod to a blue item. Regal Orb turns blue into yellow and adds a mod. Exalted Orb adds a mod to a yellow item. Chaos Orb swaps one random mod for a new one. Divine Orb rerolls the numbers within the mods. Jeweller's Orbs add support sockets to skill gems.
