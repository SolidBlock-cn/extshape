package pers.solid.extshape.tag;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.mininglevel.v1.FabricMineableTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.builder.BlockShape;

/**
 * 本模组提供的方块和物品标签。这些标签不应该被其他的模组使用。
 */
public final class ExtShapeTags {
  public static final TagKey<Block> DAMPENS_VIBRATIONS = BlockTags.DAMPENS_VIBRATIONS;
  /**
   * 这个标签主要是考虑到，{@link BlockTags#PICKAXE_MINEABLE} 加入了所有的墙，但事实上墙并不一定是可以使用镐来开采的，因此这里设置了一个专门的标签，如果某个方块属于 {@code minecraft:mineable/pickaxe} 但同时属于 {@code extshape:pickaxe_unmineable}，则认为镐子不能开采这个方块。
   */
  @ApiStatus.AvailableSince("0.1.5")
  public static final TagKey<Block> PICKAXE_UNMINEABLE = of("pickaxe_unmineable");
  /**
   * 所有羊毛衍生方块（不含羊毛本身）。这些方块会被注册可燃，会被剪刀剪掉，并被加入到 {@link BlockTags#OCCLUDES_VIBRATION_SIGNALS} 中（仅限 1.17 以上版本）。
   */
  public static final TagKey<Block> WOOLEN_BLOCKS = of("woolen_blocks");
  /**
   * 所有的木质方块，包括下界木，不含原版方块。
   */
  public static final TagKey<Block> WOODEN_BLOCKS = of("wooden_blocks");

  /**
   * 所有的原木、木头方块（包括去皮的）。不含原版方块。
   */
  public static final TagKey<Block> LOG_BLOCKS = of("log_blocks");

  /* 楼梯 */
  public static final TagKey<Block> STAIRS = BlockTags.STAIRS;
  public static final TagKey<Block> WOODEN_STAIRS = BlockTags.WOODEN_STAIRS;
  public static final TagKey<Block> LOG_STAIRS = of("log_stairs");
  public static final TagKey<Block> WOOLEN_STAIRS = of("woolen_stairs");
  public static final TagKey<Block> CONCRETE_STAIRS = of("concrete_stairs");
  public static final TagKey<Block> TERRACOTTA_STAIRS = of("terracotta_stairs");
  public static final TagKey<Block> STAINED_TERRACOTTA_STAIRS = of("stained_terracotta_stairs");


  /* 台阶 */
  public static final TagKey<Block> SLABS = BlockTags.SLABS;
  public static final TagKey<Block> WOODEN_SLABS = BlockTags.WOODEN_SLABS;
  public static final TagKey<Block> LOG_SLABS = of("log_slabs");
  public static final TagKey<Block> WOOLEN_SLABS = of("woolen_slabs");
  public static final TagKey<Block> CONCRETE_SLABS = of("concrete_slabs");
  public static final TagKey<Block> TERRACOTTA_SLABS = of("terracotta_slabs");
  public static final TagKey<Block> STAINED_TERRACOTTA_SLABS = of("stained_terracotta_slabs");
  public static final TagKey<Block> GLAZED_TERRACOTTA_SLABS = of("glazed_terracotta_slabs");


  /* 栅栏 */
  public static final TagKey<Block> FENCES = BlockTags.FENCES;
  public static final TagKey<Block> WOODEN_FENCES = BlockTags.WOODEN_FENCES;
  public static final TagKey<Block> LOG_FENCES = of("log_fences");
  public static final TagKey<Block> WOOLEN_FENCES = of("woolen_fences");
  public static final TagKey<Block> CONCRETE_FENCES = of("concrete_fences");
  public static final TagKey<Block> TERRACOTTA_FENCES = of("terracotta_fences");
  public static final TagKey<Block> STAINED_TERRACOTTA_FENCES = of("stained_terracotta_fences");


