# Update log (for Blockus part)

### 3.1.6-beta.3

- Fixed the issue that gingham wool is incorrectly sorted in the Creative Mode inventory.
- Added blocks related to cobblestone bricks, mossy cobblestone bricks, copper bricks and tuff blocks. Note: Currently all shapes are added, but the possibility that some shapes (especially button) may be removed in the future is not excluded.
    - Shape variants of cobblestone bricks and mossy cobblestone bricks are added to `#blockus:stone_blocks` block tag. Shape variants of copper bricks are added to `#blockus:copper_blocks` block tag. Shape variants of copper tuff blocks are added to `#blockus:tuff_blocks` block tag.
    - Shape variants of copper bricks and copper tuff bricks can be normally oxidized, unweathered, waxed or unwaxed.
- Added blocks related to water bricks. This block used not to be added due to the issue of incorrect model, but the issue is not found at present so it is added now.
    - Shape variants of water bricks are all added to `#blockus:water_bricks` block tag.

### 3.1.5

- Synchronizing to Blockus changes, now in the Creative Mode inventory, no special sorting is applied for dyed stone bricks and terracotta.
- Synchronizing to Blockus changes, added blocks related to sulfur and cinnabar as well as relevant stoncutting recipes for 26.2.
- Adjusted the following block tags:
    - Fixed the issue that blaze lantern blocks are correctly added to `#blockus:blaze_bricks` block tag.
    - Fixed the issue that the extended shapes of soul sandstone are not added to `#blockus:soul_sandstone` block tag.
    - Fixed the issue that chorus block does not have `#sword_efficient` block tag.
    - Fixed the issue that blocks of large resin bricks, herringbone resin bricks do not have `#resin_blocks` block tag.
    - Blocks related to white oak, stripped white oak, raw bamboo and mossy planks are no longer directly in the shape tags (such as `#extshape:vertical_slabs`), and are instead added to the corresponding wooden shape tag (such as `#extshape:wooden_vertical_slabs`).
- Synchronizing the changes of the main mod, making use of the [tag removal](https://fabricmc.net/2026/06/15/262.html#tag-removal) feature of Fabric API, removed some walls and fence gates of this mod out of `#mineable/pickaxe` and `#mineable/axe` tags
- Adjusted the block stonecutting recipes to synchronize the changes of Blockus or fix relevant issues (the phase "blocks related" refers to some vanilla shapes variants of some Blockus blocks that Blockus mod do not have, such as slab, or the extended shape variants of this mod of some blocks, such as vertical slab):
    - Fixed the issue that cut soul sandstone cannot be stonecut.
    - Now soul sandstone can be cut blocks related to cut soul sandstone.
    - Now the polished endstone of Blockus mod can be cut into blocks related of vanilla end bricks.
    - Now resin bricks can be cut into blocks related to herringbone resin bricks and large resin bricks.
    - Now stone can be cut into blocks related to stone tiles.
    - Now tuff, polished tuff, tuff bricks can be cut into blocks related to tuff tiles.
- Added some stone pressure plates and buttons to `#extshape:stone_pressure_plates` and `#minecraft:stone_buttons` block and item tags.
- Fixed the issue of missing texture of glazed terracotta pillar blocks in 26.2.

> Note: In some early Minecraft versions, the block tags and stonecutting recipes of Blockus mod may differ from what is described here, and the mod will be adjusted to match Blockus mod.

### 3.1.4

- Since the new version of Blockus mod has added walls for polished blocks, the mod no longer adds these blocks. The mod uses the feature of registry alias in Fabric API to make old names (blocks in Extended Block Shapes - Blockus) compatible with new version (blocks in Blockus). For example, `extshape_blockus:polished_purpur_wall` will be identical to `blockus:polished_purpur_wall`.

> The Extended Block Shapes main mod in 3.0.4 version also conducted a change of block name, but DataFixer was used at that version, and may not be applicable to Minecraft versions 1.21.3 and before. In the future, the registry alias in Fabric API might also be used, making these changes applicable to versions 1.21.3 and before.

### 3.1.3

- Fit Blockus new versions.
- Added blocks related to small crimson and warped logs into `#non_flammable_wood` item tag.
- Fixed the issue of blocks related to light gray stone bricks are missing.
- Use `@NullMarked`.
- Fixed the issue that blocks related to netherite bricks are not fire-resistant.
- Fixed the issue that glazed terracotta blocks of different colors may be crafted each other.

> **Important note**
>
> As the updates of Blockus changed the name of small logs, after updating the mod, if you have used blocks related to small logs, please make a back-up for your level.