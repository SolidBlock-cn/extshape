package pers.solid.extshape.tag;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.mininglevel.v1.FabricMineableTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.builder.BlockShape;

/**
 * 本模组提供的方块和物品标签。这些标签不应该被其他的模组使用。
 */
public final class ExtShapeTags {
  /**
   * 本模组需要准备的一些标签。
   */
  public static final TagPreparations TAG_PREPARATIONS = new TagPreparations();
  public static final TagKey<Block> DAMPENS_VIBRATIONS = ofBlockAndItem(BlockTags.DAMPENS_VIBRATIONS, ItemTags.DAMPENS_VIBRATIONS);
  /**
   * 这个标签主要是考虑到，{@link BlockTags#PICKAXE_MINEABLE} 加入了所有的墙，但事实上墙并不一定是可以使用镐来开采的，因此这里设置了一个专门的标签，如果某个方块属于 {@code minecraft:mineable/pickaxe} 但同时属于 {@code extshape:pickaxe_unmineable}，则认为镐子不能开采这个方块。
   */
  @ApiStatus.AvailableSince("0.1.5")
  public static final TagKey<Block> PICKAXE_UNMINEABLE = ofBlockOnly("pickaxe_unmineable");
  /**
   * 所有羊毛衍生方块（不含羊毛本身）。这些方块会被注册可燃，会被剪刀剪掉，并被加入到 {@link BlockTags#OCCLUDES_VIBRATION_SIGNALS} 中（仅限 1.17 以上版本）。
   */
  public static final TagKey<Block> WOOLEN_BLOCKS = ofBlockAndItem("woolen_blocks", FabricMineableTags.SHEARS_MINEABLE, DAMPENS_VIBRATIONS, BlockTags.OCCLUDES_VIBRATION_SIGNALS);
  /**
   * 所有的木质方块，包括下界木，不含原版方块。
   */
  public static final TagKey<Block> WOODEN_BLOCKS = ofBlockAndItem("wooden_blocks", BlockTags.AXE_MINEABLE);

  /**
   * 所有的原木、木头方块（包括去皮的）。不含原版方块。
   */
  public static final TagKey<Block> LOG_BLOCKS = ofBlockAndItem("log_blocks", WOODEN_BLOCKS);

