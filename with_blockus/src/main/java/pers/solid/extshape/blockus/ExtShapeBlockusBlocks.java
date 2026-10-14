package pers.solid.extshape.blockus;

import com.brand.blockus.Blockus;
import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.registry.content.bundles.BSSWBundle;
import com.brand.blockus.registry.content.bundles.WoodBundle;
import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.util.Util;
import org.jetbrains.annotations.UnmodifiableView;
import pers.solid.extshape.ExtShapeBlockItem;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.builder.*;
import pers.solid.extshape.util.ActivationSettings;
import pers.solid.extshape.util.ExtShapeBlockTypes;
import pers.solid.extshape.util.FenceSettings;

import java.util.List;
import java.util.function.BiConsumer;

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

    createAllShapes(BlockusBlocks.MOSSY_TUFF_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.TUFF)
        .setActivationSettings(ActivationSettings.TUFF)
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.TUFF_TILES)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.TUFF)
        .setActivationSettings(ActivationSettings.TUFF)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_TUFF_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.TUFF)
        .setActivationSettings(ActivationSettings.TUFF)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.TUFF_CIRCULAR_PAVING)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.TUFF)
        .setActivationSettings(ActivationSettings.TUFF));

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

    createAllShapes(BlockusBlocks.POLISHED_SCULK)
        .markStoneCuttable()
        .setFenceSettings(new FenceSettings(Items.SCULK_VEIN, ExtShapeBlockTypes.DEEPSLATE_BRICKS_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS)
        .build();
    createAllShapes(BlockusBlocks.SCULK_BRICKS)
        .markStoneCuttable()
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .setFenceSettings(new FenceSettings(Items.SCULK_VEIN, ExtShapeBlockTypes.DEEPSLATE_BRICKS_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS)
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
        .setFenceSettings(FenceSettings.STONE)
        .build();
    createAllShapes(BlockusBlocks.POLISHED_VIRIDITE)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .build();
    createAllShapes(BlockusBlocks.VIRIDITE_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.SMALL_VIRIDITE_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.VIRIDITE_TILES)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.VIRIDITE_SQUARES)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.VIRIDITE_CIRCULAR_PAVING)
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

    createAllShapes(BlockusBlocks.SNOW_BRICKS)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.STONE)
        .build();

    createAllShapes(BlockusBlocks.ICE_BRICKS)
        .markStoneCuttable()
        .setActivationSettings(ActivationSettings.stone(ExtShapeBlockusBlockTypes.ICE_BLOCK_SET_TYPE))
        .setFenceSettings(new FenceSettings(Items.ICE, ExtShapeBlockusBlockTypes.ICE_WOOD_TYPE))
        .without(BlockShape.BUTTON, BlockShape.WALL) // 墙在原模组中有
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
        .setStoneFenceSettings(Items.BLAZE_ROD)
        .build();
    createAllShapes(BlockusBlocks.BLAZE_LANTERN)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.BLAZE_ROD)
        .build();

    createAllShapes(BlockusBlocks.POLISHED_NETHER_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .build();
    createAllShapes(BlockusBlocks.POLISHED_RED_NETHER_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_NETHER_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_RED_NETHER_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.NETHER_TILES)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .without(BlockShape.BUTTON)
        .build();
    createAllShapes(BlockusBlocks.CHARRED_NETHER_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .build();
    createAllShapes(BlockusBlocks.POLISHED_CHARRED_NETHER_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .markStoneCuttable()
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_CHARRED_NETHER_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_CHARRED_NETHER_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    createAllShapes(BlockusBlocks.TEAL_NETHER_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .build();
    createAllShapes(BlockusBlocks.POLISHED_TEAL_NETHER_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .build();
    createAllShapes(BlockusBlocks.HERRINGBONE_TEAL_NETHER_BRICKS)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.NETHER_BRICK)
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
    FACTORY.createConstructionOnly(BlockusBlocks.SOUL_SANDSTONE.block)
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
        .fuelTime(300)
        .build();
    FACTORY.createAllShapes(BlockusBlocks.STRIPPED_WHITE_OAK_WOOD)
        .setActivationSettings(ActivationSettings.wood(BlockSetType.OAK))
        .setPillar()
        .addPostBuildConsumer(logFlammable)
        .fuelTime(300)
        .build();

    final BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> plankFlammable = (blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 5, 20);
    createAllShapes(BlockusBlocks.WHITE_OAK)
        .setActivationSettings(ActivationSettings.wood(BlockSetType.OAK))
        .addPostBuildConsumer(plankFlammable)
        .fuelTime(300)
        .build();
    createAllShapes(BlockusBlocks.RAW_BAMBOO)
        .setActivationSettings(ActivationSettings.BAMBOO)
        .setFenceSettings(new FenceSettings(Items.BAMBOO, WoodType.BAMBOO))
        .addPostBuildConsumer(plankFlammable)
        .fuelTime(300)
        .build();
    createAllShapes(BlockusBlocks.CHARRED)
        .setActivationSettings(ActivationSettings.wood(BlockSetType.OAK))
        .build();

    for (var bsswBundle : BlockusBlockCollections.WOODEN_MOSAICS) {
      final boolean isNonFlammable = BlockusBlocks.WARPED_MOSAIC == bsswBundle || BlockusBlocks.CRIMSON_MOSAIC == bsswBundle || BlockusBlocks.CHARRED_MOSAIC == bsswBundle;
      final BlocksBuilder builder = FACTORY.createConstructionOnly(bsswBundle.block)
          .without(BlockShape.STAIRS, BlockShape.SLAB)
          .with(BlockShape.WALL)
          .setRecipeGroup(blockShape -> "wooden_mosaic_" + blockShape.asString());
      if (isNonFlammable) {
        builder.build();
      } else {
        builder.addPostBuildConsumer(plankFlammable)
            .build();
      }
    }
    for (var bsswBundle : BlockusBlockCollections.MOSSY_PLANKS) {
      final boolean isNonFlammable = BlockusBlocks.MOSSY_WARPED_PLANKS == bsswBundle || BlockusBlocks.MOSSY_CRIMSON_PLANKS == bsswBundle || BlockusBlocks.MOSSY_CHARRED_PLANKS == bsswBundle;
      if (isNonFlammable) {
        FACTORY.createConstructionOnly(bsswBundle.block)
            .without(BlockShape.STAIRS, BlockShape.SLAB)
            .with(BlockShape.WALL)
            .setRecipeGroup(blockShape -> "mossy_wooden_" + blockShape.asString())
            .build();
      } else {
        FACTORY.createConstructionOnly(bsswBundle.block)
            .without(BlockShape.STAIRS, BlockShape.SLAB)
            .with(BlockShape.WALL)
            .addPostBuildConsumer(plankFlammable)
            .fuelTime(300)
            .setRecipeGroup(blockShape -> "mossy_wooden_" + blockShape.asString())
            .build();
      }
    }
    for (var block : BlockusBlockCollections.HERRINGBONE_PLANKS) {
      final boolean isNonFlammable = BlockusBlocks.HERRINGBONE_WARPED_PLANKS == block || BlockusBlocks.HERRINGBONE_CRIMSON_PLANKS == block || BlockusBlocks.HERRINGBONE_CHARRED_PLANKS == block;
      if (isNonFlammable) {
        FACTORY.createConstructionOnly(block)
            .with(BlockShape.WALL)
            .setRecipeGroup(blockShape -> "herringbone_wooden_" + blockShape.asString())
            .build();
      } else {
        FACTORY.createConstructionOnly(block)
            .with(BlockShape.WALL)
            .addPostBuildConsumer(plankFlammable)
            .fuelTime(300)
            .setRecipeGroup(blockShape -> "herringbone_wooden_" + blockShape.asString())
            .build();
      }
    }
    for (var block : BlockusBlockCollections.SMALL_LOGS) {
      if (block == BlockusBlocks.CRIMSON_SMALL_STEMS || block == BlockusBlocks.WARPED_SMALL_STEMS) {
        FACTORY.createConstructionOnly(block)
            .setPillar()
            .setRecipeGroup(blockShape -> "small_logs_" + blockShape.asString())
            .build();
      } else {
        FACTORY.createConstructionOnly(block)
            .setPillar()
            .addPostBuildConsumer(logFlammable)
            .fuelTime(300)
            .setRecipeGroup(blockShape -> "small_logs_" + blockShape.asString())
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
        .addPreBuildConsumer((blockShape, abstractBlockBuilder) -> abstractBlockBuilder.itemSettings.fireproof())
        .build();


    createAllShapes(BlockusBlocks.CHORUS_BLOCK)
        .setStoneFenceSettings(Items.CHORUS_PLANT)
        .setPillar()
        .build();

    for (var bsswBundle : BlockusBlockCollections.STAINED_STONE_BRICKS) {
      createAllShapes(bsswBundle)
          .markStoneCuttable()
          .setRecipeGroup(blockShape -> "stained_stone_brick_" + blockShape.asString())
          .setActivationSettings(ActivationSettings.STONE)
          .setFenceSettings(FenceSettings.STONE)
          .without(BlockShape.BUTTON)
          .build();
    }
    for (var concreteBundle : BlockusBlockCollections.CONCRETE_BRICKS) {
      FACTORY.createConstructionOnly(concreteBundle.block)
          .markStoneCuttable()
          .without(BlockShape.BUTTON, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL)
          .setRecipeGroup(blockShape1 -> "concrete_brick_" + blockShape1.asString())
          .setActivationSettings(ActivationSettings.STONE)
          .setFenceSettings(FenceSettings.STONE)
          .build();
    }

    createAllShapes(BlockusBlocks.SHINGLES)
        .markStoneCuttable()
        .setActivationSettings(ActivationSettings.STONE)
        .setFenceSettings(FenceSettings.STONE)
        .build();
    for (var bsswBundle : BlockusBlockCollections.TINTED_SHINGLES) {
      createAllShapes(bsswBundle)
          .markStoneCuttable()
          .setActivationSettings(ActivationSettings.STONE)
          .setFenceSettings(FenceSettings.STONE)
          .setRecipeGroup(blockShape -> "shingles_" + blockShape.asString())
          .build();
    }

    for (var woolBundle : Iterables.concat(BlockusBlockCollections.PATTERNED_WOOLS, BlockusBlockCollections.GINGHAM_WOOLS)) {
      FACTORY.createAllShapes(woolBundle.block)
          .without(BlockShape.STAIRS, BlockShape.SLAB, BlockShape.BUTTON)
          .addPreBuildConsumer((blockShape, blockBuilder) -> {
            if (blockShape == BlockShape.PRESSURE_PLATE) {
              ((PressurePlateBuilder) blockBuilder).setInstanceSupplier(x -> new WoolPressurePlate(x.baseBlock, x.blockSettings, ((PressurePlateBuilder) x).activationSettings, woolBundle.carpet));
            }
          })
          .addPostBuildConsumer((blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 30, 60))
          .fuelTime(100)
          .setRecipeGroup(blockShape -> "patterned_wool_" + blockShape.asString())
          .setActivationSettings(ActivationSettings.WOOL)
          .setFenceSettings(FenceSettings.WOOL)
          .build();
    }
    for (var block : BlockusBlockCollections.GLAZED_TERRACOTTA_PILLARS) {
      FACTORY.createConstructionOnly(block)
          .markStoneCuttable()
          .with(BlockShape.WALL)
          .setRecipeGroup(blockShape -> "glazed_terracotta_pillar_" + blockShape.asString())
          .setPillar()
          .build();
    }

    createAllShapes(BlockusBlocks.THATCH)
        .addPostBuildConsumer((blockShape4, blockBuilder3) -> {
          FlammableBlockRegistry.getDefaultInstance().add(blockBuilder3.instance, 60, 20);
          CompostingChanceRegistry.INSTANCE.add(blockBuilder3.instance, blockShape4.logicalCompleteness * 0.75f);
        })
        .setActivationSettings(ActivationSettings.soft(ExtShapeBlockusBlockTypes.GRASS_BLOCK_SET_TYPE))
        .setFenceSettings(new FenceSettings(Items.WHEAT, ExtShapeBlockusBlockTypes.GRASS_BLOCK_WOOD_TYPE))
        .build();
    createAllShapes(BlockusBlocks.PAPER_BLOCK)
        .addPostBuildConsumer((blockShape3, blockBuilder2) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder2.instance, 30, 60))
        .fuelTime(100)
        .setActivationSettings(ActivationSettings.soft(ExtShapeBlockusBlockTypes.GRASS_BLOCK_SET_TYPE))
        .setFenceSettings(new FenceSettings(Items.PAPER, ExtShapeBlockusBlockTypes.GRASS_BLOCK_WOOD_TYPE))
        .build();
    createAllShapes(BlockusBlocks.BURNT_PAPER_BLOCK)
        .addPostBuildConsumer((blockShape2, blockBuilder1) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder1.instance, 5, 60))
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
        .fuelTime(16000)
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
              if (entity.getType() == EntityType.PLAYER) {
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 1, 3, true, false, false));
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 900, 3, true, false, true));
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 80, 2, true, false, true));
              }
            })
            .build())
        .addPreBuildConsumer((blockShape, builder) -> {
          builder.setItemSettings(new Item.Settings().rarity(Rarity.RARE));
          builder.setItemInstanceSupplier(builder0 -> new ExtShapeBlockItem(builder0.instance, builder0.itemSettings.rarity(Rarity.UNCOMMON)) {
            @Override
            public boolean hasGlint(ItemStack stack) {
              return true;
            }

            @Override
            public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
              super.appendTooltip(stack, context, tooltip, type);
              this.getBlock().appendTooltip(stack, context, tooltip, type);
              tooltip.add(ScreenTexts.EMPTY);
              tooltip.add(Blockus.STEPPED_ON_TEXT);
              tooltip.add(ScreenTexts.space().append(StatusEffects.REGENERATION.value().getName()).append(" IV").formatted(Formatting.BLUE));
              tooltip.add(ScreenTexts.space().append(StatusEffects.ABSORPTION.value().getName()).append(" IV").formatted(Formatting.BLUE).append(" - 00:45"));
              tooltip.add(ScreenTexts.space().append(StatusEffects.STRENGTH.value().getName()).append(" III").formatted(Formatting.BLUE).append(" - 00:04"));
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
    final BlocksBuilder blocksBuilder = createAllShapes(bsswBundle.block);
    if (bsswBundle.stairs != null) blocksBuilder.without(BlockShape.STAIRS);
    if (bsswBundle.slab != null) blocksBuilder.without(BlockShape.SLAB);
    if (bsswBundle.wall != null) blocksBuilder.without(BlockShape.WALL);
    return blocksBuilder;
  }

  private static BlocksBuilder createAllShapes(WoodBundle woodBundle) {
    return createAllShapes(woodBundle.planks).without(BlockShape.STAIRS, BlockShape.SLAB, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.PRESSURE_PLATE, BlockShape.BUTTON);
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
