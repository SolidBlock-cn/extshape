
# 更新日志 Update Log

### 1.2.6
此更新仅限 1.17 以上版本。

This update is for version only 1.17 and above.

- 添加了平滑玄武岩的多种形状。
- Added multiple shapes for smooth basalt.
- 开始支持 1.18 的快照版本。
- Started supporting 1.18 snapshot versions.
- 调整了模组介绍。
- Tweaked mod description.

### 1.2.5
- 修复了垂直台阶底部部分材质不渲染的问题。
- Fixed the issue of missing rendering at the bottom of vertical stairs blocks.
- 对于 1.16 版本：移除不必要的标签，并修复挖掘等级问题。
- For 1.16 versions: Removed unnecessary tags and fixed mining level issue.
  - 请注意：1.16 版本的语言文件仍会存有未来版本的语言文件。
  - Note: For 1.16 versions, language files for future versions are still stored.

### 1.2.4
- 移除了木板墙和粘土墙。
- Removed plank wall and clay wall.

### 1.2.3
- 开始向旧版本兼容（稍后发布）。
- Starting backwards compatibility (will release later qwq).
- 修复无法用模组的双层平滑石台阶方块合成平滑石台阶的问题。
- Fixed the issue that you cannot craft a smooth stone slab with double stone slabs block in this mod.
- 修复部分方块没有垂直台阶，并导致加载数据包时报错的问题的问题。
- Fixed the issue that some blocks have no vertical slabs, and that errors are thrown when loading data-packs.
- 修复 Mineable 类中的代码会被执行的问题。其他部分方块标签生成的代码也只会在数据生成过程中执行，不再在一般的游戏环境中执行。
- Fixed the issue that codes in Mineable class are run. Codes in some other block tags will also be run only in data
  generation process, instead of in an ordinary game environment.
- 参照原版 Minecraft，添加了方块标签，同时还添加了物品标签。
- Referring to vanilla Minecraft, added some block tags, and added item tags.

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
