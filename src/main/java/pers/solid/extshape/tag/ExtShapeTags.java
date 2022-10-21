package pers.solid.extshape.tag;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.fabric.api.mininglevel.v1.FabricMineableTags;
import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.mixin.AbstractBlockAccessor;

import static net.minecraft.block.Blocks.*;
import static pers.solid.extshape.tag.ExtShapeBlockTag.create;

/**
 * 本模组提供的方块标签，其中包括了少量物品标签。这些标签都是在运行时生成的。
 */
public final class ExtShapeTags {
  public static final ImmutableList<Block> LOGS = ImmutableList.of(
      OAK_LOG,
      SPRUCE_LOG,
      BIRCH_LOG,
      JUNGLE_LOG,
      ACACIA_LOG,
      DARK_OAK_LOG,
      MANGROVE_LOG
  );
  public static final ImmutableList<Block> STRIPPED_LOGS = ImmutableList.of(
      STRIPPED_OAK_LOG,
      STRIPPED_SPRUCE_LOG,
      STRIPPED_BIRCH_LOG,
      STRIPPED_JUNGLE_LOG,
      STRIPPED_ACACIA_LOG,
      STRIPPED_DARK_OAK_LOG,
      STRIPPED_OAK_LOG,
      STRIPPED_MANGROVE_LOG
  );
  public static final ImmutableList<Block> WOODS = ImmutableList.of(
      OAK_WOOD,
      SPRUCE_WOOD,
      BIRCH_WOOD,
      JUNGLE_WOOD,
      ACACIA_WOOD,
      DARK_OAK_WOOD,
      MANGROVE_WOOD
  );
  public static final ImmutableList<Block> STRIPPED_WOODS = ImmutableList.of(
      STRIPPED_OAK_WOOD,
      STRIPPED_SPRUCE_WOOD,
      STRIPPED_BIRCH_WOOD,
      STRIPPED_JUNGLE_WOOD,
      STRIPPED_ACACIA_WOOD,
      STRIPPED_DARK_OAK_WOOD,
      STRIPPED_MANGROVE_WOOD
  );
  public static final ImmutableList<Block> STEMS = ImmutableList.of(
      WARPED_STEM,
      CRIMSON_STEM
  );
  public static final ImmutableList<Block> STRIPPED_STEMS = ImmutableList.of(
      STRIPPED_WARPED_STEM,
      STRIPPED_CRIMSON_STEM
  );
  public static final ImmutableList<Block> HYPHAES = ImmutableList.of(
      WARPED_HYPHAE,
      CRIMSON_HYPHAE
  );
  public static final ImmutableList<Block> STRIPPED_HYPHAES = ImmutableList.of(
      STRIPPED_WARPED_HYPHAE,
      STRIPPED_CRIMSON_HYPHAE
  );

