# 扩展方块形状模组

If you do not understand Chinese, you may refer to the [document in English](README-en.md).

本模组为许多的原版方块添加了其楼梯、台阶、栅栏、栅栏门、按钮和压力板，以及原版不具有的纵台阶、纵楼梯、横条、纵条。具体内容列表请参考[此文件](BlockList.md)。

模组目前支持 Minecraft Java 版 1.18.2、1.18.1、1.18、1.17.1、1.17 和 1.16.5，请根据你的游戏版本安装对应的模组（1.16.5 的版本**不兼容 1.16-1.16.1**），并需要 Fabric API 和 BRRP。此外，通常还需要 Mod Menu（非必须）以打开模组配置界面。

**请注意：自从模组版本 1.4.0 开始，本模组依赖 ARRP（高级运行时资源包，Advance Runtime Resource Pack）模组才能运行，因此您必须同时安装该模组。如果不安装 ARRP 而安装了扩展方块形状模组，游戏将无法正常运行。**

**请注意：自从模组版本 1.5.0 开始，本模组依赖 BRRP（更好的运行时资源包，Better Runtime Resource Pack）模组才能运行，因此您必须同时安装该模组。BRRP 内嵌了 ARRP，您无需再安装 ARRP，否则可能会出现问题。**

<s>请注意：如果将模组由 1.2.3 更新到 1.2.4 以上的版本，请提前拆除所有的 木板墙 和 粘土墙 方块。从 1.2.4 开始，木板墙和粘土墙被移除。</s>自 1.5.0 开始，木板墙和黏土墙重新被加入。

本模组的更新记录[点击此处](UpdateLog.md)。欢迎加入QQ群**587928350**或开黑啦频道邀请码**KlFS0n**体验本模组的最新更新。

## 特性

### 方块

本模组为大量方块提供了各种形状的变种。例如，羊毛就有羊毛楼梯、羊毛台阶、羊毛墙、羊毛压力板等。纵楼梯、纵台阶、横条、纵条是本模组增加的，这些方块和楼梯、台阶一样均可含水。

所有的楼梯、台阶、栅栏、墙的硬度、挖掘工具和挖掘时间与其基础方块相当（这是参照了原版的特性），而非与其体积成正比。例如，破坏羊毛台阶的时间与破坏整个羊毛的时间相同，而非前者是后者的一半。

在原版中，大多数压力板和按钮的硬度为其基础方块的 1/4 到 1/3。本模组添加的压力板和按钮的硬度均为基础方块的 1/4。（基岩制品在生存模式不可破坏，但是基岩按钮、基岩压力板在失去其依靠的方块时仍会正常掉落。）本模组对按钮进行了扩展，质地较软的按钮（如羊毛按钮、雪按钮）的触发时长为 60 刻（3 秒），质地很硬的按钮（如黑曜石按钮、基岩按钮）的触发时长为 5 刻（1/4 秒）。参照原版代码，所有的按钮、压力板均无碰撞箱。

另外此模组还添加了“双层平滑石台阶”方块和“石化橡木木板”方块，分别可以合成平滑石台阶和石化橡木台阶（原版的平滑石块也可以合成平滑石台阶）。

本模组添加的方块继承了其基础方块的大多数特性：

- 所有的羊毛制品（即以羊毛为基础的方块，包括各种颜色的羊毛楼梯、羊毛台阶等）和木板制品均可以燃烧且（其燃烧时间与原版完整方块相同）。
- 羊毛和木板制品可用作燃料，台阶和纵台阶作燃料时，其燃烧时间为基础方块的一半。按钮作燃料时，其燃烧时间为基础方块的约 1/3。横条、纵条作燃料时，其燃烧时间为基础方块的 1/4。
- 羊毛制品无论是否占了一整格，均具有阻挡幽匿感测体（Sculk Sensor，1.19 更新之前称潜声传感器）的功能。
- 所有的下界岩制品（下界岩 1.16 之前称为地狱岩）均可无限燃烧，基岩制品在末地可无限燃烧。
- 所有的末地石、黑曜石和哭泣的黑曜石制品均可抵御末影龙。
- 下界合金和远古残骸制品的掉落形式能够抵抗火焰、熔岩。
- 金块、粗金块的制品可被猪灵捡起。破坏金块、粗金块和镶金黑石制品会触怒猪灵。
- 雪楼梯和雪台阶放置在草方块上时，如果正好压住整个草方块顶部，会让草方块显示为积雪形式，就像雪块和雪一样。
- 小型垂滴叶和大型垂滴叶可以放置在苔藓和黏土制品上（仅限建筑方块）。
- 南瓜、西瓜、苔藓、菌光体、下界疣块和诡异疣块制品可用于堆肥。
- 浮冰制品只能通过有精准采集（丝绸之触）附魔的物品获取。
- 黏土、雪块、西瓜等方块制成的方块在破坏时会掉落对应的物品，如黏土球、雪球（仅限用锹采集）和西瓜片。台阶、横条、纵台阶、纵条掉落的数量还会在基础方块掉落的数量的基础上除以 2 或 4，且双层台阶掉落双倍。部分物品的掉落可能会受到时运魔咒影响，且带有精准采集附魔时仍可掉落方块本身。

### 配方

