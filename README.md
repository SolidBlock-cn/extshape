# 扩展方块形状模组 Extended Block Shapes Mod

本模组为许多的原版方块添加了其楼梯、台阶、栅栏、栅栏门、按钮和压力板，以及原版不具有的纵台阶、纵楼梯、横条、纵条。具体内容列表请参考[此文件](BlockList.md)。

This mod adds stairs, slabs, fences, fence gates, button and pressure plates for many vanilla blocks, along with
vertical slabs, vertical stairs, quarter pieces and vertical quarter pieces that does not exist in vanilla Minecraft.
List of the contents can be found in [this file](BlockList.md).

模组目前仅支持 Minecraft Java 版 1.17.x，并需要 Fabric API。

At present this mod supports only Minecraft Java Edition 1.17.x and requires Fabric API.

## 特性 Features

### 方块 Blocks

本模组添加的方块继承了其基础方块的大多数特性。

Blocks added by this mod inherit most features of their base blocks.

所有的羊毛制品（即以羊毛为基础的方块，包括各种颜色的羊毛楼梯、羊毛台阶等）均可以燃烧且可以作为燃料（其燃烧时间与原版完整方块相同）。羊毛制品无论是否占了一整格，均具有阻挡潜声传感器（Sculk Sensor）的功能。

All woolen blocks (blocks based on wool block, including wool stairs, woo slabs in all colors) are flammable and can be
used as fuels (fueling time equals their base blocks). All woolen blocks, no matter they occupy a full block, can block
sculk sensors.

所有的下界岩制品（下界岩1.16之前称为地狱岩）均可无限燃烧，基岩制品在末地可无限燃烧。

All netherrack blocks are infiniburn. Bedrock blocks are infiniburn in the end.

下界合金制品的掉落形式能够抵抗火焰、熔岩。

Netherite blocks as dropped items resist fire and lava.

所有的楼梯、台阶、栅栏、墙的硬度、挖掘工具和挖掘时间与其基础方块相当（这是参照了原版的特性），而非与其基础方块成正比。例如，破坏羊毛台阶的时间与破坏整个羊毛的时间相同，而非前者是后者的一半。

The hardness, mining tools and mining time of all stairs, slabs, fences and walls equal to their basic blocks (like
vanilla features). For example, the time spent breaking a wool slab equals to a full wool block, instead of half of the
latter.

在原版中，大多数压力板和按钮的硬度为其基础方块的1/4到1/3。本模组添加的压力板和按钮的硬度均为基础方块的1/4。（基岩制品在生存模式不可破坏，但是基岩按钮、基岩压力板在失去其依靠的方块时仍会正常掉落）。本模组对按钮进行了扩展，质地较软的按钮（如羊毛按钮、雪按钮）的触发时长为60刻（3秒），质地很硬的按钮（如黑曜石按钮、基岩按钮）的触发时长为5
刻（1/4秒）。参照原版代码，所有的按钮、压力板均无碰撞箱。

In vanilla, the hardness of pressure plates and buttons equals to 1/4 or 1/3 of their basic blocks. All pressure plates
and buttons added in this mod have the hardness same as 1/4 of their base blocks. (Bedrock blocks are invulnerable in
Survival Mode, but bedrock pressure plates and bedrock buttons drop normally when losing blocks relied on.) This mod
simply extends buttons: soft buttons (wool buttons, snow buttons) trigger 60 ticks (3 seconds); hard buttons
(obsidian buttons, bedrock buttons) trigger 5 ticks (1/4 second). All buttons and pressure plates have no collision box
like vanilla MC.

另外此模组还添加了“双层平滑石台阶”方块和“石化橡木木板”方块，分别可以合成平滑石台阶和石化橡木台阶（原版的平滑石块也可以合成平滑石台阶）。

Besides, this mod also adds "double smooth stone slab" and "petrified oak planks" block, respectively crafting
ingredient of smooth stone slab and petrified oak slab. (Still, vanilla smooth stone block can also be crafted into
smooth stone slab).

### 合成与烧炼 Crafting and smelting

