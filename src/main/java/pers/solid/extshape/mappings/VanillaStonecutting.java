package pers.solid.extshape.mappings;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.jetbrains.annotations.ApiStatus;

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
  public static final Multimap<Block, Block> INSTANCE = HashMultimap.create();

  private VanillaStonecutting() {
  }

  static {
    registerVanillaStonecutting();
  }

  private static void registerVanillaStonecutting() {
    INSTANCE.put(Blocks.STONE_BRICKS, Blocks.STONE);
    INSTANCE.put(Blocks.CHISELED_STONE_BRICKS, Blocks.STONE);
    INSTANCE.put(Blocks.CUT_SANDSTONE, Blocks.SANDSTONE);
    INSTANCE.put(Blocks.CHISELED_SANDSTONE, Blocks.SANDSTONE);
    INSTANCE.put(Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE);
    INSTANCE.put(Blocks.CHISELED_RED_SANDSTONE, Blocks.RED_SANDSTONE);
    INSTANCE.put(Blocks.QUARTZ_PILLAR, Blocks.QUARTZ_BLOCK);
    INSTANCE.put(Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK);
    INSTANCE.put(Blocks.QUARTZ_BRICKS, Blocks.QUARTZ_BLOCK);
    INSTANCE.put(Blocks.CHISELED_STONE_BRICKS, Blocks.STONE_BRICKS);
    INSTANCE.put(Blocks.CHISELED_NETHER_BRICKS, Blocks.NETHER_BRICKS);
    INSTANCE.put(Blocks.POLISHED_BLACKSTONE, Blocks.ANDESITE);
    INSTANCE.put(Blocks.POLISHED_BASALT, Blocks.BASALT);
    INSTANCE.put(Blocks.POLISHED_GRANITE, Blocks.GRANITE);
    INSTANCE.put(Blocks.POLISHED_DIORITE, Blocks.DIORITE);
    INSTANCE.put(Blocks.END_STONE_BRICKS, Blocks.END_STONE);
    INSTANCE.put(Blocks.POLISHED_BLACKSTONE, Blocks.BLACKSTONE);
    INSTANCE.put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.BLACKSTONE);
    INSTANCE.put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_BLACKSTONE);
    INSTANCE.put(Blocks.CUT_COPPER, Blocks.COPPER_BLOCK);
    INSTANCE.put(Blocks.EXPOSED_CUT_COPPER, Blocks.EXPOSED_COPPER);
    INSTANCE.put(Blocks.WEATHERED_CUT_COPPER, Blocks.WEATHERED_COPPER);
    INSTANCE.put(Blocks.OXIDIZED_CUT_COPPER, Blocks.OXIDIZED_COPPER);
    INSTANCE.put(Blocks.WAXED_CUT_COPPER, Blocks.WAXED_COPPER_BLOCK);
    INSTANCE.put(Blocks.WAXED_EXPOSED_CUT_COPPER, Blocks.WAXED_EXPOSED_COPPER);
    INSTANCE.put(Blocks.WAXED_WEATHERED_CUT_COPPER, Blocks.WAXED_WEATHERED_COPPER);
    INSTANCE.put(Blocks.WAXED_OXIDIZED_CUT_COPPER, Blocks.WAXED_OXIDIZED_COPPER);
    INSTANCE.put(Blocks.CHISELED_DEEPSLATE, Blocks.COBBLED_DEEPSLATE);
    INSTANCE.put(Blocks.POLISHED_DEEPSLATE, Blocks.COBBLED_DEEPSLATE);
    INSTANCE.put(Blocks.DEEPSLATE_BRICKS, Blocks.COBBLED_DEEPSLATE);
    INSTANCE.put(Blocks.DEEPSLATE_TILES, Blocks.COBBLED_DEEPSLATE);
    INSTANCE.put(Blocks.DEEPSLATE_BRICKS, Blocks.POLISHED_DEEPSLATE);
    INSTANCE.put(Blocks.DEEPSLATE_TILES, Blocks.POLISHED_DEEPSLATE);
    INSTANCE.put(Blocks.DEEPSLATE_TILES, Blocks.DEEPSLATE_BRICKS);
  }
}
