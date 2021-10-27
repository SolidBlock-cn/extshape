# Extended Block Shapes Mod

如果看不懂英文，可以阅读[中文版文档](README.md)。

This mod adds stairs, slabs, fences, fence gates, button and pressure plates for many vanilla blocks, along with vertical slabs, vertical stairs, quarter pieces and vertical quarter pieces that does not exist in vanilla Minecraft. List of the contents can be found in [this file](BlockList.md).

At present this mod supports Minecraft Java Edition 1.17.x and 1.16.x and requires Fabric API.

**NOTICE: If you update the mod from 1.2.3 to ≥1.2.4, please take off all _plank wall_ and _clay wall_ blocks! Since 1.2.4, plank walls and clay walls are _removed_!**

## Features

### Blocks

Blocks added by this mod inherit most features of their base blocks.

All woolen blocks (blocks based on wool block, including wool stairs, woo slabs in all colors) are flammable and can be used as fuels (fueling time equals their base blocks). All woolen blocks, no matter they occupy a full block, can block sculk sensors.

All netherrack blocks are infiniburn. Bedrock blocks are infiniburn in the end.

Netherite blocks as dropped items resist fire and lava.

The hardness, mining tools and mining time of all stairs, slabs, fences and walls equal to their basic blocks (like vanilla features). For example, the time spent breaking a wool slab equals to a full wool block, instead of half of the latter.

In vanilla, the hardness of pressure plates and buttons equals to 1/4 or 1/3 of their basic blocks. All pressure plates and buttons added in this mod have the hardness same as 1/4 of their base blocks. (Bedrock blocks are invulnerable in Survival Mode, but bedrock pressure plates and bedrock buttons drop normally when losing blocks relied on.) This mod simply extends buttons: soft buttons (wool buttons, snow buttons) trigger 60 ticks (3 seconds); hard buttons (obsidian buttons, bedrock buttons) trigger 5 ticks (1/4 second). All buttons and pressure plates have no collision box like vanilla MC.

Besides, this mod also adds "double smooth stone slab" and "petrified oak planks" block, respectively crafting ingredient of smooth stone slab and petrified oak slab. (Still, vanilla smooth stone block can also be crafted into smooth stone slab).

### Crafting and smelting

The crafting recipes of all blocks in this mod are similar to those in vanilla. Some can also be crafted with a stone cutter. Stairs can be crafted 3:2 in crafting table (ingredient:result) and 1:1 in the stone cutter. Slabs and vertical slabs are craftable 1:2 in either crafting table or stone cutter.

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

Ingredients of fences and fence gates, apart from base blocks, resemble vanilla recipes. For the crafting of stone fences and fence gates, flint is used along with base blocks, and string for wool, stick for sandstone and its variants.

Used as fuels, slab and vertical slab burn half the time of its base blocks, button burns 1/3 the time of base blocks, and quarter piece and vertical quarter piece burns 1/4 the time of basic blocks.

### Creative Inventory

In creative mode, multiple item groups are set in order to sort items in different ways.

Block items added in this mod are grouped in vanilla item groups. Besides, several extra item groups are set to sort blocks (including vanilla blocks) by their basic blocks, so that players can easily fetch blocks of a same block in different shapes.

If you installed Reasonable Sorting mod (>1.3 version), these blocks will sort by their base blocks. Notice that Reasonable Sorting relies on Cloth Config, but this mod does not reply on it.

## This update

### 1.3.0

This update is for version only 1.17 and above. Versions for older versions will come later.

- Added co-working with Reasonable Sorting mod (versions above 1.13).
- Fixed the conflict between wool pressure plate and wool carpet. Wool pressure plates are crafted from a carpet in the same color now.
- Adjust some code.
- Added different shapes for dripstone, honeycomb block, moss block, etc.

See [Update Log](UpdateLog.md) for previous update logs.