所有的方块均可使用其基础方块参照类似原版合成表合成，部分可以通过切石机合成。具体为：楼梯在工作台可3:2合成（原料:产物，下同），在切石机可1:1合成。台阶和纵台阶在工作台、切石机可1:2合成。

The crafting recipes of all blocks in this mod are similar to those in vanilla. Some can also be crafted with a stone
cutter. Stairs can be crafted 3:2 in crafting table (ingredient:result) and 1:1 in the stone cutter. Slabs and vertical
slabs are craftable 1:2 in either crafting table or stone cutter.

台阶、楼梯、横条可以直接在合成表中旋转形成对应的纵向方块，也可以“转回来”。例如 1 个台阶可以合成 1 个对应的纵台阶，1 个纵台阶也可以直接合成 1 个对应的台阶。

Slabs, stairs and quarter pieces can be rotated in crafting recipes to be corresponding vertical blocks; you can also "
rotate them back". For instance, one slab can be crafted into one corresponding vertical slab, and one vertical slab can
also be crafted into a corresponding slab directly.

各个形状方块在工作台中的合成关系如下：

- 6×基础方块 → 4×楼梯
- 3×基础方块 → 6×台阶
- 3×台阶 → 6×横条 （台阶水平排列）
- 1×台阶 ↔ 1×纵台阶
- 1×楼梯 ↔ 1×纵楼梯
- 1×横条 ↔ 1×纵条
- 3×纵台阶 → 6×纵条 （纵台阶竖直排列）

Crafting recipes of blocks in different shapes are as follows:

- 6×base blocks → 4×stairs
- 3×base blocks → 6×slabs
- 3×slabs → 6×quarter pieces (slabs arranged horizontally)
- 1×slab ↔ 1×vertical slab
- 1×stairs ↔ 1×vertical stairs
- 1×quarter piece ↔ 1×vertical quarter piece
- 3×vertical slabs → 6×vertical quarter pieces (vertical slabs arranged vertically)

部分方块的各个形状的方块在切石机中的合成关系如下：

- 1×基础方块 → 1×楼梯 / 1×纵楼梯 / 2×台阶 / 2×纵台阶 / 4×横条 / 4×纵条
- 1×楼梯 → 3×横条
- 1×台阶 → 2×横条
- 1×纵楼梯 → 3×纵条
- 1×纵台阶 → 2×横条 / 2×纵条

Stone-cutting recipe of some blocks are as follows:

- 1×base block → 1×stairs / 1×vertical stairs / 2×slabs / 2×vertical slabs / 4×quarter pieces / 4×vertical quarter
  pieces
- 1×stairs → 3×quarter pieces
- 1×slab → 2×quarter pieces
- 1×vertical stairs → 3×vertical quarter pieces
- 1×vertical slab → 2×quarter pieces / 2×vertical quarter pieces

注意：本模组中的物品暂不能通过切石机像原版 Minecraft 那样进行“递归切石”。例如，原版游戏中，一个未磨制的安山岩可以直接切石成磨制的安山岩楼梯或者台阶。模组中的方块暂时不能这样切石。

NOTE: Items in this mod are not recursively stone-cuttable like some items in vanilla Minecraft. For example, in vanilla
Minecraft, an unpolished andesite can be cut into a polished andesite stairs or slab. Blocks in this mod can not be cut
like this.

栅栏和栅栏门合成时的原材料（除了基础方块之外），参照既有合成表。石质栅栏和栅栏门的原材料使用燧石，羊毛的为线，砂岩及其变种的为木棍。

Ingredients of fences and fence gates, apart from base blocks, resemble vanilla recipes. For the crafting of stone
fences and fence gates, flint is used along with base blocks, and string for wool, stick for sandstone and its variants.

台阶和纵台阶作燃料时，其燃烧时间为基础方块的一半。按钮作燃料时，其燃烧时间为基础方块的1/3。横条、纵条作燃料时，其燃烧时间为基础方块的1/4。

Used as fuels, slab and vertical slab burn half the time of its base blocks, button burns 1/3 the time of base blocks,
and quarter piece and vertical quarter piece burns 1/4 the time of basic blocks.

### 创造模式物品栏 Creative Inventory

在创造模式下，我们设置了多个物品组来以不同方式区分物品。