  /* 楼梯 */
  public static final TagKey<Block> STAIRS = ofBlockAndItem(BlockTags.STAIRS, ItemTags.STAIRS);
  public static final TagKey<Block> WOODEN_STAIRS = ofBlockAndItem(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
  public static final TagKey<Block> LOG_STAIRS = ofBlockAndItem("log_stairs", WOODEN_STAIRS, LOG_BLOCKS);
  public static final TagKey<Block> WOOLEN_STAIRS = ofBlockAndItem("woolen_stairs", WOOLEN_BLOCKS, STAIRS);
  public static final TagKey<Block> CONCRETE_STAIRS = ofBlockAndItem("concrete_stairs", STAIRS, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> TERRACOTTA_STAIRS = ofBlockAndItem("terracotta_stairs", STAIRS, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> STAINED_TERRACOTTA_STAIRS = ofBlockAndItem("stained_terracotta_stairs", TERRACOTTA_STAIRS);


  /* 台阶 */
  public static final TagKey<Block> SLABS = ofBlockAndItem(BlockTags.SLABS, ItemTags.SLABS);
  public static final TagKey<Block> WOODEN_SLABS = ofBlockAndItem(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
  public static final TagKey<Block> LOG_SLABS = ofBlockAndItem("log_slabs", WOODEN_SLABS, LOG_BLOCKS);
  public static final TagKey<Block> WOOLEN_SLABS = ofBlockAndItem("woolen_slabs", WOOLEN_BLOCKS, SLABS);
  public static final TagKey<Block> CONCRETE_SLABS = ofBlockAndItem("concrete_slabs", SLABS, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> TERRACOTTA_SLABS = ofBlockAndItem("terracotta_slabs", SLABS, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> STAINED_TERRACOTTA_SLABS = ofBlockAndItem("stained_terracotta_slabs", TERRACOTTA_SLABS);
  public static final TagKey<Block> GLAZED_TERRACOTTA_SLABS = ofBlockAndItem("glazed_terracotta_slabs", SLABS);


  /* 栅栏 */
  public static final TagKey<Block> FENCES = ofBlockAndItem(BlockTags.FENCES, ItemTags.FENCES);
  public static final TagKey<Block> WOODEN_FENCES = ofBlockAndItem(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
  public static final TagKey<Block> LOG_FENCES = ofBlockAndItem("log_fences", LOG_BLOCKS, WOODEN_FENCES);
  public static final TagKey<Block> WOOLEN_FENCES = ofBlockAndItem("woolen_fences", WOOLEN_BLOCKS, FENCES);
  public static final TagKey<Block> CONCRETE_FENCES = ofBlockAndItem("concrete_fences", FENCES, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> TERRACOTTA_FENCES = ofBlockAndItem("terracotta_fences", FENCES, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> STAINED_TERRACOTTA_FENCES = ofBlockAndItem("stained_terracotta_fences", TERRACOTTA_FENCES);


  /* 栅栏门 */
  public static final TagKey<Block> FENCE_GATES = BlockTags.FENCE_GATES;
  public static final TagKey<Block> LOG_FENCE_GATES = ofBlockAndItem("log_fence_gates", LOG_BLOCKS, FENCE_GATES);
  public static final TagKey<Block> WOOLEN_FENCE_GATES = ofBlockAndItem("woolen_fence_gates", WOOLEN_BLOCKS, FENCE_GATES);
  public static final TagKey<Block> CONCRETE_FENCE_GATES = ofBlockAndItem("concrete_fence_gates", FENCE_GATES, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> TERRACOTTA_FENCE_GATES = ofBlockAndItem("terracotta_fence_gates", FENCE_GATES, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> STAINED_TERRACOTTA_FENCE_GATES = ofBlockAndItem("stained_terracotta_fence_gates", TERRACOTTA_FENCE_GATES);


  /* 墙 */
  public static final TagKey<Block> WALLS = ofBlockAndItem(BlockTags.WALLS, ItemTags.WALLS);
  @ApiStatus.AvailableSince("1.5.0")
  public static final TagKey<Block> WOODEN_WALLS = ofBlockAndItem("wooden_walls", WALLS, WOODEN_BLOCKS, PICKAXE_UNMINEABLE);
  public static final TagKey<Block> LOG_WALLS = ofBlockAndItem("log_walls", WOODEN_WALLS, LOG_BLOCKS, PICKAXE_UNMINEABLE);
  @ApiStatus.AvailableSince("1.5.0")
  public static final TagKey<Block> WOOLEN_WALLS = ofBlockAndItem("woolen_walls", WALLS, WOOLEN_BLOCKS, PICKAXE_UNMINEABLE);
  public static final TagKey<Block> CONCRETE_WALLS = ofBlockAndItem("concrete_walls", WALLS, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> TERRACOTTA_WALLS = ofBlockAndItem("terracotta_walls", WALLS, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> STAINED_TERRACOTTA_WALLS = ofBlockAndItem("stained_terracotta_walls", TERRACOTTA_WALLS);


  /* 按钮 */
  public static final TagKey<Block> BUTTONS = ofBlockAndItem(BlockTags.BUTTONS, ItemTags.BUTTONS);
  public static final TagKey<Block> WOODEN_BUTTONS = ofBlockAndItem(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
  public static final TagKey<Block> LOG_BUTTONS = ofBlockAndItem("log_buttons", LOG_BLOCKS, WOODEN_BUTTONS);
  public static final TagKey<Block> WOOLEN_BUTTONS = ofBlockAndItem("woolen_buttons", WOOLEN_BLOCKS, BUTTONS);
  public static final TagKey<Block> CONCRETE_BUTTONS = ofBlockAndItem("concrete_buttons", BUTTONS, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> TERRACOTTA_BUTTONS = ofBlockAndItem("terracotta_buttons", BUTTONS, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> STAINED_TERRACOTTA_BUTTONS = ofBlockAndItem("stained_terracotta_buttons", TERRACOTTA_BUTTONS);


  /* 压力板 */
  public static final TagKey<Block> PRESSURE_PLATES = BlockTags.PRESSURE_PLATES;  // ItemTags.PRESSURE_PLATES不存在
  public static final TagKey<Block> WOODEN_PRESSURE_PLATES = ofBlockAndItem(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
  public static final TagKey<Block> LOG_PRESSURE_PLATES = ofBlockAndItem("log_pressure_plates", WOODEN_PRESSURE_PLATES, LOG_BLOCKS);
  public static final TagKey<Block> WOOLEN_PRESSURE_PLATES = ofBlockAndItem("woolen_pressure_plates", PRESSURE_PLATES, WOOLEN_BLOCKS);
  public static final TagKey<Block> CONCRETE_PRESSURE_PLATES = ofBlockAndItem("concrete_pressure_plates", PRESSURE_PLATES, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> TERRACOTTA_PRESSURE_PLATES = ofBlockAndItem("terracotta_pressure_plates", PRESSURE_PLATES, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> STAINED_TERRACOTTA_PRESSURE_PLATES = ofBlockAndItem("stained_terracotta_pressure_plates", TERRACOTTA_PRESSURE_PLATES);


  /* 竖直台阶 */
  public static final TagKey<Block> VERTICAL_SLABS = ofBlockAndItem("vertical_slabs");
  public static final TagKey<Block> WOODEN_VERTICAL_SLABS = ofBlockAndItem("wooden_vertical_slabs", WOODEN_BLOCKS, VERTICAL_SLABS);
  public static final TagKey<Block> LOG_VERTICAL_SLABS = ofBlockAndItem("log_vertical_slabs", LOG_BLOCKS, WOODEN_VERTICAL_SLABS);
  public static final TagKey<Block> WOOLEN_VERTICAL_SLABS = ofBlockAndItem("woolen_vertical_slabs", WOOLEN_BLOCKS, VERTICAL_SLABS);
  public static final TagKey<Block> CONCRETE_VERTICAL_SLABS = ofBlockAndItem("concrete_vertical_slabs", VERTICAL_SLABS, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> TERRACOTTA_VERTICAL_SLABS = ofBlockAndItem("terracotta_vertical_slabs", VERTICAL_SLABS, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> STAINED_TERRACOTTA_VERTICAL_SLABS = ofBlockAndItem("stained_terracotta_vertical_slabs", TERRACOTTA_VERTICAL_SLABS);


  /* 横条 */
  public static final TagKey<Block> QUARTER_PIECES = ofBlockAndItem("quarter_pieces");
  public static final TagKey<Block> WOODEN_QUARTER_PIECES = ofBlockAndItem("wooden_quarter_pieces", WOODEN_BLOCKS, QUARTER_PIECES);
  public static final TagKey<Block> LOG_QUARTER_PIECES = ofBlockAndItem("log_quarter_pieces", WOODEN_QUARTER_PIECES, LOG_BLOCKS);
  public static final TagKey<Block> WOOLEN_QUARTER_PIECES = ofBlockAndItem("woolen_quarter_pieces", WOOLEN_BLOCKS, QUARTER_PIECES);
  public static final TagKey<Block> CONCRETE_QUARTER_PIECES = ofBlockAndItem("concrete_quarter_pieces", QUARTER_PIECES, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> TERRACOTTA_QUARTER_PIECES = ofBlockAndItem("terracotta_quarter_pieces", QUARTER_PIECES, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> STAINED_TERRACOTTA_QUARTER_PIECES = ofBlockAndItem("stained_terracotta_quarter_pieces", TERRACOTTA_QUARTER_PIECES);


  /* 纵楼梯 */
  public static final TagKey<Block> VERTICAL_STAIRS = ofBlockAndItem("vertical_stairs");
  public static final TagKey<Block> WOODEN_VERTICAL_STAIRS = ofBlockAndItem("wooden_vertical_stairs", WOODEN_BLOCKS, VERTICAL_STAIRS);
  public static final TagKey<Block> LOG_VERTICAL_STAIRS = ofBlockAndItem("log_vertical_stairs", WOODEN_VERTICAL_STAIRS, LOG_BLOCKS);
  public static final TagKey<Block> WOOLEN_VERTICAL_STAIRS = ofBlockAndItem("woolen_vertical_stairs", WOOLEN_BLOCKS, VERTICAL_STAIRS);
  public static final TagKey<Block> CONCRETE_VERTICAL_STAIRS = ofBlockAndItem("concrete_vertical_stairs", VERTICAL_STAIRS, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> TERRACOTTA_VERTICAL_STAIRS = ofBlockAndItem("terracotta_vertical_stairs", VERTICAL_STAIRS, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> STAINED_TERRACOTTA_VERTICAL_STAIRS = ofBlockAndItem("stained_terracotta_vertical_stairs", TERRACOTTA_VERTICAL_STAIRS);


  /* 纵条 */
  public static final TagKey<Block> VERTICAL_QUARTER_PIECES = ofBlockAndItem("vertical_quarter_pieces");
  public static final TagKey<Block> WOODEN_VERTICAL_QUARTER_PIECES = ofBlockAndItem("wooden_vertical_quarter_pieces", WOODEN_BLOCKS, VERTICAL_QUARTER_PIECES);
  public static final TagKey<Block> LOG_VERTICAL_QUARTER_PIECES = ofBlockAndItem("log_vertical_quarter_pieces", WOODEN_VERTICAL_QUARTER_PIECES, LOG_BLOCKS);
  public static final TagKey<Block> WOOLEN_VERTICAL_QUARTER_PIECES = ofBlockAndItem("woolen_vertical_quarter_pieces", WOOLEN_BLOCKS, VERTICAL_QUARTER_PIECES);
  public static final TagKey<Block> CONCRETE_VERTICAL_QUARTER_PIECES = ofBlockAndItem("concrete_vertical_quarter_pieces", VERTICAL_QUARTER_PIECES, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> TERRACOTTA_VERTICAL_QUARTER_PIECES = ofBlockAndItem("terracotta_vertical_quarter_pieces", VERTICAL_QUARTER_PIECES, BlockTags.PICKAXE_MINEABLE);
  public static final TagKey<Block> STAINED_TERRACOTTA_VERTICAL_QUARTER_PIECES = ofBlockAndItem("stained_terracotta_vertical_quarter_pieces", TERRACOTTA_VERTICAL_QUARTER_PIECES);


  /*
   * 其他部分
   */

  public static final ImmutableMap<BlockShape, TagKey<Block>> SHAPE_TO_LOG_TAG = new ImmutableMap.Builder<BlockShape, TagKey<Block>>()
      .put(BlockShape.STAIRS, ExtShapeTags.LOG_STAIRS)
      .put(BlockShape.SLAB, ExtShapeTags.LOG_SLABS)
      .put(BlockShape.VERTICAL_SLAB, ExtShapeTags.LOG_VERTICAL_SLABS)
      .put(BlockShape.VERTICAL_STAIRS, ExtShapeTags.LOG_VERTICAL_STAIRS)
      .put(BlockShape.QUARTER_PIECE, ExtShapeTags.LOG_QUARTER_PIECES)
      .put(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.LOG_VERTICAL_QUARTER_PIECES)
      .put(BlockShape.FENCE, ExtShapeTags.LOG_FENCES)
      .put(BlockShape.FENCE_GATE, ExtShapeTags.LOG_FENCE_GATES)
      .put(BlockShape.BUTTON, ExtShapeTags.LOG_BUTTONS)
      .put(BlockShape.PRESSURE_PLATE, ExtShapeTags.LOG_PRESSURE_PLATES)
      .put(BlockShape.WALL, ExtShapeTags.LOG_WALLS)
      .build();

  public static final ImmutableMap<BlockShape, TagKey<Block>> SHAPE_TO_WOODEN_TAG = ImmutableMap.<BlockShape, TagKey<Block>>builder()
      .put(BlockShape.STAIRS, ExtShapeTags.WOODEN_STAIRS)
      .put(BlockShape.SLAB, ExtShapeTags.WOODEN_SLABS)
      .put(BlockShape.VERTICAL_SLAB, ExtShapeTags.WOODEN_VERTICAL_SLABS)
      .put(BlockShape.VERTICAL_STAIRS, ExtShapeTags.WOODEN_VERTICAL_STAIRS)
      .put(BlockShape.QUARTER_PIECE, ExtShapeTags.WOODEN_QUARTER_PIECES)
      .put(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.WOODEN_VERTICAL_QUARTER_PIECES)
      .put(BlockShape.FENCE, ExtShapeTags.WOODEN_FENCES)
      .put(BlockShape.PRESSURE_PLATE, ExtShapeTags.WOODEN_PRESSURE_PLATES)
      .put(BlockShape.BUTTON, ExtShapeTags.WOODEN_BUTTONS)
      .put(BlockShape.WALL, ExtShapeTags.WOODEN_WALLS)
      .build();

  /**
   * 类似于 {@link BlockTags#SNOW}，但是不同的是，该方块标签中，{@link net.minecraft.block.SnowyBlock#isSnow(BlockState)} 对于该标签的方块必须有底部的完整碰撞箱才会让方块显示为雪。
   */
  public static final TagKey<Block> SNOW = ofBlockOnly("snow");

  private ExtShapeTags() {
  }

  public static void init() {
  }

  private static TagKey<Block> ofBlockOnly(@NotNull String path) {
    return TagKey.of(Registry.BLOCK_KEY, ExtShape.id(path));
  }

  private static TagKey<Block> ofBlockAndItem(@NotNull String path) {
    final TagKey<Block> blockTag = ofBlockOnly(path);
    TAG_PREPARATIONS.forceSetBlockTagWithItem(blockTag);
    return blockTag;
  }

  @SafeVarargs
  private static TagKey<Block> ofBlockAndItem(@NotNull String path, TagKey<Block>... alsoAddTo) {
    final TagKey<Block> blockTagKey = ofBlockAndItem(path);
    for (TagKey<Block> tagKeyToAddTo : alsoAddTo) {
      TAG_PREPARATIONS.put(tagKeyToAddTo, blockTagKey);
    }
    return blockTagKey;
  }

  private static TagKey<Block> ofBlockAndItem(@NotNull TagKey<Block> blockTag, @NotNull TagKey<Item> itemTag) {
    Preconditions.checkArgument(blockTag.id().equals(itemTag.id()));
    TAG_PREPARATIONS.setBlockTagWithItem(blockTag, itemTag);
    return blockTag;
  }
}