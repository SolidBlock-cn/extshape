# 更新日志 Update Log

### 1.5.0

这次更新表面上看上去不是很明显，但实际上代码几乎从头到尾都被改了。模组进行过多轮的测试，以确保能够稳定运行，经过反复调试与修改才发布。该版本是与 BRRP 同时开发的，开发过程中也可以一并排查 BRRP 模组中的问题。

- 自从该版本，模组将依赖 BRRP（更好的运行时资源包）才能运行。由于 BRRP 内嵌了 ARRP 功能，因此您可以不再安装 ARRP 模组。
- 大幅度优化代码，删除了不必要的代码内容。
- 南瓜、西瓜、苔藓、菌光体、下界疣块、诡异疣块制成的方块可以在堆肥桶中堆肥。
- 重新加入了下界疣墙和诡异疣墙，并加入了羊毛、苔藓块等制成的墙。
- 加入了更多标签：
    - 现在，所有基于金块和粗金块的方块都会被猪灵喜欢（物品标签 `#piglin_loved`）。
    - 雪楼梯、雪台阶放置在草方块时，如果把整个草方块覆盖住，草方块会显示为覆雪的格式（方块标签 `#extshape:snow`）。
    - 破坏由金块、粗金块或镶金黑石制成的方块时，猪灵会被激怒（方块标签 `#piglin_guarded`）。
    - 垂滴叶可以放置在苔藓和黏土制成的方块上（方块标签 `#small_dripleaf_placeable_on`）。
    - 羊毛制品现在可以像羊毛一样用剪刀更快破坏了（方块标签 `#fabric:mineable/shears`）。
    - 重新加入了非石质方块的墙，且不能由石镐更快地破坏。尽管 `#minecraft:mineable/pickaxes` 直接包含了 `#minecraft:walls`，但是本模组让 `#extshape:pickaxe_unmineable` 的方块不再能被镐更快破坏。
- 为配方加入了对应的解锁进度，当获得配方原料时，进度会被触发，从而解锁该配方。
- 加入了配置界面。可以配置是否将物品加入原版物品组，以及是否加入本模组专用的物品组。此外，还加入了手动重新生成和导出运行时资源包内容的功能。
- 修正语言文件中的一些错误，优化了语言文件中的一些表述。
- 为各类锈蚀程度的涂蜡铜块、黏土块和粗矿石方块添加其他所有形状的方块。
- 修复苔藓压力板与苔藓地毯配方冲突的问题，参照羊毛的做法，改为配方与压力板相互合成。
- 为解决与 Minecraft 版权协议不兼容的问题，版权协议调整为 LGPL 3.0。
- 由于合成表并无冲突，西瓜按钮现在可以使用一个西瓜方块合成了。
- 现在，金属制的方块（如下界合金块、远古残骸）也可以被切石了。
- 调整了创造模式物品组中的物品排列。

The update does not seem obvious, but code is changed from head to toe, actually. Mod is tested repetitively to ensure the stability, and published only rounds of debugs and modifications. This version is developed at the same time with BRRP, so the developing process can find out BRRP-related issues as well.

- Since this version, the mod relies on BRRP (Better Runtime Resource Pack). As BRRP nests ARRP, it's not required to install ARRP anymore.
- Optimized code massively, and unnecessary codes are removed.
- Blocks base on pumpkin, melon, moss, shroomlight, nether wart block, warped wart block can be composted.
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
- As there is no conflicts in recipes, melon buttons can be crafted with a melon block now.
- Metal blocks (such as netherite blocks and ancient debris) can be stonecut now.
- Adjusted arrangement of items in Creative Mode item groups.

### 1.4.0

- 使用 ARRP（高级运行时资源包）取代了传统的资源包和数据包文件。模组文件可以大幅度减小，但是自从该版本，模组将依赖 ARRP 才能运行。请确保同时安装了 ARRP 模组。
- 修复西瓜按钮和南瓜按钮合成配方冲突的问题，因此移除了这两个方块的合成表。
- 修复压力板在按压与未按压时使用相同模型的问题。
- 修复含水方块在附近有方块更新时水不流动的问题。

- Replaced traditional resource packs and data packs with ARRP (Advanced Runtime Resource Pack). File size of the mod has been largely reduced, but since this version, the mod depends on ARRP to run. Make sure you have ARRP mod installed.
- Fixed the conflict in recipes of melon buttons and pumpkin buttons. Recipes of the two are removed.
- Fixed the issue that pressure plates uses the same model whether pressed down.
- Fixed the issue that water logged in blocks does not flow when there is a block update.

### 1.3.1

- <span style="color:red">删除了下界疣墙、诡异疣墙。</span>
- 添加了更多方块，主要是按钮。
- 修复了 BlocksBuilder 的构造函数中，参数若为 <code>null</code>仍会创建对应对象的问题（可能导致部分压力板或按钮有崩溃风险）的问题。
- 修复了铁、金、钻石、绿宝石、青金石块可能存在的与合成表冲突的问题。<b>这些方块仍然存在，但是不再能够合成。</b>
- 稍微调整了物品分组。
- 修复了部分石质方块无法用切石机切石的问题。

- <span style="color:red">Removed nether wart wall and warped wart wall.</span>
- Added more blocks, main buttons.
- Fixed the issue that, in constructor of BlocksBuilder with <code>null</code> parameters, objects are created unexpectedly, which may cause some buttons and pressure plates to crash.
- Fixed the potential recipe conflict of block of iron, gold, diamond, emerald and lapis. <b>These blocks still exist, but can no longer be crafted.</b>
- Slightly adjusted item groups.
- Fixed the issue that some stone blocks cannot be cut in stone-cutters.

