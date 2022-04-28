package pers.solid.extshape.tag;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.block.Blocks.*;

public class ExtShapeBlockTags {
  /**
   * 包含所有已创建的标签。
   **/
  public static final List<UsableTag<?>> TAG_LIST = new ArrayList<>();

  public static final @Unmodifiable ImmutableSet<Block> PLANKS = ImmutableSet.of(
      OAK_PLANKS,
      SPRUCE_PLANKS,
      BIRCH_PLANKS,
      JUNGLE_PLANKS,
      ACACIA_PLANKS,
      DARK_OAK_PLANKS,
      CRIMSON_PLANKS,
      WARPED_PLANKS
  );
  public static final @Unmodifiable ImmutableSet<Block> OVERWORLD_PLANKS = ImmutableSet.of(
      OAK_PLANKS,
      SPRUCE_PLANKS,
      BIRCH_PLANKS,
      JUNGLE_PLANKS,
      ACACIA_PLANKS,
      DARK_OAK_PLANKS
  );
  public static final ExtShapeBlockTag STONE_VARIANTS = ExtShapeBlockTag.createLinked(
      GRANITE,
      POLISHED_GRANITE,
      DIORITE,
      POLISHED_DIORITE,
      ANDESITE,
      POLISHED_ANDESITE
  );
  public static final ExtShapeBlockTag STONES = Util.make(ExtShapeBlockTag.createLinked(
      STONE), blockTag -> blockTag.addTag(STONE_VARIANTS));
  public static final ImmutableSet<Block> SANDSTONES = ImmutableSet.of(
      SANDSTONE,
      CUT_SANDSTONE,
      CHISELED_SANDSTONE,
      SMOOTH_SANDSTONE,
      RED_SANDSTONE,
      CUT_RED_SANDSTONE,
      CHISELED_RED_SANDSTONE,
      SMOOTH_RED_SANDSTONE
  );
  public static final ImmutableSet<Block> WOOLS = ImmutableSet.of(WHITE_WOOL,
      ORANGE_WOOL,
      MAGENTA_WOOL,
      LIGHT_BLUE_WOOL,
      YELLOW_WOOL,
      LIME_WOOL,
      PINK_WOOL,
      GRAY_WOOL,
      LIGHT_GRAY_WOOL,
      CYAN_WOOL,
      PURPLE_WOOL,
      BLUE_WOOL,
      BROWN_WOOL,
      GREEN_WOOL,
      RED_WOOL,
      BLACK_WOOL);
  public static final ImmutableSet<Block> GLAZED_TERRACOTTA = ImmutableSet.of(
      WHITE_GLAZED_TERRACOTTA,
      ORANGE_GLAZED_TERRACOTTA,
      MAGENTA_GLAZED_TERRACOTTA,
      LIGHT_BLUE_GLAZED_TERRACOTTA,
      YELLOW_GLAZED_TERRACOTTA,
      LIME_GLAZED_TERRACOTTA,
      PINK_GLAZED_TERRACOTTA,
      GRAY_GLAZED_TERRACOTTA,
      LIGHT_GRAY_GLAZED_TERRACOTTA,
      CYAN_GLAZED_TERRACOTTA,
      PURPLE_GLAZED_TERRACOTTA,
      BLUE_GLAZED_TERRACOTTA,
      BROWN_GLAZED_TERRACOTTA,
      GREEN_GLAZED_TERRACOTTA,
      RED_GLAZED_TERRACOTTA,
      BLACK_GLAZED_TERRACOTTA
  );
  public static final ImmutableSet<Block> CONCRETES = ImmutableSet.of(WHITE_CONCRETE,
      ORANGE_CONCRETE,
      MAGENTA_CONCRETE,
      LIGHT_BLUE_CONCRETE,
      YELLOW_CONCRETE,
      LIME_CONCRETE,
      PINK_CONCRETE,
      GRAY_CONCRETE,
      LIGHT_GRAY_CONCRETE,
      CYAN_CONCRETE,
      PURPLE_CONCRETE,
      BLUE_CONCRETE,
      BROWN_CONCRETE,
      GREEN_CONCRETE,
      RED_CONCRETE,
      BLACK_CONCRETE);
  public static final ImmutableSet<Block> STAINED_TERRACOTTA = ImmutableSet.of(
      WHITE_TERRACOTTA,
      ORANGE_TERRACOTTA,
      MAGENTA_TERRACOTTA,
      LIGHT_BLUE_TERRACOTTA,
      YELLOW_TERRACOTTA,
      LIME_TERRACOTTA,
      PINK_TERRACOTTA,
      GRAY_TERRACOTTA,
      LIGHT_GRAY_TERRACOTTA,
      CYAN_TERRACOTTA,
      PURPLE_TERRACOTTA,
      BLUE_TERRACOTTA,
      BROWN_TERRACOTTA,
      GREEN_TERRACOTTA,
      RED_TERRACOTTA,
      BLACK_TERRACOTTA
  );

