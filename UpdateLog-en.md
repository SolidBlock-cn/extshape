# Update log

### 3.1.6-beta.3

- Fixed the issue that gingham wool is incorrectly sorted in the Creative Mode inventory.
- Added blocks related to cobblestone bricks, mossy cobblestone bricks, copper bricks and tuff blocks. Note: Currently all shapes are added, but the possibility that some shapes (especially button) may be removed in the future is not excluded.
    - Shape variants of cobblestone bricks and mossy cobblestone bricks are added to `#blockus:stone_blocks` block tag. Shape variants of copper bricks are added to `#blockus:copper_blocks` block tag. Shape variants of copper tuff blocks are added to `#blockus:tuff_blocks` block tag.
    - Shape variants of copper bricks and copper tuff bricks can be normally oxidized, unweathered, waxed or unwaxed.
- Added blocks related to water bricks. This block used not to be added due to the issue of incorrect model, but the issue is not found at present so it is added now.
    - Shape variants of water bricks are all added to `#blockus:water_bricks` block tag.
- Optimized the way the fuels are registered.
    - Now registration will no longer happen for every single block. It can improve loading speed in theory.
    - Now the fuel time is automatically decided according to registered fuel time.
    - Added the verification of fuel time. In the development environment, if a base block can be used as fuel but its shape variants cannot, or a bae block cannot be used as fuel but the shape variants can, exceptions will be thrown.
    - As vanilla treats all items with `#fence_gates` item tags as fuel, some fence gate blocks made of non-burnable blocks were also used as fuels. This issue has now been fixed.
- Now in vanilla, when handling special sorted blocks (such as copper-related blocks), the "shapes to add to vanilla" in the mod's config will also be used.

### 3.1.6-beta.2

- Added the verification feature of wooden block types, and solved the potential issues that some wood blocks may have incorrect sounds.

### 3.1.6-beta.1

- Adjusted the sorting of copper blocks in the creative mode inventory for versions 26.2 and above to match vanilla.
- Fit 26.3-snapshot-1.
    - Now in 26.3 versions, the mod no longer provides wool stairs and slabs, because they already exist in vanilla. The former block and item IDs in the mod will be redirected to vanilla IDs, making use of the registry alias feature of Fabric API. For example, `extshape:red_wood_stairs` will be identical to `minecraft:red_wood_stairs`.
    - Now in 26.3 versions, there are no longer `#extshape:woolen_stairs` and `#extshape:woolen_slabs` block and item tags, and the vanilla `#minecraft:wool_stairs` and `#minecraft:wool_slabs` block and item tags will be directly used.
    - Now in 26.3 versions, petrified oak planks, double smooth stone slab blocks and all extended shapes are added to `#blocks_motion_no_leaves` block tag, so as to be automatically added to block tags like`#blocks_motion`.

### 3.1.5

- Now in the development environment, block tags are verified more. Block Tags related to block harvesting (`#mineable/*`, `#needs_*_tool`, `#incorrect_for_*_tool`, and `#shears_*_breaking_speed` introduced in 26.2), those existing in base blocks must exist in variants block, and those not existing in base blocks must not exist in variants block, otherwise errors are thrown. Meanwhile, the following changes are applied to block tags:
    - Fixed the issue that blocks of waxed copper block of any oxidization level do not have `#needs_stone_tool` tag.
    - Fixed the issue that pumpkins and melons blocks do not have `#sword_efficient` tag.
- Now in the development environment, item tags will be varified. As vanilla `#pressure_plates` and `#stone_pressure_plates` only provide block tags, not item tags, the mod now uses block and item tags `#extshape:pressure_plates` and `#extshape:stone_pressure_plates`, and the block tags `#extshape:pressure_plates` and `#extshape:stone_pressure_plates` will be treated as the aliases of vanilla block tags `#pressure_plates` and `#extshape:stone_pressure_plates`. Meanwhile, the following changes are made on item tags:
    - Fixed the issue that `#pressure_plates` item tag does not contain `#wooden_pressure_plates` and `#stone_pressure_plates` (now item tags do not use `#pressure_plates` and `#stone_pressure_plates` which does not exist in vanilla).
    - Added more stone blocks' pressure plates and buttons to `#extshape:stone_pressure_plates` and `#minecraft:stone_buttons` block and item tags.