### 1.3.0

本次更新仅限 Minecraft 1.17 以上版本。用于旧版本的模组更新将会稍后发布。

- 加入了与合理排序（Reasonable Sorting）模组（仅限1.3以上）的联动。
- 修复了羊毛压力板与地毯合成表冲突的问题。羊毛压力板的合成方式改为由对应的地毯合成。
- 调整了一些代码。
- 修改了一些方块名称，例如“平滑石英块纵台阶”改为“平滑石英纵台阶”。
- 增加了滴水石、蜜脾块、苔藓块等方块的不同形状。

This update is for version only 1.17 and above. Versions for older versions will come later.

- Added co-working with Reasonable Sorting mod (versions above 1.13).
- Fixed the conflict between wool pressure plate and wool carpet. Wool pressure plates are crafted from a carpet in the same color now.
- Adjust some code.
- Added different shapes for dripstone, honeycomb block, moss block, etc.

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
- Fixed the issue that codes in Mineable class are run. Codes in some other block tags will also be run only in data generation process, instead of in an ordinary game environment.
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
  Fence gates, buttons and pressure plates are not water-loggable because of vanilla Minecraft code, which is not modifiable by the mod.)
- 修复“其他”物品组以海晶石作为图标但海晶石不在该物品组的问题。
- Fixed the issue that "Others" item group uses prismarine block as icon, while prismarine block is not in this item group.

### 1.2.0

- 删除了额外的物品组，将物品添加到原版物品组中。另外仍有4个用于分类存放方块及其变种的额外物品组仍保留。
- Deleted extra item groups. Vanilla item groups are used instead. Besides, there are 4 other item groups in order to contain blocks (including vanilla) in sort of base blocks.
    - 因为物品添加到了原版物品组中，所以同时修复了在配方书中归类错误的问题。
    - The issue that items are not sorted correctly in recipe groups is also fixed because of this change.
- 方块映射由普通的 Map 改为更加高效的 BiMap（双向映射）。BiMap 的值也是不能重复的，从而更高效地得到反向映射。
- BlockMappings use more effective BiMap instead of ordinary Map any longer. Values of BiMap is unique, so it's more effective to get an inverse map.
- 添加了 JavaDoc。
- Added JavaDoc.
- 参照原版习惯，本模组中的方块的英文名改为每个单词首字母大写形式。
- Referring to vanilla convention, English names of blocks in this mod are capitalised now.
- 修复了横条、纵条、纵楼梯方块在英文模式下的命名错误。
- Fixed naming error of quarter piece, vertical quarter piece and vertical stairs blocks in English.
- 修改了纵台阶、纵条、纵楼梯的配方。这些方块可以直接由1个对应的台阶、横条、楼梯合成而成（相当于进行旋转），不再需要由3个合成；同时也可以转回去。
- Changed recipes of vertical slabs, vertical quarter pieces and vertical stairs. These blocks can now be crafted from one corresponding slab, quarter piece of vertical stairs, which can be seen as rotating, instead of crafting from 3; you can also "rotate" them "back".

### 1.1.1

- 添加了纵楼梯、横条、纵条。其中横条、纵条占用1/4方块位置，可使用3个台阶/垂直台阶1:2合成，部分方块可在切石机内用1个完整方块1:4合成或用台阶/垂直台阶1:2合成。
- Added vertical stairs, quarter pieces and vertical quarter pieces. Quarter pieces and vertical quarter pieces can be crafted 1:2 from slabs/vertical slabs in crafting tables, and some of those can be crafted 1:4 from full blocks or 1:2 from slabs/vertical slabs in stone cutters.
- 移除了部分方块。
- Removed some blocks.
- 加入了黏土块、末地石等方块的衍生方块。
- Added variant blocks for clay and end stone etc.
- 移除了部分方块的压力板、按钮，因而同时修复了部分合成表的冲突问题。
- Removed pressure plates and buttons for some blocks. Therefore, some conflicts between recipes have been fixed meanwhile.
- 调整了纵台阶在物品栏中的显示形式。
- Adjusted display form of vertical slabs in inventories.
- 添加了台湾繁体中文（zh_tw）、香港繁体中文（zh_hk）和文言文（lzh）语言支持。
- Added language support for Traditional Chinese Taiwan, Traditional Chinese Hong Kong and Classical Chinese Language.
- 当前版本仍然存在的问题：
- Issues currently existing in this version:
    - 在原版中，部分方块，如浮冰、荧石、雪块，其生存模式下若无精准采集，破坏后无法获得原方块，但其衍生的所有方块均未受影响。
    - In vanilla, some blocks, such as packed ice, glowstone, snow block, cannot drop themselves when mined in survival mode without Silk Touch. However, their variant blocks are not affected.
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
- Identifiers and base blocks are not stored inside objects any longer; Minecraft vanilla registry for identifiers and specific maps for relations between blocks and their base blocks are used instead.
- 会从原版的BlockFamilies中导入数据至本模组的BlockMappings。未来或许会直接将BlockMappings合并至BlockFamilies。
- Imports data from vanilla BlockFamilies to BlockMappings in this mod. In the future, this mod's BlockMappings may be directly merged into BlockMappings.
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
- Before this release, a data generation system was used similar to vanilla Minecraft, which has been deprecated, replaced with a new data generation system. But the deprecated one still remains in the code.
- 此版本仍存在此问题：配方书中，本模组添加的配方归类不正确。
- The issue exists in this version: In recipe books, recipes added by this mod are not correctly classified.
