# Extended Block Shapes Mod

如果看不懂英文，可以阅读[中文版文档](README.md)。

This mod adds stairs, slabs, fences, fence gates, button and pressure plates for many vanilla blocks, along with vertical slabs, vertical stairs, quarter pieces and vertical quarter pieces that do not exist in vanilla Minecraft. A list of the contents can be found in [this file](BlockList.md).

At present this mod supports Minecraft Java Edition 1.19.3-1.16.4. Please install the relevant mod according to your MC version (mod for 1.19.2 is compatible with 1.19.1 and 1.19, mod for 1.18.1 is compatible with 1.18; mod for 1.16.5 is compatible for 1.16.4) and requires Fabric API and BRRP. Besides, Mod Menu is usually needed (but not required) to open mod config screen.

**Notice: The mod requires BRRP (Better Runtime Resource Pack) to run, so you must have this mod installed. BRRP is a branch of ARRP; please do not install both BRRP and ARRP simultaneously, or some unexpected issues may happen.**

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
    - Note that in vanilla, wool carpets dampen vibrations but cannot occlude signals. However, all woolen blocks in this mod occlude all vibration signals.
- All netherrack blocks are infiniburn. Bedrock blocks are infiniburn in the end.
- All blocks of endstone, obsidian and crying obsidian are immune to ender dragons.
- Netherite blocks and ancient debris blocks, when dropped as items, resist fire and lava.
- Blocks base on gold blocks and raw gold blocks can be admired by piglins. Breaking blocks base on gold blocks, raw gold blocks and gilded blackstone irritates piglins.
- A snow stairs or snow slab, when placed on a grass block, if it just covers the whole top of the grass block, makes it snowy, just like covered by a snow block or snow.
- Small dripleaves and large dripleaves can be placed on blocks base on moss blocks and clay (only building blocks).
- Blocks base on pumpkin, melon, moss block, shroomlight, nether wart block and warped wart block can be composted.
- Blocks based on packed ice drop only mined with items with Silk Touch enchantment.
- Blocks based on clay block, snow block, melon block, etc., drop their corresponding items when mined, such as clay ball, snow ball (only when shoveled) and melon slice. Number of items dropped by slabs, quarter pieces, vertical slabs, vertical slabs are based on that of base blocks divided by 2 or 4, and double slabs drop the double. Some items dropped may be affected by Fortune enchantment, and blocks themselves are dropped when mined with tools with Silk Touch.

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

Since 1.5.1, items in this mod are recursively stonecuttable. For example, an unpolished andesite can be cut into a polished andesite vertical slab.

Ingredients of fences and fence gates, aside from base blocks, resemble their vanilla recipes. For the crafting of stone fences and fence gates, flint is used alongside base blocks. Similarly, string is used for wool fences and fence gates, and sticks for sandstone and its variants.

To avoid recipe conflicts, some blocks are not craftable. For example, iron blocks can be crafted to iron ingots only, instead of iron buttons; pumpkins can be crafted to pumpkin seeds only, instead of pumpkin buttons. Besides, wool pressure plates and moss pressure plates are not craftable, but can be converted 1:1 from their corresponding carpets. Snow slabs can not be crafted directly from snow, either; instead, craft a snow (layer) with three snow blocks, and then craft the slab with the snow. You can test potential recipe conflicts via `/extshape:check-conflict` command.

Since 1.5.0, all recipes can be unlocked when obtaining any of the base block. To put explicitly, each recipe has a corresponding advancement, and as soon as the player obtains the base block or unlocks the recipe, the advancement is triggered and relative recipe is unlocked.

Since 1.6.0, you can configure to cancel avoiding recipes that conflict. For instance, if you disable "prevent wooden wall recipes", wooden walls can be crafted like other wall blocks, which conflict with the recipe of wooden trapdoors. It's advised to modify these settings on condition that you have installed mods that can solve recipe conflict.

### Creative Inventory

In creative mode, multiple item groups are set in order to sort items in order. Blocks based on a same block (including vanilla blocks) are arranged together, so that it's possible to conveniently obtain multiple shapes of one block.

Besides, you can choose to make blocks added by the mod appear in vanilla item groups, which however may case the item list messy. You can configure it in your mod config screen (requires Mod Menu).

If you installed Reasonable Sorting mod, the blocks in vanilla item groups will be sorted by their base blocks.