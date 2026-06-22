## 更新日志（适用于 Blockus 部分）

### 3.1.5

- 同步 Blockus 变更，现在在创造模式物品栏中，不再为染色的石砖和陶瓦特殊排序。
- 同步 Blockus 变更，为 26.2 版本加入硫黄和朱砂相关方块。

### 3.1.4

- 由于新版本 Blockus 添加了磨制方块的墙，因此本模组不再加入这些方块。本模组会使用 Fabric API 中的 registry alias 功能，使其旧版名称（Extended Block Shapes - Blockus 中的方块）兼容新版（Blockus 中的方块），例如 `extshape_blockus:polished_purpur_wall` 将等价于 `blockus:polished_purpur_wall`。

> Extended Block Shapes 主模组的 3.0.4 版本也进行过一次方块名称变更，但当时使用的是 Minecraft 原版的 DataFixer 实现的，并且不适用于 1.21.3 之前的 Minecraft。未来也可能会改用 Fabric API 的 registry alias，并使这些变更应用于 1.21.3 之前的版本。

### 3.1.3

- 适应 Blockus 新版本。
- 将小型绯红和诡异原木的相关方块加入 `#non_flammable_wood` 物品标签。
- 修复淡灰色石砖相关方块缺失的问题。
- 使用 `@NullMarked`。
- 修复下界合金砖相关方块的物品不能抵抗火的问题。
- 修复不同颜色之间的带釉陶瓦之间会互相合成的问题。

> **重要提醒**
>
> 由于 Blockus 的更新变更了小型原木的命名，模组更新后，如果使用了小型原木相关方块，请备份您的存档。