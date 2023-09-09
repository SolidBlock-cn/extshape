# Extended Block Shapes Mod

如果看不懂英文，可以阅读[中文版文档](README.md)。

This mod adds stairs, slabs, fences, fence gates, button and pressure plates for many vanilla blocks, along with vertical slabs, vertical stairs, quarter pieces and vertical quarter pieces that do not exist in vanilla Minecraft. A list of the contents can be found in [this file](BlockList.md).

At present this mod supports Minecraft Java Edition 1.20.1-1.16.5. Please install the relevant mod according to your MC version, and it requires Fabric API and BRRP. Besides, for Fabric, Mod Menu is usually needed (but not required) to open mod config screen.

**Notice: The mod requires BRRP (Better Runtime Resource Pack) to run.**

If you have installed Extended Block Shapes and Blockus, you may also optionally install [Extended Block Shapes - Blockus](#Blockus), to add inter-mod support for Blockus.

[Click here](UpdateLog-en.md) for previous update logs. Welcome to join Tencent QQ group **587928350** to experience the latest update of this mod.

## Features

### Blocks

This mod provides amounts of variants of many blocks. For example, wool has wool stairs, wool slab, wool wall, wool pressure plate, etc. Vertical stairs, vertical slab, quarter piece and vertical quarter piece are added by the mod; these blocks, like regular stairs and slabs, are waterloggable.

The hardness, mining tools and mining time of all stairs, slabs, fences and walls are equal to their base blocks, which is the same behaviour of vanilla, instead of related to their volume. For example, a plank slab and a plank block will take the same time to mine.

In vanilla, the hardness of pressure plates and buttons equals to 1/4 or 1/3 of their basic blocks. All pressure plates and buttons added in this mod have the same hardness as 1/4 of their base blocks. This mod simply extends buttons: soft buttons (wool buttons, snow buttons) trigger 60 ticks (3 seconds); hard buttons (obsidian buttons, bedrock buttons) trigger 5 ticks (1/4 second). Like in vanilla Minecraft, all buttons and pressure plates have no collision box.

This mod also adds "double smooth stone slab" and "petrified oak planks" blocks, respectively crafted from smooth stone slabs and petrified oak slabs. Vanilla smooth stone blocks can still be crafted into smooth stone slabs.

Blocks added by this mod inherit most features of their base blocks:

- **Wool and planks** blocks (which means blocks in based on wool or planks different shapes) are flammable (burning time and spreading speed equal vanilla full blocks).
- **Wool, planks, wood and log** blocks can be used as fuel. Slabs and vertical slabs, when used as fuel, can consume half the time of their base blocks. Buttons used as fuels smelt about 1/3 the time of their base blocks. Quarter pieces and vertical quarter pieces, when used as fuel, smelt 1/4 the time of the base blocks.
- **Wool** blocks, no matter whether they occupy the space of a whole block, can block sculk sensors.
    - Note that in vanilla, wool carpets dampen vibrations but cannot occlude signals. However, all woolen blocks in this mod occlude all vibration signals.
- **Netherrack** blocks are infiniburn.
- **Bedrock** blocks are infiniburn in the end. They are not harvestable in Survival Mode. However, bedrock buttons and pressure plates may drop when they lose the blocks they relie on.
- **Endstone, obsidian and crying obsidian** blocks are immune to ender dragons.
- **Netherite** blocks and **ancient debris** blocks, when dropped as items, resist fire and lava.
- **Gold and raw gold** blocks can be admired by piglins.
- Breaking **gold blocks, raw gold blocks and gilded blackstone** blocks irritates piglins.
- A **snow** stairs or snow slab, when placed on a grass block, if it just covers the whole top of the grass block, makes it snowy, just like covered by a snow block or snow.
- Small dripleaves and large dripleaves can be placed on blocks base on **moss blocks and clay** (only building blocks).
- Blocks base on **pumpkin, melon, moss block, shroomlight, nether wart block and warped wart block** can be composted.
- Blocks based on **packed ice and sculk block** drop only mined with items with Silk Touch enchantment.
- Blocks based on **clay block, snow block, melon block**, etc., drop their corresponding items when mined, such as clay ball, snow ball (only when shoveled) and melon slice. Number of items dropped by slabs, quarter pieces, vertical slabs, vertical slabs are based on that of base blocks divided by 2 or 4, and double slabs drop the double. Some items dropped may be affected by Fortune enchantment, and blocks themselves are dropped when mined with tools with Silk Touch.

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

All recipes can be unlocked when obtaining any of the base block. To put explicitly, each recipe has a corresponding advancement, and as soon as the player obtains the base block or unlocks the recipe, the advancement is triggered and relative recipe is unlocked.

You can configure to cancel avoiding recipes that conflict. For instance, if you disable "prevent wooden wall recipes", wooden walls can be crafted like other wall blocks, which conflict with the recipe of wooden trapdoors. It's advised to modify these settings on condition that you have installed mods that can solve recipe conflict. You can access the mod configuration screen via Mod Menu (of Fabric) or Forge, or typing command `/extshape:config` in game.

### Creative Inventory

In versions above 1.19.3, items in these mod will by default be added to vanilla item groups. If you do not want to add items into vanilla item groups, instead add four specific groups to place blocks in various shapes in the order of their base blocks, you may go to the config screen of the mod (which can be accessed through the mod menu of Mod Menu mod), and disable "Add to vanilla groups" and enable "Show specific groups".

In versions 1.19.2 and before, if you enabled "Add to vanilla groups", mods will be directly appended after all existing blocks, which may make item groups appear messy. You may install Reasonable Sorting mod to sort the content. (Reasonable Sorting mod does not support versions above 1.19.3, and it is also not needed.)

You may also configure the shapes to be added into groups. For example, if you enable "add to vanilla groups", and set "shapes added to vanilla groups" to `stairs slab`, then only stairs and slabs in this mod are added into vanilla groups (not affecting vanilla existing items). For 1.19.3, the shapes are added in order (but cannot be duplicate). For example, if you write `slab stairs`, stairs will be added after the slabs (vanilla stairs and slabs will not be affected).

## Inter-mod support

The mod can currently add utilities with Blockus mod.

### Blockus

If you have installed Extended Block Shapes and Blockus mod, you may optionally install "Extended Block Shapes - Blockus" mod based on that two mods, so that extended shapes for Blockus blocks will also be added.

Note: There are no specific groups for these blocks. Therefore, to find these blocks in creative inventory, you need to enable "Add to vanilla groups" in the mod options of "Extended Block Shapes", and find these blocks in Blockus item groups.