所有的方块均可使用其基础方块参照类似原版合成表合成，石质和金属方块还可以通过切石机合成。具体为：楼梯在工作台可 3:2 合成（原料:产物，下同），在切石机可 1:1 切石。台阶和纵台阶在工作台、切石机可 1:2 制作。

台阶、楼梯、横条可以直接在合成表中旋转形成对应的纵向方块，也可以“转回来”。例如 1 个台阶可以合成 1 个对应的纵台阶，1 个纵台阶也可以直接合成 1 个对应的台阶。

各个形状方块在工作台中的合成关系如下：

- 6×基础方块 → 4×楼梯
- 3×基础方块 → 6×台阶
- 3×台阶 → 6×横条 （台阶水平排列）
- 1×台阶 ↔ 1×纵台阶
- 1×楼梯 ↔ 1×纵楼梯
- 1×横条 ↔ 1×纵条
- 3×纵台阶 → 6×纵条 （纵台阶竖直排列）

部分方块的各个形状的方块在切石机中的合成关系如下：

- 1×基础方块 → 1×楼梯 / 1×纵楼梯 / 2×台阶 / 2×纵台阶 / 4×横条 / 4×纵条
- 1×楼梯 → 3×横条
- 1×台阶 → 2×横条
- 1×纵楼梯 → 3×纵条
- 1×纵台阶 → 2×横条 / 2×纵条

自 1.5.1 开始，本模组中的物品可以通过切石机进行“递归切石”。例如，一个未磨制的安山岩可以直接切石成磨制的安山岩纵台阶。

栅栏和栅栏门合成时的原材料（除了基础方块之外），参照既有合成表。石质栅栏和栅栏门的原材料使用燧石，羊毛的为线，砂岩及其变种的为木棍。

为避免合成表冲突，部分按钮不可合成。例如铁块只能合成铁锭，不能合成铁制按钮，南瓜只能合成南瓜种子，不能合成南瓜按钮。此外，羊毛和苔藓的压力板也不能直接合成，而是直接与其对应的地毯进行 1:1 的转换。雪台阶也不能由雪块合成，而是先用 3 个雪块合成 1 个雪（片），再用雪合成雪台阶。

自 1.5.0 开始，所有合成配方在获得了任意基础方块之后即可解锁。具体来说，就是为每个合成配方加入了对应的进度，在获取基础方块或者解锁配方之后，该进度就会被触发并解锁相应的配方。

### 创造模式物品栏

在创造模式下，本模组提供多个物品组以分门别类地摆放各个形状的方块：相同基础方块的方块（包含原版的方块）排列在一起，这样可以很方便地获取同一个方块的多个形状。

此外，你也可以选择让本模组新增的方块出现在原版物品组，但这有可能会导致物品混乱。你可以在模组配置界面（需要安装 Mod Menu）进行配置。

如果安装了 1.3 以上版本的合理排序（Reasonable Sorting）模组，原版物品组内的方块也会按照基础方块进行排序。

## 关于运行时资源包

本模组依赖 BRRP（更好的运行时资源包，Better Runtime Resource Pack）模组，该模组基于此前的 ARRP（高级运行时资源包，Advanced Runtime Resource Pack）。该模组可以用来在游戏运行时在内部创建资源和数据文件，而不是将文件写在模组文件中。

通常，一个方块需要有对应的方块状态、方块模型以使其具有正确的外观，同时需要有战利品表使其破坏后能正常掉落，合成配方则定义了方块如何合成，每一个合成配方都有一个对应的进度，该进度通常是在获取一个原料时触发并让玩家解锁该配方。此外方块还有对应的物品，因此还需要创建物品模型。上面说到的“方块状态”“方块模型”“物品模型”“战利品表”“配方”“进度”等都是 json 文件。在模组内，为每个方块都创建这些 json 非常麻烦，而且这些 json 的内容基本上都是重复的，例如方块物品的模型都是直接继承对应的方块模型，但还是需要在每个文件中定义一次。又如，楼梯的方块状态文件非常复杂，然而不同楼梯的方块状态文件的内容基本上都是一样的，只不过是把名字改了下而已，为每个楼梯都定义一次这么复杂的文件，实在是令人费解。

而使用运行时资源包之后，这些文件都可以直接在运行时生成，而不再是存储在模组包中。这样大大减小了模组文件的大小，同时也可以节省输入流和输出流。这就是 ARRP 模组的目的，BRRP 在此基础上进行了增强。

尽管运行时资源包节省了输入和输出，但是在游戏内部，还是会将对象转化为 json 并序列化为字节格式存储在内存中，游戏读取时，需要将这些文件反序列化成 json，然后转变成游戏内的对象。其中，ARRP 的对象和 Minecraft 内的原版对象并不直接互通，不过 BRRP 已经尽可能在 ARRP 与 Minecraft 内搭建桥梁。

运行时资源包并未节省将 json 反序列化并转化为游戏内对象的过程，还增加了一次转化并序列化 json 的过程。但事实证明，运行时生成数据比从模组文件中读取快得多。

然而，ARRP 模组的设计就是如此，归根结底还是 Mojang 的问题。Minecraft 为了实现资源包与数据包，采用了如此离奇且低效的方式读取游戏资源和数据（包括原版 Minecraft 的数据），我们作为模组开发者只好奉陪。