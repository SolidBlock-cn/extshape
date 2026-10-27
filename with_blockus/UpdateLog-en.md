# Update log (for Blockus part)

### 3.2.0

- Fit the new version of Extended Block Shapes mod.
- Followed the 3.1.5 updates for versions below 1.21.10, including:
    - Various shape variants of blocks like raw bamboo, mossy planks, oak planks are added to shape variants prefixed with `wooden`.
    - `minebale/pickaxe` block tag added blocks related star block, and removed blocks of blaze lantern.
    - Fixed the issue that some shape tags prefixed with `wooden` or `lag` has only block tags, no item tags.
    - Follow the updates of Blockus, polished end stone can be stone-cut into shape variants of vanilla end stone bricks.
    - Stone can be cut into the shape variants of stone tiles.
- Fixed the issue that blocks related to chiseled tuff block before the version where chiseled tuff become part of vanilla game do not have tag `mineable/pickaxe`.
- Removed the wall for rough sandstone, rough red sandstone and rough soul sandstone, because their textures are fully the same to the walls of sandstone, red sandstone and soul sandstone.
    - Blocks and items described about that already existed before, will be converted.
    - This update automatically applies to all Minecraft versions.
    - Actually, vanilla sandstone wall and red sandstone wall, and the soul sandstone wall of Blockus, have the same texture on all sides, and in theory they are closer to rough sandstone, rough red sandstone and rough soul sandstone. But as they already exist in vanilla, the mod no longer provides walls for these three rough sandstone blocks.
    - **Previously, in the version 3.0.5 of this mod, shape variants for many blocks were deleted, but the deletion only applied to Minecraft version 1.21.3 and above. Blocks placed in previous Minecraft versions, when the world is updated to versions 1.21.3 and above, will be automatically substituted. These removed blocks, still exist in the current version mods for Minecraft versions before 1.21.3, but may also be removed and automatically substituted in the future. For the list of actual block removal and substitution, please refer to the update log for 3.0.5.**
- Further solved the texture issue related to blocks of cut soul sandstone. This issue may only existed in some Minecraft versions.
- Removed the buttons, fences and fence gates for sulfur tiles, herringbone sulfur bricks, cinnabar tiles and herringbone cinnabar bricks. When updating the mod, these blocks and items will be automatically replaced with relevant blocks of sulfur bricks and cinnabar bricks.
- Since in versions 1.21.1 and 1.21.8 and above, Blockus renamed small logs, now similar to Blockus mod, this mod treats the block and item IDs of old small logs (except those added in new versions like poplar) as aliases of new IDs, and can automatically convert. For example, `extshape_blockus:oak_small_logs_stairs` placed in other versions will be automatically replaced with `extshape_blockus:small_oak_logs_stairs`.
- Added relevant shape variants of copper bricks and copper tuff bricks (if not added yet), and these blocks can now be waxed by crafting from one with a honeycomb.
- Fixed the incorrect stonecutting or copper bricks. Since in Blockus, one copper block can be cut into four copper bricks, in this mod, when cutting copper block to shape variants of copper bricks, the amount is also calculated by multiplying by 4. Cut copper block cutting to copper brick is still 1:1 without changes.
- Fixed the failure of compatibility with the latest Blockus mod for 1.21.4.

### 3.1.6

- Fit the new version Blockus 2.17.3 and 2.16.6, since Blockus has added stairs, slab and wall for some square blocks, this mod no longer adds these blocks. For these blocks, old block and item ids namespaced `extshape_blockus` are registered as an alias for ids namespace `blockus`. For example, block and item ID `extshape_blockus:marble_squares_slab` will be equivalent to `blockus:marble_squares_slab`.
    - Now blocks of chocolate blocks can be directly cut into blocks of chocolate squares in the stonecutter.
- See the update log for 3.1.6-beta.3.

### 3.1.6-beta.3

- Fixed the issue that gingham wool is incorrectly sorted in the Creative Mode inventory.
- Added blocks related to cobblestone bricks, mossy cobblestone bricks, copper bricks and copper tuff bricks. Note: Currently all shapes are added, but the possibility that some shapes (especially button) may be removed in the future is not excluded.
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

### 3.1.0

- Fixed the wrong model of some blocks in Blockus mod.
- Followed the updates of Blockus, added Blockus blocks related to pale oaks, resin.
- Fixed the wrong tag name of some terracotta pillar blocks in Blockus, and added relevant tag translation.
- Fixed the issue that some block variants are still added even if the variants already exist.

### 3.0.5

