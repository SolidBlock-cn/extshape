# Extended Block Shapes Mod

本文档有[中文版](README.md)。

> Since 3.0.0, [Better Runtime Resource Pack](https://github.com/SolidBlock-cn/BRRP/) mod is **no longer required**.

This mod adds stairs, slabs, fences, fence gates, button and pressure plates for many vanilla blocks, along with vertical slabs, vertical stairs, quarter pieces and vertical quarter pieces that do not exist in vanilla Minecraft. A list of the contents can be found in [Block List](BlockList.md).

At present this mod supports Minecraft Java Edition 1.21.1-1.16.5. Please install the relevant mod according to your MC version, and it requires Fabric API to run. Besides, for Fabric, Mod Menu is usually needed (but not required) to open mod config screen.

If you have installed Extended Block Shapes and Blockus, you may also optionally install [Extended Block Shapes - Blockus](#Blockus), to add inter-mod support for Blockus.

See [update log](UpdateLog-en.md) for previous update logs. Welcome to join Tencent QQ group **587928350** to experience the latest update of this mod.

## Features

### Blocks

This mod provides amounts of variants of many blocks. For example, wool has wool stairs, wool slab, wool wall, wool pressure plate, etc. Vertical stairs, vertical slab, quarter piece and vertical quarter piece are added by the mod; these blocks, like regular stairs and slabs, are waterloggable.

The hardness, mining tools and mining time of all stairs, slabs, fences and walls are equal to their base blocks, which is the same behaviour of vanilla, instead of related to their volume. For example, a plank slab and a plank block will take the same time to mine.

In vanilla, the hardness of pressure plates and buttons equals to 1/4 or 1/3 of their basic blocks. All pressure plates and buttons added in this mod have the same hardness as 1/4 of their base blocks. This mod simply extends buttons: soft buttons (wool buttons, snow buttons) trigger 60 ticks (3 seconds); hard buttons (obsidian buttons, bedrock buttons) trigger 5 ticks (1/4 second). For the actual activation time, see the table below. Like in vanilla Minecraft, all buttons and pressure plates have no collision box.

| base block                              | button activation time | plate activation time |
|-----------------------------------------|------------------------|-----------------------|
| stone blocks                            | 20                     | 20                    |
| wooden and bamboo                       | 30                     | 20                    |
| other soft blocks                       | 50                     | 40                    |
| hard blocks (obsidian, netherite, etc.) | 5                      | 5                     |
| wool                                    | 50                     | 40                    |
| snow                                    | 55                     | 55                    |
| moss                                    | 60                     | 60                    |
| dirt, clay                              | 45                     | 45                    |
| ore blocks                              | 15                     | 10                    |
| melon, pumpkin                          | 35                     | 25                    |
| quartz                                  | 25                     | 25                    |
| basalt                                  | 15                     | 15                    |
| tuff, calcite, netherrack               | 25                     | 20                    |
| deepslate (including bricks)            | 10                     | 10                    |
| unoxidized copper                       | 10                     | 10                    |
| exposed copper                          | 40                     | 40                    |
| weathered copper                        | 70                     | 70                    |
| oxidized copper                         | 100                    | 100                   |
| glowstone                               | 30                     | 30                    |
| nether wart, warped wart                | 45                     | 45                    |
| amethyst                                | 35                     | 25                    |

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
- About the interaction between blocks and pistons: for blocks that cannot be pushed by piston, such as obsidian, bedrock, the buttons and pressure plates can also not be pushed by piston, while buttons and pressure plates for other blocks will be directly destroyed.
- About note block: To be consistent with vanilla, the note block instrument of button blocks are always harp, while others are same as their base blocks.
- Buttons will not be displayed as colors in maps.
- **Copper** blocks, like vanilla copper blocks, can be oxidized, and be de-oxidized by using axes or being hit by lighting bolt. They can also be waxed and de-waxed. Oxidation of waxed copper does not change.

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

In stonecutters, one base blocks can be cut into various shapes for cut base blocks. For instance, a stone can be cut into stone bricks, and stone bricks can be cut into 2 vertical stone brick slabs, while one stone can also be cut directly into 2 vertical stone brick slabs. However, blocks of various shapes cannot be cut into their corresponding shapes of cut base blocks.

The second crafting ingredient for crafting fences and fences (apart from base blocks) is dependent to its base block. See details in [block list](BlockList.md).

### Built-in data pack fixing recipe conflicts

Some recipes may conflict with vanilla. To avoid the conflicts, there is a built-in data pack in this mod to avoid recipe conflicts, which is enabled by default. When the data pack is enabled, the following crafting will change:

- Wool pressure plates are not crafted from 2 blocks of wool, but 3 carpets crafts into 1 wool pressure plate, and 1 wool pressure plate also crafts into 3 carpets.
- Moss pressure plates are not crafted from 2 moss blocks, but 1 moss carpet crafts into 1 moss pressure plate, and 1 moss pressure plate also crafts into 2 moss carpets.
- Snow slabs are not crafted from 2 snow blocks, but 1 snow (layer).
- Stairs and slabs of vanilla sandstone, red sandstone and quartz must be crafted from base blocks, instead of variants of the base blocks.
- Buttons of block of iron, block of gold, block of diamond, block of coal, block of lapis, pumpkin, block of netherite, raw gold block, raw copper block and raw iron block, are crafted from 1 base block and 1 iron ingot, gold ingot, diamond, coal, lapis lazuli, pumpkin seeds, netherite ingot, raw gold, raw copper or raw iron.
- Buttons of bamboo block, stripped bamboo block, copper block (including all oxidization levels, not including cut copper), waxed copper (including all oxidization levels, not including waxed cut copper), various logs, wood, stem, hyphae and those stripped variants, are crafted from 1 base block and 1 redstone dust.
- Walls of planks are crafted from 6 planks and 1 stick.
- Copper walls and waxed copper walls (including all oxidization level) are crafted from 6 base blocks and 1 copper ingot.

All unwaxed copper blocks can be crafted with honeycomb into corresponding waxed blocks.

#### The command to manually test recipe conflicts

The command `/extshape:check-conflict` can be used to test conflicts in crafting recipes, which may cause the server lag several seconds or minutes. The command can be used only by the server owner, not ordinary players or command blocks.

The syntax the command supports:

- `/extshape:conflict`: Test all conflicts between crafting recipes.
- `/extshape:conflict <namespace>`: Test conflicts between crafting recipes of the specified namespace and the vanilla namespace.
- `/extshape:conflict <namespace> ...`(multiple namespaces are separated by space): Test conflicts of crafting recipes of the specified multiple namespaces.

### Creative Inventory

In versions above 1.19.3, items in these mod will by default be added to vanilla item groups.

In versions 1.19.2 and before, if you enabled "Add to vanilla groups", mods will be directly appended after all existing blocks, which may make item groups appear messy. You may install Reasonable Sorting mod to sort the content. (Reasonable Sorting mod does not support versions above 1.19.3, and it is also not needed.)

You may also configure the shapes to be added into groups. You can enter the mod config screen through the mod menu of Mod Menu mod. If you did not install Mod Menu mod, you can also access the config screen of the mod through typing `/extshape:config` in game.

For example, if you enable "add to vanilla groups", and set "shapes added to vanilla groups" to `stairs slab`, then only stairs and slabs in this mod are added into vanilla groups (not affecting vanilla existing items). For 1.19.3, the shapes are added in order (but cannot be duplicate). For example, if you write `slab stairs`, stairs will be added after the slabs (vanilla stairs and slabs will not be affected).

## Inter-mod support

The mod can currently add utilities with Blockus mod.

### Blockus

If you have installed Extended Block Shapes and Blockus mod, you may optionally install "Extended Block Shapes - Blockus" mod based on that two mods, so that extended shapes for Blockus blocks will also be added.

Note: There are no specific groups for these blocks. Therefore, to find these blocks in creative inventory, you need to enable "Add to vanilla groups" in the mod options of "Extended Block Shapes", and find these blocks in Blockus item groups.