In creative mode, multiple item groups are set in order to sort items in different ways.

参照原版的物品分组，将本模组添加的方块分为建筑方块、装饰性方块和红石。此外，还设置了几个物品组，以按照不同的基础方块排列各方块（含原版方块），以便玩家快速获取同一方块的多个形状。

Referring to vanilla item grouping, blocks added in this mod are sorted as building blocks, decoration blocks and
redstone. Besides, several extra item groups are set to sort blocks (including vanilla blocks) by their basic blocks, so
that players can easily fetch blocks of a same block in different shapes.

## 更新日志 Update log

### 1.2.2
- 改善了日志系统。
- Improved logging system.
- 修复了与 OptiFine 不兼容的问题。
- Fixed the issue of incompatibility with OptiFine.
  - 问题详情：在启用了 OptiFine 的情况下，类似于 `new Identifier("#minecraft:banners")` 这样的操作不会抛出 
    `InvalidIdentifierException`，导致代码无法正确运行。
  - Issue details: When OptiFine is on, operations like `new Identifier("#minecraft:banners")` do not throw 
    `InvalidIdentiferException`, causing code abnormal behavior.

### 1.2.1
- 修复了 ButtonMixin 仅在客户端执行的问题。
- Fixed the issue that ButtonMixin is only run on client side.
- 现在 ExtShapeTag 类继承了 AbstractCollection，而不再是仅仅实现 Iterable 接口。
- Now ExtShapeTag class extends AbstractCollection, instead of merely implements Iterable interface.
- 移除了部分不再使用的代码。
- Removed some code not used anymore.
- 修复了纵条和纵楼梯在水中放置时默认不含水的问题。（注：栅栏门、按钮和压力板不能含水这是由 Minecraft 原版决定的，模组暂时无法修改。）
- Fixed the issue that vertical quarter pieces and vertical stairs are not waterlogged when placed in water. (Note: 
  Fence gates, buttons and pressure plates are not water-loggable because of vanilla Minecraft code, which is not 
  modifiable by the mod.)
- 修复“其他”物品组以海晶石作为图标但海晶石不在该物品组的问题。
- Fixed the issue that "Others" item group uses prismarine block as icon, while prismarine block is not in this item 
  group.

### 1.2.0

- 删除了额外的物品组，将物品添加到原版物品组中。另外仍有4个用于分类存放方块及其变种的额外物品组仍保留。
- Deleted extra item groups. Vanilla item groups are used instead. Besides, there are 4 other item groups in order to
  contain blocks (including vanilla) in sort of base blocks.
    - 因为物品添加到了原版物品组中，所以同时修复了在配方书中归类错误的问题。
    - The issue that items are not sorted correctly in recipe groups is also fixed because of this change.
- 方块映射由普通的 Map 改为更加高效的 BiMap（双向映射）。BiMap 的值也是不能重复的，从而更高效地得到反向映射。
- BlockMappings use more effective BiMap instead of ordinary Map any longer. Values of BiMap is unique, so it's more
  effective to get an inverse map.
- 添加了 JavaDoc。
- Added JavaDoc.
- 参照原版习惯，本模组中的方块的英文名改为每个单词首字母大写形式。
- Referring to vanilla convention, English names of blocks in this mod are capitalised now.
- 修复了横条、纵条、纵楼梯方块在英文模式下的命名错误。
- Fixed naming error of quarter piece, vertical quarter piece and vertical stairs blocks in English.
- 修改了纵台阶、纵条、纵楼梯的配方。这些方块可以直接由1个对应的台阶、横条、楼梯合成而成（相当于进行旋转），不再需要由3个合成；同时也可以转回去。
- Changed recipes of vertical slabs, vertical quarter pieces and vertical stairs. These blocks can now be crafted from
  one corresponding slab, quarter piece of vertical stairs, which can be seen as rotating, instead of crafting from 3;
  you can also "rotate" them "back".

### 1.1.1

