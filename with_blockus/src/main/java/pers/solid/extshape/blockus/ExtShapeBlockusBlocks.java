package pers.solid.extshape.blockus;

import com.brand.blockus.Blockus;
import com.brand.blockus.content.BlockusBlocks;
import com.brand.blockus.content.types.BSSTypes;
import com.brand.blockus.content.types.BSSWTypes;
import com.brand.blockus.content.types.WoodTypes;
import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
import net.minecraft.client.item.TooltipType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.util.Util;
import org.jetbrains.annotations.UnmodifiableView;
import pers.solid.extshape.ExtShapeBlockItem;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapeBlockInterface;
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

  private static boolean markStoneCuttableWhenCreating;

  static {
    final ObjectSet<Block> blocks = new ObjectLinkedOpenHashSet<>();
    final ObjectSet<Block> baseBlocks = new ObjectLinkedOpenHashSet<>();
    BLOCKUS_BLOCKS = ObjectSets.unmodifiable(blocks);
    BLOCKUS_BASE_BLOCKS = ObjectSets.unmodifiable(baseBlocks);

    FACTORY.instanceCollection = blocks;
    FACTORY.baseBlockCollection = baseBlocks;

    BlockusBlockBiMaps.importFromBlockus();
    markStoneCuttableWhenCreating = true;
    create(BlockusBlocks.STONE_TILES)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.HERRINGBONE_STONE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.STONE_CIRCULAR_PAVING)
        .markStoneCuttable());

    create(BlockusBlocks.ANDESITE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .build();
    create(BlockusBlocks.CHISELED_ANDESITE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.HERRINGBONE_ANDESITE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.ANDESITE_CIRCULAR_PAVING)
        .markStoneCuttable());

    create(BlockusBlocks.DIORITE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.CHISELED_DIORITE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.HERRINGBONE_DIORITE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.DIORITE_CIRCULAR_PAVING)
        .markStoneCuttable());

    create(BlockusBlocks.GRANITE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.CHISELED_GRANITE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.HERRINGBONE_GRANITE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.GRANITE_CIRCULAR_PAVING)
        .markStoneCuttable());

    create(BlockusBlocks.CHISELED_MUD_BRICKS)
        .without(BlockShape.BUTTON)
        .setStoneFenceSettings(Items.MUD)
        .build();

    create(BlockusBlocks.POLISHED_DRIPSTONE)
        .setFenceSettings(FenceSettings.DRIPSTONE)

        .setActivationSettings(ActivationSettings.DRIPSTONE)
        .build();
    create(BlockusBlocks.DRIPSTONE_BRICKS)
        .without(BlockShape.BUTTON)
        .setFenceSettings(FenceSettings.DRIPSTONE)

        .setActivationSettings(ActivationSettings.DRIPSTONE)
        .build();
    create(BlockusBlocks.CHISELED_DRIPSTONE)
        .without(BlockShape.BUTTON)
        .setFenceSettings(FenceSettings.DRIPSTONE);

    create(BlockusBlocks.POLISHED_TUFF)
        .setFenceSettings(FenceSettings.TUFF)
        .setActivationSettings(ActivationSettings.TUFF).build();
    create(BlockusBlocks.TUFF_BRICKS)
        .setFenceSettings(FenceSettings.TUFF)
        .setActivationSettings(ActivationSettings.TUFF)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.MOSSY_TUFF_BRICKS)
        .setFenceSettings(FenceSettings.TUFF)
        .setActivationSettings(ActivationSettings.TUFF)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.CHISELED_TUFF)
        .setFenceSettings(FenceSettings.TUFF)
        .setActivationSettings(ActivationSettings.TUFF)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.HERRINGBONE_TUFF_BRICKS)
        .setFenceSettings(FenceSettings.TUFF)
        .setActivationSettings(ActivationSettings.TUFF)
        .without(BlockShape.BUTTON)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.TUFF_CIRCULAR_PAVING)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.TUFF)
        .setActivationSettings(ActivationSettings.TUFF));

    create(BlockusBlocks.MOSSY_TUFF_BRICKS)
        .setFenceSettings(FenceSettings.TUFF)
        .setActivationSettings(ActivationSettings.TUFF)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.CRACKED_TUFF_BRICKS)
        .setFenceSettings(FenceSettings.TUFF)
        .setActivationSettings(ActivationSettings.TUFF)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.HERRINGBONE_TUFF_BRICKS)
        .setFenceSettings(FenceSettings.TUFF)
        .setActivationSettings(ActivationSettings.TUFF)
        .without(BlockShape.BUTTON)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.TUFF_CIRCULAR_PAVING)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.TUFF)
        .setActivationSettings(ActivationSettings.TUFF));

    create(BlockusBlocks.MOSSY_DEEPSLATE_BRICKS)
        .setFenceSettings(FenceSettings.DEEPSLATE_BRICKS)
        .setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.HERRINGBONE_DEEPSLATE_BRICKS)
        .setFenceSettings(FenceSettings.DEEPSLATE_BRICKS)
        .setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS)
        .without(BlockShape.BUTTON)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.DEEPSLATE_CIRCULAR_PAVING)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.DEEPSLATE_BRICKS)
        .setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS));

    create(BlockusBlocks.POLISHED_SCULK)
        .setFenceSettings(new FenceSettings(Items.SCULK_VEIN, ExtShapeBlockTypes.DEEPSLATE_BRICKS_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS)
        .build();
    create(BlockusBlocks.SCULK_BRICKS)
        .without(BlockShape.BUTTON)
        .setFenceSettings(new FenceSettings(Items.SCULK_VEIN, ExtShapeBlockTypes.DEEPSLATE_BRICKS_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS)
        .build();
    create(BlockusBlocks.CHISELED_SCULK_BRICKS)
        .without(BlockShape.BUTTON)
        .setFenceSettings(new FenceSettings(Items.SCULK_VEIN, ExtShapeBlockTypes.DEEPSLATE_BRICKS_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS)
        .build();

    create(BlockusBlocks.POLISHED_AMETHYST)
        .withoutRedstone()
        .withExtension(BlockExtension.AMETHYST)
        .setFenceSettings(FenceSettings.AMETHYST)
        .build();
    create(BlockusBlocks.AMETHYST_BRICKS)
        .withoutRedstone()
        .withExtension(BlockExtension.AMETHYST)
        .setFenceSettings(FenceSettings.AMETHYST)
        .without(BlockShape.BUTTON)
        .build();
    FACTORY.createAllShapes(BlockusBlocks.CHISELED_AMETHYST)
        .markStoneCuttable()
        .withoutRedstone()
        .withExtension(BlockExtension.AMETHYST)
        .setFenceSettings(FenceSettings.AMETHYST)
        .build();

    final ActivationSettings blackstoneActivationSettings = ActivationSettings.stone(BlockSetType.POLISHED_BLACKSTONE);
    create(BlockusBlocks.POLISHED_BLACKSTONE_TILES)
        .setActivationSettings(blackstoneActivationSettings)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.CRIMSON_WARTY_BLACKSTONE_BRICKS)
        .setActivationSettings(blackstoneActivationSettings)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.WARPED_WARTY_BLACKSTONE_BRICKS)
        .setActivationSettings(blackstoneActivationSettings)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.HERRINGBONE_POLISHED_BLACKSTONE_BRICKS)
        .setActivationSettings(blackstoneActivationSettings)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.POLISHED_BLACKSTONE_CIRCULAR_PAVING)
        .markStoneCuttable()
        .setActivationSettings(blackstoneActivationSettings)
        .setFenceSettings(FenceSettings.STONE));

    create(BlockusBlocks.ROUGH_BASALT)
        .setFenceSettings(FenceSettings.BASALT)
        .setActivationSettings(ActivationSettings.BASALT)
        .build();
    create(BlockusBlocks.POLISHED_BASALT_BRICKS)
        .setFenceSettings(FenceSettings.BASALT)
        .setActivationSettings(ActivationSettings.BASALT)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.HERRINGBONE_POLISHED_BASALT_BRICKS)
        .setFenceSettings(FenceSettings.BASALT)
        .setActivationSettings(ActivationSettings.BASALT)
        .without(BlockShape.BUTTON)
        .build();
    buildCircularPavingBlock(create(BlockusBlocks.POLISHED_BASALT_CIRCULAR_PAVING)
        .setFenceSettings(FenceSettings.BASALT)
        .setActivationSettings(ActivationSettings.BASALT));

    create(BlockusBlocks.CRIMSON_WART_BRICKS)
        .setFenceSettings(new FenceSettings(Items.NETHER_WART, ExtShapeBlockTypes.NETHER_BRICKS_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.soft(ExtShapeBlockTypes.NETHER_BRICKS_BLOCK_SET_TYPE))
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.WARPED_WART_BRICKS)
        .setFenceSettings(new FenceSettings(Items.NETHER_WART, ExtShapeBlockTypes.NETHER_BRICKS_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.soft(ExtShapeBlockTypes.NETHER_BRICKS_BLOCK_SET_TYPE))
        .without(BlockShape.BUTTON)
        .build();

    create(BlockusBlocks.LIMESTONE)
        .setFenceSettings(FenceSettings.STONE)
        .build();
    create(BlockusBlocks.POLISHED_LIMESTONE)
        .setFenceSettings(FenceSettings.STONE)
        .build();
    create(BlockusBlocks.LIMESTONE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.SMALL_LIMESTONE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.LIMESTONE_TILES)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.CHISELED_LIMESTONE)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.LIMESTONE_SQUARES)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.LIMESTONE_CIRCULAR_PAVING)
        .markStoneCuttable());

    create(BlockusBlocks.MARBLE)
        .setFenceSettings(FenceSettings.STONE)
        .build();
    create(BlockusBlocks.POLISHED_MARBLE)
        .setFenceSettings(FenceSettings.STONE)
        .build();
    create(BlockusBlocks.MARBLE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.SMALL_MARBLE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.MARBLE_TILES)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.CHISELED_MARBLE)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.MARBLE_SQUARES)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.MARBLE_CIRCULAR_PAVING)
        .markStoneCuttable());

    create(BlockusBlocks.BLUESTONE)
        .setFenceSettings(FenceSettings.STONE)
        .build();
    create(BlockusBlocks.POLISHED_BLUESTONE)
        .setFenceSettings(FenceSettings.STONE)
        .build();
    create(BlockusBlocks.BLUESTONE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.SMALL_BLUESTONE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.BLUESTONE_TILES)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.CHISELED_BLUESTONE)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.BLUESTONE_SQUARES)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.BLUESTONE_CIRCULAR_PAVING)
        .markStoneCuttable());

    create(BlockusBlocks.VIRIDITE)
        .setFenceSettings(FenceSettings.STONE)
        .build();
    create(BlockusBlocks.POLISHED_VIRIDITE)
        .setFenceSettings(FenceSettings.STONE)
        .build();
    create(BlockusBlocks.VIRIDITE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.SMALL_VIRIDITE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.VIRIDITE_TILES)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.CHISELED_VIRIDITE)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.VIRIDITE_SQUARES)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.VIRIDITE_CIRCULAR_PAVING)
        .markStoneCuttable());

    create(BlockusBlocks.LAVA_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .build();
    create(BlockusBlocks.CHISELED_LAVA_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.LAVA_POLISHED_BLACKSTONE_BRICKS)
        .setActivationSettings(ActivationSettings.stone(BlockSetType.POLISHED_BLACKSTONE))
        .setFenceSettings(FenceSettings.STONE)
        .build();
    create(BlockusBlocks.CHISELED_LAVA_POLISHED_BLACKSTONE)
        .setActivationSettings(ActivationSettings.stone(BlockSetType.POLISHED_BLACKSTONE))
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();

    // because of issues related to tint index and render layers, water bricks do not have extended shapes
    create(BlockusBlocks.SNOW_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .build();

    create(BlockusBlocks.ICE_BRICKS)
        .setActivationSettings(ActivationSettings.stone(ExtShapeBlockusBlockTypes.ICE_BLOCK_SET_TYPE))
        .setFenceSettings(new FenceSettings(Items.ICE, ExtShapeBlockusBlockTypes.ICE_WOOD_TYPE))
        .without(BlockShape.BUTTON)
        .build();

    create(BlockusBlocks.OBSIDIAN_BRICKS)
        .setActivationSettings(ActivationSettings.HARD)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.SMALL_OBSIDIAN_BRICKS)
        .setActivationSettings(ActivationSettings.HARD)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.OBSIDIAN_CIRCULAR_PAVING)
        .markStoneCuttable()
        .setActivationSettings(ActivationSettings.HARD));
    create(BlockusBlocks.GLOWING_OBSIDIAN)
        .setActivationSettings(ActivationSettings.HARD)
        .setFenceSettings(FenceSettings.STONE)
        .build();

    create(BlockusBlocks.POLISHED_NETHERRACK)
        .setFenceSettings(FenceSettings.NETHERRACK)
        .setActivationSettings(ActivationSettings.NETHERRACK)
        .build();
    create(BlockusBlocks.NETHERRACK_BRICKS)
        .setFenceSettings(FenceSettings.NETHERRACK)
        .setActivationSettings(ActivationSettings.NETHERRACK)
        .without(BlockShape.BUTTON)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.NETHERRACK_CIRCULAR_PAVING)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.NETHERRACK)
        .setActivationSettings(ActivationSettings.NETHERRACK));

    create(BlockusBlocks.QUARTZ_TILES)
        .setFenceSettings(FenceSettings.QUARTZ)
        .without(BlockShape.BUTTON)
        .build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.QUARTZ_CIRCULAR_PAVING)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.QUARTZ));

    create(BlockusBlocks.MAGMA_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .build();
    create(BlockusBlocks.SMALL_MAGMA_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .build();
    create(BlockusBlocks.CHISELED_MAGMA_BRICKS)
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE)
        .setFenceSettings(FenceSettings.STONE)
        .without(BlockShape.BUTTON)
        .build();

    create(BlockusBlocks.BLAZE_BRICKS)
        .setStoneFenceSettings(Items.BLAZE_ROD)
        .build();
    create(BlockusBlocks.BLAZE_LANTERN)
        .setStoneFenceSettings(Items.BLAZE_ROD)
        .build();

    create(BlockusBlocks.POLISHED_NETHER_BRICKS)
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .build();
    create(BlockusBlocks.POLISHED_RED_NETHER_BRICKS)
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .build();
    create(BlockusBlocks.HERRINGBONE_NETHER_BRICKS)
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.HERRINGBONE_RED_NETHER_BRICKS)
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.NETHER_TILES)
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.CHARRED_NETHER_BRICKS)
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.POLISHED_CHARRED_NETHER_BRICKS)
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.TEAL_NETHER_BRICKS)
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.POLISHED_TEAL_NETHER_BRICKS)
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.HERRINGBONE_TEAL_NETHER_BRICKS)
        .setStoneFenceSettings(Items.NETHER_BRICK)
        .without(BlockShape.BUTTON)
        .build();

    create(BlockusBlocks.CHISELED_PRISMARINE)
        .setStoneFenceSettings(Items.PRISMARINE_SHARD)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    buildCircularPavingBlock(create(BlockusBlocks.PRISMARINE_CIRCULAR_PAVING));
    create(BlockusBlocks.CHISELED_DARK_PRISMARINE)
        .setStoneFenceSettings(Items.PRISMARINE_SHARD)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    create(BlockusBlocks.PRISMARINE_TILES)
        .setStoneFenceSettings(Items.PRISMARINE_SHARD)
        .without(BlockShape.BUTTON)
        .build();

    create(BlockusBlocks.LARGE_BRICKS)
        .setStoneFenceSettings(Items.BRICK)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.HERRINGBONE_BRICKS)
        .setStoneFenceSettings(Items.BRICK)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.SOAKED_BRICKS)
        .setStoneFenceSettings(Items.BRICK)
        .build();
    create(BlockusBlocks.HERRINGBONE_SOAKED_BRICKS)
        .setStoneFenceSettings(Items.BRICK)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.CHARRED_BRICKS)
        .setStoneFenceSettings(Items.BRICK)
        .build();
    create(BlockusBlocks.HERRINGBONE_CHARRED_BRICKS)
        .setStoneFenceSettings(Items.BRICK)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.SANDY_BRICKS)
        .setStoneFenceSettings(Items.BRICK)
        .build();
    create(BlockusBlocks.HERRINGBONE_SANDY_BRICKS)
        .setStoneFenceSettings(Items.BRICK)
        .without(BlockShape.BUTTON)
        .build();

    create(BlockusBlocks.ROUGH_SANDSTONE)
        .setStoneFenceSettings(Items.SAND)
        .build();
    create(BlockusBlocks.SANDSTONE_BRICKS)
        .setStoneFenceSettings(Items.SAND)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.SMALL_SANDSTONE_BRICKS)
        .setStoneFenceSettings(Items.SAND)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.ROUGH_RED_SANDSTONE)
        .setStoneFenceSettings(Items.RED_SAND)
        .build();
    create(BlockusBlocks.RED_SANDSTONE_BRICKS)
        .setStoneFenceSettings(Items.RED_SAND)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.SMALL_RED_SANDSTONE_BRICKS)
        .setStoneFenceSettings(Items.RED_SAND)
        .without(BlockShape.BUTTON)
        .build();
    FACTORY.createConstructionOnly(BlockusBlocks.SOUL_SANDSTONE.block)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.SOUL_SAND)
        .build();
    create(BlockusBlocks.ROUGH_SOUL_SANDSTONE)
        .setStoneFenceSettings(Items.SOUL_SAND)
        .build();
    create(BlockusBlocks.SOUL_SANDSTONE_BRICKS)
        .setStoneFenceSettings(Items.SOUL_SAND)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.SMALL_SOUL_SANDSTONE_BRICKS)
        .setStoneFenceSettings(Items.SOUL_SAND)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.SMOOTH_SOUL_SANDSTONE)
        .setStoneFenceSettings(Items.SOUL_SAND)
        .build();
    create(BlockusBlocks.CUT_SOUL_SANDSTONE)
        .setStoneFenceSettings(Items.SOUL_SAND)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.CHISELED_SOUL_SANDSTONE)
        .setStoneFenceSettings(Items.SOUL_SAND)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();

    create(BlockusBlocks.RAINBOW_BLOCK)
        .setStoneFenceSettings(BlockusBlocks.RAINBOW_PETALS.asItem()).setPillar()
        .build();
    create(BlockusBlocks.RAINBOW_BRICKS)
        .setStoneFenceSettings(BlockusBlocks.RAINBOW_PETALS.asItem())
        .build();
    create(BlockusBlocks.RAINBOW_GLOWSTONE)
        .setFenceSettings(new FenceSettings(BlockusBlocks.RAINBOW_PETALS.asItem(), ExtShapeBlockTypes.GLOWSTONE_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.stone(ExtShapeBlockTypes.GLOWSTONE_BLOCK_SET_TYPE))
        .build();

    create(BlockusBlocks.HONEYCOMB_BRICKS)
        .setStoneFenceSettings(Items.HONEYCOMB)
        .without(BlockShape.BUTTON)
        .build();

    create(BlockusBlocks.PURPUR_BRICKS)
        .setStoneFenceSettings(Items.PURPUR_BLOCK)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.SMALL_PURPUR_BRICKS)
        .setStoneFenceSettings(Items.PURPUR_BLOCK)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.POLISHED_PURPUR)
        .setStoneFenceSettings(Items.PURPUR_BLOCK)
        .build();
    create(BlockusBlocks.CHISELED_PURPUR)
        .setStoneFenceSettings(Items.PURPUR_BLOCK)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    create(BlockusBlocks.PURPUR_SQUARES)
        .setStoneFenceSettings(Items.PURPUR_BLOCK)
        .without(BlockShape.BUTTON)
        .build();

    create(BlockusBlocks.PHANTOM_PURPUR_BRICKS)
        .setStoneFenceSettings(Items.PHANTOM_MEMBRANE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.SMALL_PHANTOM_PURPUR_BRICKS)
        .setStoneFenceSettings(Items.PHANTOM_MEMBRANE)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.PHANTOM_PURPUR_BLOCK)
        .setStoneFenceSettings(Items.PHANTOM_MEMBRANE)
        .build();
    create(BlockusBlocks.POLISHED_PHANTOM_PURPUR)
        .setStoneFenceSettings(Items.PHANTOM_MEMBRANE)
        .build();
    create(BlockusBlocks.CHISELED_PHANTOM_PURPUR)
        .setStoneFenceSettings(Items.PHANTOM_MEMBRANE)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    create(BlockusBlocks.PHANTOM_PURPUR_SQUARES)
        .setStoneFenceSettings(Items.PHANTOM_MEMBRANE)
        .without(BlockShape.BUTTON)
        .build();

    create(BlockusBlocks.POLISHED_END_STONE)
        .setStoneFenceSettings(Items.END_STONE_BRICKS)
        .build();
    create(BlockusBlocks.SMALL_END_STONE_BRICKS)
        .setStoneFenceSettings(Items.END_STONE_BRICKS)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.CHISELED_END_STONE_BRICKS)
        .setStoneFenceSettings(Items.END_STONE_BRICKS)
        .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    create(BlockusBlocks.HERRINGBONE_END_STONE_BRICKS)
        .setStoneFenceSettings(Items.END_STONE_BRICKS)
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.END_TILES)
        .setStoneFenceSettings(Items.END_STONE_BRICKS)
        .without(BlockShape.BUTTON)
        .build();

    markStoneCuttableWhenCreating = false;

    final BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> logFlammable = (blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 5, 5);
    final BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> logFuel = (blockShape, blockBuilder) -> FuelRegistry.INSTANCE.add(blockBuilder.instance, (int) (blockShape.logicalCompleteness * 300));
    FACTORY.createConstructionOnly(BlockusBlocks.WHITE_OAK_LOG)
        .setPillar()
        .addPostBuildConsumer(logFlammable)
        .addPostBuildConsumer(logFuel)
        .build();
    FACTORY.createConstructionOnly(BlockusBlocks.STRIPPED_WHITE_OAK_LOG)
        .setPillar()
        .addPostBuildConsumer(logFlammable)
        .addPostBuildConsumer(logFuel)
        .build();
    FACTORY.createAllShapes(BlockusBlocks.WHITE_OAK_WOOD)
        .setActivationSettings(ActivationSettings.wood(BlockSetType.OAK))
        .setPillar()
        .addPostBuildConsumer(logFlammable)
        .addPostBuildConsumer(logFuel)
        .build();
    FACTORY.createAllShapes(BlockusBlocks.STRIPPED_WHITE_OAK_WOOD)
        .setActivationSettings(ActivationSettings.wood(BlockSetType.OAK))
        .setPillar()
        .addPostBuildConsumer(logFlammable)
        .addPostBuildConsumer(logFuel)
        .build();

    final BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> plankFlammable = (blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 5, 20);
    final BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> plankFuel = (blockShape, blockBuilder) -> FuelRegistry.INSTANCE.add(blockBuilder.instance, (int) (blockShape.logicalCompleteness * 300));
    create(BlockusBlocks.WHITE_OAK)
        .setActivationSettings(ActivationSettings.wood(BlockSetType.OAK))
        .addPostBuildConsumer(plankFlammable)
        .addPostBuildConsumer(plankFuel)
        .build();
    create(BlockusBlocks.RAW_BAMBOO)
        .setActivationSettings(ActivationSettings.BAMBOO)
        .setFenceSettings(new FenceSettings(Items.BAMBOO, WoodType.BAMBOO))
        .addPostBuildConsumer(plankFlammable)
        .addPostBuildConsumer(plankFuel)
        .build();
    create(BlockusBlocks.CHARRED)
        .setActivationSettings(ActivationSettings.wood(BlockSetType.OAK))
        .build();

    for (var bssTypes : BlockusBlockCollections.WOODEN_MOSAICS) {
      final boolean isNonFlammable = BlockusBlocks.WARPED_MOSAIC == bssTypes || BlockusBlocks.CRIMSON_MOSAIC == bssTypes || BlockusBlocks.CHARRED_MOSAIC == bssTypes;
      final BlocksBuilder builder = FACTORY.createConstructionOnly(bssTypes.block)
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
    for (var bssTypes : BlockusBlockCollections.MOSSY_PLANKS) {
      final boolean isNonFlammable = BlockusBlocks.MOSSY_WARPED_PLANKS == bssTypes || BlockusBlocks.MOSSY_CRIMSON_PLANKS == bssTypes || BlockusBlocks.MOSSY_CHARRED_PLANKS == bssTypes;
      if (isNonFlammable) {
        FACTORY.createConstructionOnly(bssTypes.block)
            .without(BlockShape.STAIRS, BlockShape.SLAB)
            .with(BlockShape.WALL)
            .setRecipeGroup(blockShape -> "mossy_wooden_" + blockShape.asString())
            .build();
      } else {
        FACTORY.createConstructionOnly(bssTypes.block)
            .without(BlockShape.STAIRS, BlockShape.SLAB)
            .with(BlockShape.WALL)
            .addPostBuildConsumer(plankFlammable)
            .addPostBuildConsumer(plankFuel)
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
            .addPostBuildConsumer(plankFuel)
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
            .addPostBuildConsumer(logFuel)
            .setRecipeGroup(blockShape -> "small_logs_" + blockShape.asString())
            .build();
      }
    }

    markStoneCuttableWhenCreating = true;
    create(BlockusBlocks.CHOCOLATE_BLOCK)
        .setFenceSettings(FenceSettings.STONE)
        .setActivationSettings(ActivationSettings.STONE)
        .build();
    create(BlockusBlocks.CHOCOLATE_BRICKS)
        .setFenceSettings(FenceSettings.STONE)
        .setActivationSettings(ActivationSettings.STONE)
        .build();
    create(BlockusBlocks.CHOCOLATE_SQUARES)
        .setFenceSettings(FenceSettings.STONE)
        .setActivationSettings(ActivationSettings.STONE)
        .build();

    // 注意：墙等形状均不属于 beacon_base_block
    create(BlockusBlocks.IRON_BRICKS)
        .withoutRedstone()
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    create(BlockusBlocks.GOLD_BRICKS)
        .withoutRedstone()
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    create(BlockusBlocks.LAPIS_BRICKS)
        .withoutRedstone()
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    create(BlockusBlocks.REDSTONE_BRICKS)
        .withExtension(BlockExtension.builder()
            .setEmitsRedstonePower((state, original) -> true)
            .setWeakRedstonePower((state, world, pos, direction, original) -> 15)
            .build())
        .withoutRedstone()
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    create(BlockusBlocks.EMERALD_BRICKS)
        .withoutRedstone()
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    create(BlockusBlocks.DIAMOND_BRICKS)
        .withoutRedstone()
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE)
        .build();
    create(BlockusBlocks.NETHERITE_BRICKS)
        .withoutRedstone()
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE)
        .addPostBuildConsumer((blockShape, abstractBlockBuilder) -> abstractBlockBuilder.itemSettings.fireproof())
        .build();

    markStoneCuttableWhenCreating = false;

    create(BlockusBlocks.CHORUS_BLOCK)
        .setStoneFenceSettings(Items.CHORUS_PLANT).setPillar()
        .build();

    for (var bsswTypes : BlockusBlockCollections.STAINED_STONE_BRICKS) {
      create(bsswTypes)
          .markStoneCuttable()
          .setRecipeGroup(blockShape -> "stained_stone_brick_" + blockShape.asString())
          .setActivationSettings(ActivationSettings.STONE)
          .setFenceSettings(FenceSettings.STONE)
          .without(BlockShape.BUTTON)
          .build();
    }
    for (var concreteTypes : BlockusBlockCollections.CONCRETE_BRICKS) {
      FACTORY.createConstructionOnly(concreteTypes.block)
          .markStoneCuttable()
          .without(BlockShape.BUTTON, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL)
          .setRecipeGroup(blockShape1 -> "concrete_brick_" + blockShape1.asString())
          .setActivationSettings(ActivationSettings.STONE)
          .setFenceSettings(FenceSettings.STONE)
          .build();
      FACTORY.createConstructionOnly(concreteTypes.chiseled)
          .markStoneCuttable()
          .without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE)
          .setRecipeGroup(blockShape -> "chiseled_concrete_brick_" + blockShape.asString())
          .setActivationSettings(ActivationSettings.STONE)
          .setFenceSettings(FenceSettings.STONE)
          .build();
    }

    create(BlockusBlocks.SHINGLES)
        .markStoneCuttable()
        .setActivationSettings(ActivationSettings.STONE)
        .setFenceSettings(FenceSettings.STONE)
        .build();
    for (var bssTypes : BlockusBlockCollections.TINTED_SHINGLES) {
      create(bssTypes)
          .markStoneCuttable()
          .setActivationSettings(ActivationSettings.STONE)
          .setFenceSettings(FenceSettings.STONE)
          .setRecipeGroup(blockShape -> "shingles_" + blockShape.asString())
          .build();
    }

    for (var woolTypes : Iterables.concat(BlockusBlockCollections.PATTERNED_WOOLS, BlockusBlockCollections.GINGHAM_WOOLS)) {
      FACTORY.createAllShapes(woolTypes.block)
          .without(BlockShape.STAIRS, BlockShape.SLAB, BlockShape.BUTTON)
          .addPreBuildConsumer((blockShape, blockBuilder) -> {
            if (blockShape == BlockShape.PRESSURE_PLATE) {
              ((PressurePlateBuilder) blockBuilder).setInstanceSupplier(x -> new WoolPressurePlate(x.baseBlock, x.blockSettings, ((PressurePlateBuilder) x).activationSettings, woolTypes.carpet));
            }
          })
          .addPostBuildConsumer((blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 30, 60))
          .addPostBuildConsumer((blockShape, blockBuilder) -> FuelRegistry.INSTANCE.add(blockBuilder.instance, (int) (100 * blockShape.logicalCompleteness)))
          .setRecipeGroup(blockShape -> "patterned_wool_" + blockShape.asString())
          .setActivationSettings(ActivationSettings.WOOL)
          .setFenceSettings(FenceSettings.WOOL)
          .build();
    }
    for (var block : BlockusBlockCollections.GLAZED_TERRACOTTA_PILLARS) {
      FACTORY.createConstructionOnly(block)
          .markStoneCuttable().with(BlockShape.WALL)
          .setRecipeGroup(blockShape -> "glazed_terracotta_pillar_" + blockShape.asString())
          .setPillar()
          .build();
    }

    create(BlockusBlocks.THATCH)
        .addPostBuildConsumer((blockShape4, blockBuilder3) -> {
          FlammableBlockRegistry.getDefaultInstance().add(blockBuilder3.instance, 60, 20);
          CompostingChanceRegistry.INSTANCE.add(blockBuilder3.instance, blockShape4.logicalCompleteness * 0.75f);
        })
        .setActivationSettings(ActivationSettings.soft(ExtShapeBlockusBlockTypes.GRASS_BLOCK_SET_TYPE))
        .setFenceSettings(new FenceSettings(Items.WHEAT, ExtShapeBlockusBlockTypes.GRASS_BLOCK_WOOD_TYPE))
        .build();
    create(BlockusBlocks.PAPER_BLOCK)
        .addPostBuildConsumer((blockShape3, blockBuilder2) -> {
          FlammableBlockRegistry.getDefaultInstance().add(blockBuilder2.instance, 30, 60);
          FuelRegistry.INSTANCE.add(blockBuilder2.instance, (int) (blockShape3.logicalCompleteness * 100));
        })
        .setActivationSettings(ActivationSettings.soft(ExtShapeBlockusBlockTypes.GRASS_BLOCK_SET_TYPE))
        .setFenceSettings(new FenceSettings(Items.PAPER, ExtShapeBlockusBlockTypes.GRASS_BLOCK_WOOD_TYPE))
        .build();
    create(BlockusBlocks.BURNT_PAPER_BLOCK)
        .addPostBuildConsumer((blockShape2, blockBuilder1) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder1.instance, 5, 60))
        .setActivationSettings(ActivationSettings.soft(ExtShapeBlockusBlockTypes.GRASS_BLOCK_SET_TYPE))
        .setFenceSettings(new FenceSettings(Items.GUNPOWDER, ExtShapeBlockusBlockTypes.GRASS_BLOCK_WOOD_TYPE))
        .build();

    create(BlockusBlocks.IRON_PLATING)
        .markStoneCuttable()
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.BUTTON)
        .setActivationSettings(ActivationSettings.IRON)
        .build();
    create(BlockusBlocks.GOLD_PLATING)
        .markStoneCuttable()
        .without(BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.BUTTON)
        .setActivationSettings(ActivationSettings.GOLD)
        .build();

    create(BlockusBlocks.CHARCOAL_BLOCK)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.CHARCOAL)
        .addPostBuildConsumer((blockShape1, blockBuilder) -> FuelRegistry.INSTANCE.add(blockBuilder.instance, (int) (blockShape1.logicalCompleteness * 16000)))
        .build();

    // sugar 没有，因为是下落方块，不符合条件。

    create(BlockusBlocks.ENDER_BLOCK)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.ENDER_PEARL)
        .build();

    create(BlockusBlocks.ROTTEN_FLESH_BLOCK)
        .setActivationSettings(ActivationSettings.soft(ExtShapeBlockTypes.SLIME_BLOCK_SET_TYPE))
        .setFenceSettings(new FenceSettings(Items.ROTTEN_FLESH, ExtShapeBlockTypes.SLIME_WOOD_TYPE))
        .build();
    create(BlockusBlocks.MEMBRANE_BLOCK)
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

    create(BlockusBlocks.STARS_BLOCK)
        .markStoneCuttable()
        .setStoneFenceSettings(Items.COAL)
        .build();
  }

  private ExtShapeBlockusBlocks() {
  }

  private static BlocksBuilder create(Block baseBlock) {
    if (markStoneCuttableWhenCreating) {
      ExtShapeBlockInterface.STONECUTTABLE_BASE_BLOCKS.add(baseBlock);
    }
    return FACTORY.createAllShapes(baseBlock);
  }

  private static BlocksBuilder create(BSSWTypes bsswTypes) {
    final BlocksBuilder blocksBuilder = create(bsswTypes.block);
    if (bsswTypes.stairs != null) blocksBuilder.without(BlockShape.STAIRS);
    if (bsswTypes.slab != null) blocksBuilder.without(BlockShape.SLAB);
    if (bsswTypes.wall != null) blocksBuilder.without(BlockShape.WALL);
    return blocksBuilder;
  }

  private static BlocksBuilder create(BSSTypes bssTypes) {
    final BlocksBuilder blocksBuilder = create(bssTypes.block);
    if (bssTypes.stairs != null) blocksBuilder.without(BlockShape.STAIRS);
    if (bssTypes.slab != null) blocksBuilder.without(BlockShape.SLAB);
    return blocksBuilder;
  }

  private static BlocksBuilder create(WoodTypes woodTypes) {
    return create(woodTypes.planks).without(BlockShape.STAIRS, BlockShape.SLAB, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.PRESSURE_PLATE, BlockShape.BUTTON);
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