  /* 栅栏门 */
  public static final TagKey<Block> FENCE_GATES = BlockTags.FENCE_GATES;
  public static final TagKey<Block> LOG_FENCE_GATES = of("log_fence_gates");
  public static final TagKey<Block> WOOLEN_FENCE_GATES = of("woolen_fence_gates");
  public static final TagKey<Block> CONCRETE_FENCE_GATES = of("concrete_fence_gates");
  public static final TagKey<Block> TERRACOTTA_FENCE_GATES = of("terracotta_fence_gates");
  public static final TagKey<Block> STAINED_TERRACOTTA_FENCE_GATES = of("stained_terracotta_fence_gates");


  /* 墙 */
  public static final TagKey<Block> WALLS = BlockTags.WALLS;
  @ApiStatus.AvailableSince("1.5.0")
  public static final TagKey<Block> WOODEN_WALLS = of("wooden_walls");
  public static final TagKey<Block> LOG_WALLS = of("log_walls");
  @ApiStatus.AvailableSince("1.5.0")
  public static final TagKey<Block> WOOLEN_WALLS = of("woolen_walls");
  public static final TagKey<Block> CONCRETE_WALLS = of("concrete_walls");
  public static final TagKey<Block> TERRACOTTA_WALLS = of("terracotta_walls");
  public static final TagKey<Block> STAINED_TERRACOTTA_WALLS = of("stained_terracotta_walls");


  /* 按钮 */
  public static final TagKey<Block> BUTTONS = BlockTags.BUTTONS;
  public static final TagKey<Block> WOODEN_BUTTONS = BlockTags.WOODEN_BUTTONS;
  public static final TagKey<Block> LOG_BUTTONS = of("log_buttons");
  public static final TagKey<Block> WOOLEN_BUTTONS = of("woolen_buttons");
  public static final TagKey<Block> CONCRETE_BUTTONS = of("concrete_buttons");
  public static final TagKey<Block> TERRACOTTA_BUTTONS = of("terracotta_buttons");
  public static final TagKey<Block> STAINED_TERRACOTTA_BUTTONS = of("stained_terracotta_buttons");


  /* 压力板 */
  public static final TagKey<Block> PRESSURE_PLATES = BlockTags.PRESSURE_PLATES;  // ItemTags.PRESSURE_PLATES不存在
  public static final TagKey<Block> WOODEN_PRESSURE_PLATES = BlockTags.WOODEN_PRESSURE_PLATES;
  public static final TagKey<Block> LOG_PRESSURE_PLATES = of("log_pressure_plates");
  public static final TagKey<Block> WOOLEN_PRESSURE_PLATES = of("woolen_pressure_plates");
  public static final TagKey<Block> CONCRETE_PRESSURE_PLATES = of("concrete_pressure_plates");
  public static final TagKey<Block> TERRACOTTA_PRESSURE_PLATES = of("terracotta_pressure_plates");
  public static final TagKey<Block> STAINED_TERRACOTTA_PRESSURE_PLATES = of("stained_terracotta_pressure_plates");


  /* 竖直台阶 */
  public static final TagKey<Block> VERTICAL_SLABS = of("vertical_slabs");
  public static final TagKey<Block> WOODEN_VERTICAL_SLABS = of("wooden_vertical_slabs");
  public static final TagKey<Block> LOG_VERTICAL_SLABS = of("log_vertical_slabs");
  public static final TagKey<Block> WOOLEN_VERTICAL_SLABS = of("woolen_vertical_slabs");
  public static final TagKey<Block> CONCRETE_VERTICAL_SLABS = of("concrete_vertical_slabs");
  public static final TagKey<Block> TERRACOTTA_VERTICAL_SLABS = of("terracotta_vertical_slabs");
  public static final TagKey<Block> STAINED_TERRACOTTA_VERTICAL_SLABS = of("stained_terracotta_vertical_slabs");


  /* 横条 */
  public static final TagKey<Block> QUARTER_PIECES = of("quarter_pieces");
  public static final TagKey<Block> WOODEN_QUARTER_PIECES = of("wooden_quarter_pieces");
  public static final TagKey<Block> LOG_QUARTER_PIECES = of("log_quarter_pieces");
  public static final TagKey<Block> WOOLEN_QUARTER_PIECES = of("woolen_quarter_pieces");
  public static final TagKey<Block> CONCRETE_QUARTER_PIECES = of("concrete_quarter_pieces");
  public static final TagKey<Block> TERRACOTTA_QUARTER_PIECES = of("terracotta_quarter_pieces");
  public static final TagKey<Block> STAINED_TERRACOTTA_QUARTER_PIECES = of("stained_terracotta_quarter_pieces");


