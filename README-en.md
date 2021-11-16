# Extended Block Shapes Mod

如果看不懂英文，可以阅读[中文版文档](README.md)。

This mod adds stairs, slabs, fences, fence gates, button and pressure plates for many vanilla blocks, along with vertical slabs, vertical stairs, quarter pieces and vertical quarter pieces that do not exist in vanilla Minecraft. A list of the contents can be found in [this file](BlockList.md).

At present this mod supports Minecraft Java Edition 1.17.x and 1.16.x and requires Fabric API.

**NOTICE: If you update the mod from 1.2.3 to ≥1.2.4, please remove all _plank wall_ and _clay wall_ blocks from your worlds! As of 1.2.4, plank walls and clay walls are no longer a feature of this mod.**

## Features

### Blocks

Blocks added by this mod inherit most features of their base blocks.

All blocks based on the wool block are flammable and can be used as fuels (smelting time equals vanilla wool). All wool blocks can block sculk sensors.

All netherrack blocks are infiniburn. Bedrock blocks are infiniburn in the end.

Netherite blocks when dropped as items resist fire and lava.

The hardness, mining tools and mining time of all stairs, slabs, fences and walls are equal to their base blocks. For example, a wool slab and a wool block will take the same time to mine.

In vanilla, the hardness of pressure plates and buttons equals to 1/4 or 1/3 of their basic blocks. All pressure plates and buttons added in this mod have the same hardness as 1/4 of their base blocks. (Bedrock blocks are invulnerable in Survival Mode, but bedrock pressure plates and bedrock buttons drop normally when losing blocks relied on.) This mod simply extends buttons: soft buttons (wool buttons, snow buttons) trigger 60 ticks (3 seconds); hard buttons (obsidian buttons, bedrock buttons) trigger 5 ticks (1/4 second). All buttons and pressure plates have no collision box like in vanilla MC.

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

## This update

- <span style="color:red">Removed nether wart wall and warped wart wall.</span>
- Added more blocks, main buttons.
- Fixed the issue that, in constructor of BlocksBuilder with <code>null</code> parameters, objecteds are created unexpectedly, which may cause some buttons and pressure plates to crash.
- Fixed the potential recipe conflict of block of iron, gold, diamond, emerald and lapis. <b>These blocks still exist, but can no longer be crafted.</b>
- Slightly adjusted item groups.
- Fixed the issue that some stone blocks cannot be cut in stone-cutters.

See [Update Log](UpdateLog.md) for previous update logs.