- Now in the development environment, stonecutting recipes are verified. If a base block can be cut into some shape variants of some base block (which can be the same or another), while cannot be cut into other shapes (only limited to construction shapes and walls), errors will be thrown. For instance, if A can be cut into stairs and slab of B (A and B can be a same base block), but cannot be cut into a vertical slab of B, an error will be thrown. Besides, as stonecutting is limited to construction shapes and walls, if some block can be cut into other shapes (such as button, pressure plate), an error will be thrown as well. Meanwhile, the following changes are applied to stonecutting recipes.
    - Synchronizing the changes of Minecraft 26.1, now stone can be cut into blocks related to cobblestone, and deepslate can be cut into blocks related to cobbled deepslate, polished deepslate, deepslate bricks and deepslate tiles.
- Now the [tag removal feature of Fabric API](https://fabricmc.net/2026/06/15/262.html#tag-removal) introduced in 0.150.1 will be used, removing non-pickaxe-mineable walls (such as wooden walls and woolen walls) and non-axe-mineable fence gates (such as stone fence gates) directly from `#mineable/pickaxe` and `#mineable/axe` block tags; these blocks no longer have those tags.
- In versions 26.2 and above, the sulfur cube archetype of petrified oak planks and smooth stone double slab are `slow_bouncy`.
- Added stonecutting recipes for blocks of sulfur and cinnabar.

### 3.1.3

- Support Minecraft 26.1 release version.
- Use `@NullMarked`.
- Fixed the issue that items of netherite blocks are not fire-resistant in 26.1.

> Since this version, the version number of the mod file use an add symbol instead of hyphen to identify Minecraft versions, for example `3.1.3+mc26.1` instead of `3.1.3-mc26.1`.

### 3.1.2

- Fixed the issue that the bottom side of bottom slabs and the top side of the top slabs do not emit light when blocked by solid blocks on the four horizontal sides (issue #78).

### 3.1.1

- Fixed the incompatibility with Sinytra Connector.
- Mod updated to 1.21.11.

### 3.1.0

- Adjusted the inclusion rule of block tags `#stone_buttons` and `#stone_pressure_plates` to limit to some stone blocks.
- Fixed the wrong issue of some blocks in Blockus mod.
- Followed up to updates of Blockus, added Blockus blocks related to pale oaks, resin.
- Fixed the wrong tag name of some terracotta pillar blocks in Blockus, and added relevant tag translation.
- Fixed the issue that some block variants are still added even if the variants already exist.

### 3.0.6

- Since version 1.21.4, following vanilla update, all buttons and pressure plates can be harvested by hand with dropping themselves.
    - Buttons and pressure plates for glowstone and clay block also drop themselves. Versions below 1.21.3 are not affected.
    - Buttons and pressure plates of bedrock cannot be harvested in Survival Mode, but when losing support blocks, will drop normally.
    - Except for bedrock, the hardness and resistance of buttons and pressures, if higher than 0.5 in previous versions, will be all adjusted to 0.5. Versions below 1.21.3 are not affected.
- Fixed the issue that buttons and pressure plates of bedrock may not drop when losing support blocks.
- Fixed the issue that buttons and pressure plates of bedrock may be destroyed when pushed by a piston.
- Fixed the issue that items of blocks of gilded blackstone do not have the item tag `#minecraft:piglin_loved`.
- Fixed the issue of mod pathfinding caused by vertical slabs, vertical stairs, quarter pieces and vertical quarter pieces. (Issue #37)

### 3.0.5

- Fixed the issue that in versions 1.21.3 and above, pale moss is not added. Meanwhile, added into block tag `hoe_mineable` and the special recipes in `recipe_tweak`.
- Fixed the issue that some recipes do not correctly substitute vanilla recipes in the `recipe_tweak` datapack.
- Fixed some incorrect sounds of some resin blocks in 1.21.4.
- Since version 1.21.3, added the fence, fence gate, pressure plate and button for blackstone; added fence, fence gate and pressure plate for polished blackstone (the button is vanilla block), and the pressure plate of polished blackstone bricks.
- Fixed the issue that the block/item tag for glazed terracotta slabs (`#extshape:glazed_terracotta_slabs`) is not contained in block/item tag `#minecraft:slab`.

#### The Blockus part of 3.0.5

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
        - netherrack bricks (replaced with polished netehrrack)
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

### 3.0.4

- Fixed the issue that blocks of stone bricks, mossy stone bricks and chiseled stone bricks cannot be harvested with pickaxes.
- Slightly changed the grammar in the text.
- Since the snapshot of 1.21.4, blocks in the mod will be made the following changes:
    - Delete various shapes of logs, stripped logs, stems, stripped stems.
    - Delete various shapes of chiseled stone bricks, chiseled polished blackstone, chiseled nether bricks, chiseled tuff, chiseled tuff bricks, chiseled deepslate.
    - Delete buttons, fences, fence gates of quartz block, chiseled quartz block, chiseled quartz bricks; deleted pressure plates of quartz block.
    - Delete all extended shapes of chiseled sandstone and chiseled red sandstone.
    - Deleted walls of sandstone, cut sandstone, red sandstone, cut red sandstone. Only walls of smooth stone and smooth sandstone retain.
    - Deleted all extended shapes of polished basalt.
    - The changes above do not affect versions before 1.21.3. When upgrading worlds from versions before 1.21.3 to 1.21.4, the blocks above will be replaced with existing blocks via DataFixer.

### 3.0.3

- Fixed the issue that glazed terracotta slabs cannot be harvested with pickaxes.

### 3.0.2

- Fixed the issue that slab loot tables are incorrect. (#65)

### 3.0.1

- Fixed the issue that cobblestone blocks cannot be mined with pickaxe.
- Fixed the issue of duplicate smooth sandstone blocks in block tag `mineable/pickaxe`.

### 3.0.0

- The mod no longer depends on Better Runtime Resource Pack (BRRP) mod.
- Fixed the issues that logs may lack `#buttons` tag.
- Fixed the issue that some wooden fence gates lack `#fence_gates` tag.
- Buttons of pumpkin and melon will no longer have `#wooden_buttons` tag.
- Pressure plates of pumpkin and melon will no longer have `#wooden_pressure_plates` tag.
- Fixed the issue that `#pressure_plates` lack its corresponding item tag.
- Removed the feature in the configuration screen to avoid some specific recipes, and added a built-in datapack to avoid recipe conflicts, which is enabled by default and can be disabled through command `/datapack disbale ...`.
- The command `/extshape:check-conflict` added a new parameter to filter namespaces.
    - Tests recipe conflicts of all namespaces if not specified.
    - Tests recipe conflicts between the specified namespace and vanilla if only specified one.
    - Tests the specified namespaces if specified multiple ones.
- Removed the BlockFamilies feature which lacks practical usage.
- Fixed the issue that various shapes of white oak wood, white oak log, herringbone planks, small log blocks of Blockus cannot be burnt in the furnace.
- Totally removed various shapes for sugar blocks, as it is a falling block and does not meet the criterion of creating various shapes.
- Fixed the issue that some fence gate blocks can be mined with axes.
- Removed block tag `extshape:pickaxe_unmineable` and modified the implementation of block mining.

### 2.2.2

- Fit for new BRRP API.
- Fit Blocks 2.9.2。
- Following Blockus 2.9.2 changes, canceled rainbow glowstone dropping rainbow petals.

### 2.2.1.9

The update only applies to older versions, fixing the issue that the pressure plates and buttons of some blocks like wool may cause the server to crash (issue #80).

This version is published in Jan 2026, which is only a fix update to mods for older MC versions, and still depends on BRRP. It does not mean that the mod for these MC versions will follow up updates for the subsequent updates.

### 2.2.1

- Fixed the issue that andesite is cut into blackstone in a stonecutter.

### 2.2.0

- Fixed the issue of lack of stonecutting related to tuff.
- Added stairs and slabs related to sandstones into tags of Fabric Convention Tags.
- Tweaked some translations: "quarter piece" is translated to <span lang=ja>四半ブロック</span> in Japanese, <span lang=lzh>橫條</span> in Legacy Chinese.
- Corrected the wrong tag name `mishanguc:concrete_fence_walls`, `mishanguc:terracotta_fence_walls` and `mishanguc:stained_terracotta_fence_walls` into `mishanguc:concrete_walls`, `mishanguc:terracotta_walls` and `mishanguc:stained_terracotta_walls`.
- Fixed the issue that loot tables are incorrect for some blocks like snow and clay.
- Fixed the issue that stripping blocks related to mangrove turn them into oak.
- Other code optimization.

### 2.1.5

- Fixed the severe issue of not able to run with 1.20.5 and 1.20.6.

### 2.1.4

- Updated to 1.21.
- Added sounds added by this mod into the registry.
- Added colored blocks to corresponding tags in Fabric Conventional Tags in Fabric API.
- Since 1.21, removed tuff stairs, tuff slab and tuff wall, as those blocks are formally added in Minecraft. If you open worlds created in former versions in 1.21, the mod will utilize DataFixer to convert in-mod tuff stairs (`extshape:tuff_stairs`), tuff slab (`extshape:tuff_slab`) and tuff wall (`extshape:tuff_wall`) into vanilla ones (`minecraft:tuff_stairs`, `minecraft:tuff_slab` and`tuff_wall`). Items also have the same conversion.
- Fixed the issue that the texture of vertical slabs rotates along with blocks.

### 2.1.3

- Adjusted the sorting of some items in the Creative inventory. Some items that already exist in Building Blocks will not be displayed in Natural.
- Fixed the incorrect textures.
- While avoiding wooden wall recipes, recipes for copper wall and waxed copper walls are also avoided, to avoid conflict with the new version copper trapdoor.

### 2.1.2

- Optimized the logic of item display in creative inventory and items are displayed faster.
- Fixed a crash error related to creative inventory along with Sinytra Connector.
- Improved some language files.

### 2.1.1

- Fixed the incorrect subtitle of sound.
- Fixed the issue that Extended Block Shapes Blockus cannot run correctly due to compilation issue.
- Fixed the issue that pressure plates with special features (such as sculk pressure plate) cannot work normally.
- Adjusted again the activation time of some pressure plates and buttons.
- Adjusted some loot tables. Now blocks who drop other items, no matter when dropping other items, or dropping themselves under the effect of Silk Touch enchantment, when the block is a double slab, the dropped stacks are doubled.
- Fixed the issue that sculk buttons do not drop experience upon harvested.

### 2.1.0

- Added blocks of various shapes for unwaxed copper blocks (waxed blocks already exist in previous versions). Now these blocks can be normally waxed and de-waxed. Unnwaxed block can be oxidized or restored.
- Fixed the issue that activated pressure plates or buttons cannot release normally after changing blocks (such as wood pressure plates or buttons being stripped).
- Adjusted the activation time for some pressure plate blocks, instead of all 20 ticks.
- (For 1.20.4) Added blocks of various shapes for tuff variants. Meanwhile, considering tuff stairs, tuff slab and tuff walls are experimental features, they are not regarded as formal content.
- Catching up to updates of Blockus mod, added various shapes for mossy planks.
- Adjusted the crafting ingredient of fences and fence gates of some blocks.
- Adjusted sounds of some fence gates, pressure plates and button blocks, as well as the trigger behavior.
- Adjusted the crafting recipes to crafting with 3 wool carpets or moss carpets.
- Added fences, fence gates and pressure plates for patterned wool and gingham wool (but not buttons). A pressure plate is crafted from 3 carpets.
- No longer allows charring planks and wooden mosaic in various shapes.
- Made blocks of sugar blocks invisible in creative inventory and removed crafting recipe, because sugar block is a falling block, however, the variants in various shapes do not implement this feature.
- Added button and pressure plate for stone bricks, mossy stone bricks, bricks block, nether wart and warped wart.
- Fixed the issue that the texture of blocks of blackstone on top and bottom side is incorrect.
- Adjusted the second crafting ingredient of fences and fence gates (including those whose base block is from Blockus mod).
- Improved the compatibility with Sinytra Connector (but the part of Extended Block Shapes Blockus is not compatible yet).

### 2.0.9

- Fixed the issue that plank blocks are not flammable.

### 2.0.8

- Again, fixed the issue that when some mods are installed, crashes happen because of inventories.

### 2.0.7

- Fixed the issue that when some mods are installed, crashes happen because of inventories.

### 2.0.5

- Fixed the issue of incompatibility with Blockus.

### 2.0.4

- Modified the piston behavior of blocks to match base blocks. Pressure plates and buttons of unmovable blocks are unmovable, and other pressure plates and buttons will be destroyed by piston.
- Modified the note block instrument to match vanilla. The note block instrument of blocks (except button) is the same as the base block, but the note block instrument is always harp (the default instrument).
- To match the vanilla behavior, buttons will not display colors in a map.
- For Blockus mod: fixed the issue of missing stairs, slabs and walls for concrete blocks.

### 2.0.3

- Fixed the issue that some blocks lack tags such as `minecraft:mineable/pickaxe`.
- Fixed the issue that walls of non-`mineable/pickaxe` blocks are pickaxe-mineable.
- Fixed the correct recipes of slabs and stairs of pattered wools in Blockus mod.
- Fixed the issue that some blocks of pattered wools in Blockus are not shears-mineable.
- Fixed the issue that herringbone cherry planks blocks cannot be charred into relevant charred planks blocks.
- Fixed the identifying of mining tools of blocks during data generation.
- For versions 1.20 and above, ore blocks can now be crafted into Blockus ore brick blocks.

### 2.0.2

- Adapt to new version Blockus mod.
- Fixed the issue that vertical stairs have the incorrect tag `extshape:vertical_slabs` instead
  of `extshape:vertical_stairs`.
- Adjusted the way item groups are modified.
- Modified the loot table of variants of Stars Block and Nether Star Block in Blockus mod.

### 2.0.0

- Adapted to the new version of Better Runtime Resource Packs.
- Fixed the issue that some bedrock blocks occupy the empty loot table.
- The permission level of `/extshape:check-conflict` has been adjusted to 4.
- Fixed the issue that Blockus white oaks cannot be stripped with axe.

### 1.9.0

- Added Korean translation, contributed by PR#25。
- Adapted to Blockus mod.
- Fixed the issue that amethyst blocks do not play sound effects when hit by projectile.
- Added the localized mod name in Mod Menu mod.
- Adjusted tags related to pressure plates and buttons.
- Fixed the issue that blocks of coal blocks cannot be fuels in the furnace.
- Fences, fence gates, buttons and pressure plates can also have special features now, such as reaction when hit by projectiles.
- Added all shapes for sculk blocks.
- Modified the sound of fence gate open and close of amethyst, which is the same as break sound.
- Optimized some contents related to RecipeGroup to be more extendable.
- Fixed the wrong number of vertical quarter pieces crafted from vertical slab.
- Fixed the issue that block rotation recipe content does not match their names.
- Fixed the issue of duplicated writing of resource packs.

### 1.8.1

- Added slabs with directions for pillar blocks with uvLock, such as bamboo block and cherry log.
- Fixed the conversion issue for block names that end with "木材".

### 1.7.3

- Adjusted the code structure and improved extension behaviour.
    - Added `BlocksBuilderFactory` class, to allow different mods to add their blocks. Also, made sure `ExtShapeBlocks.BLOCKS` and `ExtShapeBlocks.BASE_BLOCKS` contains only blocks in this mod.
    - Added `TagPreparationFactory` class, to allow different mods to add tags with classes of this mod, and reduce conflicts to existing content.
    - Split the code about generating data between different block shapes to `CrossShapeDataGeneration` and improved the readability.
- Fixed the issue that the direction of the default block state of glazed terracotta slab is not successfully set.
- Now `#extshape:log_blocks` belongs to block tag `#extshape:wooden_blocks`.
- Optimized the logic of adding items in 1.19.3 to be more efficient.
- Fixed the issue that `/extshape:config` has no effect.

### 1.7.2

- Added the name of translations in some languages.
- For 1.19.3, added extended shapes for bamboo block, stripped bamboo block, bamboo planks, and bamboo mosaic, which require Update 1.20 datapack.
    - The blocks are flammable and can be used as fuels.
    - In order to avoid recipe conflict, bamboo blocks and stripped bamboo blocks cannot be crafted into buttons.
- In block tags, log and wood blocks belong to wooden blocks now. Wood fences and bamboo fences are considered as wooden fences and can be connected with vanilla wooden plank fences.
- For 1.19.3, no longer interact with Reasonable Sorting mod.
- For 1.19.3, as not supported yet, disabled "Display Specific Groups" button.

### 1.7.1

- Adapted to new version Reasonable Sorting mod.
- Fixed the issue that blocks and items are not registered according to their shapes.
- Fixed some translations.

### 1.7.0

- Renamed "XXX Vertical Stairs", "XXX Vertical Slab" to "Vertical XXX Stairs" and "Vertical XXX Slab". Names in `lzh` will be left unchanged.
- Added shapes for logs and woods (blocks with 6-face sparks) as well as hyphaes and stems, including their stripped variants.
- Added shapes for dirt, coarse dirt, coal block, froglights, deepslate, polished deepslate and sculk.
- Fixed incorrect texture for quarter piece block.
- The command `extshape:check-confict` can only be executed by player now.
- Optimized the code.

### 1.6.0

- Adjusted the direction of vertical stairs, vertical slab, quarter piece, vertical quarter piece when placed. Now it depends on the position that the cross-hair aims instead of player facing.
- (Only Fabric) Fixed the grammatical error in the mod description and incorrect link.
- (Only Fabric) Fixed the issue of texture direction of glazed terracotta slabs.
- (Only Fabric) Adjusted the fuel burn time of wooden wall to be same as wooden planks.
- Adjusted some language files.
- You may configure which conflicting recipes to allow.
- (Only Forge) Fixed the issue that you cannot shear woolen blocks with shears.

### 1.5.2

- Optimized the configuration screen of the mod.
- Chiseled sandstone, chiseled red sandstone, chiseled quartz, cut sandstone, cut red sandstone cannot be used to craft stairs and slabs, in order to avoid conflict with the vanilla crafting recipe.
- Wooden walls are no longer allowed to craft, to avoid conflict with the vanilla crafting recipe.
- Fixed the recipe conflict of fence and fence gate of snow block and clay block to walls.
- Added `/extshape:check-conflict` command to test conflict between recipes.
- (Only 1.19) Added `dampens_vibrations` tag, with the same content as `occludes_vibration_signals`.
- (Only Forge) Fixed the grammatical error in the mod description and incorrect link.
- (Only Forge) Fixed the issue of texture direction of glazed terracotta slabs.
- (Only Forge) Adjusted the fuel burn time of wooden wall to be same as wooden planks.

### 1.5.1

- (For Minecraft 1.19) Added shapes for mangrove planks, packed mud, and mud bricks.
- (For Minecraft 1.19) Altered the `zh-cn` translation for "Brick", as vanilla Minecraft does.
- Fixed the incorrect method of `HorizontalCornerDirection.random`.
- Recursive stonecutting is allowed now. For example, a stone can be cut into multiple shapes of chiseled stone bricks.
- Adjusted the recipe of snow slab, avoiding conflict to the recipe of snow.

### 1.5.0

The update does not seem obvious, but code is changed from head to toe, actually. Mod is tested repetitively to ensure stability, and published only rounds of debugs and modifications. This version is developed at the same time with BRRP, so the developing process can find out BRRP-related issues as well.

- Since this version, the mod relies on BRRP (Better Runtime Resource Pack). As BRRP nests ARRP, it's not required to install ARRP anymore.
- Optimized code massively, and unnecessary codes are removed.
- Blocks based on pumpkin, melon, moss, shroomlight, nether wart block, warped wart block can be composted.
- Re-added nether wart walls and crimson wart walls, and added walls made from wool and moss.
- Added more tags:
    - Now more blocks based on gold block and raw gold block are loved by piglins (item tag `#piglin_loved`).
    - If the whole top face of a grass block is covered by a snow stairs or snow slab, the grass block will display snowy (block tag `#extshape:snow`).
    - Mining blocks based on gold block, raw gold block and gilded blackstone irritates piglins (block tag `#piglin_guarded`).
    - Dripleaves can be placed on blocks of moss and clay (block tag `#small_dripleaf_placeable_on`).
    - Wool blocks, like vanilla wools, can be sheared quickly (block tag `#fabric:mineable/shears`).
    - Re-added walls of non-stone blocks, which cannot be harvested quickly by pickaxes. Although `#minecraft:mineable/pickaxes` directly contains `#minecraft:walls`, this mod lets blocks of `#extshape:pickaxe_unmineable` no possible to be harvested quickly by pickaxes.
- Added a corresponding unlocking advancement for recipes. When obtaining some ingredients, the advancement is triggered and the recipe is unlocked.
- Added configuration screen, to config whether to add items into vanilla item groups, and whether to add extra item groups for this block. Besides, the mod adds the feature of manually re-generate and dump runtime resource packs.
- Fixed some issues in language files, and refined some wording.
- Added blocks in other all shapes for waxed copper blocks of all oxidation level and raw ore blocks.
- Fixed the conflict in the recipe of moss pressure plate and moss carpet. Like wool blocks, a moss pressure plate is now crafted from a moss carpet.
- The license is widened to LGPL 3.0 to fix copyright incompatibility with Minecraft.
- As there are no conflicts in recipes, melon buttons can be crafted with a melon block now.
- Metal blocks (such as netherite blocks and ancient debris) can be stonecut now.
- Adjusted arrangement of items in Creative Mode item groups.

### 1.4.0

- Replaced traditional resource packs and data packs with ARRP (Advanced Runtime Resource Pack). The file size of the mod has been largely reduced, but since this version, the mod depends on ARRP to run. Make sure you have ARRP mod installed.
- Fixed the conflict in recipes of melon buttons and pumpkin buttons. Recipes of the two are removed.
- Fixed the issue that pressure plates use the same model whether pressed down.
- Fixed the issue that water logged in blocks does not flow when there is a block update.

### 1.3.1

- <span style="color:red">Removed nether wart wall and warped wart wall.</span>
- Added more blocks, main buttons.
- Fixed the issue that, in the constructor of BlocksBuilder with <code>null</code> parameters, objects are created unexpectedly, which may cause some buttons and pressure plates to crash.
- Fixed the potential recipe conflict of block of iron, gold, diamond, emerald and lapis. <b>These blocks still exist, but can no longer be crafted.</b>
- Slightly adjusted item groups.
- Fixed the issue that some stone blocks cannot be cut in stone-cutters.

### 1.3.0

This update is for version only 1.17 and above. Versions for older versions will come later.

- Added co-working with Reasonable Sorting mod (versions above 1.13).
- Fixed the conflict between wool pressure plate and wool carpet. Wool pressure plates are crafted from a carpet in the same color now.
- Adjust some code.
- Added different shapes for dripstone, honeycomb block, moss block, etc.

### 1.2.6

This update is for version only 1.17 and above.

- Added multiple shapes for smooth basalt.
- Started supporting 1.18 snapshot versions.
- Tweaked mod description.

### 1.2.5

- Fixed the issue of missing rendering at the bottom of vertical stairs blocks.
- For 1.16 versions: Removed unnecessary tags and fixed mining level issue.
    - Note: For 1.16 versions, language files for future versions are still stored.

### 1.2.4

- Removed plank wall and clay wall.

### 1.2.3

- Starting backwards compatibility (will release later qwq).
- Fixed the issue that you cannot craft a smooth stone slab with double stone slabs block in this mod.
- Fixed the issue that some blocks have no vertical slabs, and errors are thrown when loading data-packs.
- Fixed the issue that codes in Mineable class are run. Codes in some other block tags will also be run only in the data generation process, instead of in an ordinary game environment.
- Referring to vanilla Minecraft, added some block tags, and added item tags.

### 1.2.2

- Improved logging system.
- Fixed the issue of incompatibility with OptiFine.
    - Issue details: When OptiFine is on, operations like `new Identifier("#minecraft:banners")` do not throw
      `InvalidIdentiferException`, causing code abnormal behavior.

### 1.2.1

- Fixed the issue that ButtonMixin is only run on the client side.
- Now ExtShapeTag class extends AbstractCollection, instead of merely implementing Iterable interface.
- Removed some code not used anymore.
- Fixed the issue that vertical quarter pieces and vertical stairs are not waterlogged when placed in water. (Note:
  Fence gates, buttons and pressure plates are not water-loggable because of vanilla Minecraft code, which is not modifiable by the mod.)
- Fixed the issue that "Others" item group uses prismarine block as icon, while prismarine block is not in this item group.

### 1.2.0

- Deleted extra item groups. Vanilla item groups are used instead. Besides, there are 4 other item groups in order to contain blocks (including vanilla) in sort of base blocks.
    - The issue that items are not sorted correctly in recipe groups is also fixed because of this change.
- BlockMappings use more effective BiMap instead of ordinary Map any longer. Values in BiMap are unique, so it's more effective to get an inverse map.
- Added JavaDoc.
- Referring to vanilla convention, English names of blocks in this mod are capitalised now.
- Fixed naming error of quarter piece, vertical quarter piece and vertical stairs blocks in English.
- Changed recipes of vertical slabs, vertical quarter pieces and vertical stairs. These blocks can now be crafted from one corresponding slab, quarter piece of vertical stairs, which can be seen as rotating, instead of crafting from 3; you can also "rotate" them "back".

### 1.1.1

- Added vertical stairs, quarter pieces and vertical quarter pieces. Quarter pieces and vertical quarter pieces can be crafted 1:2 from slabs/vertical slabs in crafting tables, and some of those can be crafted 1:4 from full blocks or 1:2 from slabs/vertical slabs in stone cutters.
- Removed some blocks.
- Added variant blocks for clay and end stone, etc.
- Removed pressure plates and buttons for some blocks. Therefore, some conflicts between recipes have been fixed meanwhile.
- Adjusted display form of vertical slabs in inventories.
- Added language support for Traditional Chinese Taiwan, Traditional Chinese Hong Kong and Classical Chinese Language.
- Issues currently existing in this version:
    - In vanilla, some blocks, such as packed ice, glowstone, snow block, cannot drop themselves when mined in survival mode without Silk Touch. However, their variant blocks are not affected.
    - The bottom half of slab and quarter piece of packed ice and bottom-half packed ice stairs are not slippery.
    - In recipe books, recipes added by this mod are not correctly classified.

### 1.1.0-snapshot [Snapshot version]

- Added vertical slabs.
- Added stairs, slab, fence and fence gate for packed ice.
- Glazed terracotta slabs can be rotated now.
- Removed deprecated code.
- Identifiers and base blocks are not stored inside objects any longer; Minecraft vanilla registry for identifiers and specific maps for relations between blocks and their base blocks are used instead.
- Imports data from vanilla BlockFamilies to BlockMappings in this mod. In the future, this mod's BlockMappings may be directly merged into BlockMappings.
- Issue existing in this version: Packed ice slab bottom half and the first stage of bottom-half stair are not slippery.
- Added item groups where items are sorted by on base blocks.
- Undone.

### 1.0.0

Updated on Apr. 5th 2021

- Added stairs, slabs, fences, fence gates, pressure plates for multiple mods.
- In this version, only simplified Chinese (zh_cn) and English (en) are supported.
- Before this release, a data generation system was used similar to vanilla Minecraft, which has been deprecated, replaced with a new data generation system. But the deprecated one still remains in the code.
- The issue exists in this version: In recipe books, recipes added by this mod are not correctly classified.