  /**
   * 包含本模组中所有方块的标签。其可能直接包含方块，亦有可能通过其他标签间接包含方块。如需迭代，建议使用 {@link pers.solid.extshape.block.ExtShapeBlocks#BLOCKS}。
   */
  public static final ExtShapeBlockTag EXTSHAPE_BLOCKS = ExtShapeBlockTag.createLinked();

  /**
   * 完整大小的方块（主要用于双石台阶方块）。
   */
  public static final ExtShapeBlockTag FULL_BLOCKS = ExtShapeBlockTag.createLinked().addToTag(EXTSHAPE_BLOCKS);

  /**
   * 所有羊毛衍生方块（不含羊毛本身）。这些方块会被注册可燃，并被加入到 {@link TagGenerator#OCCLUDES_VIBRATION_SIGNALS} 中（仅限 1.17 以上版本）。
   */
  public static final ExtShapeBlockTag WOOLEN_BLOCKS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "woolen_blocks"));
  /**
   * 所有的木质方块，包括下界木。
   */
  public static final ExtShapeBlockTag WOODEN_BLOCKS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "wooden_blocks"));
  /**
   * 所有主世界的木质方块，用于可燃。
   */
  public static final ExtShapeBlockTag OVERWORLD_WOODEN_BLOCKS = ExtShapeBlockTag.createBiPart(null); // 仅包含由模组添加的方块，用于模组内部使用


  /* 楼梯 */

  public static final ExtShapeBlockTag STAIRS = ExtShapeBlockTag.createLinked(new Identifier("minecraft", "stairs")).addToTag(EXTSHAPE_BLOCKS);
  public static final ExtShapeBlockTag WOOLEN_STAIRS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "woolen_stairs")).addToTag(WOOLEN_BLOCKS).addToTag(STAIRS);
  public static final ExtShapeBlockTag CONCRETE_STAIRS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "concrete_stairs")).addToTag(STAIRS);
  public static final ExtShapeBlockTag TERRACOTTA_STAIRS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "terracotta_stairs")).addToTag(STAIRS);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_STAIRS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "stained_terracotta_stairs")).addToTag(TERRACOTTA_STAIRS);



  /* 台阶 */

  public static final ExtShapeBlockTag SLABS = ExtShapeBlockTag.createLinked(new Identifier("minecraft", "slabs")).addToTag(EXTSHAPE_BLOCKS);
  public static final ExtShapeBlockTag WOOLEN_SLABS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "woolen_slabs")).addToTag(WOOLEN_BLOCKS).addToTag(SLABS);
  public static final ExtShapeBlockTag CONCRETE_SLABS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "concrete_slabs")).addToTag(SLABS);
  public static final ExtShapeBlockTag TERRACOTTA_SLABS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "terracotta_slabs")).addToTag(SLABS);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_SLABS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "stained_terracotta_slabs")).addToTag(TERRACOTTA_SLABS);
  public static final ExtShapeBlockTag GLAZED_TERRACOTTA_SLABS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "glazed_terracotta_slabs")).addToTag(SLABS);



  /* 栅栏 */

  public static final ExtShapeBlockTag FENCES = ExtShapeBlockTag.createLinked(new Identifier("minecraft", "fences")).addToTag(EXTSHAPE_BLOCKS);
  public static final ExtShapeBlockTag WOOLEN_FENCES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "woolen_fences")).addToTag(WOOLEN_BLOCKS).addToTag(FENCES);
  public static final ExtShapeBlockTag CONCRETE_FENCES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "concrete_fences")).addToTag(FENCES);
  public static final ExtShapeBlockTag TERRACOTTA_FENCES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "terracotta_fences")).addToTag(FENCES);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_FENCES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "stained_terracotta_fences")).addToTag(TERRACOTTA_FENCES);



  /* 栅栏门 */

  public static final ExtShapeBlockTag FENCE_GATES = ExtShapeBlockTag.createLinked(new Identifier("minecraft", "fence_gates")).addToTag(EXTSHAPE_BLOCKS);
  public static final ExtShapeBlockTag WOOLEN_FENCE_GATES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "woolen_fence_gates")).addToTag(WOOLEN_BLOCKS).addToTag(FENCE_GATES);
  public static final ExtShapeBlockTag CONCRETE_FENCE_GATES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "concrete_fence_gates")).addToTag(FENCE_GATES);
  public static final ExtShapeBlockTag TERRACOTTA_FENCE_GATES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "terracotta_fence_gates")).addToTag(FENCE_GATES);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_FENCE_GATES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "stained_terracotta_fence_gates")).addToTag(TERRACOTTA_FENCE_GATES);



  /* 墙 */

  public static final ExtShapeBlockTag WALLS = ExtShapeBlockTag.createLinked(new Identifier("minecraft", "walls")).addToTag(EXTSHAPE_BLOCKS);
  public static final ExtShapeBlockTag CONCRETE_WALLS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "concrete_fence_walls")).addToTag(WALLS);
  public static final ExtShapeBlockTag TERRACOTTA_WALLS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "terracotta_fence_walls")).addToTag(WALLS);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_WALLS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "stained_terracotta_fence_walls")).addToTag(TERRACOTTA_WALLS);



  /* 按钮 */

  public static final ExtShapeBlockTag BUTTONS = ExtShapeBlockTag.createLinked(new Identifier("minecraft", "buttons")).addToTag(EXTSHAPE_BLOCKS);
  public static final ExtShapeBlockTag WOOLEN_BUTTONS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "woolen_buttons")).addToTag(WOOLEN_BLOCKS).addToTag(BUTTONS);
  public static final ExtShapeBlockTag CONCRETE_BUTTONS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "concrete_buttons")).addToTag(BUTTONS);
  public static final ExtShapeBlockTag TERRACOTTA_BUTTONS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "terracotta_buttons")).addToTag(BUTTONS);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_BUTTONS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "stained_terracotta_buttons")).addToTag(TERRACOTTA_BUTTONS);



  /* 压力板 */

  public static final ExtShapeBlockTag PRESSURE_PLATES = ExtShapeBlockTag.createLinked(new Identifier("minecraft", "pressure_plates")).addToTag(EXTSHAPE_BLOCKS);
  public static final ExtShapeBlockTag CONCRETE_PRESSURE_PLATES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "concrete_pressure_plates")).addToTag(PRESSURE_PLATES);
  public static final ExtShapeBlockTag TERRACOTTA_PRESSURE_PLATES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "terracotta_pressure_plates")).addToTag(PRESSURE_PLATES);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_PRESSURE_PLATES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "stained_terracotta_pressure_plates")).addToTag(TERRACOTTA_PRESSURE_PLATES);
  public static final ExtShapeBlockTag WOOLEN_PRESSURE_PLATES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "woolen_pressure_plates")).addToTag(PRESSURE_PLATES);



  /* 纵向台阶 */

  public static final ExtShapeBlockTag VERTICAL_SLABS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "vertical_slabs")).addToTag(EXTSHAPE_BLOCKS);
  public static final ExtShapeBlockTag WOODEN_VERTICAL_SLABS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "wooden_vertical_slabs")).addToTag(WOODEN_BLOCKS).addToTag(VERTICAL_SLABS);
  public static final ExtShapeBlockTag WOOLEN_VERTICAL_SLABS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "woolen_vertical_slabs")).addToTag(WOOLEN_BLOCKS).addToTag(VERTICAL_SLABS);
  public static final ExtShapeBlockTag CONCRETE_VERTICAL_SLABS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "concrete_vertical_slabs")).addToTag(VERTICAL_SLABS);
  public static final ExtShapeBlockTag TERRACOTTA_VERTICAL_SLABS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "terracotta_vertical_slabs")).addToTag(VERTICAL_SLABS);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_VERTICAL_SLABS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "stained_terracotta_vertical_slabs")).addToTag(TERRACOTTA_VERTICAL_SLABS);



  /* 横条 */

  public static final ExtShapeBlockTag QUARTER_PIECES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "quarter_pieces")).addToTag(EXTSHAPE_BLOCKS);
  public static final ExtShapeBlockTag WOODEN_QUARTER_PIECES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "wooden_quarter_pieces")).addToTag(WOODEN_BLOCKS).addToTag(QUARTER_PIECES);
  public static final ExtShapeBlockTag WOOLEN_QUARTER_PIECES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "woolen_quarter_pieces")).addToTag(WOOLEN_BLOCKS).addToTag(QUARTER_PIECES);
  public static final ExtShapeBlockTag CONCRETE_QUARTER_PIECES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "concrete_quarter_pieces")).addToTag(QUARTER_PIECES);
  public static final ExtShapeBlockTag TERRACOTTA_QUARTER_PIECES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "terracotta_quarter_pieces")).addToTag(QUARTER_PIECES);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_QUARTER_PIECES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "stained_terracotta_quarter_pieces")).addToTag(TERRACOTTA_QUARTER_PIECES);




  /* 纵楼梯 */

  public static final ExtShapeBlockTag VERTICAL_STAIRS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "vertical_stairs")).addToTag(EXTSHAPE_BLOCKS);
  public static final ExtShapeBlockTag WOODEN_VERTICAL_STAIRS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "wooden_vertical_stairs")).addToTag(WOODEN_BLOCKS).addToTag(VERTICAL_STAIRS);
  public static final ExtShapeBlockTag WOOLEN_VERTICAL_STAIRS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "woolen_vertical_stairs")).addToTag(WOOLEN_BLOCKS).addToTag(VERTICAL_STAIRS);
  public static final ExtShapeBlockTag CONCRETE_VERTICAL_STAIRS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "concrete_vertical_stairs")).addToTag(VERTICAL_STAIRS);
  public static final ExtShapeBlockTag TERRACOTTA_VERTICAL_STAIRS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "terracotta_vertical_stairs")).addToTag(VERTICAL_STAIRS);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_VERTICAL_STAIRS = ExtShapeBlockTag.createLinked(new Identifier("extshape", "stained_terracotta_vertical_stairs")).addToTag(TERRACOTTA_VERTICAL_STAIRS);



  /* 纵条 */

  public static final ExtShapeBlockTag VERTICAL_QUARTER_PIECES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "vertical_quarter_pieces")).addToTag(EXTSHAPE_BLOCKS);
  public static final ExtShapeBlockTag WOODEN_VERTICAL_QUARTER_PIECES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "wooden_vertical_quarter_pieces")).addToTag(WOODEN_BLOCKS).addToTag(VERTICAL_QUARTER_PIECES);
  public static final ExtShapeBlockTag WOOLEN_VERTICAL_QUARTER_PIECES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "woolen_vertical_quarter_pieces")).addToTag(WOOLEN_BLOCKS).addToTag(VERTICAL_QUARTER_PIECES);
  public static final ExtShapeBlockTag CONCRETE_VERTICAL_QUARTER_PIECES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "concrete_vertical_quarter_pieces")).addToTag(VERTICAL_QUARTER_PIECES);
  public static final ExtShapeBlockTag TERRACOTTA_VERTICAL_QUARTER_PIECES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "terracotta_vertical_quarter_pieces")).addToTag(VERTICAL_QUARTER_PIECES);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_VERTICAL_QUARTER_PIECES = ExtShapeBlockTag.createLinked(new Identifier("extshape", "stained_terracotta_vertical_quarter_pieces")).addToTag(TERRACOTTA_VERTICAL_QUARTER_PIECES);
}