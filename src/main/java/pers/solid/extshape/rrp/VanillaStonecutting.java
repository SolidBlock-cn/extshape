package pers.solid.extshape.rrp;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

/**
 * 这里记录了 Minecraft 原版的可切石方块。在生成切石配方的过程中，需要考虑间接切石的情形，因此需要使用到这里的例子。
 * <p>
 * 例如，石头可以切石成石砖，因此也应该能够切石成以石砖为基础的方块，类似地，石台阶也应该切石成石砖横条，等等。
 *
 * @author SolidBlock
 * @since 1.5.1
 */
@ApiStatus.AvailableSince("1.5.1")
public final class VanillaStonecutting {
  /**
   * 键为切石<i>之后</i>的方块，值为切石<i>之前</i>的方块。
   */
  public static final @Unmodifiable ImmutableMultimap<Block, Block> INSTANCE;

  private VanillaStonecutting() {
  }

  static {
    final ImmutableMultimap.Builder<Block, Block> builder = new ImmutableMultimap.Builder<>();
    registerVanillaStonecutting(builder);
    INSTANCE = builder.build();
  }

  private static void registerVanillaStonecutting(ImmutableMultimap.Builder<Block, Block> builder) {
    builder.put(Blocks.STONE_BRICKS, Blocks.STONE);
    builder.put(Blocks.CHISELED_STONE_BRICKS, Blocks.STONE);
    builder.put(Blocks.CUT_SANDSTONE, Blocks.SANDSTONE);
    builder.put(Blocks.CHISELED_SANDSTONE, Blocks.SANDSTONE);
    builder.put(Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE);
    builder.put(Blocks.CHISELED_RED_SANDSTONE, Blocks.RED_SANDSTONE);
    builder.put(Blocks.QUARTZ_PILLAR, Blocks.QUARTZ_BLOCK);
    builder.put(Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK);
    builder.put(Blocks.QUARTZ_BRICKS, Blocks.QUARTZ_BLOCK);
    builder.put(Blocks.CHISELED_STONE_BRICKS, Blocks.STONE_BRICKS);
    builder.put(Blocks.CHISELED_NETHER_BRICKS, Blocks.NETHER_BRICKS);
    builder.put(Blocks.POLISHED_BLACKSTONE, Blocks.ANDESITE);
    builder.put(Blocks.POLISHED_BASALT, Blocks.BASALT);
    builder.put(Blocks.POLISHED_GRANITE, Blocks.GRANITE);
    builder.put(Blocks.POLISHED_DIORITE, Blocks.DIORITE);
    builder.put(Blocks.END_STONE_BRICKS, Blocks.END_STONE);
    builder.put(Blocks.POLISHED_BLACKSTONE, Blocks.BLACKSTONE);
    builder.put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.BLACKSTONE);
    builder.put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_BLACKSTONE);
    builder.put(Blocks.CUT_COPPER, Blocks.COPPER_BLOCK);
    builder.put(Blocks.EXPOSED_CUT_COPPER, Blocks.EXPOSED_COPPER);
    builder.put(Blocks.WEATHERED_CUT_COPPER, Blocks.WEATHERED_COPPER);
    builder.put(Blocks.OXIDIZED_CUT_COPPER, Blocks.OXIDIZED_COPPER);
    builder.put(Blocks.WAXED_CUT_COPPER, Blocks.WAXED_COPPER_BLOCK);
    builder.put(Blocks.WAXED_EXPOSED_CUT_COPPER, Blocks.WAXED_EXPOSED_COPPER);
    builder.put(Blocks.WAXED_WEATHERED_CUT_COPPER, Blocks.WAXED_WEATHERED_COPPER);
    builder.put(Blocks.WAXED_OXIDIZED_CUT_COPPER, Blocks.WAXED_OXIDIZED_COPPER);
    builder.put(Blocks.CHISELED_DEEPSLATE, Blocks.COBBLED_DEEPSLATE);
    builder.put(Blocks.POLISHED_DEEPSLATE, Blocks.COBBLED_DEEPSLATE);
    builder.put(Blocks.DEEPSLATE_BRICKS, Blocks.COBBLED_DEEPSLATE);
    builder.put(Blocks.DEEPSLATE_TILES, Blocks.COBBLED_DEEPSLATE);
    builder.put(Blocks.DEEPSLATE_BRICKS, Blocks.POLISHED_DEEPSLATE);
    builder.put(Blocks.DEEPSLATE_TILES, Blocks.POLISHED_DEEPSLATE);
    builder.put(Blocks.DEEPSLATE_TILES, Blocks.DEEPSLATE_BRICKS);
  }
}