- Since version 1.21.3, the following blocks in Blockus will be changed:
    - Removed _fences and fence gates_ of the following blocks:
        - stone tiles (replaced with stone)
        - herringbone stone bricks (replaced with stone bricks)
        - andesite bricks (replaced with andesite)
        - herringbone andesite bricks (replaced with andesite)
        - diorite bricks (replaced with diorite)
        - herringbone diorite bricks (replaced with diorite)
        - granite bricks (replaced with granite)
        - herringbone granite bricks (replaced with granite)
        - dripstone bricks (replaced with dripstone)
        - herringbone tuff bricks (replaced with tuff bricks)
        - herringbone deepslate bricks (replaced with deepslate bricks)
        - sculk bricks (replaced with polished sculk)
        - amethyst bricks (replaced with polished amethyst)
        - polished blackstone tiles (replaced with polished blackstone)
        - herringbone polished blackstone bricks (replaced with polished blackstone bricks)
        - polished basalt bricks (replaced with polished blackstone)
        - herringbone polished basalt bricks (replaced with polished basalt)
        - limestone bricks (replaced with small limestone bricks)
        - limestone tiles (replaced with limestone)
        - limestone squares (replaced with limestone)
        - marble bricks (replaced with small marble bricks)
        - marble tiles (replaced with marble)
        - marble squares (replaced with marble)
        - bluestone bricks (replaced with bluestone)
        - bluestone squares (replaced with bluestone)
        - viridite bricks (replaced with viridite)
        - viridite tiles (replaced with viridite)
        - viridite squares (replaced with viridite)
        - obsidian bricks (replaced with small obsidian bricks)
        - netherrack bricks (replaced with polished netherrack)
        - quartz tiles (replaced with smooth quartz)
        - magma bricks (replaced with small magma bricks; buttons are removed and replaced too)
        - herringbone nether bricks (replaced with nether bricks)
        - herringbone red nether bricks (replaced with red nether bricks)
        - herringbone teal nether bricks (replaced with teal nether bricks)
        - large bricks (replaced with bricks)
        - herringbone bricks (replaced with bricks)
        - herringbone soaked bricks (replaced with soaked bricks)
        - herringbone charred bricks (replaced with charred bricks)
        - herringbone sandy bricks (replaced with sandy bricks)
        - sandstone bricks (replaced with small sandstone bricks)
        - red sandstone bricks (replaced with small red sandstone bricks)
        - soul sandstone bricks (replaced with small soul sandstone bricks)
        - chorus bricks (replaced with small chorus bricks)
        - chorus squares (replaced with polished chorus block)
        - phantom chorus bricks (replaced with small phantom chorus bricks)
        - phantom chorus squares (replaced with polished phantom chorus block)
        - herringbone end stone bricks (replaced with small end stone bricks)
    - Removed _all shapes_ of the following blocks:
        - chiseled andesite (replaced with andesite)
        - chiseled diorite (replaced with diorite)
        - chiseled granite (replaced with granite)
        - chiseled mud bricks (replaced with mud bricks)
        - chiseled dripstone (replaced with dripstone)
        - cracked tuff bricks (replaced with tuff bricks)
        - carved tuff bricks (replaced with tuff bricks)
        - chiseled sculk bricks (replaced with sculk bricks)
        - chiseled amethyst (replaced with polished amethyst)
        - chiseled limestone (replaced with limestone)
        - chiseled marble (replaced with marble)
        - chiseled bluestone (replaced with bluestone)
        - chiseled viridite (replaced with viridite)
        - chiseled lava bricks (replaced with lava bricks)
        - chiseled lava polished blackstone (replaced with lava polished blackstone)
        - chiseled magma bricks (replaced with small magma bricks)
        - chiseled prismarine (replaced with prismarine)
        - chiseled dark prismarine (replaced with dark prismarine)
        - chiseled soul sandstone (replaced with smooth soul sandstone)
        - chiseled chorus block (replaced with polished chorus block)
        - chiseled phantom chorus block (replaced with polished phantom chorus block)
        - white oak log (replaced with white oak wood)
        - stripped white oak log (replaced with stripped white oak wood)
        - chiseled concrete bricks (replaced with concrete bricks)
    - Added the following blocks:
        - mossy dripstone (excluding button, fence, fence gate)
        - tuff tiles (excluding button, fence, fence gate)
        - charred nether brick button (charred nether bricks will have blocks of all shapes)
        - polished charred nether brick button (polished nether bricks will have blocks of all shapes)
        - herringbone charred nether bricks (excluding button, fence, fence gate)
        - teal nether brick button (teal nether bricks will have blocks of all shapes)
        - polished teal nether brick button (polished teal nether bricks will have blocks of all shapes)
    - Of the following blocks, as they belong to circular paving blocks, _only slabs and pressure plates_ will be obtained, and blocks in other shapes will be removed and replaced:
        - polished basalt circular paving (replaced with polished basalt)
        - prismarine circular paving (replaced with prismarine)
    - Removed the following blocks:
        - ice brick wall (which exists in Blockus mod)
        - various shapes of cut soul sand stone except construction shapes (replaced with smooth soul sandstone)
        - slab of cut soul sandstone (which exists in Blockus mod)
- The activation times of crimson wart brick and warped wart brick pressure plate will be adjusted from 2 seconds to 1 second.

### 2.2.2

- Updated for Blockus 2.9.2.
- Following Blockus 2.9.2 changes, canceled rainbow glowstone dropping rainbow petals.