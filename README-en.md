# Extended Block Shapes Mod

如果看不懂英文，可以阅读[中文版文档](README.md)。

This mod adds stairs, slabs, fences, fence gates, button and pressure plates for many vanilla blocks, along with vertical slabs, vertical stairs, quarter pieces and vertical quarter pieces that do not exist in vanilla Minecraft. A list of the contents can be found in [this file](BlockList.md).

At present this mod supports Minecraft Java Edition 1.18.1, 1.17.1 and 1.16.5. Please install the relevant mod according to your MC version (nearby versions compatible in theory, **except 1.16-1.16.1**) and requires Fabric API.

**Notice: Since mod version 1.4.0, the mod requires ARRP (Advanced Runtime Resource Pack) to run, so you must have this mod installed. If you install Extended Block Shapes without installing ARRP, the game instance will refuse to run.**

**NOTICE: If you update the mod from 1.2.3 to ≥1.2.4, please remove all _plank wall_ and _clay wall_ blocks from your worlds! As of 1.2.4, plank walls and clay walls are no longer a feature of this mod.**

[Click here](UpdateLog.md) for previous update logs.

## Features

### Blocks

Blocks added by this mod inherit most features of their base blocks.

All blocks based on the wool block are flammable and can be used as fuels (smelting time equals vanilla wool). All wool blocks can block sculk sensors.

All netherrack blocks are infiniburn. Bedrock blocks are infiniburn in the end.

Netherite blocks when dropped as items resist fire and lava.

The hardness, mining tools and mining time of all stairs, slabs, fences and walls are equal to their base blocks. For example, a plank slab and a plank block will take the same time to mine.

In vanilla, the hardness of pressure plates and buttons equals to 1/4 or 1/3 of their basic blocks. All pressure plates and buttons added in this mod have the same hardness as 1/4 of their base blocks. (Bedrock blocks are invulnerable in Survival Mode, but bedrock pressure plates and bedrock buttons drop normally when losing blocks relied on.) This mod simply extends buttons: soft buttons (wool buttons, snow buttons) trigger 60 ticks (3 seconds); hard buttons (obsidian buttons, bedrock buttons) trigger 5 ticks (1/4 second). All buttons and pressure plates have no collision box like in vanilla MC.

To avoid recipe conflicts, some buttons are not craftable. For example, iron blocks can be crafted to iron ingots only, instead of iron buttons; pumpkins can be crafted to pumpkin seeds only, instead of pumpkin buttons.

This mod also adds "double smooth stone slab" and "petrified oak planks" blocks, respectively crafted from smooth stone slabs and petrified oak slabs. Vanilla smooth stone blocks can still be crafted into smooth stone slabs.

### Crafting and smelting

The crafting recipes for all blocks in this mod are similar to those in vanilla. Some can also be crafted with a stone cutter. Stairs can be crafted 3:2 in a crafting table (ingredient:result) and 1:1 in the stone cutter. Slabs and vertical slabs are craftable 1:2 in either crafting table or stone cutter.

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

NOTE: Items in this mod are not recursively stone-cuttable like some items in vanilla Minecraft. For example, in vanilla Minecraft, an unpolished andesite can be cut into a polished andesite stairs or slab. Blocks in this mod can not be cut like this.

Ingredients of fences and fence gates, aside from base blocks, resemble their vanilla recipes. For the crafting of stone fences and fence gates, flint is used alongside base blocks. Similarly, string is used for wool fences and fence gates, and sticks for sandstone and its variants.

When used as fuels, slab and vertical slab burn half the time of its base blocks, button burns 1/3 the time of base blocks, and quarter piece and vertical quarter piece burn 1/4 the time of basic blocks.

### Creative Inventory

In creative mode, multiple item groups are set in order to sort items in different ways.

Block items added in this mod are grouped in vanilla item groups. Additionally, several extra item groups have been added to sort blocks (including vanilla blocks) by their base blocks, so that players can easily fetch blocks of a same block in different shapes.

If you installed Reasonable Sorting mod (≥1.3 version), these blocks will sort by their base blocks. Note that Reasonable Sorting relies on Cloth Config, but Extended Block Shapes does not.

## About ARRP

ARRP (short for Advanced Runtime Resource Pack) is a third-party library mod, which allows you to create asset and data files when initializing the instance internally, instead of writing the files in the mod file.

Usually a block requires a corresponding block states definition and block model for a correct visual, as well as a loot table to drop correctly after being broken, and a recipe that defined how the block is crafted. A block has also an item, which needs an item model. The "block states definitions", "block models", "item models", "loot tables" and "recipes" as referred to above are json files. In mods, creating these json files for each block is very troublesome, and the json files are basically duplicate. For example, models of block items usually directly inherit corresponding block models, yet requiring each definition in each file. Another example, block states of stairs are quite complicated, but block states of different stairs blocks are basically the same, with the only difference
of name. It's hard to understand to define such a complicated file for each stairs block.

But when using ARRP, the files can be generated in runtime, instead of stored in the mod file. The filesize of mod has been largely reduced, and so does as I/O stream. Of course, you must install ARRP first.

However, while creating runtime resource packs with ARRP, though large numbers of repetitive contents reduced, the grammar in the Java code is still complex. Besides, although I/O reduces, objects are converted to json, serialized as bytes and stored internally, and when used by MC, these bytes are deserialized to json and converted to relevant objects in MC. In the process, ARRP objects and MC vanilla objects are not directly convertible. Therefore, ARRP does not reduce the process of deserializing to json and reading as object, and even adds a process of converting and serailizing json.

ARRP is just designed for this, and the issue should be attributed to Mojang. To fulfill data packs and resource packs, Minecraft takes such a wierd and inefficient method to read game assets and data (including vanilla ones). As mod developers, we can do anything but changing this.
