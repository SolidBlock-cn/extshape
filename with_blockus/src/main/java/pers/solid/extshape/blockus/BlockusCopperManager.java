package pers.solid.extshape.blockus;

import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.registry.content.bundles.CopperBSSWBundle;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.world.level.block.WeatheringCopper;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.CopperManager;
import pers.solid.extshape.util.ActivationSettings;
import pers.solid.extshape.util.FenceSettings;

import java.util.List;
import java.util.Map;

/**
 * 处理 Blockus 模组中的铜相关方块。
 *
 * @see CopperManager
 */
public final class BlockusCopperManager {
  public static final List<CopperBSSWBundle> COPPER_BRICK_BUNDLES = List.of(BlockusBlocks.COPPER_BRICKS, BlockusBlocks.EXPOSED_COPPER_BRICKS, BlockusBlocks.WEATHERED_COPPER_BRICKS, BlockusBlocks.OXIDIZED_COPPER_BRICKS);
  public static final List<CopperBSSWBundle> COPPER_TUFF_BUNDLES = List.of(BlockusBlocks.COPPER_TUFF_BRICKS, BlockusBlocks.EXPOSED_COPPER_TUFF_BRICKS, BlockusBlocks.WEATHERED_COPPER_TUFF_BRICKS, BlockusBlocks.OXIDIZED_COPPER_TUFF_BRICKS);

  public static final CopperManager COPPER_BRICKS = of(COPPER_BRICK_BUNDLES, null, null);
  public static final CopperManager COPPER_TUFF = of(COPPER_TUFF_BUNDLES, ExtShapeBlockusBlockTypes.COPPER_TUFF_ACTIVATION_SETTINGS, ExtShapeBlockusBlockTypes.COPPER_TUFF_FENCE_SETTINGS);

  public static CopperManager of(List<CopperBSSWBundle> bundles, @Nullable Map<WeatheringCopper.WeatherState, ActivationSettings> activationSettings, @Nullable FenceSettings fenceSettings) {
    return new CopperManager(
        ImmutableList.copyOf(Lists.transform(bundles, CopperBSSWBundle::block)),
        ImmutableList.copyOf(Lists.transform(bundles, CopperBSSWBundle::blockWaxed)),
        activationSettings,
        fenceSettings
    );
  }
}