- 添加了纵楼梯、横条、纵条。其中横条、纵条占用1/4方块位置，可使用3个台阶/垂直台阶1:2合成，部分方块可在切石机内用1个完整方块1:4合成或用台阶/垂直台阶1:2合成。
- Added vertical stairs, quarter pieces and vertical quarter pieces. Quarter pieces and vertical quarter pieces can be
  crafted 1:2 from slabs/vertical slabs in crafting tables, and some of those can be crafted 1:4 from full blocks or 1:2
  from slabs/vertical slabs in stone cutters.
- 移除了部分方块。
- Removed some blocks.
- 加入了黏土块、末地石等方块的衍生方块。
- Added variant blocks for clay and end stone etc.
- 移除了部分方块的压力板、按钮，因而同时修复了部分合成表的冲突问题。
- Removed pressure plates and buttons for some blocks. Therefore, some conflicts between recipes have been fixed
  meanwhile.
- 调整了纵台阶在物品栏中的显示形式。
- Adjusted display form of vertical slabs in inventories.
- 添加了台湾繁体中文（zh_tw）、香港繁体中文（zh_hk）和文言文（lzh）语言支持。
- Added language support for Traditional Chinese Taiwan, Traditional Chinese Hong Kong and Classical Chinese Language.
- 当前版本仍然存在的问题：
- Issues currently existing in this version:
    - 在原版中，部分方块，如浮冰、荧石、雪块，其生存模式下若无精准采集，破坏后无法获得原方块，但其衍生的所有方块均未受影响。
    - In vanilla, some blocks, such as packed ice, glowstone, snow block, cannot drop themselves when mined in survival
      mode without Silk Touch. However, their variant blocks are not affected.
    - 浮冰台阶下半台阶、横条和正立的浮冰楼梯第一阶顶部表面不滑。
    - The bottom half of slab and quarter piece of packed ice and bottom-half packed ice stairs are not slippery.
    - 配方书中，本模组添加的配方归类不正确。
    - In recipe books, recipes added by this mod are not correctly classified.

### 1.1.0-snapshot [快照版 Snapshot version]

- 添加了纵向台阶。
- Added vertical slabs.
- 为浮冰添加了楼梯、台阶、栅栏、栅栏门。
- Added stairs, slab, fence and fence gate for packed ice.
- 现在带釉陶瓦台阶可以旋转了。
- Glazed terracotta slabs can be rotated now.
- 移除了弃用的代码。
- Removed deprecated code.
- 不再在各对象内存储其 id、基础方块等信息，而是使用 Minecraft 原版注册表储存并调取 id，并使用映射储存方块与其基础方块的对应关系。
- Identifiers and base blocks are not stored inside objects any longer; Minecraft vanilla registry for identifiers and
  specific maps for relations between blocks and their base blocks are used instead.
- 会从原版的BlockFamilies中导入数据至本模组的BlockMappings。未来或许会直接将BlockMappings合并至BlockFamilies。
- Imports data from vanilla BlockFamilies to BlockMappings in this mod. In the future, this mod's BlockMappings may be
  directly merged into BlockMappings.
- 当前版本仍存在的问题：浮冰台阶下半台阶和正立的浮冰楼梯第一阶顶部表面不滑。
- Issue existing in this version: Packed ice slab bottom half and the first stage of bottom-half stair are not slippery.
- 添加了按基础方块排序的物品组。
- Added item groups where items are sorted by on base blocks.
- 未完成。
- Undone.

### 1.0.0

更新于2021年8月5日 Updated on Apr. 5th 2021

- 为多种方块添加了楼梯、台阶、栅栏、栅栏门、按钮、压力板。
- Added stairs, slabs, fences, fence gates, pressure plates for multiple mods.
- 该版本目前支持简体中文（zh_cn）和英语（en）。
- In this version only simplified Chinese (zh_cn) and English (en) are supported.
- 此前曾参照 Minecraft 原版的数据生成系统生成数据，现已弃用，改为自制的数据生成系统。但弃用的数据生成系统仍保留在代码中。
- Before this release, a data generation system was used similar to vanilla Minecraft, which has been deprecated,
  replaced with a new data generation system. But the deprecated one still remains in the code.
- 此版本仍存在此问题：配方书中，本模组添加的配方归类不正确。
- The issue exists in this version: In recipe books, recipes added by this mod are not correctly classified.
