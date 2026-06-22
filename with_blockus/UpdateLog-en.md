# Update log (for Blockus part)

### 3.1.5

- Synchronizing to Blockus changes, now in the Creative Mode inventory, no special sorting is applied for dyed stone bricks and terracotta.
- Synchronizing to Blockus changes, added blocks related to sulfur and cinnabar for 26.2.

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