package pers.solid.extshape.tag;

import com.google.common.collect.ImmutableSet;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.block.ExtShapeBlocks;

/**
 * 此类中定义的所有标签将会写入 ARRP 资源包。<br>
 * Tags defined in this class will be written to ARRP resource pack.
 */
public final class TagGenerator {
  public static final ExtShapeBlockTag AXE_MINEABLE = ExtShapeBlockTag.createBiPart(new Identifier("minecraft", "mineable/axe"));
  public static final ExtShapeBlockTag HOE_MINEABLE = ExtShapeBlockTag.createBiPart(new Identifier("minecraft", "mineable/hoe"));
  public static final ExtShapeBlockTag PICKAXE_MINEABLE = ExtShapeBlockTag.createBiPart(new Identifier("minecraft", "mineable/pickaxe"), ExtShapeBlocks.PETRIFIED_OAK_PLANKS, ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB);
  public static final ExtShapeBlockTag SHOVEL_MINEABLE = ExtShapeBlockTag.createBiPart(new Identifier("minecraft", "mineable/shovel"));

  public static final ExtShapeBlockTag OCCLUDES_VIBRATION_SIGNALS = ExtShapeBlockTag.createBiPart(new Identifier("minecraft", "occludes_vibration_signals"));

  public static final ExtShapeBlockTag DRAGON_IMMUNE = ExtShapeBlockTag.createBiPart(new Identifier("minecraft", "dragon_immune"));
  public static final ExtShapeBlockTag INFINIBURN_OVERWORLD = ExtShapeBlockTag.createBiPart(new Identifier("minecraft", "infiniburn_overworld"));
  public static final ExtShapeBlockTag INFINIBURN_END = ExtShapeBlockTag.createBiPart(new Identifier("minecraft", "infiniburn_end"));
  public static final ExtShapeBlockTag GEODE_INVALID_BLOCKS = ExtShapeBlockTag.createBiPart(new Identifier("minecraft", "geode_invalid_blocks"));
  public static final ExtShapeBlockTag WITHER_IMMUNE = ExtShapeBlockTag.createBiPart(new Identifier("minecraft", "wither_immune"));

  public static final ExtShapeBlockTag NEEDS_DIAMOND_TOOL = ExtShapeBlockTag.createBiPart(new Identifier("minecraft", "needs_diamond_tool"));
  public static final ExtShapeBlockTag NEEDS_IRON_TOOL = ExtShapeBlockTag.createBiPart(new Identifier("minecraft", "needs_iron_tool"));
  public static final ExtShapeBlockTag NEEDS_STONE_TOOL = ExtShapeBlockTag.createBiPart(new Identifier("minecraft", "needs_stone_tool"));
  public static final ExtShapeBlockTag NON_FLAMMABLE_WOOD = ExtShapeBlockTag.createBiPart(new Identifier("minecraft", "non_flammable_wood"));
  public static final ExtShapeItemTag PIGLIN_LOVED = ExtShapeItemTag.createBiPart(new Identifier("minecraft", "piglin_loved"));

  /**
   * 这个集合中的标签不会加入方块标签中。
   */
  private static final @Unmodifiable ImmutableSet<UsableTag<? extends ItemConvertible>> NO_BLOCK_TAGS = ImmutableSet.of(PIGLIN_LOVED);
  /**
   * 这个集合中的标签不会加入物品标签中。
   */
  private static final @Unmodifiable ImmutableSet<UsableTag<? extends ItemConvertible>> NO_ITEM_TAGS = ImmutableSet.of(AXE_MINEABLE, HOE_MINEABLE, PICKAXE_MINEABLE, SHOVEL_MINEABLE, DRAGON_IMMUNE, INFINIBURN_END, INFINIBURN_OVERWORLD, GEODE_INVALID_BLOCKS, WITHER_IMMUNE, NEEDS_IRON_TOOL, NEEDS_STONE_TOOL, NEEDS_DIAMOND_TOOL);
  /**
   * 需要石质工具的基础方块。基础方块为这些方块的将会加入 {@link #NEEDS_STONE_TOOL}。
   */
  @ApiStatus.AvailableSince("1.5.0")
  private static final ImmutableSet<Block> BASE_BLOCKS_THAT_NEEDS_STONE_TOOL = ImmutableSet.of(
      Blocks.IRON_BLOCK, Blocks.RAW_IRON_BLOCK, Blocks.LAPIS_BLOCK, Blocks.COPPER_BLOCK, Blocks.RAW_COPPER_BLOCK, Blocks.CUT_COPPER, Blocks.WEATHERED_COPPER, Blocks.OXIDIZED_COPPER, Blocks.OXIDIZED_CUT_COPPER, Blocks.EXPOSED_COPPER, Blocks.EXPOSED_CUT_COPPER, Blocks.WAXED_COPPER_BLOCK, Blocks.WAXED_CUT_COPPER, Blocks.WAXED_EXPOSED_COPPER, Blocks.WAXED_EXPOSED_CUT_COPPER, Blocks.WAXED_WEATHERED_COPPER, Blocks.WAXED_WEATHERED_CUT_COPPER, Blocks.WAXED_OXIDIZED_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER
  );

  static {
    OCCLUDES_VIBRATION_SIGNALS.addTag(ExtShapeBlockTags.WOOLEN_BLOCKS);
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
      if (baseBlock == Blocks.OBSIDIAN || baseBlock == Blocks.CRYING_OBSIDIAN || baseBlock == Blocks.NETHERITE_BLOCK || baseBlock == Blocks.ANCIENT_DEBRIS) {
        NEEDS_DIAMOND_TOOL.add(block);
      }
      if (baseBlock == Blocks.DIAMOND_BLOCK || baseBlock == Blocks.EMERALD_BLOCK || baseBlock == Blocks.GOLD_BLOCK || baseBlock == Blocks.RAW_GOLD_BLOCK) {
        NEEDS_IRON_TOOL.add(block);
      }
      if (BASE_BLOCKS_THAT_NEEDS_STONE_TOOL.contains(baseBlock)) {
        NEEDS_STONE_TOOL.add(block);
      }
      if (baseBlock == Blocks.CRIMSON_PLANKS || baseBlock == Blocks.WARPED_PLANKS) {
        NON_FLAMMABLE_WOOD.add(block);
      }
      if (baseBlock == Blocks.GOLD_BLOCK || baseBlock == Blocks.RAW_GOLD_BLOCK) {
        PIGLIN_LOVED.add(block);
      }
      if (Mineable.VANILLA_AXE_MINEABLE.contains(baseBlock)) AXE_MINEABLE.add(block);
      if (Mineable.VANILLA_HOE_MINEABLE.contains(baseBlock)) HOE_MINEABLE.add(block);
      if (Mineable.VANILLA_PICKAXE_MINEABLE.contains(baseBlock)) PICKAXE_MINEABLE.add(block);
      if (Mineable.VANILLA_SHOVEL_MINEABLE.contains(baseBlock)) SHOVEL_MINEABLE.add(block);
    }
  }

  /**
   * 写入所有的方块标签文件。
   */
  public static void writeAllBlockTagFiles(RuntimeResourcePack pack) {
    for (UsableTag<?> tag : ExtShapeBlockTags.TAG_LIST) {
      final Identifier identifier = tag.getIdentifier();
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