  public static final @Unmodifiable ImmutableSet<Block> PLANKS = ImmutableSet.of(
      OAK_PLANKS,
      SPRUCE_PLANKS,
      BIRCH_PLANKS,
      JUNGLE_PLANKS,
      ACACIA_PLANKS,
      DARK_OAK_PLANKS,
      MANGROVE_PLANKS /* 1.19 */,
      CRIMSON_PLANKS,
      WARPED_PLANKS
  );
  public static final @Unmodifiable ImmutableSet<Block> OVERWORLD_PLANKS = ImmutableSet.of(
      OAK_PLANKS,
      SPRUCE_PLANKS,
      BIRCH_PLANKS,
      JUNGLE_PLANKS,
      ACACIA_PLANKS,
      DARK_OAK_PLANKS,
      MANGROVE_PLANKS /* 1.19 */
  );
  public static final @Unmodifiable ImmutableList<Block> STONE_VARIANTS = ImmutableList.of(
      GRANITE,
      POLISHED_GRANITE,
      DIORITE,
      POLISHED_DIORITE,
      ANDESITE,
      POLISHED_ANDESITE
  );
  public static final @Unmodifiable ImmutableList<Block> STONES = new ImmutableList.Builder<Block>().add(STONE).addAll(STONE_VARIANTS).build();
  public static final @Unmodifiable ImmutableSet<Block> SANDSTONES = ImmutableSet.of(
      SANDSTONE,
      CUT_SANDSTONE,
      CHISELED_SANDSTONE,
      SMOOTH_SANDSTONE,
      RED_SANDSTONE,
      CUT_RED_SANDSTONE,
      CHISELED_RED_SANDSTONE,
      SMOOTH_RED_SANDSTONE
  );
  public static final @Unmodifiable ImmutableSet<Block> WOOLS = ImmutableSet.of(WHITE_WOOL,
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
  public static final @Unmodifiable ImmutableSet<Block> GLAZED_TERRACOTTA = ImmutableSet.of(
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
  public static final @Unmodifiable ImmutableSet<Block> CONCRETES = ImmutableSet.of(WHITE_CONCRETE,
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
  public static final @Unmodifiable ImmutableSet<Block> STAINED_TERRACOTTA = ImmutableSet.of(
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
   * 所有羊毛衍生方块（不含羊毛本身）。这些方块会被注册可燃，会被剪刀剪掉，并被加入到 {@link #OCCLUDES_VIBRATION_SIGNALS} 中（仅限 1.17 以上版本）。
   */
  public static final ExtShapeBlockTag WOOLEN_BLOCKS = create("woolen_blocks");
  /**
   * 所有的木质方块，包括下界木，不含原版方块。
   */
  public static final ExtShapeBlockTag WOODEN_BLOCKS = create("wooden_blocks");
  /**
   * 所有的原木、木头方块（包括去皮的）。不含原版方块。
   */
  public static final ExtShapeBlockTag LOG_BLOCKS = create("log_blocks");


  /* 楼梯 */

  public static final ExtShapeBlockTag STAIRS = create("minecraft", "stairs");
  public static final ExtShapeBlockTag LOG_STAIRS = create("log_stairs").addSelfToTag(STAIRS).addSelfToTag(LOG_BLOCKS);
  public static final ExtShapeBlockTag WOOLEN_STAIRS = create("woolen_stairs").addSelfToTag(WOOLEN_BLOCKS).addSelfToTag(STAIRS);
  public static final ExtShapeBlockTag CONCRETE_STAIRS = create("concrete_stairs").addSelfToTag(STAIRS);
  public static final ExtShapeBlockTag TERRACOTTA_STAIRS = create("terracotta_stairs").addSelfToTag(STAIRS);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_STAIRS = create("stained_terracotta_stairs").addSelfToTag(TERRACOTTA_STAIRS);



  /* 台阶 */

  public static final ExtShapeBlockTag SLABS = create("minecraft", "slabs");
  public static final ExtShapeBlockTag LOG_SLABS = create("log_slabs").addSelfToTag(SLABS).addSelfToTag(LOG_BLOCKS);
  public static final ExtShapeBlockTag WOOLEN_SLABS = create("woolen_slabs").addSelfToTag(WOOLEN_BLOCKS).addSelfToTag(SLABS);
  public static final ExtShapeBlockTag CONCRETE_SLABS = create("concrete_slabs").addSelfToTag(SLABS);
  public static final ExtShapeBlockTag TERRACOTTA_SLABS = create("terracotta_slabs").addSelfToTag(SLABS);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_SLABS = create("stained_terracotta_slabs").addSelfToTag(TERRACOTTA_SLABS);
  public static final ExtShapeBlockTag GLAZED_TERRACOTTA_SLABS = create("glazed_terracotta_slabs").addSelfToTag(SLABS);



  /* 栅栏 */

  public static final ExtShapeBlockTag FENCES = create("minecraft", "fences");
  public static final ExtShapeBlockTag LOG_FENCES = create("log_fences").addSelfToTag(LOG_BLOCKS).addSelfToTag(FENCES);
  public static final ExtShapeBlockTag WOOLEN_FENCES = create("woolen_fences").addSelfToTag(WOOLEN_BLOCKS).addSelfToTag(FENCES);
  public static final ExtShapeBlockTag CONCRETE_FENCES = create("concrete_fences").addSelfToTag(FENCES);
  public static final ExtShapeBlockTag TERRACOTTA_FENCES = create("terracotta_fences").addSelfToTag(FENCES);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_FENCES = create("stained_terracotta_fences").addSelfToTag(TERRACOTTA_FENCES);



  /* 栅栏门 */

  public static final ExtShapeBlockTag FENCE_GATES = create("minecraft", "fence_gates");
  public static final ExtShapeBlockTag LOG_FENCE_GATES = create("log_fence_gates").addSelfToTag(LOG_BLOCKS).addSelfToTag(FENCE_GATES);
  public static final ExtShapeBlockTag WOOLEN_FENCE_GATES = create("woolen_fence_gates").addSelfToTag(WOOLEN_BLOCKS).addSelfToTag(FENCE_GATES);
  public static final ExtShapeBlockTag CONCRETE_FENCE_GATES = create("concrete_fence_gates").addSelfToTag(FENCE_GATES);
  public static final ExtShapeBlockTag TERRACOTTA_FENCE_GATES = create("terracotta_fence_gates").addSelfToTag(FENCE_GATES);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_FENCE_GATES = create("stained_terracotta_fence_gates").addSelfToTag(TERRACOTTA_FENCE_GATES);



  /* 墙 */

  public static final ExtShapeBlockTag WALLS = create("minecraft", "walls");
  public static final ExtShapeBlockTag LOG_WALLS = create("log_walls").addSelfToTag(WALLS).addSelfToTag(LOG_BLOCKS);
  @ApiStatus.AvailableSince("1.5.0")
  public static final ExtShapeBlockTag WOODEN_WALLS = create("wooden_walls").addSelfToTag(WALLS).addSelfToTag(WOODEN_BLOCKS);
  @ApiStatus.AvailableSince("1.5.0")
  public static final ExtShapeBlockTag WOOLEN_WALLS = create("woolen_walls").addSelfToTag(WALLS).addSelfToTag(WOOLEN_BLOCKS);
  public static final ExtShapeBlockTag CONCRETE_WALLS = create("concrete_fence_walls").addSelfToTag(WALLS);
  public static final ExtShapeBlockTag TERRACOTTA_WALLS = create("terracotta_fence_walls").addSelfToTag(WALLS);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_WALLS = create("stained_terracotta_fence_walls").addSelfToTag(TERRACOTTA_WALLS);



  /* 按钮 */

  public static final ExtShapeBlockTag BUTTONS = create("minecraft", "buttons");
  public static final ExtShapeBlockTag LOG_BUTTONS = create("log_buttons").addSelfToTag(LOG_BLOCKS).addSelfToTag(BUTTONS);
  public static final ExtShapeBlockTag WOOLEN_BUTTONS = create("woolen_buttons").addSelfToTag(WOOLEN_BLOCKS).addSelfToTag(BUTTONS);
  public static final ExtShapeBlockTag CONCRETE_BUTTONS = create("concrete_buttons").addSelfToTag(BUTTONS);
  public static final ExtShapeBlockTag TERRACOTTA_BUTTONS = create("terracotta_buttons").addSelfToTag(BUTTONS);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_BUTTONS = create("stained_terracotta_buttons").addSelfToTag(TERRACOTTA_BUTTONS);



  /* 压力板 */

  public static final ExtShapeBlockTag PRESSURE_PLATES = create("minecraft", "pressure_plates");
  public static final ExtShapeBlockTag LOG_PRESSURE_PLATES = create("log_pressure_plates").addSelfToTag(PRESSURE_PLATES).addSelfToTag(LOG_BLOCKS);
  public static final ExtShapeBlockTag WOOLEN_PRESSURE_PLATES = create("woolen_pressure_plates").addSelfToTag(PRESSURE_PLATES).addSelfToTag(WOOLEN_BLOCKS);
  public static final ExtShapeBlockTag CONCRETE_PRESSURE_PLATES = create("concrete_pressure_plates").addSelfToTag(PRESSURE_PLATES);
  public static final ExtShapeBlockTag TERRACOTTA_PRESSURE_PLATES = create("terracotta_pressure_plates").addSelfToTag(PRESSURE_PLATES);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_PRESSURE_PLATES = create("stained_terracotta_pressure_plates").addSelfToTag(TERRACOTTA_PRESSURE_PLATES);



  /* 竖直台阶 */

  public static final ExtShapeBlockTag VERTICAL_SLABS = create("vertical_slabs");
  public static final ExtShapeBlockTag LOG_VERTICAL_SLABS = create("log_vertical_slabs").addSelfToTag(LOG_BLOCKS).addSelfToTag(VERTICAL_SLABS);
  public static final ExtShapeBlockTag WOODEN_VERTICAL_SLABS = create("wooden_vertical_slabs").addSelfToTag(WOODEN_BLOCKS).addSelfToTag(VERTICAL_SLABS);
  public static final ExtShapeBlockTag WOOLEN_VERTICAL_SLABS = create("woolen_vertical_slabs").addSelfToTag(WOOLEN_BLOCKS).addSelfToTag(VERTICAL_SLABS);
  public static final ExtShapeBlockTag CONCRETE_VERTICAL_SLABS = create("concrete_vertical_slabs").addSelfToTag(VERTICAL_SLABS);
  public static final ExtShapeBlockTag TERRACOTTA_VERTICAL_SLABS = create("terracotta_vertical_slabs").addSelfToTag(VERTICAL_SLABS);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_VERTICAL_SLABS = create("stained_terracotta_vertical_slabs").addSelfToTag(TERRACOTTA_VERTICAL_SLABS);



  /* 横条 */

  public static final ExtShapeBlockTag QUARTER_PIECES = create("quarter_pieces");
  public static final ExtShapeBlockTag LOG_QUARTER_PIECES = create("log_quarter_pieces").addSelfToTag(QUARTER_PIECES).addSelfToTag(LOG_BLOCKS);
  public static final ExtShapeBlockTag WOODEN_QUARTER_PIECES = create("wooden_quarter_pieces").addSelfToTag(WOODEN_BLOCKS).addSelfToTag(QUARTER_PIECES);
  public static final ExtShapeBlockTag WOOLEN_QUARTER_PIECES = create("woolen_quarter_pieces").addSelfToTag(WOOLEN_BLOCKS).addSelfToTag(QUARTER_PIECES);
  public static final ExtShapeBlockTag CONCRETE_QUARTER_PIECES = create("concrete_quarter_pieces").addSelfToTag(QUARTER_PIECES);
  public static final ExtShapeBlockTag TERRACOTTA_QUARTER_PIECES = create("terracotta_quarter_pieces").addSelfToTag(QUARTER_PIECES);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_QUARTER_PIECES = create("stained_terracotta_quarter_pieces").addSelfToTag(TERRACOTTA_QUARTER_PIECES);




  /* 纵楼梯 */

  public static final ExtShapeBlockTag VERTICAL_STAIRS = create("vertical_stairs");
  public static final ExtShapeBlockTag LOG_VERTICAL_STAIRS = create("log_vertical_stairs").addSelfToTag(VERTICAL_STAIRS).addSelfToTag(LOG_BLOCKS);
  public static final ExtShapeBlockTag WOODEN_VERTICAL_STAIRS = create("wooden_vertical_stairs").addSelfToTag(WOODEN_BLOCKS).addSelfToTag(VERTICAL_STAIRS);
  public static final ExtShapeBlockTag WOOLEN_VERTICAL_STAIRS = create("woolen_vertical_stairs").addSelfToTag(WOOLEN_BLOCKS).addSelfToTag(VERTICAL_STAIRS);
  public static final ExtShapeBlockTag CONCRETE_VERTICAL_STAIRS = create("concrete_vertical_stairs").addSelfToTag(VERTICAL_STAIRS);
  public static final ExtShapeBlockTag TERRACOTTA_VERTICAL_STAIRS = create("terracotta_vertical_stairs").addSelfToTag(VERTICAL_STAIRS);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_VERTICAL_STAIRS = create("stained_terracotta_vertical_stairs").addSelfToTag(TERRACOTTA_VERTICAL_STAIRS);



  /* 纵条 */

  public static final ExtShapeBlockTag VERTICAL_QUARTER_PIECES = create("vertical_quarter_pieces");
  public static final ExtShapeBlockTag LOG_VERTICAL_QUARTER_PIECES = create("log_vertical_quarter_pieces").addSelfToTag(VERTICAL_QUARTER_PIECES).addSelfToTag(LOG_BLOCKS);
  public static final ExtShapeBlockTag WOODEN_VERTICAL_QUARTER_PIECES = create("wooden_vertical_quarter_pieces").addSelfToTag(WOODEN_BLOCKS).addSelfToTag(VERTICAL_QUARTER_PIECES);
  public static final ExtShapeBlockTag WOOLEN_VERTICAL_QUARTER_PIECES = create("woolen_vertical_quarter_pieces").addSelfToTag(WOOLEN_BLOCKS).addSelfToTag(VERTICAL_QUARTER_PIECES);
  public static final ExtShapeBlockTag CONCRETE_VERTICAL_QUARTER_PIECES = create("concrete_vertical_quarter_pieces").addSelfToTag(VERTICAL_QUARTER_PIECES);
  public static final ExtShapeBlockTag TERRACOTTA_VERTICAL_QUARTER_PIECES = create("terracotta_vertical_quarter_pieces").addSelfToTag(VERTICAL_QUARTER_PIECES);
  public static final ExtShapeBlockTag STAINED_TERRACOTTA_VERTICAL_QUARTER_PIECES = create("stained_terracotta_vertical_quarter_pieces").addSelfToTag(TERRACOTTA_VERTICAL_QUARTER_PIECES);


  /*
   * 原版部分
   */

  public static final ExtShapeBlockTag AXE_MINEABLE = create(BlockTags.AXE_MINEABLE);
  public static final ExtShapeBlockTag HOE_MINEABLE = create(BlockTags.HOE_MINEABLE);
  public static final ExtShapeBlockTag PICKAXE_MINEABLE = create(BlockTags.PICKAXE_MINEABLE);
  public static final ExtShapeBlockTag SHOVEL_MINEABLE = create(BlockTags.SHOVEL_MINEABLE);
  public static final ExtShapeBlockTag SHEARS_MINEABLE = create(FabricMineableTags.SHEARS_MINEABLE);

  public static final ExtShapeBlockTag OCCLUDES_VIBRATION_SIGNALS = create(BlockTags.OCCLUDES_VIBRATION_SIGNALS);
  public static final ExtShapeBlockTag DAMPENS_VIBRATIONS = create(BlockTags.DAMPENS_VIBRATIONS);
  public static final ExtShapeBlockTag DRAGON_IMMUNE = create(BlockTags.DRAGON_IMMUNE);
  public static final ExtShapeBlockTag ENDERMAN_HOLDABLE = create(BlockTags.ENDERMAN_HOLDABLE);
  public static final ExtShapeBlockTag INFINIBURN_OVERWORLD = create(BlockTags.INFINIBURN_OVERWORLD);
  public static final ExtShapeBlockTag INFINIBURN_END = create(BlockTags.INFINIBURN_END);
  public static final ExtShapeBlockTag GEODE_INVALID_BLOCKS = create(BlockTags.GEODE_INVALID_BLOCKS);
  public static final ExtShapeBlockTag WITHER_IMMUNE = create(BlockTags.WITHER_IMMUNE);
  public static final ExtShapeBlockTag NEEDS_DIAMOND_TOOL = create(BlockTags.NEEDS_DIAMOND_TOOL);
  public static final ExtShapeBlockTag NEEDS_IRON_TOOL = create(BlockTags.NEEDS_IRON_TOOL);
  public static final ExtShapeBlockTag NEEDS_STONE_TOOL = create(BlockTags.NEEDS_STONE_TOOL);
  public static final ExtShapeBlockTag NON_FLAMMABLE_WOOD = create(BlockTags.NON_FLAMMABLE_WOOD);
  /**
   * 类似于 {@link BlockTags#SNOW}，但是不同的是，该方块标签中，{@link net.minecraft.block.SnowyBlock#isSnow(BlockState)} 对于该标签的方块必须有底部的完整碰撞箱才会让 方块显示为雪。
   */
  @SuppressWarnings("JavadocReference")
  public static final ExtShapeBlockTag SNOW = create("snow");
  public static final ExtShapeItemTag PIGLIN_LOVED = ExtShapeItemTag.create(ItemTags.PIGLIN_LOVED);
  public static final ExtShapeBlockTag GUARDER_BY_PIGLINS = create(BlockTags.GUARDED_BY_PIGLINS);
  public static final ExtShapeBlockTag SMALL_DRIPLEAF_PLACEABLE = create(BlockTags.SMALL_DRIPLEAF_PLACEABLE);
  public static final ExtShapeBlockTag BAMBOO_PLANTABLE_ON = create(BlockTags.BAMBOO_PLANTABLE_ON);
  public static final ExtShapeBlockTag DEAD_BUSH_MAY_PLACE_ON = create(BlockTags.DEAD_BUSH_MAY_PLACE_ON);

  /**
   * 这个标签主要是考虑到，{@link BlockTags#PICKAXE_MINEABLE} 加入了所有的墙，但事实上墙并不一定是可以使用镐来开采的，因此这里设置了一个专门的标签，如果某个方块属于 {@code minecraft:mineable/pickaxe} 但同时属于 {@code extshape:pickaxe_unmineable}，则认为镐子不能开采这个方块。
   */
  @ApiStatus.AvailableSince("0.1.5")
  public static final ExtShapeBlockTag PICKAXE_UNMINEABLE = create("pickaxe_unmineable");

  /**
   * 这个集合中的标签不会加入方块标签中。
   */
  private static final @Unmodifiable ImmutableSet<UsableTag<? extends ItemConvertible>> NO_BLOCK_TAGS = ImmutableSet.of(PIGLIN_LOVED);
  /**
   * 这个集合中的标签不会加入物品标签中。
   */
  private static final @Unmodifiable ImmutableSet<UsableTag<? extends ItemConvertible>> NO_ITEM_TAGS = ImmutableSet.of(AXE_MINEABLE, HOE_MINEABLE, PICKAXE_MINEABLE, SHOVEL_MINEABLE, DRAGON_IMMUNE, INFINIBURN_END, INFINIBURN_OVERWORLD, GEODE_INVALID_BLOCKS, WITHER_IMMUNE, NEEDS_IRON_TOOL, NEEDS_STONE_TOOL, NEEDS_DIAMOND_TOOL, PICKAXE_UNMINEABLE, DEAD_BUSH_MAY_PLACE_ON, SMALL_DRIPLEAF_PLACEABLE, BAMBOO_PLANTABLE_ON, ENDERMAN_HOLDABLE, GUARDER_BY_PIGLINS, SHEARS_MINEABLE, SNOW, OCCLUDES_VIBRATION_SIGNALS);

  private ExtShapeTags() {
  }

  /**
   * 填充所有的方块标签内容，会先删除既有内容。
   */
  public static void refillTags() {
    OCCLUDES_VIBRATION_SIGNALS.addTag(WOOLEN_BLOCKS);
    DAMPENS_VIBRATIONS.addTag(WOOLEN_BLOCKS);
    SHEARS_MINEABLE.addTag(WOOLEN_BLOCKS);
    for (Block block : ExtShapeBlocks.BLOCKS) {
      final Block baseBlock;
      if (!(block instanceof ExtShapeBlockInterface bi)) {
        continue;
      } else {
        baseBlock = bi.getBaseBlock();
      }
      if (baseBlock == null) continue;
      if (baseBlock == Blocks.END_STONE || baseBlock == Blocks.OBSIDIAN || baseBlock == Blocks.CRYING_OBSIDIAN) {
        DRAGON_IMMUNE.add(block);
      }
      if (baseBlock == Blocks.NETHERRACK) INFINIBURN_OVERWORLD.add(block);
      if (baseBlock == Blocks.BEDROCK) INFINIBURN_END.add(block);
      if (baseBlock == Blocks.BEDROCK || baseBlock == Blocks.PACKED_ICE || baseBlock == Blocks.BLUE_ICE || baseBlock == Blocks.ICE) {
        GEODE_INVALID_BLOCKS.add(block);
      }
      if (baseBlock == Blocks.BEDROCK) WITHER_IMMUNE.add(block);
      if (Mineable.NEEDS_DIAMOND_TOOL.contains(baseBlock)) {
        NEEDS_DIAMOND_TOOL.add(block);
      }
      if (Mineable.NEEDS_IRON_TOOL.contains(baseBlock)) {
        NEEDS_IRON_TOOL.add(block);
      }
      if (Mineable.NEEDS_STONE_TOOL.contains(baseBlock)) {
        NEEDS_STONE_TOOL.add(block);
      }
      if (((AbstractBlockAccessor) baseBlock).getMaterial() == Material.NETHER_WOOD) {
        NON_FLAMMABLE_WOOD.add(block);
      }
      if (baseBlock == Blocks.GOLD_BLOCK || baseBlock == Blocks.RAW_GOLD_BLOCK) {
        PIGLIN_LOVED.add(block);
        GUARDER_BY_PIGLINS.add(block);
      } else if (baseBlock == Blocks.GILDED_BLACKSTONE) {
        GUARDER_BY_PIGLINS.add(block);
      }
      if (baseBlock == Blocks.SNOW_BLOCK) {
        SNOW.add(block);
      }

      // 以下部分仅限建筑方块
      final BlockShape shape;
      if ((shape = BlockShape.getShapeOf(block)) != null && shape.isConstruction) {
        if (baseBlock == Blocks.MOSS_BLOCK || baseBlock == Blocks.CLAY) {
          SMALL_DRIPLEAF_PLACEABLE.add(block);
        }
        if (baseBlock == DIRT || baseBlock == CLAY || baseBlock == PUMPKIN || baseBlock == MELON) {
          ENDERMAN_HOLDABLE.add(block);
        }
        if (baseBlock == DIRT) {
          BAMBOO_PLANTABLE_ON.add(block);
          DEAD_BUSH_MAY_PLACE_ON.add(block);
        }
        if (STAINED_TERRACOTTA.contains(baseBlock) || baseBlock == TERRACOTTA) {
          DEAD_BUSH_MAY_PLACE_ON.add(block);
        }
      }
      if (Mineable.VANILLA_AXE_MINEABLE.contains(baseBlock)) AXE_MINEABLE.add(block);
      if (Mineable.VANILLA_HOE_MINEABLE.contains(baseBlock)) HOE_MINEABLE.add(block);
      if (Mineable.VANILLA_PICKAXE_MINEABLE.contains(baseBlock)) {
        PICKAXE_MINEABLE.add(block);
      } else if (block instanceof WallBlock) {
        PICKAXE_UNMINEABLE.add(block);
      }
      if (Mineable.VANILLA_SHOVEL_MINEABLE.contains(baseBlock)) SHOVEL_MINEABLE.add(block);
    }
    AXE_MINEABLE.addTag(WOODEN_BLOCKS);
    AXE_MINEABLE.addTag(LOG_BLOCKS);
  }

  /**
   * 写入所有的方块标签文件。
   */
  public static void writeAllBlockTagFiles(RuntimeResourcePack pack) {
    for (UsableTag<?> tag : UsableTag.TAG_LIST) {
      final Identifier identifier = tag.identifier;
      if (identifier == null) continue;
      if (!NO_BLOCK_TAGS.contains(tag)) {
        pack.addTag(new Identifier(identifier.getNamespace(), "blocks/" + identifier.getPath()), tag.toARRP());
      }
      if (!NO_ITEM_TAGS.contains(tag)) {
        pack.addTag(new Identifier(identifier.getNamespace(), "items/" + identifier.getPath()), tag.toARRP());
      }
    }
  }
}