  /* 纵楼梯 */
  public static final TagKey<Block> VERTICAL_STAIRS = of("vertical_stairs");
  public static final TagKey<Block> WOODEN_VERTICAL_STAIRS = of("wooden_vertical_stairs");
  public static final TagKey<Block> LOG_VERTICAL_STAIRS = of("log_vertical_stairs");
  public static final TagKey<Block> WOOLEN_VERTICAL_STAIRS = of("woolen_vertical_stairs");
  public static final TagKey<Block> CONCRETE_VERTICAL_STAIRS = of("concrete_vertical_stairs");
  public static final TagKey<Block> TERRACOTTA_VERTICAL_STAIRS = of("terracotta_vertical_stairs");
  public static final TagKey<Block> STAINED_TERRACOTTA_VERTICAL_STAIRS = of("stained_terracotta_vertical_stairs");


  /* 纵条 */
  public static final TagKey<Block> VERTICAL_QUARTER_PIECES = of("vertical_quarter_pieces");
  public static final TagKey<Block> WOODEN_VERTICAL_QUARTER_PIECES = of("wooden_vertical_quarter_pieces");
  public static final TagKey<Block> LOG_VERTICAL_QUARTER_PIECES = of("log_vertical_quarter_pieces");
  public static final TagKey<Block> WOOLEN_VERTICAL_QUARTER_PIECES = of("woolen_vertical_quarter_pieces");
  public static final TagKey<Block> CONCRETE_VERTICAL_QUARTER_PIECES = of("concrete_vertical_quarter_pieces");
  public static final TagKey<Block> TERRACOTTA_VERTICAL_QUARTER_PIECES = of("terracotta_vertical_quarter_pieces");
  public static final TagKey<Block> STAINED_TERRACOTTA_VERTICAL_QUARTER_PIECES = of("stained_terracotta_vertical_quarter_pieces");


  /*
   * 其他部分
   */

