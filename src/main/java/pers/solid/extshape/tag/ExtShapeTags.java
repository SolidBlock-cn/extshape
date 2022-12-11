package pers.solid.extshape.tag;

import com.google.common.base.Preconditions;
import net.fabricmc.fabric.api.mininglevel.v1.FabricMineableTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallBlock;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockCollections;

import java.util.Collection;
import java.util.LinkedHashSet;

import static net.minecraft.block.Blocks.*;

/**
 * 本模组提供的方块和物品标签，将用于数据生成，亦可在游戏运行中通过 {@link TagPreparation#toVanillaTag()} 转化为原版标签使用。。
 */
public final class ExtShapeTags {
  /**
   * 本模组使用的需要写入运行时资源包的所有标签。
   */
  private static final Collection<TagPreparation<?>> TAGS_TO_WRITE = new LinkedHashSet<>();
  /**
   * 所有羊毛衍生方块（不含羊毛本身）。这些方块会被注册可燃，会被剪刀剪掉，并被加入到 {@link #OCCLUDES_VIBRATION_SIGNALS} 中（仅限 1.17 以上版本）。
   */
  public static final BlockTagPreparation WOOLEN_BLOCKS = ofBlockAndItem("woolen_blocks");
  /**
   * 所有的木质方块，包括下界木，不含原版方块。
   */
  public static final BlockTagPreparation WOODEN_BLOCKS = ofBlockAndItem("wooden_blocks");

  /**
   * 所有的原木、木头方块（包括去皮的）。不含原版方块。
   */
  public static final BlockTagPreparation LOG_BLOCKS = ofBlockAndItem("log_blocks").addSelfToTag(WOODEN_BLOCKS);

