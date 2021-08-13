# 扩展方块形状模组 Extended Block Shapes Mod

本模组为许多的原版方块添加了其楼梯、台阶、栅栏、栅栏门、按钮和压力板。具体内容列表请参考[此文件](BlockList.md)。

This mod adds stairs, slabs, fences, fence gates, button and pressure plates for many vanilla blocks. List of the 
contents can be found in [this file](BlockList.md).

模组目前仅支持 Minecraft Java 版 1.17.x，并需要 Fabric API。

At present this mod supports only Minecraft Java Edition 1.17.x and requires Fabric API.

## 特性 Features

### 方块 Blocks

本模组添加的方块继承了其基础方块的大多数特性。

Blocks added by this mod inherit most features of their base blocks.

所有的羊毛制品（即以羊毛为基础的方块，包括各种颜色的羊毛楼梯、羊毛台阶等）均可以燃烧且可以作为燃料（其燃烧时间与原版完整方块相同）。羊毛制品无论是否占了一整格，均具有阻挡潜声传感器（Sculk Sensor）的功能。

All woolen blocks (blocks based on wool block, including wool stairs, woo slabs in all colors) are flammable and can 
be used as fuels (fueling time equals their base blocks). All woolen blocks, no matter they occupy a full block, can 
block sculk sensors.

所有的下界岩制品（下界岩1.16之前称为地狱岩）均可无限燃烧，基岩制品在末地可无限燃烧。

All netherrack blocks are infiniburn. Bedrock blocks are infiniburn in the end.

下界合金制品的掉落形式能够抵抗火焰、熔岩。

Netherite blocks as dropped items resist fire and lava.

所有的楼梯、台阶、栅栏、墙的硬度、挖掘工具和挖掘时间与其基础方块相当（这是参照了原版的特性），而非与其基础方块成正比。例如，破坏羊毛台阶的时间与破坏整个羊毛的时间相同，而非前者是后者的一半。

The hardness, mining tools and mining time of all stairs, slabs, fences and walls equal to their basic blocks (like 
vanilla features). For example, the time spent breaking a wool slab equals to a full wool block, instead of half of 
the latter.

在原版中，大多数压力板和按钮的硬度为其基础方块的1/4到1/3。 本模组添加的压力板和按钮的硬度均为基础方块的1/4。（基岩制品在生存模式不可破坏，但是基岩按钮、基岩压力板在失去其依靠的方块时仍会正常掉落）。本模组对按钮进行了扩展，质地较软的按钮（如羊毛按钮、雪按钮）的触发时长为60刻（3秒），质地很硬的按钮（如黑曜石按钮、基岩按钮）的触发时长为5
刻（1/4秒）。参照原版代码，所有的按钮、压力板均无碰撞箱。

In vanilla, the hardness of pressure plates and buttons equals to 1/4 or 1/3 of their basic blocks. All pressure plates 
and buttons added in this mod have the hardness same as 1/4 of their base blocks. (Bedrock blocks are invulnerable 
in Survival Mode, but bedrock pressure plates and bedrock buttons drop normally when losing blocks relied on.) This 
mod simply extends buttons: soft buttons (wool buttons, snow buttons) trigger 60 ticks (3 seconds); hard buttons 
(obsidian buttons, bedrock buttons) trigger 5 ticks (1/4 second). All buttons and pressure plates have no collision 
box like vanilla MC.

### 合成与烧炼 Crafting and smelting

所有的方块均可使用其基础方块参照类似原版合成表合成，部分可以通过切石机合成。具体为：楼梯在工作台可3:2合成（原料:产物，下同），在切石机可1:1合成。台阶在工作台、切石机可1:2合成。

The crafting recipes of all blocks in this mod are similar to those in vanilla. Some can also be crafted with a 
stone cutter. Stairs can be crafted 3:2 in crafting table (ingredient:result) and 1:1 in the stone cutter. Slabs are 
craftable 1:2 in either crafting table or stone cutter.

栅栏和栅栏门合成时的原材料（除了基础方块之外），参照既有合成表。石质栅栏和栅栏门的原材料使用燧石，羊毛的为线，砂岩及其变种的为沙子和红砂岩。

Ingredients of fences and fence gates, apart from base blocks, resemble vanilla recipes. For the crafting of stone 
fences and fence gates, flint is used along with base blocks, and string for wool, sand or redsand for sandstone and 
its variants.

台阶作燃料时，其燃烧时间为基础方块的一半。按钮作燃料时，其燃烧时间为基础方块的1/3。

Used as fuels, slab burns half the time of its base blocks, and button burns 1/3 the time of base blocks.

## 更新日志 Update log

### 1.1.0-snapshot [快照版 Snapshot version]
- 添加了纵向台阶。
- Added vertical slabs.
- 为浮冰添加了楼梯、台阶、栅栏、栅栏门。
- Added stairs, slab, fence and fence gate for packed ice.
- 现在带釉陶瓦台阶可以旋转了。
- Glazed terracotta slabs can be rotated now.
- 移除了弃用的代码。
- Removed deprecated code.
- 不再在各对象内存储其 id、基础方块等信息，而是使用 Minecraft 原版注册表储存并调取 id，并使用专门的映射表储存方块与其基础方块的对应关系。
- Identifiers and base blocks are not stored inside objects any longer; Minecraft vanilla registry for identifiers 
  and specific maps for relations between blocks and their base blocks are used instead.
- 会从原版的BlockFamilies中导入数据至本模组的BlockMappings。未来或许会直接将BlockMappings合并至BlockFamilies。
- Imports data from vanilla BlockFamilies to BlockMappings in this mod. In the future, this mod's BlockMappings may 
  be directly merged into BlockMappings.
- 当前版本仍存在的问题：浮冰台阶下半砖和正立的浮冰楼梯第一阶顶部表面不滑。
- Issue existing in this version: Packed ice slab bottom half and the first stage of bottom-haft stair are not slippery.
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
