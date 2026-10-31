package pers.solid.extshape.blockus;

import com.brand.blockus.Blockus;
import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.registry.content.bundles.BSSWBundle;
import com.brand.blockus.registry.content.bundles.DyedBSSWBundle;
import com.brand.blockus.registry.content.bundles.WoodBundle;
import com.brand.blockus.utils.helper.BlockOrder;
import com.brand.blockus.utils.helper.WoodMaps;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Util;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColorCollection;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.UnmodifiableView;
import pers.solid.extshape.ExtShapeBlockItem;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.CopperManager;
import pers.solid.extshape.builder.*;
import pers.solid.extshape.number.ExtShapeNumberProviders;
import pers.solid.extshape.util.ActivationSettings;
import pers.solid.extshape.util.ExtShapeBlockTypes;
import pers.solid.extshape.util.FenceSettings;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ExtShapeBlockusBlocks {

  public static final @UnmodifiableView ObjectSet<Block> BLOCKUS_BLOCKS;
  public static final @UnmodifiableView ObjectSet<Block> BLOCKUS_BASE_BLOCKS;

  private static final BlocksBuilderFactory FACTORY = Util.make(new BlocksBuilderFactory(), blocksBuilderFactory -> blocksBuilderFactory.defaultNamespace = ExtShapeBlockus.NAMESPACE);

  static {
    final ObjectSet<Block> blocks = new ObjectLinkedOpenHashSet<>();
    final ObjectSet<Block> baseBlocks = new ObjectLinkedOpenHashSet<>();
    BLOCKUS_BLOCKS = ObjectSets.unmodifiable(blocks);
    BLOCKUS_BASE_BLOCKS = ObjectSets.unmodifiable(baseBlocks);

    FACTORY.instanceCollection = blocks;
    FACTORY.baseBlockCollection = baseBlocks;

    BlockusBlockBiMaps.importFromBlockus();

    createAllShapes(BlockusBlocks.COBBLESTONE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .markStoneCuttable()
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.MOSSY_COBBLESTONE_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.STONE_TILES)
        .setFenceSettings(FenceSettings.STONE)
        .markStoneCuttable()
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_STONE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .markStoneCuttable()
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.STONE_CIRCULAR_PAVING)
        .markStoneCuttable());

    createAllShapes(BlockusBlocks.ANDESITE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .markStoneCuttable()
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_ANDESITE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .markStoneCuttable()
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.ANDESITE_CIRCULAR_PAVING)
        .markStoneCuttable());

    createAllShapes(BlockusBlocks.DIORITE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .markStoneCuttable()
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_DIORITE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .markStoneCuttable()
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.DIORITE_CIRCULAR_PAVING)
        .markStoneCuttable());

    createAllShapes(BlockusBlocks.GRANITE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .markStoneCuttable()
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_GRANITE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .markStoneCuttable()
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.GRANITE_CIRCULAR_PAVING)
        .markStoneCuttable());

    createAllShapes(BlockusBlocks.POLISHED_DRIPSTONE)
        .setFenceSettings(FenceSettings.DRIPSTONE)
        .markStoneCuttable()
        .setActivationSettings(ActivationSettings.DRIPSTONE)
        .build();
    createAllShapes(BlockusBlocks.DRIPSTONE_BRICKS)
        .markStoneCuttable()
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .setActivationSettings(ActivationSettings.DRIPSTONE)
        .build();
    createAllShapes(BlockusBlocks.MOSSY_DRIPSTONE_BRICKS)
        .markStoneCuttable()
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .setActivationSettings(ActivationSettings.DRIPSTONE)
        .build();

    createAllShapes(BlockusBlocks.SULFUR_TILES)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .setFenceSettings(FenceSettings.SULFUR)
        .setActivationSettings(ActivationSettings.SULFUR)
        .markStoneCuttable()
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_SULFUR_BRICKS)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .setFenceSettings(FenceSettings.SULFUR)
        .setActivationSettings(ActivationSettings.SULFUR)
        .markStoneCuttable()
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.SULFUR_CIRCULAR_PAVING)
        .setFenceSettings(FenceSettings.SULFUR)
        .setActivationSettings(ActivationSettings.SULFUR)
        .markStoneCuttable());

    createAllShapes(BlockusBlocks.CINNABAR_TILES)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .setFenceSettings(FenceSettings.CINNABAR)
        .setActivationSettings(ActivationSettings.CINNABAR)
        .markStoneCuttable()
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_CINNABAR_BRICKS)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .setFenceSettings(FenceSettings.CINNABAR)
        .setActivationSettings(ActivationSettings.CINNABAR)
        .markStoneCuttable()
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.CINNABAR_CIRCULAR_PAVING)
        .setFenceSettings(FenceSettings.CINNABAR)
        .setActivationSettings(ActivationSettings.CINNABAR)
        .markStoneCuttable());

    createAllShapes(BlockusBlocks.MOSSY_TUFF_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.TUFF_BRICKS)
        .setActivationSettings(ActivationSettings.TUFF_BRICKS)
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.TUFF_TILES)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.TUFF_BRICKS)
        .setActivationSettings(ActivationSettings.TUFF_BRICKS)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_TUFF_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.TUFF_BRICKS)
        .setActivationSettings(ActivationSettings.TUFF_BRICKS)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.TUFF_CIRCULAR_PAVING)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.TUFF_BRICKS)
        .setActivationSettings(ActivationSettings.TUFF_BRICKS));


    // 可能是忘记加了：深板岩圆石砖
    createAllShapes(BlockusBlocks.COBBLED_DEEPSLATE_BRICKS)
        .setFenceSettings(FenceSettings.DEEPSLATE)
        .setActivationSettings(ActivationSettings.DEEPSLATE)
        .markStoneCuttable()
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.MOSSY_DEEPSLATE_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.DEEPSLATE_BRICKS)
        .setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS)
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_DEEPSLATE_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.DEEPSLATE_BRICKS)
        .setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.DEEPSLATE_CIRCULAR_PAVING)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.DEEPSLATE_BRICKS)
        .setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS));

    // Blockus 模组新增的深板岩苔石和深板岩苔石砖
    createAllShapes(BlockusBlocks.MOSSY_COBBLED_DEEPSLATE)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.DEEPSLATE)
        .setActivationSettings(ActivationSettings.DEEPSLATE)
        .build();
    createAllShapes(BlockusBlocks.MOSSY_COBBLED_DEEPSLATE_BRICKS)
        .setFenceSettings(FenceSettings.DEEPSLATE)
        .setActivationSettings(ActivationSettings.DEEPSLATE)
        .markStoneCuttable()
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();

    createAllShapes(BlockusBlocks.POLISHED_SCULK)
        .markStoneCuttable()
        .setFenceSettings(ExtShapeBlockusBlockTypes.SCULK_BRICKS_FENCE_SETTINGS)
        .setActivationSettings(ExtShapeBlockusBlockTypes.SCULK_BRICKS_ACTIVATION_SETTINGS)
        .build();
    createAllShapes(BlockusBlocks.SCULK_BRICKS)
        .markStoneCuttable()
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .setFenceSettings(ExtShapeBlockusBlockTypes.SCULK_BRICKS_FENCE_SETTINGS)
        .setActivationSettings(ExtShapeBlockusBlockTypes.SCULK_BRICKS_ACTIVATION_SETTINGS)
        .build();

    createAllShapes(BlockusBlocks.POLISHED_AMETHYST)
        .markStoneCuttable()
        .withoutRedstone()
        .withExtension(BlockExtension.AMETHYST)
        .setFenceSettings(FenceSettings.AMETHYST)
        .build();
    createAllShapes(BlockusBlocks.AMETHYST_BRICKS)
        .markStoneCuttable()
        .withoutRedstone()
        .withExtension(BlockExtension.AMETHYST)
        .setFenceSettings(FenceSettings.AMETHYST)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();

    final ActivationSettings blackstoneActivationSettings = ActivationSettings.stone(BlockSetType.POLISHED_BLACKSTONE);
    createAllShapes(BlockusBlocks.POLISHED_BLACKSTONE_TILES)
        .markStoneCuttable()
        .setActivationSettings(blackstoneActivationSettings)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.CRIMSON_WARTY_BLACKSTONE_BRICKS)
        .markStoneCuttable()
        .setActivationSettings(blackstoneActivationSettings)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.WARPED_WARTY_BLACKSTONE_BRICKS)
        .markStoneCuttable()
        .setActivationSettings(blackstoneActivationSettings)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_POLISHED_BLACKSTONE_BRICKS)
        .markStoneCuttable()
        .setActivationSettings(blackstoneActivationSettings)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.POLISHED_BLACKSTONE_CIRCULAR_PAVING)
        .markStoneCuttable()
        .setActivationSettings(blackstoneActivationSettings)
        .setFenceSettings(FenceSettings.STONE));

    createAllShapes(BlockusBlocks.ROUGH_BASALT)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.BASALT)
        .setActivationSettings(ActivationSettings.BASALT)
        .build();
    createAllShapes(BlockusBlocks.POLISHED_BASALT_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.BASALT)
        .setActivationSettings(ActivationSettings.BASALT)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_POLISHED_BASALT_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.BASALT)
        .setActivationSettings(ActivationSettings.BASALT)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.POLISHED_BASALT_CIRCULAR_PAVING)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.BASALT)
        .setActivationSettings(ActivationSettings.BASALT));

    createAllShapes(BlockusBlocks.CRIMSON_WART_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(new FenceSettings(Items.NETHER_WART, ExtShapeBlockTypes.NETHER_BRICKS_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.stone(ExtShapeBlockTypes.NETHER_BRICKS_BLOCK_SET_TYPE))
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.WARPED_WART_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(new FenceSettings(Items.NETHER_WART, ExtShapeBlockTypes.NETHER_BRICKS_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.stone(ExtShapeBlockTypes.NETHER_BRICKS_BLOCK_SET_TYPE))
        .without(BlockShape.BUTTON)
        .build();

    createAllShapes(BlockusBlocks.LIMESTONE)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .build();
    createAllShapes(BlockusBlocks.POLISHED_LIMESTONE)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .build();
    createAllShapes(BlockusBlocks.LIMESTONE_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.SMALL_LIMESTONE_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.LIMESTONE_TILES)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.LIMESTONE_SQUARES)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.LIMESTONE_CIRCULAR_PAVING)
        .markStoneCuttable());

    createAllShapes(BlockusBlocks.MARBLE)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .build();
    createAllShapes(BlockusBlocks.POLISHED_MARBLE)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .build();
    createAllShapes(BlockusBlocks.MARBLE_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.SMALL_MARBLE_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.MARBLE_TILES)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.MARBLE_SQUARES)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.MARBLE_CIRCULAR_PAVING)
        .markStoneCuttable());

    createAllShapes(BlockusBlocks.BLUESTONE)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .build();
    createAllShapes(BlockusBlocks.POLISHED_BLUESTONE)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .build();
    createAllShapes(BlockusBlocks.BLUESTONE_BRICKS)
        .markStoneCuttable()
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.SMALL_BLUESTONE_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.BLUESTONE_TILES)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.BLUESTONE_SQUARES)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.BLUESTONE_CIRCULAR_PAVING)
        .markStoneCuttable());

    createAllShapes(BlockusBlocks.VIRIDITE)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.DEEPSLATE)
        .setActivationSettings(ActivationSettings.DEEPSLATE)
        .build();
    createAllShapes(BlockusBlocks.POLISHED_VIRIDITE)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.DEEPSLATE)
        .setActivationSettings(ActivationSettings.DEEPSLATE)
        .build();
    createAllShapes(BlockusBlocks.VIRIDITE_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.DEEPSLATE)
        .setActivationSettings(ActivationSettings.DEEPSLATE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.SMALL_VIRIDITE_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.DEEPSLATE)
        .setActivationSettings(ActivationSettings.DEEPSLATE)
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.VIRIDITE_TILES)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.DEEPSLATE)
        .setActivationSettings(ActivationSettings.DEEPSLATE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.VIRIDITE_SQUARES)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.DEEPSLATE)
        .setActivationSettings(ActivationSettings.DEEPSLATE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.VIRIDITE_CIRCULAR_PAVING)
        .setActivationSettings(ActivationSettings.DEEPSLATE)
        .markStoneCuttable());

    createAllShapes(BlockusBlocks.WATER_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .build();
    createAllShapes(BlockusBlocks.LAVA_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .build();
    createAllShapes(BlockusBlocks.LAVA_POLISHED_BLACKSTONE_BRICKS)
        .markStoneCuttable()
        .setActivationSettings(ActivationSettings.stone(BlockSetType.POLISHED_BLACKSTONE))
        .setFenceSettings(FenceSettings.STONE)
        .build();

    createAllShapes(BlockusBlocks.SNOW_BRICKS) // 更新后的雪砖同步 26.4 版本不再可以切石
        .setFenceSettings(FenceSettings.SNOW)
        .setActivationSettings(ActivationSettings.SNOW)
        .build();

    createAllShapes(BlockusBlocks.PACKED_ICE_BRICKS)
        .markStoneCuttable()
        .setActivationSettings(ActivationSettings.stone(ExtShapeBlockusBlockTypes.ICE_BLOCK_SET_TYPE))
        .setFenceSettings(new FenceSettings(Items.ICE, ExtShapeBlockusBlockTypes.ICE_WOOD_TYPE))
        .without(BlockShape.BUTTON, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL)
        .build();

    createAllShapes(BlockusBlocks.OBSIDIAN_BRICKS)
        .markStoneCuttable()
        .setActivationSettings(ActivationSettings.HARD)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.SMALL_OBSIDIAN_BRICKS)
        .markStoneCuttable()
        .setActivationSettings(ActivationSettings.HARD)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.OBSIDIAN_CIRCULAR_PAVING)
        .markStoneCuttable()
        .setActivationSettings(ActivationSettings.HARD));
    createAllShapes(BlockusBlocks.GLOWING_OBSIDIAN)
        .markStoneCuttable()
        .setActivationSettings(ActivationSettings.HARD)
        .setFenceSettings(FenceSettings.STONE)
        .build();

    // Blockus 新增：哭泣黑曜石砖
    createAllShapes(BlockusBlocks.CRYING_OBSIDIAN_BRICKS)
        .markStoneCuttable()
        .setActivationSettings(ActivationSettings.HARD)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.SMALL_CRYING_OBSIDIAN_BRICKS)
        .markStoneCuttable()
        .setActivationSettings(ActivationSettings.HARD)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.CRYING_OBSIDIAN_CIRCULAR_PAVING)
        .markStoneCuttable()
        .setActivationSettings(ActivationSettings.HARD));

    createAllShapes(BlockusBlocks.POLISHED_NETHERRACK)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.NETHERRACK)
        .setActivationSettings(ActivationSettings.NETHERRACK)
        .build();
    createAllShapes(BlockusBlocks.NETHERRACK_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.NETHERRACK)
        .setActivationSettings(ActivationSettings.NETHERRACK)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.NETHERRACK_CIRCULAR_PAVING)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.NETHERRACK)
        .setActivationSettings(ActivationSettings.NETHERRACK));

    createAllShapes(BlockusBlocks.QUARTZ_TILES)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.QUARTZ)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.QUARTZ_CIRCULAR_PAVING)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.QUARTZ));

    createAllShapes(BlockusBlocks.MAGMA_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.SMALL_MAGMA_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .build();

    createAllShapes(BlockusBlocks.BLAZE_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(new FenceSettings(Items.BLAZE_ROD, ExtShapeBlockTypes.NETHER_BRICKS_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.stone(ExtShapeBlockTypes.NETHER_BRICKS_BLOCK_SET_TYPE))
        .build();
    createAllShapes(BlockusBlocks.BLAZE_LANTERN)
        .markStoneCuttable()
        .setFenceSettings(new FenceSettings(Items.BLAZE_ROD, ExtShapeBlockusBlockTypes.BLAZE_LANTERN_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.stone(ExtShapeBlockusBlockTypes.BLAZE_LANTERN_BLOCK_SET_TYPE))
        .build();

    createAllShapes(BlockusBlocks.POLISHED_NETHER_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.NETHER_BRICKS)
        .setActivationSettings(ActivationSettings.NETHER_BRICKS)
        .build();
    createAllShapes(BlockusBlocks.POLISHED_RED_NETHER_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.NETHER_BRICKS)
        .setActivationSettings(ActivationSettings.NETHER_BRICKS)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_NETHER_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.NETHER_BRICKS)
        .setActivationSettings(ActivationSettings.NETHER_BRICKS)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_RED_NETHER_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.NETHER_BRICKS)
        .setActivationSettings(ActivationSettings.NETHER_BRICKS)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.NETHER_TILES)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.NETHER_BRICKS)
        .setActivationSettings(ActivationSettings.NETHER_BRICKS)
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.CHARRED_NETHER_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.NETHER_BRICKS)
        .setActivationSettings(ActivationSettings.NETHER_BRICKS)
        .build();
    createAllShapes(BlockusBlocks.POLISHED_CHARRED_NETHER_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.NETHER_BRICKS)
        .setActivationSettings(ActivationSettings.NETHER_BRICKS)
        .markStoneCuttable()
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_CHARRED_NETHER_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.NETHER_BRICKS)
        .setActivationSettings(ActivationSettings.NETHER_BRICKS)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_CHARRED_NETHER_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.NETHER_BRICKS)
        .setActivationSettings(ActivationSettings.NETHER_BRICKS)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.TEAL_NETHER_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.NETHER_BRICKS)
        .setActivationSettings(ActivationSettings.NETHER_BRICKS)
        .build();
    createAllShapes(BlockusBlocks.POLISHED_TEAL_NETHER_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.NETHER_BRICKS)
        .setActivationSettings(ActivationSettings.NETHER_BRICKS)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_TEAL_NETHER_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.NETHER_BRICKS)
        .setActivationSettings(ActivationSettings.NETHER_BRICKS)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();

    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.PRISMARINE_CIRCULAR_PAVING)
        .markStoneCuttable());
    createAllShapes(BlockusBlocks.PRISMARINE_TILES)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.PRISMARINE_SHARD)
        .without(BlockShape.BUTTON)
        .build();

    createAllShapes(BlockusBlocks.LARGE_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.BRICK)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.BRICK)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.SOAKED_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.BRICK)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_SOAKED_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.BRICK)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.CHARRED_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.BRICK)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_CHARRED_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.BRICK)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.SANDY_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.BRICK)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_SANDY_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.BRICK)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();

    createAllShapes(BlockusBlocks.LARGE_RESIN_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.RESIN_BRICKS)
        .setActivationSettings(ActivationSettings.RESIN_BRICKS)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();

    createAllShapes(BlockusBlocks.HERRINGBONE_RESIN_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.RESIN_BRICKS)
        .setActivationSettings(ActivationSettings.RESIN_BRICKS)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();

    createAllShapes(BlockusBlocks.ROUGH_SANDSTONE)
        .markStoneCuttable()
        .without(BlockShape.WALL)
        .setStoneFenceSettings(Items.SAND)
        .build();
    createAllShapes(BlockusBlocks.SANDSTONE_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.SAND)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.SMALL_SANDSTONE_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.SAND)
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.ROUGH_RED_SANDSTONE)
        .markStoneCuttable()
        .without(BlockShape.WALL)
        .setStoneFenceSettings(Items.RED_SAND)
        .build();
    createAllShapes(BlockusBlocks.RED_SANDSTONE_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.RED_SAND)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.SMALL_RED_SANDSTONE_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.RED_SAND)
        .without(BlockShape.BUTTON)
        .build();
    FACTORY.createConstructionOnly(BlockusBlocks.SOUL_SANDSTONE.block())
        .markStoneCuttable()
        .setStoneFenceSettings(Items.SOUL_SAND)
        .build();
    createAllShapes(BlockusBlocks.ROUGH_SOUL_SANDSTONE)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.SOUL_SAND)
        .without(BlockShape.WALL)
        .build();
    createAllShapes(BlockusBlocks.SOUL_SANDSTONE_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.SOUL_SAND)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.SMALL_SOUL_SANDSTONE_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.SOUL_SAND)
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.SMOOTH_SOUL_SANDSTONE)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.SOUL_SAND)
        .build();
    FACTORY.createConstructionOnly(BlockusBlocks.CUT_SOUL_SANDSTONE)
        .markStoneCuttable()
        .build();

    createAllShapes(BlockusBlocks.RAINBOW_BLOCK)
        .markStoneCuttable()
        .setStoneFenceSettings(BlockusBlocks.RAINBOW_PETALS.asItem())
        .setPillar()
        .build();
    createAllShapes(BlockusBlocks.RAINBOW_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(BlockusBlocks.RAINBOW_PETALS.asItem())
        .build();
    createAllShapes(BlockusBlocks.RAINBOW_GLOWSTONE)
        .setFenceSettings(new FenceSettings(BlockusBlocks.RAINBOW_PETALS.asItem(), ExtShapeBlockTypes.GLOWSTONE_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.stone(ExtShapeBlockTypes.GLOWSTONE_BLOCK_SET_TYPE))
        .build();

    createAllShapes(BlockusBlocks.HONEYCOMB_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.HONEYCOMB)
        .without(BlockShape.BUTTON)
        .build();

    createAllShapes(BlockusBlocks.PURPUR_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.PURPUR_BLOCK)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.SMALL_PURPUR_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.PURPUR_BLOCK)
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.POLISHED_PURPUR)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.PURPUR_BLOCK)
        .build();
    createAllShapes(BlockusBlocks.PURPUR_SQUARES)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.PURPUR_BLOCK)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();

    createAllShapes(BlockusBlocks.PHANTOM_PURPUR_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.PHANTOM_MEMBRANE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.SMALL_PHANTOM_PURPUR_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.PHANTOM_MEMBRANE)
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.PHANTOM_PURPUR_BLOCK)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.PHANTOM_MEMBRANE)
        .build();
    createAllShapes(BlockusBlocks.POLISHED_PHANTOM_PURPUR)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.PHANTOM_MEMBRANE)
        .build();
    createAllShapes(BlockusBlocks.PHANTOM_PURPUR_SQUARES)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.PHANTOM_MEMBRANE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();

    createAllShapes(BlockusBlocks.POLISHED_END_STONE)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.END_STONE_BRICKS)
        .build();
    createAllShapes(BlockusBlocks.SMALL_END_STONE_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.END_STONE_BRICKS)
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_END_STONE_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.END_STONE_BRICKS)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.END_TILES)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.END_STONE_BRICKS)
        .without(BlockShape.BUTTON)
        .build();


    final BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> logFlammable = (blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 5, 5);
    FACTORY.createAllShapes(BlockusBlocks.WHITE_OAK_WOOD)
        .setActivationSettings(ActivationSettings.wood(BlockSetType.OAK))
        .setPillar()
        .addPostBuildConsumer(logFlammable)
        .build();
    FACTORY.createAllShapes(BlockusBlocks.STRIPPED_WHITE_OAK_WOOD)
        .setActivationSettings(ActivationSettings.wood(BlockSetType.OAK))
        .setPillar()
        .addPostBuildConsumer(logFlammable)
        .build();

    final BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> plankFlammable = (blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 5, 20);
    createAllShapes(BlockusBlocks.WHITE_OAK)
        .setActivationSettings(ActivationSettings.wood(BlockSetType.OAK))
        .addPostBuildConsumer(plankFlammable)
        .build();
    createAllShapes(BlockusBlocks.RAW_BAMBOO)
        .setActivationSettings(ActivationSettings.BAMBOO)
        .setFenceSettings(new FenceSettings(Items.BAMBOO, WoodType.BAMBOO))
        .addPostBuildConsumer(plankFlammable)
        .build();
    createAllShapes(BlockusBlocks.CHARRED)
        .setActivationSettings(ActivationSettings.wood(BlockSetType.OAK))
        .build();

    final Map<String, BSSWBundle> woodenMosaicBundle = BlockusBlocks.WOODEN_MOSAIC.bundle();
    for (WoodMaps maps : BlockOrder.WOOD) {
      final BSSWBundle bsswBundle = woodenMosaicBundle.get(maps.getId());
      if (bsswBundle == null) {
        continue;
      }
      final boolean isNonFlammable = maps.equals(WoodMaps.WARPED) || maps.equals(WoodMaps.CRIMSON) || maps.equals(WoodMaps.CHARRED);
      final BlocksBuilder builder = FACTORY.createConstructionOnly(bsswBundle.block())
          .without(BlockShape.STAIRS, BlockShape.SLAB)
          .with(BlockShape.WALL)
          .setRecipeGroup(blockShape -> "wooden_mosaic_" + blockShape.getSerializedName());
      if (isNonFlammable) {
        builder.build();
      } else {
        builder.addPostBuildConsumer(plankFlammable)
            .build();
      }
    }
    final Map<String, BSSWBundle> mossyPlanksBundle = BlockusBlocks.MOSSY_PLANKS.bundle();
    for (WoodMaps maps : BlockOrder.WOOD) {
      final BSSWBundle bsswBundle = mossyPlanksBundle.get(maps.getId());
      if (bsswBundle == null) {
        continue;
      }
      final boolean isNonFlammable = maps.equals(WoodMaps.WARPED) || maps.equals(WoodMaps.CRIMSON) || maps.equals(WoodMaps.CHARRED);
      if (isNonFlammable) {
        FACTORY.createConstructionOnly(bsswBundle.block())
            .without(BlockShape.STAIRS, BlockShape.SLAB)
            .with(BlockShape.WALL)
            .setRecipeGroup(blockShape -> "mossy_wooden_" + blockShape.getSerializedName())
            .build();
      } else {
        FACTORY.createConstructionOnly(bsswBundle.block())
            .without(BlockShape.STAIRS, BlockShape.SLAB)
            .with(BlockShape.WALL)
            .addPostBuildConsumer(plankFlammable)
            .setRecipeGroup(blockShape -> "mossy_wooden_" + blockShape.getSerializedName())
            .build();
      }
    }
    final Map<String, Block> herringbonePlanksBundle = BlockusBlocks.HERRINGBONE_PLANKS.bundle();
    for (WoodMaps woodMap : BlockOrder.WOOD) {
      final Block block = herringbonePlanksBundle.get(woodMap.getId());
      if (block == null) {
        continue;
      }
      final boolean isNonFlammable = woodMap.equals(WoodMaps.WARPED) || woodMap.equals(WoodMaps.CRIMSON) || woodMap.equals(WoodMaps.CHARRED);
      if (isNonFlammable) {
        FACTORY.createConstructionOnly(block)
            .with(BlockShape.WALL)
            .setRecipeGroup(blockShape -> "herringbone_wooden_" + blockShape.getSerializedName())
            .build();
      } else {
        FACTORY.createConstructionOnly(block)
            .with(BlockShape.WALL)
            .cookingTime(ExtShapeNumberProviders.COOKING_TIME_WOOD_BLOCKS)
            .addPostBuildConsumer(plankFlammable)
            .setRecipeGroup(blockShape -> "herringbone_wooden_" + blockShape.getSerializedName())
            .build();
      }
    }
    final Map<String, Block> smallLogsBundle = BlockusBlocks.SMALL_LOGS.bundle();
    for (WoodMaps woodMaps : BlockOrder.WOOD) {
      final Block block = smallLogsBundle.get(woodMaps.getId());
      if (block == null) continue;
      if (woodMaps.equals(WoodMaps.CRIMSON) || woodMaps.equals(WoodMaps.WARPED) || woodMaps.equals(WoodMaps.CHARRED)) {
        FACTORY.createConstructionOnly(block)
            .setPillar()
            .setRecipeGroup(blockShape -> "small_logs_" + blockShape.getSerializedName())
            .build();
      } else {
        FACTORY.createConstructionOnly(block)
            .setPillar()
            .addPostBuildConsumer(logFlammable)
            .setRecipeGroup(blockShape -> "small_logs_" + blockShape.getSerializedName())
            .cookingTime(ExtShapeNumberProviders.COOKING_TIME_WOOD_BLOCKS)
            .build();
      }
    }

    createAllShapes(BlockusBlocks.CHOCOLATE_BLOCK)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .setActivationSettings(ActivationSettings.STONE)
        .build();
    createAllShapes(BlockusBlocks.CHOCOLATE_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .setActivationSettings(ActivationSettings.STONE)
        .build();
    createAllShapes(BlockusBlocks.CHOCOLATE_SQUARES)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .setActivationSettings(ActivationSettings.STONE)
        .build();

    // 注意：墙等形状均不属于 beacon_base_block
    createAllShapes(BlockusBlocks.IRON_BRICKS)
        .markStoneCuttable()
        .withoutRedstone()
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.GOLD_BRICKS)
        .markStoneCuttable()
        .withoutRedstone()
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.LAPIS_BRICKS)
        .markStoneCuttable()
        .withoutRedstone()
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.REDSTONE_BRICKS)
        .markStoneCuttable()
        .withExtension(BlockExtension.builder()
            .setEmitsRedstonePower((state, original) -> true)
            .setWeakRedstonePower((state, world, pos, direction, original) -> 15)
            .build())
        .withoutRedstone()
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.EMERALD_BRICKS)
        .markStoneCuttable()
        .withoutRedstone()
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.DIAMOND_BRICKS)
        .markStoneCuttable()
        .withoutRedstone()
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.NETHERITE_BRICKS)
        .markStoneCuttable()
        .withoutRedstone()
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE)
        .addPreBuildConsumer((blockShape, abstractBlockBuilder) -> abstractBlockBuilder.itemSettings.fireResistant())
        .build();

    // 铜砖
    new CopperManager(BlockusBlocks.COPPER_BRICKS.block().blocks()).registerBlocks(FACTORY);
    new CopperManager(BlockusBlocks.COPPER_TUFF_BRICKS.block().blocks(), ExtShapeBlockusBlockTypes.COPPER_TUFF_ACTIVATION_SETTINGS, ExtShapeBlockusBlockTypes.COPPER_TUFF_FENCE_SETTINGS).registerBlocks(FACTORY);

    createAllShapes(BlockusBlocks.CHORUS_BLOCK)
        .setFenceSettings(new FenceSettings(Items.CHORUS_PLANT, ExtShapeBlockusBlockTypes.CHORUS_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.soft(ExtShapeBlockusBlockTypes.CHORUS_BLOCK_SET_TYPE))
        .setPillar()
        .build();

    createForDyed(BlockusBlocks.DYED_STONE_BRICKS, ExtShapeBlockusBlocks::createAllShapes, (dyeColor, blocksBuilder) -> blocksBuilder
        .markStoneCuttable()
        .setRecipeGroup(blockShape -> "stained_stone_brick_" + blockShape.getSerializedName())
        .setActivationSettings(ActivationSettings.STONE)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build()
    );

    final ColorCollection<Block> concreteBricksBlocks = BlockusBlocks.CONCRETE_BRICKS.block().blocks();
    for (DyeColor dyeColor : BlockOrder.COLOR) {
      FACTORY.createConstructionOnly(concreteBricksBlocks.pick(dyeColor))
          .markStoneCuttable()
          .without(BlockShape.BUTTON, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL)
          .setRecipeGroup(blockShape1 -> "concrete_brick_" + blockShape1.getSerializedName())
          .setActivationSettings(ActivationSettings.STONE)
          .setFenceSettings(FenceSettings.STONE)
          .build();
    }

    createAllShapes(BlockusBlocks.SHINGLES)
        .markStoneCuttable()
        .setActivationSettings(ExtShapeBlockusBlockTypes.SHINGLES_ACTIVATION_SETTINGS)
        .setFenceSettings(ExtShapeBlockusBlockTypes.SHINGLES_FENCE_SETTINGS)
        .build();
    createForDyed(BlockusBlocks.DYED_SHINGLES, ExtShapeBlockusBlocks::createAllShapes, (dyeColor, blocksBuilder) -> blocksBuilder
        .markStoneCuttable()
        .setActivationSettings(ActivationSettings.STONE)
        .setFenceSettings(FenceSettings.STONE)
        .setRecipeGroup(blockShape -> "shingles_" + blockShape.getSerializedName())
        .build());

    for (var woolBundle : List.of(BlockusBlocks.PATTERNED_WOOL, BlockusBlocks.GINGHAM_WOOL)) {
      final ColorCollection<Block> woolBlocks = woolBundle.block().blocks();
      final ColorCollection<Block> woolCarpetBlocks = woolBundle.carpet().blocks();
      for (DyeColor dyeColor : BlockOrder.COLOR) {
        FACTORY.createAllShapes(woolBlocks.pick(dyeColor))
            .without(BlockShape.STAIRS, BlockShape.SLAB, BlockShape.BUTTON)
            .addPreBuildConsumer((blockShape, blockBuilder) -> {
              if (blockShape == BlockShape.PRESSURE_PLATE) {
                ((PressurePlateBuilder) blockBuilder).setInstanceSupplier(x -> new WoolPressurePlate(x.baseBlock, x.blockSettings, ((PressurePlateBuilder) x).activationSettings, woolCarpetBlocks.pick(dyeColor)));
              }
            })
            .addPostBuildConsumer((blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 30, 60))
            .setRecipeGroup(blockShape -> "patterned_wool_" + blockShape.getSerializedName())
            .setActivationSettings(ActivationSettings.WOOL)
            .setFenceSettings(FenceSettings.WOOL)
            .build();
      }
    }

    final ColorCollection<Block> glazedTerracottaPillarBlocks = BlockusBlocks.GLAZED_TERRACOTTA_PILLAR.blocks();
    for (DyeColor dyeColor : BlockOrder.COLOR) {
      final Block block = glazedTerracottaPillarBlocks.pick(dyeColor);
      if (block == null) continue;
      FACTORY.createConstructionOnly(block)
          .markStoneCuttable()
          .with(BlockShape.WALL)
          .setRecipeGroup(blockShape -> "glazed_terracotta_pillar_" + blockShape.getSerializedName())
          .setPillar()
          .build();
    }

    createAllShapes(BlockusBlocks.THATCH)
        .addPostBuildConsumer((blockShape, blockBuilder) -> {
          FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 60, 20);
        })
        .setActivationSettings(ActivationSettings.soft(ExtShapeBlockusBlockTypes.GRASS_BLOCK_SET_TYPE))
        .setFenceSettings(new FenceSettings(Items.WHEAT, ExtShapeBlockusBlockTypes.GRASS_BLOCK_WOOD_TYPE))
        .build();
    createAllShapes(BlockusBlocks.PAPER_BLOCK)
        .addPostBuildConsumer((blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 30, 60))
        .setActivationSettings(ActivationSettings.soft(ExtShapeBlockusBlockTypes.GRASS_BLOCK_SET_TYPE))
        .setFenceSettings(new FenceSettings(Items.PAPER, ExtShapeBlockusBlockTypes.GRASS_BLOCK_WOOD_TYPE))
        .cookingTime(ExtShapeNumberProviders.COOKING_TIME_WOOD_BLOCKS)
        .build();
    createAllShapes(BlockusBlocks.BURNT_PAPER_BLOCK)
        .addPostBuildConsumer((blockShape, blockBuilder1) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder1.instance, 5, 60))
        .setActivationSettings(ActivationSettings.soft(ExtShapeBlockusBlockTypes.GRASS_BLOCK_SET_TYPE))
        .setFenceSettings(new FenceSettings(Items.GUNPOWDER, ExtShapeBlockusBlockTypes.GRASS_BLOCK_WOOD_TYPE))
        .build();

    createAllShapes(BlockusBlocks.IRON_PLATING)
        .markStoneCuttable()
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.BUTTON)
        .setActivationSettings(ActivationSettings.IRON)
        .build();
    createAllShapes(BlockusBlocks.GOLD_PLATING)
        .markStoneCuttable()
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.BUTTON)
        .setActivationSettings(ActivationSettings.GOLD)
        .build();

    createAllShapes(BlockusBlocks.CHARCOAL_BLOCK)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.CHARCOAL)
        .cookingTime(ExtShapeNumberProviders.COOKING_TIME_COAL_BLOCK)
        .build();

    // sugar 没有，因为是下落方块，不符合条件。

    createAllShapes(BlockusBlocks.ENDER_BLOCK)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.ENDER_PEARL)
        .build();

    createAllShapes(BlockusBlocks.ROTTEN_FLESH_BLOCK)
        .setActivationSettings(ActivationSettings.soft(ExtShapeBlockTypes.SLIME_BLOCK_SET_TYPE))
        .setFenceSettings(new FenceSettings(Items.ROTTEN_FLESH, ExtShapeBlockTypes.SLIME_WOOD_TYPE))
        .build();
    createAllShapes(BlockusBlocks.MEMBRANE_BLOCK)
        .setFenceSettings(new FenceSettings(Items.PHANTOM_MEMBRANE, ExtShapeBlockTypes.SLIME_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.soft(ExtShapeBlockTypes.SLIME_BLOCK_SET_TYPE))
        .build();
    FACTORY.createAllShapes(BlockusBlocks.NETHER_STAR_BLOCK)
        .markStoneCuttable()
        .withoutRedstone()
        .setStoneFenceSettings(Items.NETHER_STAR)
        .withExtension(BlockExtension.builder()
            .setSteppedOnCallback((world, pos, state, entity) -> {
              if (entity.getType() == EntityTypes.PLAYER) {
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1, 3, true, false, false));
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 900, 3, true, false, true));
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.STRENGTH, 80, 2, true, false, true));
              }
            })
            .build())
        .addPreBuildConsumer((blockShape, builder) -> {
          builder.setItemSettings(new Item.Properties().rarity(Rarity.RARE));
          builder.setItemInstanceSupplier(builder0 -> new ExtShapeBlockItem(builder0.instance, builder0.itemSettings.rarity(Rarity.UNCOMMON)) {
            @Override
            public boolean isFoil(ItemStack stack) {
              return true;
            }

            @SuppressWarnings("deprecation")
            @Override
            public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
              super.appendHoverText(stack, context, displayComponent, textConsumer, type);
              textConsumer.accept(CommonComponents.EMPTY);
              textConsumer.accept(Component.translatable(Util.makeDescriptionId("blockitem", Blockus.id("when_stepped_on"))).withStyle(ChatFormatting.GRAY));
              textConsumer.accept(CommonComponents.space().append(MobEffects.REGENERATION.value().getDisplayName()).append(" IV").withStyle(ChatFormatting.BLUE));
              textConsumer.accept(CommonComponents.space().append(MobEffects.ABSORPTION.value().getDisplayName()).append(" IV").withStyle(ChatFormatting.BLUE).append(" - 00:45"));
              textConsumer.accept(CommonComponents.space().append(MobEffects.STRENGTH.value().getDisplayName()).append(" III").withStyle(ChatFormatting.BLUE).append(" - 00:04"));
            }
          });
        })
        .build();

    createAllShapes(BlockusBlocks.STARS_BLOCK)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.COAL)
        .build();
  }

  private ExtShapeBlockusBlocks() {
  }

  private static BlocksBuilder createAllShapes(Block baseBlock) {
    return FACTORY.createAllShapes(baseBlock);
  }

  private static BlocksBuilder createAllShapes(BSSWBundle bsswBundle) {
    final BlocksBuilder blocksBuilder = createAllShapes(bsswBundle.block());
    if (bsswBundle.stairs() != null) blocksBuilder.without(BlockShape.STAIRS);
    if (bsswBundle.slab() != null) blocksBuilder.without(BlockShape.SLAB);
    if (bsswBundle.wall() != null) blocksBuilder.without(BlockShape.WALL);
    return blocksBuilder;
  }

  private static BlocksBuilder createAllShapes(WoodBundle woodBundle) {
    return createAllShapes(woodBundle.planks()).without(BlockShape.STAIRS, BlockShape.SLAB, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.PRESSURE_PLATE, BlockShape.BUTTON);
  }

  private static void createForDyed(DyedBSSWBundle dyedBSSWBundle, Function<Block, BlocksBuilder> builderCreator, BiConsumer<DyeColor, BlocksBuilder> builderConsumer) {
    final ColorCollection<Block> blocks = dyedBSSWBundle.block().blocks();
    for (DyeColor dyeColor : DyeColor.values()) {
      final Block baseBlock = blocks.pick(dyeColor);
      final BlocksBuilder blocksBuilder = builderCreator.apply(baseBlock);
      if (dyedBSSWBundle.stairs() != null) blocksBuilder.without(BlockShape.STAIRS);
      if (dyedBSSWBundle.slab() != null) blocksBuilder.without(BlockShape.SLAB);
      if (dyedBSSWBundle.wall() != null) blocksBuilder.without(BlockShape.WALL);
      builderConsumer.accept(dyeColor, blocksBuilder);
    }
  }

  private static void buildCircularPavingBlock(BlocksBuilder blocksBuilder) {
    blocksBuilder
        .with(BlockShape.SLAB, BlockShape.PRESSURE_PLATE)
        .setCircularPaving()
        .build();
  }

  public static void init() {
  }
}
