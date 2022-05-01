# Extended Block Shapes Mod

如果看不懂英文，可以阅读[中文版文档](README.md)。

This mod adds stairs, slabs, fences, fence gates, button and pressure plates for many vanilla blocks, along with vertical slabs, vertical stairs, quarter pieces and vertical quarter pieces that do not exist in vanilla Minecraft. A list of the contents can be found in [this file](BlockList.md).

At present this mod supports Minecraft Java Edition 1.18.2, 1.18.1, 1.17.1 and 1.16.5. Please install the relevant mod according to your MC version (nearby versions compatible in theory, **except 1.16-1.16.1**) and requires Fabric API and BRRP. Besides, Mod Menu is usually needed (but not required) to open mod config screen.

**Notice: Since mod version 1.4.0, the mod requires ARRP (Advanced Runtime Resource Pack) to run, so you must have this mod installed. If you install Extended Block Shapes without installing ARRP, the game instance will refuse to run.**

**Notice: Since mod version 1.5.0, the mod requires BRRP (Better Runtime Resource Pack) to run, so you must have this mod installed. BRRP has ARRP nested; you do not have to install ARRP again, or some issues may be raised.**

<s>If you update the mod from 1.2.3 to ≥1.2.4, please remove all _plank wall_ and _clay wall_ blocks from your worlds! As of 1.2.4, plank walls and clay walls are no longer a feature of this mod.</s> Since 1.5.0, plank walls and clay walls are re-added.

[Click here](UpdateLog.md) for previous update logs. Welcome to join Tencent QQ group **587928350** or KaiHeiLa channel invitation code **KlFS0n** to experience the latest update of this mod.

## Features

### Blocks

This mod provides amounts of variants of many blocks. For example, wool has wool stairs, wool slab, wool wall, wool pressure plate, etc. Vertical stairs, vertical slab, quarter piece and vertical quarter piece are added by the mod; these blocks, like regular stairs and slabs, are waterloggable.

The hardness, mining tools and mining time of all stairs, slabs, fences and walls are equal to their base blocks, which is the same behaviour of vanilla, instead of related to their volume. For example, a plank slab and a plank block will take the same time to mine.

In vanilla, the hardness of pressure plates and buttons equals to 1/4 or 1/3 of their basic blocks. All pressure plates and buttons added in this mod have the same hardness as 1/4 of their base blocks. (Bedrock blocks are invulnerable in Survival Mode, but bedrock pressure plates and bedrock buttons drop normally when losing blocks relied on.) This mod simply extends buttons: soft buttons (wool buttons, snow buttons) trigger 60 ticks (3 seconds); hard buttons (obsidian buttons, bedrock buttons) trigger 5 ticks (1/4 second). Like in vanilla Minecraft, all buttons and pressure plates have no collision box.

This mod also adds "double smooth stone slab" and "petrified oak planks" blocks, respectively crafted from smooth stone slabs and petrified oak slabs. Vanilla smooth stone blocks can still be crafted into smooth stone slabs.

Blocks added by this mod inherit most features of their base blocks:

- All blocks based on the wool blocks and planks are flammable (burning time equals vanilla full blocks).
- Wool blocks and plank blocks can be used as fuels. Slabs and vertical slabs when use as fuel can consume half the time of their base blocks. Buttons used as fuels smelts about 1/3 the time of their base blocks. Quarter pieces and vertical quarter pieces when used as fuels smelts 1/4 the time of the base blocks.
- All wool blocks, no matter whether they occupy the space of a whole block, can block sculk sensors.
- All netherrack blocks are infiniburn. Bedrock blocks are infiniburn in the end.
- All blocks of endstone, obsidian and crying obsidian are immune to ender dragons.
- Netherite blocks and ancient debris blocks, when dropped as items, resist fire and lava.
- Blocks base on gold blocks and raw gold blocks can be admired by piglins. Breaking blocks base on gold blocks, raw gold blocks and gilded blackstone irritates piglins.
- A snow stairs or snow slab, when placed on a grass block, if it just covers the whole top of the grass block, makes it snowy, just like covered by a snow block or snow.
- Small dripleaves and large dripleaves can be placed on blocks base on moss blocks and clay (only building blocks).
- Blocks base on pumpkin, melon, moss block, shroomlight, nether wart block and warped wart block can be composted.

### Crafting and smelting

The crafting recipes for all blocks in this mod are similar to those in vanilla. Stone and metal blocks can also be made with a stonecutter. Stairs can be crafted 3:2 in a crafting table (ingredient:result) and 1:1 in the stone cutter. Slabs and vertical slabs are craftable 1:2 in either crafting table or stone cutter.

