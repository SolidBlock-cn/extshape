package pers.solid.extshape;

import com.google.common.collect.Streams;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.rrp.ExtShapeRRP;
import pers.solid.extshape.rs.ExtShapeBridgeImpl;
import pers.solid.extshape.tag.ExtShapeTags;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.BlockCollections;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * <p>欢迎使用扩展方块形状模组。本模组为许多方块提供了各个形状的变种，包括原版不存在的形状。
 * <p>本模组中的所有方块是在 {@link ExtShapeBlocks} 中创建的，创建的同时将其注册，并创建和注册对应的方块物品。物品组由 {@link ExtShapeItemGroup} 提供。本模组还提供了一定的配置功能，参见 {@link ExtShapeConfig}。
 * <p>本模组还有一个内置的方块映射管理系统，由 {@link BlockBiMaps} 提供。方块映射是指的方块与方块之间的关系。本模组的方块被创建时，就会自动加入映射中。此外，原版的方块映射也会加入。可以利用 {@link BlockBiMaps#getBlockOf} 来获取特定方块的特定形状的变种。
 * <p>本模组的方块和物品所使用的资源，包括客户端资源和服务器数据，绝大多数都是在运行时生成的，并不会保存为本地文件，这是依赖的 Better Runtime Resource Pack（BRRP）模组。
 * <hr>
 * <p>Welcome to use Extended Block Shapes mod, which provides various variants in different shapes of many blocks, including shapes that do not exist in vanilla Minecraft.
 * <p>Blocks of this mod are created in {@link ExtShapeBlocks}; while created, they are also registered, and so as their corresponding block items. Item groups are provided in {@link ExtShapeItemGroup}. This mod also provides a simple configuration. See {@link ExtShapeConfig}.
 * <p>This mod contains an internal block mapping management, provided by {@link BlockBiMaps}。Block mapping means the relations between blocks. Block in this mod are added instantly to the mappings upon created. Besides, vanilla block mappings are also added. You may get the specified variant of a specified block by {@link BlockBiMaps#getBlockOf}.
 * <p>Resources used by this mod, including client assets and server data, are generated on runtime, without saving local files, which relies on Better Runtime Resource Pack (BRRP) mod.
 *
 * @author SolidBlock
 */
public class ExtShape implements ModInitializer {
  public static final String MOD_ID = "extshape";
  public static final Logger LOGGER = LoggerFactory.getLogger(ExtShape.class);

  @Override
  public void onInitialize() {
    ExtShapeConfig.init();
    ExtShapeBlocks.init();
    ExtShapeItemGroup.init();
    ExtShapeTags.refillTags();

    // registerFlammableBlocks(); 关于注册可燃方块的部分，请直接参见 ExtShapeBlocks 中的有关代码。

    registerStrippableBlocks();
    registerFuels();

    ExtShapeRRP.registerRRP();
    CommandRegistrationCallback.EVENT.register(RecipeConflict::registerCommand);

    if (FabricLoader.getInstance().isModLoaded("reasonable-sorting")) {
      try {
        ExtShapeBridgeImpl.initialize();
      } catch (LinkageError e) {
        LOGGER.info("An error ({}) was thrown when initializing Reasonable Sorting Mod with Extended Block Shapes mod. This is expected.", e.getClass().getSimpleName());
      } catch (Throwable e) {
        LOGGER.warn("Failed to call ExtShapeBridgeImpl.initialize():", e);
      }
    }

    FabricLoader.getInstance().getEntrypoints("extshape:post_initialize", ModInitializer.class).forEach(ModInitializer::onInitialize);
  }

  /**
   * 可通过斧去皮的方块，包括模组中的。
   */
  public static final Map<Block, Block> EXTENDED_STRIPPABLE_BLOCKS = new HashMap<>();

  /**
   * 注册所有可去皮的方块。考虑到存在复杂的方块状态的情况，故不使用 {@link net.fabricmc.fabric.api.registry.StrippableBlockRegistry}，而使用 {@link pers.solid.extshape.mixin.AxeItemMixin}。
   */
  private static void registerStrippableBlocks() {
    Streams.concat(
        IntStream.range(0, BlockCollections.LOGS.size()).mapToObj(i -> Pair.of(BlockCollections.LOGS.get(i), BlockCollections.STRIPPED_LOGS.get(i))),
        IntStream.range(0, BlockCollections.WOODS.size()).mapToObj(i -> Pair.of(BlockCollections.WOODS.get(i), BlockCollections.STRIPPED_WOODS.get(i))),
        IntStream.range(0, BlockCollections.HYPHAES.size()).mapToObj(i -> Pair.of(BlockCollections.HYPHAES.get(i), BlockCollections.STRIPPED_HYPHAES.get(i))),
        IntStream.range(0, BlockCollections.STEMS.size()).mapToObj(i -> Pair.of(BlockCollections.STEMS.get(i), BlockCollections.STRIPPED_STEMS.get(i)))
    ).forEach(pair -> {
      final Block inputBase = pair.getFirst();
      final Block outputBase = pair.getSecond();
      for (BlockShape shape : BlockShape.values()) {
        final Block input = BlockBiMaps.getBlockOf(shape, inputBase);
        final Block output = BlockBiMaps.getBlockOf(shape, outputBase);
        if (input != null && output != null) {
          EXTENDED_STRIPPABLE_BLOCKS.put(input, output);
        }
      }
    });
  }

  /**
   * 在初始化时，注册所有的燃料。注意：对于 Forge 版本，物品的燃烧由 {@code IForgeItem} 的相关接口决定。部分是直接由其标签决定的，例如木制、竹制的楼梯、台阶，原版的标签即定义了可作为燃料。
   *
   * @see ExtShapeBlocks
   * @see net.minecraft.block.entity.AbstractFurnaceBlockEntity#createFuelTimeMap()
   */
  @ApiStatus.AvailableSince("1.5.0")
  private static void registerFuels() {
    final Object2IntMap<TagKey<Block>> map = new Object2IntOpenHashMap<>();

    // 参照原版木制（含下界木）楼梯和台阶，楼梯燃烧时间为 300 刻，台阶燃烧时间为 150 刻。
    // 但是，non_flammable_wood 标签的仍然不会被熔炉接受。
    map.put(ExtShapeTags.WOODEN_VERTICAL_STAIRS, 300);
    map.put(ExtShapeTags.WOODEN_VERTICAL_SLABS, 150);
    map.put(ExtShapeTags.WOODEN_QUARTER_PIECES, 75);
    map.put(ExtShapeTags.WOODEN_VERTICAL_QUARTER_PIECES, 75);
    map.put(ExtShapeTags.WOODEN_WALLS, 300);

    // 参照原版羊毛燃烧时间为 100 刻，楼梯燃烧时间和基础方块相同，台阶燃烧时间为一半。
    map.put(ExtShapeTags.WOOLEN_STAIRS, 100);
    map.put(ExtShapeTags.WOOLEN_SLABS, 50);
    map.put(ExtShapeTags.WOOLEN_QUARTER_PIECES, 25);
    map.put(ExtShapeTags.WOOLEN_VERTICAL_STAIRS, 100);
    map.put(ExtShapeTags.WOOLEN_VERTICAL_SLABS, 50);
    map.put(ExtShapeTags.WOOLEN_VERTICAL_QUARTER_PIECES, 25);

    // 栅栏、栅栏门、压力板、燃烧时间和基础方块一致，门的燃烧时间为三分之二，按钮为三分之一。
    // 但考虑到羊毛压力板是与地毯相互合成的，故燃烧时间与地毯一致，为 67。
    map.put(ExtShapeTags.WOOLEN_FENCES, 100);
    map.put(ExtShapeTags.WOOLEN_FENCE_GATES, 100);
    map.put(ExtShapeTags.WOOLEN_PRESSURE_PLATES, 67);
    map.put(ExtShapeTags.WOOLEN_BUTTONS, 33);
    map.put(ExtShapeTags.WOOLEN_WALLS, 100);

    map.forEach((blockTagKey, integer) -> FuelRegistry.INSTANCE.add(ExtShapeTags.TAG_PREPARATIONS.getItemTagOf(blockTagKey), integer));
  }
}
