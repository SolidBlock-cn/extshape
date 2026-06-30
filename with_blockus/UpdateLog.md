## 更新日志（适用于 Blockus 部分）

### 3.1.6-beta.3

- 修复方格羊毛在创造模式物品栏中的排序错误的问题。

### 3.1.5

- 同步 Blockus 变更，现在在创造模式物品栏中，不再为染色的石砖和陶瓦特殊排序。
- 同步 Blockus 变更，为 26.2 版本加入硫黄和朱砂相关方块，以及相应的切石配方。
- 调整了以下方块标签：
    - 修复烈焰灯笼相关方块被错误加入 `#blockus:blaze_bricks` 方块标签的问题。
    - 修复灵魂砂岩的扩展形状的方块未加入 `#blockus:soul_sandstone` 方块标签的问题。
    - 修复紫颂块没有 `#sword_efficient` 方块标签的问题。
    - 修复大型树脂砖、人字形纹（herringbone）树脂砖相关方块没有 `#resin_blocks` 标签的问题。
    - 白色橡木、去皮白色橡木、粗竹和青苔木板相关方块不再直接在各形状标签（如 `#extshape:vertical_slabs`）中定义，而是加入相应的木制的形状标签（如 `#extshape:wooden_vertical_slabs`）。
- 同步主模组的变更，利用 Fabric API 的[标签移除](https://fabricmc.net/2026/06/15/262.html#tag-removal)功能，将本模组中的一些墙和栅栏门移出 `#mineable/pickaxe` 和 `#mineable/axe` 标签。
- 调整方块切石配方，以同步 Blockus 模组更改或修复相关问题（这里所说的“相关方块”，包括一些 Blockus 方块的 Blockus 模组中没有的原版形状变种，如台阶，以及本模组中的扩展形状的方块，如竖直台阶）：
    - 修复切制灵魂砂岩不能切石的问题。
    - 现在灵魂砂岩可切成切制灵魂砂岩相关方块。
    - 现在 Blockus 模组的磨制末地石可切成原版的末地砖相关方块。
    - 现在树脂砖可切成人字形纹（herringbone）树脂砖和大型树脂砖相关方块。
    - 现在石头现在可切成石瓦（stone tiles）相关方块。
    - 现在凝灰岩、磨制凝灰岩、凝灰岩砖可切成凝灰岩瓦相关方块。
- 将一些石质的压力板按钮加入 `#extshape:stone_pressure_plates` 和 `#minecraft:stone_buttons` 方块和物品标签。
- 修复 26.2 中带釉陶瓦柱相关方块纹理缺失的问题。

> 注：一些较早期的 Minecraft 版本中，Blockus 模组中的方块标签以及切石配方可能与此处描述的有所不同，本模组也会相应调整以匹配 Blockus 模组。

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