Slabs, stairs and quarter pieces can be rotated in crafting recipes to be corresponding vertical blocks; you can also "rotate them back". For instance, one slab can be crafted into one corresponding vertical slab, and one vertical slab can also be crafted into a corresponding slab directly.

Crafting recipes of blocks in different shapes are as follows:

- 6×base blocks → 4×stairs
- 3×base blocks → 6×slabs
- 3×slabs → 6×quarter pieces (slabs arranged horizontally)
- 1×slab ↔ 1×vertical slab
- 1×stairs ↔ 1×vertical stairs
- 1×quarter piece ↔ 1×vertical quarter piece
- 3×vertical slabs → 6×vertical quarter pieces (vertical slabs arranged vertically)

Stone-cutting recipe of some blocks are as follows:

- 1×base block → 1×stairs / 1×vertical stairs / 2×slabs / 2×vertical slabs / 4×quarter pieces / 4×vertical quarter pieces
- 1×stairs → 3×quarter pieces
- 1×slab → 2×quarter pieces
- 1×vertical stairs → 3×vertical quarter pieces
- 1×vertical slab → 2×quarter pieces / 2×vertical quarter pieces

NOTE: Items in this mod are not recursively stonecuttable like some items in vanilla Minecraft. For example, in vanilla Minecraft, an unpolished andesite can be cut into a polished andesite stairs or slab. Blocks in this mod can not be cut like this.

Ingredients of fences and fence gates, aside from base blocks, resemble their vanilla recipes. For the crafting of stone fences and fence gates, flint is used alongside base blocks. Similarly, string is used for wool fences and fence gates, and sticks for sandstone and its variants.

To avoid recipe conflicts, some buttons are not craftable. For example, iron blocks can be crafted to iron ingots only, instead of iron buttons; pumpkins can be crafted to pumpkin seeds only, instead of pumpkin buttons. Besides, wool pressure plates and moss pressure plates are not craftable, but can be converted 1:1 from their corresponding carpets.

Since 1.5.0, all recipes can be unlocked when obtaining any of the base block. To put explicitly, each recipe has a corresponding advancement, and as soon as the player obtains the base block or unlocks the recipe, the advancement is triggered and relative recipe is unlocked.

### Creative Inventory

In creative mode, multiple item groups are set in order to sort items in order. Blocks based on a same block (including vanilla blocks) are arranged together, so that it's possible to conveniently obtain multiple shapes of one block.

Besides, you can choose to make blocks added by the mod appear in vanilla item groups, which however may case the item list messy. You can configure it in your mod config screen (requires Mod Menu).

If you installed Reasonable Sorting mod (≥1.3 version), the blocks in vanilla item groups will be sorted by their base blocks.

## About ARRP

The mod relies on BRRP (Better Runtime Resource Pack) mod, which is base on previous ARRP (Advanced Runtime Resource Pack). THd mod allows you to create resources and data inside Minecraft instance at runtime, instead of dumped to your mod file.

Usually a block requires a corresponding block states definition and block model for a correct visual, as well as a loot table to drop correctly after being broken, and a recipe that defines how the block is crafted, plus a corresponding advancement for each recipe, which is triggered when the player obtains one of ingredients and allows the player to unlock the recipe. A block has also an item, which needs an item model. The "block states definitions", "block models", "item models", "loot tables", "recipes" and "advancements" as referred to above are json files. In mods, creating these json files for each block is very troublesome, and the json files are basically duplicate. For example, models of block items typically directly inherit corresponding block models, yet requiring each
definition in each file. Another example, block states of stairs are quite complicated, but block states of different stairs blocks are basically the same, with the only difference of name. It's hard to understand to define such a complicated file for each stairs block.

But when using ARRP, the files can be generated in runtime, instead of stored in the mod file. The file size of mod has been largely reduced, and so does as I/O stream. This is what ARRP is indented, and BRRP adds enhancements based on it.

However, while creating runtime resource packs with ARRP, though large numbers of repetitive contents reduced, the grammar in the Java code is still complex. Besides, although I/O reduces, objects are converted to json, serialized as bytes and stored internally, and when used by Minecraft, these bytes are deserialized to json and converted to relevant objects in Minecraft. In the process, ARRP objects and MC vanilla objects are not directly convertible. However, BRRP is trying to bridge between ARRP and Minecraft.

Therefore, runtime resource packs do not reduce the process of deserializing to json and reading as object, and even adds a process of converting and serializing json. But it's proved that the process of generating runtime resources is much faster than reading from mod files.

ARRP is just designed for this, and the issue should be attributed to Mojang. To fulfill data packs and resource packs, Minecraft takes such a wierd and inefficient method to read game assets and data (including vanilla ones). As mod developers, what we can do is anything but changing this.