  public static final ImmutableMap<BlockShape, TagKey<Block>> SHAPE_TO_TAG = new ImmutableMap.Builder<BlockShape, TagKey<Block>>()
      .put(BlockShape.STAIRS, ExtShapeTags.STAIRS)
      .put(BlockShape.SLAB, ExtShapeTags.SLABS)
      .put(BlockShape.VERTICAL_SLAB, ExtShapeTags.VERTICAL_SLABS)
      .put(BlockShape.VERTICAL_STAIRS, ExtShapeTags.VERTICAL_STAIRS)
      .put(BlockShape.QUARTER_PIECE, ExtShapeTags.QUARTER_PIECES)
      .put(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.VERTICAL_QUARTER_PIECES)
      .put(BlockShape.FENCE, ExtShapeTags.FENCES)
      .put(BlockShape.FENCE_GATE, ExtShapeTags.FENCE_GATES)
      .put(BlockShape.WALL, ExtShapeTags.WALLS)
      .put(BlockShape.BUTTON, ExtShapeTags.BUTTONS)
      .put(BlockShape.PRESSURE_PLATE, ExtShapeTags.PRESSURE_PLATES)
      .build();
  public static final ImmutableMap<BlockShape, TagKey<Block>> SHAPE_TO_WOODEN_TAG = new ImmutableMap.Builder<BlockShape, TagKey<Block>>()
      .put(BlockShape.STAIRS, ExtShapeTags.WOODEN_STAIRS)
      .put(BlockShape.SLAB, ExtShapeTags.WOODEN_SLABS)
      .put(BlockShape.VERTICAL_SLAB, ExtShapeTags.WOODEN_VERTICAL_SLABS)
      .put(BlockShape.VERTICAL_STAIRS, ExtShapeTags.WOODEN_VERTICAL_STAIRS)
      .put(BlockShape.QUARTER_PIECE, ExtShapeTags.WOODEN_QUARTER_PIECES)
      .put(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.WOODEN_VERTICAL_QUARTER_PIECES)
      .put(BlockShape.FENCE, ExtShapeTags.WOODEN_FENCES)
      .put(BlockShape.WALL, ExtShapeTags.WOODEN_WALLS)
      .put(BlockShape.BUTTON, ExtShapeTags.WOODEN_BUTTONS)
      .put(BlockShape.PRESSURE_PLATE, ExtShapeTags.WOODEN_PRESSURE_PLATES)
      .build();
  public static final ImmutableMap<BlockShape, TagKey<Block>> SHAPE_TO_LOG_TAG = new ImmutableMap.Builder<BlockShape, TagKey<Block>>()
      .put(BlockShape.STAIRS, ExtShapeTags.LOG_STAIRS)
      .put(BlockShape.SLAB, ExtShapeTags.LOG_SLABS)
      .put(BlockShape.VERTICAL_SLAB, ExtShapeTags.LOG_VERTICAL_SLABS)
      .put(BlockShape.VERTICAL_STAIRS, ExtShapeTags.LOG_VERTICAL_STAIRS)
      .put(BlockShape.QUARTER_PIECE, ExtShapeTags.LOG_QUARTER_PIECES)
      .put(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.LOG_VERTICAL_QUARTER_PIECES)
      .put(BlockShape.FENCE, ExtShapeTags.LOG_FENCES)
      .put(BlockShape.FENCE_GATE, ExtShapeTags.LOG_FENCE_GATES)
      .put(BlockShape.WALL, ExtShapeTags.LOG_WALLS)
      .put(BlockShape.BUTTON, ExtShapeTags.LOG_BUTTONS)
      .put(BlockShape.PRESSURE_PLATE, ExtShapeTags.LOG_PRESSURE_PLATES)
      .build();
  public static final ImmutableMap<BlockShape, TagKey<Block>> SHAPE_TO_WOOLEN_TAG = new ImmutableMap.Builder<BlockShape, TagKey<Block>>()
      .put(BlockShape.STAIRS, ExtShapeTags.WOOLEN_STAIRS)
      .put(BlockShape.SLAB, ExtShapeTags.WOOLEN_SLABS)
      .put(BlockShape.VERTICAL_SLAB, ExtShapeTags.WOOLEN_VERTICAL_SLABS)
      .put(BlockShape.VERTICAL_STAIRS, ExtShapeTags.WOOLEN_VERTICAL_STAIRS)
      .put(BlockShape.QUARTER_PIECE, ExtShapeTags.WOOLEN_QUARTER_PIECES)
      .put(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.WOOLEN_VERTICAL_QUARTER_PIECES)
      .put(BlockShape.FENCE, ExtShapeTags.WOOLEN_FENCES)
      .put(BlockShape.FENCE_GATE, ExtShapeTags.WOOLEN_FENCE_GATES)
      .put(BlockShape.WALL, ExtShapeTags.WOOLEN_WALLS)
      .put(BlockShape.BUTTON, ExtShapeTags.WOOLEN_BUTTONS)
      .put(BlockShape.PRESSURE_PLATE, ExtShapeTags.WOOLEN_PRESSURE_PLATES)
      .build();
  public static final ImmutableMap<BlockShape, TagKey<Block>> SHAPE_TO_CONCRETE_TAG = new ImmutableMap.Builder<BlockShape, TagKey<Block>>()
      .put(BlockShape.STAIRS, ExtShapeTags.CONCRETE_STAIRS)
      .put(BlockShape.SLAB, ExtShapeTags.CONCRETE_SLABS)
      .put(BlockShape.VERTICAL_SLAB, ExtShapeTags.CONCRETE_VERTICAL_SLABS)
      .put(BlockShape.VERTICAL_STAIRS, ExtShapeTags.CONCRETE_VERTICAL_STAIRS)
      .put(BlockShape.QUARTER_PIECE, ExtShapeTags.CONCRETE_QUARTER_PIECES)
      .put(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.CONCRETE_VERTICAL_QUARTER_PIECES)
      .put(BlockShape.FENCE, ExtShapeTags.CONCRETE_FENCES)
      .put(BlockShape.FENCE_GATE, ExtShapeTags.CONCRETE_FENCE_GATES)
      .put(BlockShape.WALL, ExtShapeTags.CONCRETE_WALLS)
      .put(BlockShape.BUTTON, ExtShapeTags.CONCRETE_BUTTONS)
      .put(BlockShape.PRESSURE_PLATE, ExtShapeTags.CONCRETE_PRESSURE_PLATES)
      .build();
  public static final ImmutableMap<BlockShape, TagKey<Block>> SHAPE_TO_TERRACOTTA_TAG = new ImmutableMap.Builder<BlockShape, TagKey<Block>>()
      .put(BlockShape.STAIRS, ExtShapeTags.TERRACOTTA_STAIRS)
      .put(BlockShape.SLAB, ExtShapeTags.TERRACOTTA_SLABS)
      .put(BlockShape.VERTICAL_SLAB, ExtShapeTags.TERRACOTTA_VERTICAL_SLABS)
      .put(BlockShape.VERTICAL_STAIRS, ExtShapeTags.TERRACOTTA_VERTICAL_STAIRS)
      .put(BlockShape.QUARTER_PIECE, ExtShapeTags.TERRACOTTA_QUARTER_PIECES)
      .put(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.TERRACOTTA_VERTICAL_QUARTER_PIECES)
      .put(BlockShape.FENCE, ExtShapeTags.TERRACOTTA_FENCES)
      .put(BlockShape.FENCE_GATE, ExtShapeTags.TERRACOTTA_FENCE_GATES)
      .put(BlockShape.WALL, ExtShapeTags.TERRACOTTA_WALLS)
      .put(BlockShape.BUTTON, ExtShapeTags.TERRACOTTA_BUTTONS)
      .put(BlockShape.PRESSURE_PLATE, ExtShapeTags.TERRACOTTA_PRESSURE_PLATES)
      .build();
  public static final ImmutableMap<BlockShape, TagKey<Block>> SHAPE_TO_STAINED_TERRACOTTA_TAG = new ImmutableMap.Builder<BlockShape, TagKey<Block>>()
      .put(BlockShape.STAIRS, ExtShapeTags.STAINED_TERRACOTTA_STAIRS)
      .put(BlockShape.SLAB, ExtShapeTags.STAINED_TERRACOTTA_SLABS)
      .put(BlockShape.VERTICAL_SLAB, ExtShapeTags.STAINED_TERRACOTTA_VERTICAL_SLABS)
      .put(BlockShape.VERTICAL_STAIRS, ExtShapeTags.STAINED_TERRACOTTA_VERTICAL_STAIRS)
      .put(BlockShape.QUARTER_PIECE, ExtShapeTags.STAINED_TERRACOTTA_QUARTER_PIECES)
      .put(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.STAINED_TERRACOTTA_VERTICAL_QUARTER_PIECES)
      .put(BlockShape.FENCE, ExtShapeTags.STAINED_TERRACOTTA_FENCES)
      .put(BlockShape.FENCE_GATE, ExtShapeTags.STAINED_TERRACOTTA_FENCE_GATES)
      .put(BlockShape.WALL, ExtShapeTags.STAINED_TERRACOTTA_WALLS)
      .put(BlockShape.BUTTON, ExtShapeTags.STAINED_TERRACOTTA_BUTTONS)
      .put(BlockShape.PRESSURE_PLATE, ExtShapeTags.STAINED_TERRACOTTA_PRESSURE_PLATES)
      .build();

  /**
   * 类似于 {@link BlockTags#SNOW}，但是不同的是，该方块标签中，{@link net.minecraft.block.SnowyBlock#isSnow(BlockState)} 对于该标签的方块必须有底部的完整碰撞箱才会让方块显示为雪。
   */
  public static final TagKey<Block> SNOW = of("snow");

  private ExtShapeTags() {
  }

  public static void init() {
  }

  private static TagKey<Block> of(@NotNull String path) {
    return TagKey.of(RegistryKeys.BLOCK, ExtShape.id(path));
  }

}