  /* 楼梯 */
  public static final BlockTagPreparation STAIRS = ofBlockAndItem(BlockTags.STAIRS, ItemTags.STAIRS);
  public static final BlockTagPreparation WOOLEN_STAIRS = ofBlockAndItem("woolen_stairs").addSelfToTag(WOOLEN_BLOCKS).addSelfToTag(STAIRS);
  public static final BlockTagPreparation CONCRETE_STAIRS = ofBlockAndItem("concrete_stairs").addSelfToTag(STAIRS);
  public static final BlockTagPreparation TERRACOTTA_STAIRS = ofBlockAndItem("terracotta_stairs").addSelfToTag(STAIRS);
  public static final BlockTagPreparation STAINED_TERRACOTTA_STAIRS = ofBlockAndItem("stained_terracotta_stairs").addSelfToTag(TERRACOTTA_STAIRS);
  public static final BlockTagPreparation WOODEN_STAIRS = ofBlockAndItem(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
  public static final BlockTagPreparation LOG_STAIRS = ofBlockAndItem("log_stairs").addSelfToTag(WOODEN_STAIRS).addSelfToTag(LOG_BLOCKS);


  /* 台阶 */
  public static final BlockTagPreparation SLABS = ofBlockAndItem(BlockTags.SLABS, ItemTags.SLABS);
  public static final BlockTagPreparation WOOLEN_SLABS = ofBlockAndItem("woolen_slabs").addSelfToTag(WOOLEN_BLOCKS).addSelfToTag(SLABS);
  public static final BlockTagPreparation CONCRETE_SLABS = ofBlockAndItem("concrete_slabs").addSelfToTag(SLABS);
  public static final BlockTagPreparation TERRACOTTA_SLABS = ofBlockAndItem("terracotta_slabs").addSelfToTag(SLABS);
  public static final BlockTagPreparation STAINED_TERRACOTTA_SLABS = ofBlockAndItem("stained_terracotta_slabs").addSelfToTag(TERRACOTTA_SLABS);
  public static final BlockTagPreparation GLAZED_TERRACOTTA_SLABS = ofBlockAndItem("glazed_terracotta_slabs").addSelfToTag(SLABS);
  public static final BlockTagPreparation WOODEN_SLABS = ofBlockAndItem(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
  public static final BlockTagPreparation LOG_SLABS = ofBlockAndItem("log_slabs").addSelfToTag(WOODEN_SLABS).addSelfToTag(LOG_BLOCKS);


  /* 栅栏 */
  public static final BlockTagPreparation FENCES = ofBlockAndItem(BlockTags.FENCES, ItemTags.FENCES);
  public static final BlockTagPreparation WOOLEN_FENCES = ofBlockAndItem("woolen_fences").addSelfToTag(WOOLEN_BLOCKS).addSelfToTag(FENCES);
  public static final BlockTagPreparation CONCRETE_FENCES = ofBlockAndItem("concrete_fences").addSelfToTag(FENCES);
  public static final BlockTagPreparation TERRACOTTA_FENCES = ofBlockAndItem("terracotta_fences").addSelfToTag(FENCES);
  public static final BlockTagPreparation STAINED_TERRACOTTA_FENCES = ofBlockAndItem("stained_terracotta_fences").addSelfToTag(TERRACOTTA_FENCES);
  public static final BlockTagPreparation WOODEN_FENCES = ofBlockAndItem(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
  public static final BlockTagPreparation LOG_FENCES = ofBlockAndItem("log_fences").addSelfToTag(LOG_BLOCKS).addSelfToTag(WOODEN_FENCES);


  /* 栅栏门 */
  public static final BlockTagPreparation FENCE_GATES = ofBlockAndItem(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
  public static final BlockTagPreparation LOG_FENCE_GATES = ofBlockAndItem("log_fence_gates").addSelfToTag(LOG_BLOCKS).addSelfToTag(FENCE_GATES);
  public static final BlockTagPreparation WOOLEN_FENCE_GATES = ofBlockAndItem("woolen_fence_gates").addSelfToTag(WOOLEN_BLOCKS).addSelfToTag(FENCE_GATES);
  public static final BlockTagPreparation CONCRETE_FENCE_GATES = ofBlockAndItem("concrete_fence_gates").addSelfToTag(FENCE_GATES);
  public static final BlockTagPreparation TERRACOTTA_FENCE_GATES = ofBlockAndItem("terracotta_fence_gates").addSelfToTag(FENCE_GATES);
  public static final BlockTagPreparation STAINED_TERRACOTTA_FENCE_GATES = ofBlockAndItem("stained_terracotta_fence_gates").addSelfToTag(TERRACOTTA_FENCE_GATES);


  /* 墙 */
  public static final BlockTagPreparation WALLS = ofBlockAndItem(BlockTags.WALLS, ItemTags.WALLS);
  @ApiStatus.AvailableSince("1.5.0")
  public static final BlockTagPreparation WOODEN_WALLS = ofBlockAndItem("wooden_walls").addSelfToTag(WALLS).addSelfToTag(WOODEN_BLOCKS);
  public static final BlockTagPreparation LOG_WALLS = ofBlockAndItem("log_walls").addSelfToTag(WOODEN_WALLS).addSelfToTag(LOG_BLOCKS);
  @ApiStatus.AvailableSince("1.5.0")
  public static final BlockTagPreparation WOOLEN_WALLS = ofBlockAndItem("woolen_walls").addSelfToTag(WALLS).addSelfToTag(WOOLEN_BLOCKS);
  public static final BlockTagPreparation CONCRETE_WALLS = ofBlockAndItem("concrete_fence_walls").addSelfToTag(WALLS);
  public static final BlockTagPreparation TERRACOTTA_WALLS = ofBlockAndItem("terracotta_fence_walls").addSelfToTag(WALLS);
  public static final BlockTagPreparation STAINED_TERRACOTTA_WALLS = ofBlockAndItem("stained_terracotta_fence_walls").addSelfToTag(TERRACOTTA_WALLS);


  /* 按钮 */
  public static final BlockTagPreparation BUTTONS = ofBlockAndItem(BlockTags.BUTTONS, ItemTags.BUTTONS);
  public static final BlockTagPreparation WOOLEN_BUTTONS = ofBlockAndItem("woolen_buttons").addSelfToTag(WOOLEN_BLOCKS).addSelfToTag(BUTTONS);
  public static final BlockTagPreparation CONCRETE_BUTTONS = ofBlockAndItem("concrete_buttons").addSelfToTag(BUTTONS);
  public static final BlockTagPreparation TERRACOTTA_BUTTONS = ofBlockAndItem("terracotta_buttons").addSelfToTag(BUTTONS);
  public static final BlockTagPreparation STAINED_TERRACOTTA_BUTTONS = ofBlockAndItem("stained_terracotta_buttons").addSelfToTag(TERRACOTTA_BUTTONS);
  public static final BlockTagPreparation WOODEN_BUTTONS = ofBlockAndItem(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
  public static final BlockTagPreparation LOG_BUTTONS = ofBlockAndItem("log_buttons").addSelfToTag(LOG_BLOCKS).addSelfToTag(WOODEN_BUTTONS);


  /* 压力板 */
  public static final BlockTagPreparation PRESSURE_PLATES = ofBlockOnly(BlockTags.PRESSURE_PLATES);  // ItemTags.PRESSURE_PLATES不存在
  public static final BlockTagPreparation WOOLEN_PRESSURE_PLATES = ofBlockAndItem("woolen_pressure_plates").addSelfToTag(PRESSURE_PLATES).addSelfToTag(WOOLEN_BLOCKS);
  public static final BlockTagPreparation CONCRETE_PRESSURE_PLATES = ofBlockAndItem("concrete_pressure_plates").addSelfToTag(PRESSURE_PLATES);
  public static final BlockTagPreparation TERRACOTTA_PRESSURE_PLATES = ofBlockAndItem("terracotta_pressure_plates").addSelfToTag(PRESSURE_PLATES);
  public static final BlockTagPreparation STAINED_TERRACOTTA_PRESSURE_PLATES = ofBlockAndItem("stained_terracotta_pressure_plates").addSelfToTag(TERRACOTTA_PRESSURE_PLATES);
  public static final BlockTagPreparation WOODEN_PRESSURE_PLATES = ofBlockAndItem(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
  public static final BlockTagPreparation LOG_PRESSURE_PLATES = ofBlockAndItem("log_pressure_plates").addSelfToTag(WOODEN_PRESSURE_PLATES).addSelfToTag(LOG_BLOCKS);


  /* 竖直台阶 */
  public static final BlockTagPreparation VERTICAL_SLABS = ofBlockAndItem("vertical_slabs");
  public static final BlockTagPreparation WOODEN_VERTICAL_SLABS = ofBlockAndItem("wooden_vertical_slabs").addSelfToTag(WOODEN_BLOCKS).addSelfToTag(VERTICAL_SLABS);
  public static final BlockTagPreparation LOG_VERTICAL_SLABS = ofBlockAndItem("log_vertical_slabs").addSelfToTag(LOG_BLOCKS).addSelfToTag(WOODEN_VERTICAL_SLABS);
  public static final BlockTagPreparation WOOLEN_VERTICAL_SLABS = ofBlockAndItem("woolen_vertical_slabs").addSelfToTag(WOOLEN_BLOCKS).addSelfToTag(VERTICAL_SLABS);
  public static final BlockTagPreparation CONCRETE_VERTICAL_SLABS = ofBlockAndItem("concrete_vertical_slabs").addSelfToTag(VERTICAL_SLABS);
  public static final BlockTagPreparation TERRACOTTA_VERTICAL_SLABS = ofBlockAndItem("terracotta_vertical_slabs").addSelfToTag(VERTICAL_SLABS);
  public static final BlockTagPreparation STAINED_TERRACOTTA_VERTICAL_SLABS = ofBlockAndItem("stained_terracotta_vertical_slabs").addSelfToTag(TERRACOTTA_VERTICAL_SLABS);


  /* 横条 */
  public static final BlockTagPreparation QUARTER_PIECES = ofBlockAndItem("quarter_pieces");
  public static final BlockTagPreparation WOODEN_QUARTER_PIECES = ofBlockAndItem("wooden_quarter_pieces").addSelfToTag(WOODEN_BLOCKS).addSelfToTag(QUARTER_PIECES);
  public static final BlockTagPreparation LOG_QUARTER_PIECES = ofBlockAndItem("log_quarter_pieces").addSelfToTag(WOODEN_QUARTER_PIECES).addSelfToTag(LOG_BLOCKS);
  public static final BlockTagPreparation WOOLEN_QUARTER_PIECES = ofBlockAndItem("woolen_quarter_pieces").addSelfToTag(WOOLEN_BLOCKS).addSelfToTag(QUARTER_PIECES);
  public static final BlockTagPreparation CONCRETE_QUARTER_PIECES = ofBlockAndItem("concrete_quarter_pieces").addSelfToTag(QUARTER_PIECES);
  public static final BlockTagPreparation TERRACOTTA_QUARTER_PIECES = ofBlockAndItem("terracotta_quarter_pieces").addSelfToTag(QUARTER_PIECES);
  public static final BlockTagPreparation STAINED_TERRACOTTA_QUARTER_PIECES = ofBlockAndItem("stained_terracotta_quarter_pieces").addSelfToTag(TERRACOTTA_QUARTER_PIECES);


  /* 纵楼梯 */
  public static final BlockTagPreparation VERTICAL_STAIRS = ofBlockAndItem("vertical_stairs");
  public static final BlockTagPreparation WOODEN_VERTICAL_STAIRS = ofBlockAndItem("wooden_vertical_stairs").addSelfToTag(WOODEN_BLOCKS).addSelfToTag(VERTICAL_STAIRS);
  public static final BlockTagPreparation LOG_VERTICAL_STAIRS = ofBlockAndItem("log_vertical_stairs").addSelfToTag(WOODEN_VERTICAL_STAIRS).addSelfToTag(LOG_BLOCKS);
  public static final BlockTagPreparation WOOLEN_VERTICAL_STAIRS = ofBlockAndItem("woolen_vertical_stairs").addSelfToTag(WOOLEN_BLOCKS).addSelfToTag(VERTICAL_STAIRS);
  public static final BlockTagPreparation CONCRETE_VERTICAL_STAIRS = ofBlockAndItem("concrete_vertical_stairs").addSelfToTag(VERTICAL_STAIRS);
  public static final BlockTagPreparation TERRACOTTA_VERTICAL_STAIRS = ofBlockAndItem("terracotta_vertical_stairs").addSelfToTag(VERTICAL_STAIRS);
  public static final BlockTagPreparation STAINED_TERRACOTTA_VERTICAL_STAIRS = ofBlockAndItem("stained_terracotta_vertical_stairs").addSelfToTag(TERRACOTTA_VERTICAL_STAIRS);


  /* 纵条 */
  public static final BlockTagPreparation VERTICAL_QUARTER_PIECES = ofBlockAndItem("vertical_quarter_pieces");
  public static final BlockTagPreparation WOODEN_VERTICAL_QUARTER_PIECES = ofBlockAndItem("wooden_vertical_quarter_pieces").addSelfToTag(WOODEN_BLOCKS).addSelfToTag(VERTICAL_QUARTER_PIECES);
  public static final BlockTagPreparation LOG_VERTICAL_QUARTER_PIECES = ofBlockAndItem("log_vertical_quarter_pieces").addSelfToTag(WOODEN_VERTICAL_QUARTER_PIECES).addSelfToTag(LOG_BLOCKS);
  public static final BlockTagPreparation WOOLEN_VERTICAL_QUARTER_PIECES = ofBlockAndItem("woolen_vertical_quarter_pieces").addSelfToTag(WOOLEN_BLOCKS).addSelfToTag(VERTICAL_QUARTER_PIECES);
  public static final BlockTagPreparation CONCRETE_VERTICAL_QUARTER_PIECES = ofBlockAndItem("concrete_vertical_quarter_pieces").addSelfToTag(VERTICAL_QUARTER_PIECES);
  public static final BlockTagPreparation TERRACOTTA_VERTICAL_QUARTER_PIECES = ofBlockAndItem("terracotta_vertical_quarter_pieces").addSelfToTag(VERTICAL_QUARTER_PIECES);
  public static final BlockTagPreparation STAINED_TERRACOTTA_VERTICAL_QUARTER_PIECES = ofBlockAndItem("stained_terracotta_vertical_quarter_pieces").addSelfToTag(TERRACOTTA_VERTICAL_QUARTER_PIECES);


  /*
   * 其他部分
   */
  public static final BlockTagPreparation AXE_MINEABLE = ofBlockOnly(BlockTags.AXE_MINEABLE);
  public static final BlockTagPreparation HOE_MINEABLE = ofBlockOnly(BlockTags.HOE_MINEABLE);
  public static final BlockTagPreparation PICKAXE_MINEABLE = ofBlockOnly(BlockTags.PICKAXE_MINEABLE);
  public static final BlockTagPreparation SHOVEL_MINEABLE = ofBlockOnly(BlockTags.SHOVEL_MINEABLE);
  public static final BlockTagPreparation SHEARS_MINEABLE = ofBlockOnly(FabricMineableTags.SHEARS_MINEABLE);
  public static final BlockTagPreparation OCCLUDES_VIBRATION_SIGNALS = ofBlockOnly(BlockTags.OCCLUDES_VIBRATION_SIGNALS);
  public static final BlockTagPreparation DAMPENS_VIBRATIONS = ofBlockAndItem(BlockTags.DAMPENS_VIBRATIONS, ItemTags.DAMPENS_VIBRATIONS);
  public static final BlockTagPreparation DRAGON_IMMUNE = ofBlockOnly(BlockTags.DRAGON_IMMUNE);
  public static final BlockTagPreparation ENDERMAN_HOLDABLE = ofBlockOnly(BlockTags.ENDERMAN_HOLDABLE);
  public static final BlockTagPreparation INFINIBURN_OVERWORLD = ofBlockOnly(BlockTags.INFINIBURN_OVERWORLD);
  public static final BlockTagPreparation INFINIBURN_END = ofBlockOnly(BlockTags.INFINIBURN_END);
  public static final BlockTagPreparation GEODE_INVALID_BLOCKS = ofBlockOnly(BlockTags.GEODE_INVALID_BLOCKS);
  public static final BlockTagPreparation WITHER_IMMUNE = ofBlockOnly(BlockTags.WITHER_IMMUNE);
  public static final BlockTagPreparation NEEDS_DIAMOND_TOOL = ofBlockOnly(BlockTags.NEEDS_DIAMOND_TOOL);
  public static final BlockTagPreparation NEEDS_IRON_TOOL = ofBlockOnly(BlockTags.NEEDS_IRON_TOOL);
  public static final BlockTagPreparation NEEDS_STONE_TOOL = ofBlockOnly(BlockTags.NEEDS_STONE_TOOL);
  /**
   * 类似于 {@link BlockTags#SNOW}，但是不同的是，该方块标签中，{@link net.minecraft.block.SnowyBlock#isSnow(BlockState)} 对于该标签的方块必须有底部的完整碰撞箱才会让 方块显示为雪。
   */
  @SuppressWarnings("JavadocReference")
  public static final BlockTagPreparation SNOW = ofBlockOnly("snow");
  public static final ItemTagPreparation PIGLIN_LOVED = ofItemOnly(ItemTags.PIGLIN_LOVED);
  public static final BlockTagPreparation GUARDER_BY_PIGLINS = ofBlockOnly(BlockTags.GUARDED_BY_PIGLINS);
  public static final BlockTagPreparation SMALL_DRIPLEAF_PLACEABLE = ofBlockOnly(BlockTags.SMALL_DRIPLEAF_PLACEABLE);
  public static final BlockTagPreparation BAMBOO_PLANTABLE_ON = ofBlockOnly(BlockTags.BAMBOO_PLANTABLE_ON);
  public static final BlockTagPreparation DEAD_BUSH_MAY_PLACE_ON = ofBlockOnly(BlockTags.DEAD_BUSH_MAY_PLACE_ON);
  /**
   * 这个标签主要是考虑到，{@link BlockTags#PICKAXE_MINEABLE} 加入了所有的墙，但事实上墙并不一定是可以使用镐来开采的，因此这里设置了一个专门的标签，如果某个方块属于 {@code minecraft:mineable/pickaxe} 但同时属于 {@code extshape:pickaxe_unmineable}，则认为镐子不能开采这个方块。
   */
  @ApiStatus.AvailableSince("0.1.5")
  public static final BlockTagPreparation PICKAXE_UNMINEABLE = ofBlockOnly("pickaxe_unmineable");

  private ExtShapeTags() {
  }

  /**
   * 填充所有的方块标签内容，会先删除既有内容。
   */
  public static void refillTags() {
    OCCLUDES_VIBRATION_SIGNALS.addTag(WOOLEN_BLOCKS);
    DAMPENS_VIBRATIONS.addTag(WOOLEN_BLOCKS);
    SHEARS_MINEABLE.addTag(WOOLEN_BLOCKS);
    for (Block block : ExtShapeBlocks.getBlocks()) {
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
      if (BlockCollections.VanillaMineable.NEEDS_DIAMOND_TOOL.contains(baseBlock)) {
        NEEDS_DIAMOND_TOOL.add(block);
      }
      if (BlockCollections.VanillaMineable.NEEDS_IRON_TOOL.contains(baseBlock)) {
        NEEDS_IRON_TOOL.add(block);
      }
      if (BlockCollections.VanillaMineable.NEEDS_STONE_TOOL.contains(baseBlock)) {
        NEEDS_STONE_TOOL.add(block);
      }
      if (baseBlock == Blocks.GOLD_BLOCK || baseBlock == Blocks.RAW_GOLD_BLOCK) {
        PIGLIN_LOVED.add(block.asItem());
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
        if (BlockCollections.STAINED_TERRACOTTA.contains(baseBlock) || baseBlock == TERRACOTTA) {
          DEAD_BUSH_MAY_PLACE_ON.add(block);
        }
      }
      if (BlockCollections.VanillaMineable.AXE.contains(baseBlock)) AXE_MINEABLE.add(block);
      if (BlockCollections.VanillaMineable.HOE.contains(baseBlock)) HOE_MINEABLE.add(block);
      if (BlockCollections.VanillaMineable.PICKAXE.contains(baseBlock)) {
        PICKAXE_MINEABLE.add(block);
      } else if (block instanceof WallBlock) {
        PICKAXE_UNMINEABLE.add(block);
      }
      if (BlockCollections.VanillaMineable.SHOVEL.contains(baseBlock)) SHOVEL_MINEABLE.add(block);
    }
    AXE_MINEABLE.addTag(WOODEN_BLOCKS);
  }

  private static BlockTagPreparation ofBlockOnly(@NotNull String path) {
    final BlockTagPreparation empty = TagPreparationFactory.BLOCK.ofEmpty(path);
    TAGS_TO_WRITE.add(empty);
    return empty;
  }

  private static BlockTagPreparation ofBlockAndItem(@NotNull String path) {
    final BlockTagPreparation blockTag = ofBlockOnly(path);
    TAGS_TO_WRITE.add(new BlockItemTagPreparation(blockTag));
    return blockTag;
  }

  private static BlockTagPreparation ofBlockOnly(@NotNull TagKey<Block> tag) {
    final BlockTagPreparation empty = TagPreparationFactory.BLOCK.ofEmpty(tag);
    TAGS_TO_WRITE.add(empty);
    return empty;
  }

  private static BlockTagPreparation ofBlockAndItem(@NotNull TagKey<Block> blockTag, @NotNull TagKey<Item> itemTag) {
    Preconditions.checkArgument(blockTag.id().equals(itemTag.id()));
    final BlockTagPreparation instance = ofBlockOnly(blockTag);
    TAGS_TO_WRITE.add(new BlockItemTagPreparation(instance));
    return instance;
  }

  private static ItemTagPreparation ofItemOnly(@NotNull TagKey<Item> tag) {
    final ItemTagPreparation empty = TagPreparationFactory.ITEM.ofEmpty(tag);
    TAGS_TO_WRITE.add(empty);
    return empty;
  }

  /**
   * 写入所有的方块标签文件。
   */
  public static void writeAllTags() {
    for (TagPreparation<?> tag : TAGS_TO_WRITE) {
      tag.writeIntoPack();
    }
  }
}