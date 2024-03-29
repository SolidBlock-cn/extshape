package pers.solid.extshape.blockus;

import com.brand.blockus.Blockus;
import com.brand.blockus.content.BlockusBlocks;
import com.brand.blockus.content.BlockusItems;
import com.brand.blockus.content.types.BSSTypes;
import com.brand.blockus.content.types.BSSWTypes;
import com.brand.blockus.content.types.WoodTypes;
import com.brand.blockus.utils.tags.BlockusBlockTags;
import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.mininglevel.v1.FabricMineableTags;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.ExtShapeBlockItem;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.builder.*;
import pers.solid.extshape.tag.ExtShapeTags;
import pers.solid.extshape.util.ActivationSettings;
import pers.solid.extshape.util.ExtShapeBlockTypes;
import pers.solid.extshape.util.FenceSettings;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static pers.solid.extshape.blockus.ExtShapeBlockusTags.ofBlockAndItem;

public final class ExtShapeBlockusBlocks {
  static final ObjectSet<Block> BLOCKUS_BLOCKS = new ObjectLinkedOpenHashSet<>();
  static final ObjectSet<Block> BLOCKUS_BASE_BLOCKS = new ObjectLinkedOpenHashSet<>();
  private static final BlocksBuilderFactory FACTORY = Util.make(new BlocksBuilderFactory(), blocksBuilderFactory -> {
    blocksBuilderFactory.defaultNamespace = ExtShapeBlockus.NAMESPACE;
    blocksBuilderFactory.instanceCollection = BLOCKUS_BLOCKS;
    blocksBuilderFactory.baseBlockCollection = BLOCKUS_BASE_BLOCKS;
    blocksBuilderFactory.tagPreparations = ExtShapeBlockusTags.TAG_PREPARATIONS;
  });

  private static boolean markStoneCuttableWhenCreating = false;

  static {
    BlockusBlockBiMaps.importFromBlockus();
    markStoneCuttableWhenCreating = true;
    create(BlockusBlocks.STONE_TILES).addExtraTag(BlockusBlockTags.STONE_BLOCKS).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.HERRINGBONE_STONE_BRICKS).addExtraTag(BlockusBlockTags.STONE_BLOCKS).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.STONE_CIRCULAR_PAVING).markStoneCuttable().addExtraTag(BlockusBlockTags.STONE_BLOCKS));

    create(BlockusBlocks.ANDESITE_BRICKS).addExtraTag(BlockusBlockTags.ANDESITE_BLOCKS).setFenceSettings(FenceSettings.STONE).build();
    create(BlockusBlocks.CHISELED_ANDESITE_BRICKS).addExtraTag(BlockusBlockTags.ANDESITE_BLOCKS).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.HERRINGBONE_ANDESITE_BRICKS).addExtraTag(BlockusBlockTags.ANDESITE_BLOCKS).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.ANDESITE_CIRCULAR_PAVING).markStoneCuttable().addExtraTag(BlockusBlockTags.ANDESITE_BLOCKS));

    create(BlockusBlocks.DIORITE_BRICKS).addExtraTag(BlockusBlockTags.DIORITE_BLOCKS).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.CHISELED_DIORITE_BRICKS).addExtraTag(BlockusBlockTags.DIORITE_BLOCKS).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.HERRINGBONE_DIORITE_BRICKS).addExtraTag(BlockusBlockTags.DIORITE_BLOCKS).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.DIORITE_CIRCULAR_PAVING).markStoneCuttable().addExtraTag(BlockusBlockTags.DIORITE_BLOCKS));

    create(BlockusBlocks.GRANITE_BRICKS).addExtraTag(BlockusBlockTags.GRANITE_BLOCKS).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.CHISELED_GRANITE_BRICKS).addExtraTag(BlockusBlockTags.GRANITE_BLOCKS).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.HERRINGBONE_GRANITE_BRICKS).addExtraTag(BlockusBlockTags.GRANITE_BLOCKS).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.GRANITE_CIRCULAR_PAVING).markStoneCuttable().addExtraTag(BlockusBlockTags.GRANITE_BLOCKS));

    create(BlockusBlocks.CHISELED_MUD_BRICKS).addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .without(BlockShape.BUTTON)
        .setStoneFenceSettings(Items.MUD).build();

    create(BlockusBlocks.POLISHED_DRIPSTONE).addExtraTag(BlockusBlockTags.DRIPSTONE_BLOCKS)
        .setFenceSettings(FenceSettings.DRIPSTONE)
        .setActivationSettings(ActivationSettings.DRIPSTONE).build();
    create(BlockusBlocks.DRIPSTONE_BRICKS).addExtraTag(BlockusBlockTags.DRIPSTONE_BLOCKS)
        .without(BlockShape.BUTTON)
        .setFenceSettings(FenceSettings.DRIPSTONE)
        .setActivationSettings(ActivationSettings.DRIPSTONE).build();
    create(BlockusBlocks.CHISELED_DRIPSTONE).addExtraTag(BlockusBlockTags.DRIPSTONE_BLOCKS)
        .without(BlockShape.BUTTON)
        .setFenceSettings(FenceSettings.DRIPSTONE)
        .setActivationSettings(ActivationSettings.DRIPSTONE).build();

    create(BlockusBlocks.POLISHED_TUFF).addExtraTag(BlockusBlockTags.TUFF_BLOCKS).setFenceSettings(FenceSettings.TUFF).setActivationSettings(ActivationSettings.TUFF).build();
    create(BlockusBlocks.TUFF_BRICKS).addExtraTag(BlockusBlockTags.TUFF_BLOCKS).setFenceSettings(FenceSettings.TUFF).setActivationSettings(ActivationSettings.TUFF).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.MOSSY_TUFF_BRICKS).addExtraTag(BlockusBlockTags.TUFF_BLOCKS).setFenceSettings(FenceSettings.TUFF).setActivationSettings(ActivationSettings.TUFF).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.CHISELED_TUFF).addExtraTag(BlockusBlockTags.TUFF_BLOCKS).setFenceSettings(FenceSettings.TUFF).setActivationSettings(ActivationSettings.TUFF).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.HERRINGBONE_TUFF_BRICKS).addExtraTag(BlockusBlockTags.TUFF_BLOCKS).setFenceSettings(FenceSettings.TUFF).setActivationSettings(ActivationSettings.TUFF).without(BlockShape.BUTTON).build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.TUFF_CIRCULAR_PAVING).markStoneCuttable().addExtraTag(BlockusBlockTags.TUFF_BLOCKS).setFenceSettings(FenceSettings.TUFF).setActivationSettings(ActivationSettings.TUFF));

    create(BlockusBlocks.MOSSY_DEEPSLATE_BRICKS).addExtraTag(BlockusBlockTags.DEEPSLATE_BLOCKS).setFenceSettings(FenceSettings.DEEPSLATE_BRICKS).setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.HERRINGBONE_DEEPSLATE_BRICKS).addExtraTag(BlockusBlockTags.DEEPSLATE_BLOCKS).setFenceSettings(FenceSettings.DEEPSLATE_BRICKS).setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS).without(BlockShape.BUTTON).build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.DEEPSLATE_CIRCULAR_PAVING).markStoneCuttable().addExtraTag(BlockusBlockTags.DEEPSLATE_BLOCKS).setFenceSettings(FenceSettings.DEEPSLATE_BRICKS).setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS));

    create(BlockusBlocks.POLISHED_SCULK).addExtraTag(BlockusBlockTags.SCULK_BLOCKS)
        .setFenceSettings(new FenceSettings(Items.SCULK_VEIN, ExtShapeBlockTypes.DEEPSLATE_BRICKS_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS)
        .build();
    create(BlockusBlocks.SCULK_BRICKS).addExtraTag(BlockusBlockTags.SCULK_BLOCKS)
        .without(BlockShape.BUTTON)
        .setFenceSettings(new FenceSettings(Items.SCULK_VEIN, ExtShapeBlockTypes.DEEPSLATE_BRICKS_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS)
        .build();
    create(BlockusBlocks.CHISELED_SCULK_BRICKS).addExtraTag(BlockusBlockTags.SCULK_BLOCKS)
        .without(BlockShape.BUTTON)
        .setFenceSettings(new FenceSettings(Items.SCULK_VEIN, ExtShapeBlockTypes.DEEPSLATE_BRICKS_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS)
        .build();

    create(BlockusBlocks.POLISHED_AMETHYST).withoutRedstone()
        .addExtraTag(BlockusBlockTags.AMETHYST_BLOCKS)
        .withExtension(BlockExtension.AMETHYST)
        .setFenceSettings(FenceSettings.AMETHYST)
        .build();
    create(BlockusBlocks.AMETHYST_BRICKS).withoutRedstone()
        .addExtraTag(BlockusBlockTags.AMETHYST_BLOCKS)
        .withExtension(BlockExtension.AMETHYST)
        .setFenceSettings(FenceSettings.AMETHYST)
        .without(BlockShape.BUTTON)
        .build();
    FACTORY.createAllShapes(BlockusBlocks.CHISELED_AMETHYST)
        .markStoneCuttable()
        .withoutRedstone()
        .addExtraTag(BlockusBlockTags.AMETHYST_BLOCKS)
        .withExtension(BlockExtension.AMETHYST)
        .setFenceSettings(FenceSettings.AMETHYST)
        .build();

    final ActivationSettings blackstoneActivationSettings = ActivationSettings.stone(BlockSetType.POLISHED_BLACKSTONE);
    create(BlockusBlocks.POLISHED_BLACKSTONE_TILES).setActivationSettings(blackstoneActivationSettings).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.CRIMSON_WARTY_BLACKSTONE_BRICKS).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS).setActivationSettings(blackstoneActivationSettings).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.WARPED_WARTY_BLACKSTONE_BRICKS).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS).setActivationSettings(blackstoneActivationSettings).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.HERRINGBONE_POLISHED_BLACKSTONE_BRICKS).setActivationSettings(blackstoneActivationSettings).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.POLISHED_BLACKSTONE_CIRCULAR_PAVING)
        .markStoneCuttable()
        .setActivationSettings(blackstoneActivationSettings).setFenceSettings(FenceSettings.STONE).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS));

    create(BlockusBlocks.ROUGH_BASALT).addExtraTag(BlockusBlockTags.BASALT_BLOCKS).setFenceSettings(FenceSettings.BASALT).setActivationSettings(ActivationSettings.BASALT).build();
    create(BlockusBlocks.POLISHED_BASALT_BRICKS).addExtraTag(BlockusBlockTags.BASALT_BLOCKS).setFenceSettings(FenceSettings.BASALT).setActivationSettings(ActivationSettings.BASALT).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.HERRINGBONE_POLISHED_BASALT_BRICKS).addExtraTag(BlockusBlockTags.BASALT_BLOCKS).setFenceSettings(FenceSettings.BASALT).setActivationSettings(ActivationSettings.BASALT).without(BlockShape.BUTTON).build();
    buildCircularPavingBlock(create(BlockusBlocks.POLISHED_BASALT_CIRCULAR_PAVING).addExtraTag(BlockusBlockTags.BASALT_BLOCKS).setFenceSettings(FenceSettings.BASALT).setActivationSettings(ActivationSettings.BASALT));

    create(BlockusBlocks.CRIMSON_WART_BRICKS).addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(new FenceSettings(Items.NETHER_WART, ExtShapeBlockTypes.NETHER_BRICKS_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.soft(ExtShapeBlockTypes.NETHER_BRICKS_BLOCK_SET_TYPE))
        .without(BlockShape.BUTTON)
        .build();
    create(BlockusBlocks.WARPED_WART_BRICKS).addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(new FenceSettings(Items.NETHER_WART, ExtShapeBlockTypes.NETHER_BRICKS_WOOD_TYPE))
        .setActivationSettings(ActivationSettings.soft(ExtShapeBlockTypes.NETHER_BRICKS_BLOCK_SET_TYPE))
        .without(BlockShape.BUTTON)
        .build();

    create(BlockusBlocks.LIMESTONE).addExtraTag(BlockusBlockTags.LIMESTONE).setFenceSettings(FenceSettings.STONE).build();
    create(BlockusBlocks.POLISHED_LIMESTONE).addExtraTag(BlockusBlockTags.LIMESTONE).setFenceSettings(FenceSettings.STONE).build();
    create(BlockusBlocks.LIMESTONE_BRICKS).addExtraTag(BlockusBlockTags.LIMESTONE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SMALL_LIMESTONE_BRICKS).addExtraTag(BlockusBlockTags.LIMESTONE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.LIMESTONE_TILES).addExtraTag(BlockusBlockTags.LIMESTONE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.CHISELED_LIMESTONE).addExtraTag(BlockusBlockTags.LIMESTONE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.LIMESTONE_SQUARES).addExtraTag(BlockusBlockTags.LIMESTONE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.LIMESTONE_CIRCULAR_PAVING).markStoneCuttable().addExtraTag(BlockusBlockTags.LIMESTONE));

    create(BlockusBlocks.MARBLE).addExtraTag(BlockusBlockTags.MARBLE).setFenceSettings(FenceSettings.STONE).build();
    create(BlockusBlocks.POLISHED_MARBLE).addExtraTag(BlockusBlockTags.MARBLE).setFenceSettings(FenceSettings.STONE).build();
    create(BlockusBlocks.MARBLE_BRICKS).addExtraTag(BlockusBlockTags.MARBLE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SMALL_MARBLE_BRICKS).addExtraTag(BlockusBlockTags.MARBLE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.MARBLE_TILES).addExtraTag(BlockusBlockTags.MARBLE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.CHISELED_MARBLE).addExtraTag(BlockusBlockTags.MARBLE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.MARBLE_SQUARES).addExtraTag(BlockusBlockTags.MARBLE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.MARBLE_CIRCULAR_PAVING).markStoneCuttable().addExtraTag(BlockusBlockTags.MARBLE));

    create(BlockusBlocks.BLUESTONE).addExtraTag(BlockusBlockTags.BLUESTONE).setFenceSettings(FenceSettings.STONE).build();
    create(BlockusBlocks.POLISHED_BLUESTONE).addExtraTag(BlockusBlockTags.BLUESTONE).setFenceSettings(FenceSettings.STONE).build();
    create(BlockusBlocks.BLUESTONE_BRICKS).addExtraTag(BlockusBlockTags.BLUESTONE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SMALL_BLUESTONE_BRICKS).addExtraTag(BlockusBlockTags.BLUESTONE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.BLUESTONE_TILES).addExtraTag(BlockusBlockTags.BLUESTONE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.CHISELED_BLUESTONE).addExtraTag(BlockusBlockTags.BLUESTONE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.BLUESTONE_SQUARES).addExtraTag(BlockusBlockTags.BLUESTONE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.BLUESTONE_CIRCULAR_PAVING).markStoneCuttable().addExtraTag(BlockusBlockTags.BLUESTONE));

    create(BlockusBlocks.VIRIDITE).addExtraTag(BlockusBlockTags.VIRIDITE).setFenceSettings(FenceSettings.STONE).build();
    create(BlockusBlocks.POLISHED_VIRIDITE).addExtraTag(BlockusBlockTags.VIRIDITE).setFenceSettings(FenceSettings.STONE).build();
    create(BlockusBlocks.VIRIDITE_BRICKS).addExtraTag(BlockusBlockTags.VIRIDITE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SMALL_VIRIDITE_BRICKS).addExtraTag(BlockusBlockTags.VIRIDITE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.VIRIDITE_TILES).addExtraTag(BlockusBlockTags.VIRIDITE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.CHISELED_VIRIDITE).addExtraTag(BlockusBlockTags.VIRIDITE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.VIRIDITE_SQUARES).addExtraTag(BlockusBlockTags.VIRIDITE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.VIRIDITE_CIRCULAR_PAVING).markStoneCuttable().addExtraTag(BlockusBlockTags.VIRIDITE));

    create(BlockusBlocks.LAVA_BRICKS).addExtraTag(BlockusBlockTags.LAVA_BRICKS).setFenceSettings(FenceSettings.STONE).build();
    create(BlockusBlocks.CHISELED_LAVA_BRICKS).addExtraTag(BlockusBlockTags.LAVA_BRICKS).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.LAVA_POLISHED_BLACKSTONE_BRICKS).addExtraTag(BlockusBlockTags.LAVA_POLISHED_BLACKSTONE_BRICKS).setActivationSettings(ActivationSettings.stone(BlockSetType.POLISHED_BLACKSTONE)).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS).setFenceSettings(FenceSettings.STONE).build();
    create(BlockusBlocks.CHISELED_LAVA_POLISHED_BLACKSTONE).addExtraTag(BlockusBlockTags.LAVA_POLISHED_BLACKSTONE_BRICKS).setActivationSettings(ActivationSettings.stone(BlockSetType.POLISHED_BLACKSTONE)).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();

    // because of issues related to tint index and render layers, water bricks do not have extended shapes
    create(BlockusBlocks.SNOW_BRICKS).addExtraTag(BlockusBlockTags.SNOW_BRICKS).setFenceSettings(FenceSettings.STONE).build();

    create(BlockusBlocks.ICE_BRICKS).setActivationSettings(ActivationSettings.stone(ExtShapeBlockusBlockTypes.ICE_BLOCK_SET_TYPE)).setFenceSettings(new FenceSettings(Items.ICE, ExtShapeBlockusBlockTypes.ICE_WOOD_TYPE)).addExtraTag(BlockusBlockTags.ICE_BRICKS).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.OBSIDIAN_BRICKS).addExtraTag(BlockusBlockTags.OBSIDIAN).setActivationSettings(ActivationSettings.HARD).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SMALL_OBSIDIAN_BRICKS).addExtraTag(BlockusBlockTags.OBSIDIAN).setActivationSettings(ActivationSettings.HARD).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.OBSIDIAN_CIRCULAR_PAVING).markStoneCuttable().addExtraTag(BlockusBlockTags.OBSIDIAN).setActivationSettings(ActivationSettings.HARD));
    create(BlockusBlocks.GLOWING_OBSIDIAN).addExtraTag(BlockusBlockTags.OBSIDIAN).setActivationSettings(ActivationSettings.HARD).setFenceSettings(FenceSettings.STONE).build();

    create(BlockusBlocks.POLISHED_NETHERRACK).addExtraTag(BlockusBlockTags.NETHERRACK_BLOCKS).setFenceSettings(FenceSettings.NETHERRACK).setActivationSettings(ActivationSettings.NETHERRACK).build();
    create(BlockusBlocks.NETHERRACK_BRICKS).addExtraTag(BlockusBlockTags.NETHERRACK_BLOCKS).setFenceSettings(FenceSettings.NETHERRACK).setActivationSettings(ActivationSettings.NETHERRACK).without(BlockShape.BUTTON).build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.NETHERRACK_CIRCULAR_PAVING).markStoneCuttable().addExtraTag(BlockusBlockTags.NETHERRACK_BLOCKS).setFenceSettings(FenceSettings.NETHERRACK).setActivationSettings(ActivationSettings.NETHERRACK));

    create(BlockusBlocks.QUARTZ_TILES).addExtraTag(BlockusBlockTags.QUARTZ_BLOCKS).setFenceSettings(FenceSettings.QUARTZ).without(BlockShape.BUTTON).build();
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.QUARTZ_CIRCULAR_PAVING).markStoneCuttable().addExtraTag(BlockusBlockTags.QUARTZ_BLOCKS).setFenceSettings(FenceSettings.QUARTZ));

    create(BlockusBlocks.MAGMA_BRICKS).addExtraTag(BlockusBlockTags.MAGMA_BRICKS).setFenceSettings(FenceSettings.STONE).build();
    create(BlockusBlocks.SMALL_MAGMA_BRICKS).addExtraTag(BlockusBlockTags.MAGMA_BRICKS).setFenceSettings(FenceSettings.STONE).build();
    create(BlockusBlocks.CHISELED_MAGMA_BRICKS).addExtraTag(BlockusBlockTags.MAGMA_BRICKS).without(BlockShape.FENCE, BlockShape.FENCE_GATE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();

    create(BlockusBlocks.BLAZE_BRICKS).setStoneFenceSettings(Items.BLAZE_ROD).addExtraTag(BlockusBlockTags.BLAZE_BRICKS).build();
    create(BlockusBlocks.BLAZE_LANTERN).setStoneFenceSettings(Items.BLAZE_ROD).build();

    create(BlockusBlocks.POLISHED_NETHER_BRICKS).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setStoneFenceSettings(Items.NETHER_BRICK).build();
    create(BlockusBlocks.POLISHED_RED_NETHER_BRICKS).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setStoneFenceSettings(Items.NETHER_BRICK).build();
    create(BlockusBlocks.HERRINGBONE_NETHER_BRICKS).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setStoneFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.HERRINGBONE_RED_NETHER_BRICKS).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setStoneFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.NETHER_TILES).setStoneFenceSettings(Items.NETHER_BRICK).addExtraTag(BlockTags.PICKAXE_MINEABLE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.CHARRED_NETHER_BRICKS).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setStoneFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.POLISHED_CHARRED_NETHER_BRICKS).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setStoneFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.TEAL_NETHER_BRICKS).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setStoneFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.POLISHED_TEAL_NETHER_BRICKS).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setStoneFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.HERRINGBONE_TEAL_NETHER_BRICKS).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setStoneFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build();

    create(BlockusBlocks.CHISELED_PRISMARINE).addExtraTag(BlockusBlockTags.PRISMARINE_BLOCKS).setStoneFenceSettings(Items.PRISMARINE_SHARD).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build();
    buildCircularPavingBlock(create(BlockusBlocks.PRISMARINE_CIRCULAR_PAVING).addExtraTag(BlockusBlockTags.PRISMARINE_BLOCKS));
    create(BlockusBlocks.CHISELED_DARK_PRISMARINE).addExtraTag(BlockusBlockTags.PRISMARINE_BLOCKS).setStoneFenceSettings(Items.PRISMARINE_SHARD).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build();

    create(BlockusBlocks.PRISMARINE_TILES).addExtraTag(BlockusBlockTags.PRISMARINE_BLOCKS).setStoneFenceSettings(Items.PRISMARINE_SHARD).without(BlockShape.BUTTON).build();

    create(BlockusBlocks.LARGE_BRICKS).addExtraTag(BlockusBlockTags.BRICKS_BLOCKS).setStoneFenceSettings(Items.BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.HERRINGBONE_BRICKS).addExtraTag(BlockusBlockTags.BRICKS_BLOCKS).setStoneFenceSettings(Items.BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SOAKED_BRICKS).addExtraTag(BlockusBlockTags.BRICKS_BLOCKS).setStoneFenceSettings(Items.BRICK).build();
    create(BlockusBlocks.HERRINGBONE_SOAKED_BRICKS).addExtraTag(BlockusBlockTags.BRICKS_BLOCKS).setStoneFenceSettings(Items.BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.CHARRED_BRICKS).addExtraTag(BlockusBlockTags.BRICKS_BLOCKS).setStoneFenceSettings(Items.BRICK).build();
    create(BlockusBlocks.HERRINGBONE_CHARRED_BRICKS).addExtraTag(BlockusBlockTags.BRICKS_BLOCKS).setStoneFenceSettings(Items.BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SANDY_BRICKS).setStoneFenceSettings(Items.BRICK).build();
    create(BlockusBlocks.HERRINGBONE_SANDY_BRICKS).setStoneFenceSettings(Items.BRICK).without(BlockShape.BUTTON).build();

    create(BlockusBlocks.ROUGH_SANDSTONE).addExtraTag(BlockusBlockTags.SANDSTONE).setStoneFenceSettings(Items.SAND).build();
    create(BlockusBlocks.SANDSTONE_BRICKS).addExtraTag(BlockusBlockTags.SANDSTONE).setStoneFenceSettings(Items.SAND).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SMALL_SANDSTONE_BRICKS).addExtraTag(BlockusBlockTags.SANDSTONE).setStoneFenceSettings(Items.SAND).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.ROUGH_RED_SANDSTONE).addExtraTag(BlockusBlockTags.RED_SANDSTONE).setStoneFenceSettings(Items.RED_SAND).build();
    create(BlockusBlocks.RED_SANDSTONE_BRICKS).addExtraTag(BlockusBlockTags.RED_SANDSTONE).setStoneFenceSettings(Items.RED_SAND).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SMALL_RED_SANDSTONE_BRICKS).addExtraTag(BlockusBlockTags.RED_SANDSTONE).setStoneFenceSettings(Items.RED_SAND).without(BlockShape.BUTTON).build();
    FACTORY.createConstructionOnly(BlockusBlocks.SOUL_SANDSTONE.block).markStoneCuttable().addExtraTag(BlockusBlockTags.SOUL_SANDSTONE).setStoneFenceSettings(Items.SOUL_SAND).build();
    create(BlockusBlocks.ROUGH_SOUL_SANDSTONE).addExtraTag(BlockusBlockTags.SOUL_SANDSTONE).setStoneFenceSettings(Items.SOUL_SAND).build();
    create(BlockusBlocks.SOUL_SANDSTONE_BRICKS).addExtraTag(BlockusBlockTags.SOUL_SANDSTONE).setStoneFenceSettings(Items.SOUL_SAND).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SMALL_SOUL_SANDSTONE_BRICKS).addExtraTag(BlockusBlockTags.SOUL_SANDSTONE).setStoneFenceSettings(Items.SOUL_SAND).without(BlockShape.BUTTON).build();

    create(BlockusBlocks.SMOOTH_SOUL_SANDSTONE).setStoneFenceSettings(Items.SOUL_SAND).build();
    create(BlockusBlocks.CUT_SOUL_SANDSTONE).setStoneFenceSettings(Items.SOUL_SAND).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.CHISELED_SOUL_SANDSTONE).setStoneFenceSettings(Items.SOUL_SAND).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build();

    create(BlockusBlocks.RAINBOW_BLOCK).addExtraTag(BlockusBlockTags.RAINBOW_BLOCKS).setStoneFenceSettings(BlockusItems.RAINBOW_PETAL).setPillar().build();
    create(BlockusBlocks.RAINBOW_BRICKS).addExtraTag(BlockusBlockTags.RAINBOW_BLOCKS).setStoneFenceSettings(BlockusItems.RAINBOW_PETAL).build();
    create(BlockusBlocks.RAINBOW_GLOWSTONE).setFenceSettings(new FenceSettings(BlockusItems.RAINBOW_PETAL, ExtShapeBlockTypes.GLOWSTONE_WOOD_TYPE)).setActivationSettings(ActivationSettings.stone(ExtShapeBlockTypes.GLOWSTONE_BLOCK_SET_TYPE)).build();
    create(BlockusBlocks.HONEYCOMB_BRICKS).addExtraTag(BlockusBlockTags.HONEYCOMB_BLOCKS).setStoneFenceSettings(Items.HONEYCOMB).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.PURPUR_BRICKS).addExtraTag(BlockusBlockTags.PURPUR_BLOCKS).setStoneFenceSettings(Items.PURPUR_BLOCK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SMALL_PURPUR_BRICKS).addExtraTag(BlockusBlockTags.PURPUR_BLOCKS).setStoneFenceSettings(Items.PURPUR_BLOCK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.POLISHED_PURPUR).addExtraTag(BlockusBlockTags.PURPUR_BLOCKS).setStoneFenceSettings(Items.PURPUR_BLOCK).build();
    create(BlockusBlocks.CHISELED_PURPUR).addExtraTag(BlockusBlockTags.PURPUR_BLOCKS).setStoneFenceSettings(Items.PURPUR_BLOCK).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build();
    create(BlockusBlocks.PURPUR_SQUARES).addExtraTag(BlockusBlockTags.PURPUR_BLOCKS).setStoneFenceSettings(Items.PURPUR_BLOCK).without(BlockShape.BUTTON).build();

    create(BlockusBlocks.PHANTOM_PURPUR_BRICKS).setStoneFenceSettings(Items.PHANTOM_MEMBRANE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SMALL_PHANTOM_PURPUR_BRICKS).setStoneFenceSettings(Items.PHANTOM_MEMBRANE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.PHANTOM_PURPUR_BLOCK).setStoneFenceSettings(Items.PHANTOM_MEMBRANE).build();
    create(BlockusBlocks.POLISHED_PHANTOM_PURPUR).setStoneFenceSettings(Items.PHANTOM_MEMBRANE).build();
    create(BlockusBlocks.CHISELED_PHANTOM_PURPUR).setStoneFenceSettings(Items.PHANTOM_MEMBRANE).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build();
    create(BlockusBlocks.PHANTOM_PURPUR_SQUARES).setStoneFenceSettings(Items.PHANTOM_MEMBRANE).without(BlockShape.BUTTON).build();

    create(BlockusBlocks.POLISHED_END_STONE).addExtraTag(BlockusBlockTags.END_STONE_BLOCKS).setStoneFenceSettings(Items.END_STONE_BRICKS).build();
    create(BlockusBlocks.SMALL_END_STONE_BRICKS).addExtraTag(BlockusBlockTags.END_STONE_BLOCKS).setStoneFenceSettings(Items.END_STONE_BRICKS).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.CHISELED_END_STONE_BRICKS).addExtraTag(BlockusBlockTags.END_STONE_BLOCKS).setStoneFenceSettings(Items.END_STONE_BRICKS).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build();
    create(BlockusBlocks.HERRINGBONE_END_STONE_BRICKS).addExtraTag(BlockusBlockTags.END_STONE_BLOCKS).setStoneFenceSettings(Items.END_STONE_BRICKS).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.END_TILES).setStoneFenceSettings(Items.END_STONE_BRICKS).addExtraTag(BlockTags.PICKAXE_MINEABLE).without(BlockShape.BUTTON).build();

    markStoneCuttableWhenCreating = false;

    final BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> logFlammable = (blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 5, 5);
    FACTORY.createConstructionOnly(BlockusBlocks.WHITE_OAK_LOG).setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_LOG_TAG).addExtraTag(ofBlockAndItem(ExtShapeTags.LOG_BLOCKS)).setPillar().addPostBuildConsumer(logFlammable).build();
    FACTORY.createConstructionOnly(BlockusBlocks.STRIPPED_WHITE_OAK_LOG).setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_LOG_TAG).addExtraTag(ofBlockAndItem(ExtShapeTags.LOG_BLOCKS)).setPillar().addPostBuildConsumer(logFlammable).build();
    FACTORY.createAllShapes(BlockusBlocks.WHITE_OAK_WOOD).setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_LOG_TAG).setActivationSettings(ActivationSettings.wood(BlockSetType.OAK))
        .setPillar().addPostBuildConsumer(logFlammable).build();
    FACTORY.createAllShapes(BlockusBlocks.STRIPPED_WHITE_OAK_WOOD).setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_LOG_TAG).setActivationSettings(ActivationSettings.wood(BlockSetType.OAK)).setPillar().addPostBuildConsumer(logFlammable).build();

    final BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> plankFlammable = (blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 5, 20);
    final BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> plankFuel = (blockShape, blockBuilder) -> FuelRegistry.INSTANCE.add(blockBuilder.instance, (int) (blockShape.logicalCompleteness * 300));
    create(BlockusBlocks.WHITE_OAK).setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_WOODEN_TAG).setActivationSettings(ActivationSettings.wood(BlockSetType.OAK)).addPostBuildConsumer(plankFlammable).addPostBuildConsumer(plankFuel).build();
    create(BlockusBlocks.RAW_BAMBOO).setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_WOODEN_TAG).setActivationSettings(ActivationSettings.BAMBOO).setFenceSettings(new FenceSettings(Items.BAMBOO, WoodType.BAMBOO)).addPostBuildConsumer(plankFlammable).addPostBuildConsumer(plankFuel).build();
    create(BlockusBlocks.CHARRED).setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_WOODEN_TAG).addExtraTag(ItemTags.NON_FLAMMABLE_WOOD).setActivationSettings(ActivationSettings.wood(BlockSetType.OAK)).build();

    for (var bssTypes : BlockusBlockCollections.WOODEN_MOSAICS) {
      final boolean isNonFlammable = BlockusBlocks.WARPED_MOSAIC == bssTypes || BlockusBlocks.CRIMSON_MOSAIC == bssTypes || BlockusBlocks.CHARRED_MOSAIC == bssTypes;
      final BlocksBuilder builder = FACTORY.createConstructionOnly(bssTypes.block).without(BlockShape.STAIRS, BlockShape.SLAB).with(BlockShape.WALL).setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_WOODEN_TAG).setRecipeGroup(blockShape -> "wooden_mosaic_" + blockShape.asString()).addExtraTag(BlockTags.AXE_MINEABLE);
      if (isNonFlammable) {
        builder.addExtraTag(ItemTags.NON_FLAMMABLE_WOOD).build();
      } else {
        builder.addPostBuildConsumer(plankFlammable).build();
      }
    }
    for (var bssTypes : BlockusBlockCollections.MOSSY_PLANKS) {
      final boolean isNonFlammable = BlockusBlocks.MOSSY_WARPED_PLANKS == bssTypes || BlockusBlocks.MOSSY_CRIMSON_PLANKS == bssTypes || BlockusBlocks.MOSSY_CHARRED_PLANKS == bssTypes;
      if (isNonFlammable) {
        FACTORY.createConstructionOnly(bssTypes.block)
            .without(BlockShape.STAIRS, BlockShape.SLAB)
            .with(BlockShape.WALL)
            .setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_WOODEN_TAG)
            .addExtraTag(ItemTags.NON_FLAMMABLE_WOOD)
            .addExtraTag(BlockusBlockTags.ALL_MOSSY_PLANKS)
            .setRecipeGroup(blockShape -> "mossy_wooden_" + blockShape.asString())
            .build();
      } else {
        FACTORY.createConstructionOnly(bssTypes.block)
            .without(BlockShape.STAIRS, BlockShape.SLAB)
            .with(BlockShape.WALL)
            .setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_WOODEN_TAG)
            .addPostBuildConsumer(plankFlammable)
            .addExtraTag(BlockusBlockTags.ALL_MOSSY_PLANKS)
            .setRecipeGroup(blockShape -> "mossy_wooden_" + blockShape.asString())
            .build();
      }
    }
    for (var block : BlockusBlockCollections.HERRINGBONE_PLANKS) {
      final boolean isNonFlammable = BlockusBlocks.HERRINGBONE_WARPED_PLANKS == block || BlockusBlocks.HERRINGBONE_CRIMSON_PLANKS == block || BlockusBlocks.HERRINGBONE_CHARRED_PLANKS == block;
      if (isNonFlammable) {
        FACTORY.createConstructionOnly(block).with(BlockShape.WALL).setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_WOODEN_TAG).addExtraTag(ItemTags.NON_FLAMMABLE_WOOD).setRecipeGroup(blockShape -> "herringbone_wooden_" + blockShape.asString()).build();
      } else {
        FACTORY.createConstructionOnly(block).with(BlockShape.WALL).setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_WOODEN_TAG).addPostBuildConsumer(plankFlammable).setRecipeGroup(blockShape -> "herringbone_wooden_" + blockShape.asString()).build();
      }
    }
    for (var block : BlockusBlockCollections.SMALL_LOGS) {
      if (block == BlockusBlocks.CRIMSON_SMALL_STEMS || block == BlockusBlocks.WARPED_SMALL_STEMS) {
        FACTORY.createConstructionOnly(block).setPillar().setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_LOG_TAG).addExtraTag(ItemTags.NON_FLAMMABLE_WOOD).setRecipeGroup(blockShape -> "small_logs_" + blockShape.asString()).build();
      } else {
        FACTORY.createConstructionOnly(block).setPillar().setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_LOG_TAG).addPostBuildConsumer(logFlammable).setRecipeGroup(blockShape -> "small_logs_" + blockShape.asString()).build();
      }
    }

    markStoneCuttableWhenCreating = true;
    create(BlockusBlocks.CHOCOLATE_BLOCK).setFenceSettings(FenceSettings.STONE).setActivationSettings(ActivationSettings.STONE).addExtraTag(BlockusBlockTags.CHOCOLATE_BLOCKS).build();
    create(BlockusBlocks.CHOCOLATE_BRICKS).setFenceSettings(FenceSettings.STONE).setActivationSettings(ActivationSettings.STONE).addExtraTag(BlockusBlockTags.CHOCOLATE_BLOCKS).build();
    create(BlockusBlocks.CHOCOLATE_SQUARES).setFenceSettings(FenceSettings.STONE).setActivationSettings(ActivationSettings.STONE).addExtraTag(BlockusBlockTags.CHOCOLATE_BLOCKS).build();

    // 注意：墙等形状均不属于 beacon_base_block
    create(BlockusBlocks.IRON_BRICKS).withoutRedstone().without(BlockShape.FENCE, BlockShape.FENCE_GATE).addExtraTag(BlockTags.PICKAXE_MINEABLE).addExtraTag(BlockTags.BEACON_BASE_BLOCKS).addExtraTag(BlockTags.NEEDS_STONE_TOOL).build();
    create(BlockusBlocks.GOLD_BRICKS).withoutRedstone().without(BlockShape.FENCE, BlockShape.FENCE_GATE).addExtraTag(BlockTags.PICKAXE_MINEABLE).addExtraTag(BlockTags.BEACON_BASE_BLOCKS).addExtraTag(BlockTags.NEEDS_IRON_TOOL).build();
    create(BlockusBlocks.LAPIS_BRICKS).withoutRedstone().without(BlockShape.FENCE, BlockShape.FENCE_GATE).addExtraTag(BlockTags.PICKAXE_MINEABLE).addExtraTag(BlockTags.NEEDS_IRON_TOOL).build();
    create(BlockusBlocks.REDSTONE_BRICKS).addExtraTag(BlockTags.PICKAXE_MINEABLE).withExtension(BlockExtension.builder().setEmitsRedstonePower((state, original) -> true).setWeakRedstonePower((state, world, pos, direction, original) -> 15).build()).withoutRedstone().without(BlockShape.FENCE, BlockShape.FENCE_GATE).build();
    create(BlockusBlocks.EMERALD_BRICKS).addExtraTag(BlockTags.PICKAXE_MINEABLE).withoutRedstone().without(BlockShape.FENCE, BlockShape.FENCE_GATE).addExtraTag(BlockTags.BEACON_BASE_BLOCKS).addExtraTag(BlockTags.NEEDS_IRON_TOOL).build();
    create(BlockusBlocks.DIAMOND_BRICKS).addExtraTag(BlockTags.PICKAXE_MINEABLE).withoutRedstone().without(BlockShape.FENCE, BlockShape.FENCE_GATE).addExtraTag(BlockTags.BEACON_BASE_BLOCKS).addExtraTag(BlockTags.NEEDS_IRON_TOOL).build();
    create(BlockusBlocks.NETHERITE_BRICKS).addExtraTag(BlockTags.PICKAXE_MINEABLE).withoutRedstone().without(BlockShape.FENCE, BlockShape.FENCE_GATE).addExtraTag(BlockTags.BEACON_BASE_BLOCKS).addExtraTag(BlockTags.NEEDS_DIAMOND_TOOL).addPostBuildConsumer((blockShape, abstractBlockBuilder) -> abstractBlockBuilder.itemSettings.fireproof()).build();

    markStoneCuttableWhenCreating = false;

    final Function<BlockShape, @Nullable TagKey<? extends ItemConvertible>> addWallToUnmineableTag = blockShape -> blockShape == BlockShape.WALL ? ExtShapeTags.PICKAXE_UNMINEABLE : null;

    create(BlockusBlocks.CHORUS_BLOCK).addExtraTag(BlockTags.HOE_MINEABLE).addExtraTag(addWallToUnmineableTag).setStoneFenceSettings(Items.CHORUS_PLANT).setPillar().build();

    for (var bsswTypes : BlockusBlockCollections.STAINED_STONE_BRICKS) {
      create(bsswTypes).markStoneCuttable().addExtraTag(BlockusBlockTags.STAINED_STONE_BRICKS).setRecipeGroup(blockShape -> "stained_stone_brick_" + blockShape.asString()).setActivationSettings(ActivationSettings.STONE).setFenceSettings(FenceSettings.STONE).without(BlockShape.BUTTON).build();
    }
    for (var concreteTypes : BlockusBlockCollections.CONCRETE_BRICKS) {
      FACTORY.createConstructionOnly(concreteTypes.block).markStoneCuttable().without(BlockShape.BUTTON, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL).addExtraTag(BlockusBlockTags.CONCRETE_BLOCKS).setRecipeGroup(blockShape1 -> "concrete_brick_" + blockShape1.asString()).setActivationSettings(ActivationSettings.STONE).setFenceSettings(FenceSettings.STONE).build();
      FACTORY.createConstructionOnly(concreteTypes.chiseled).markStoneCuttable().without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).addExtraTag(BlockusBlockTags.CONCRETE_BLOCKS).setRecipeGroup(blockShape -> "chiseled_concrete_brick_" + blockShape.asString()).setActivationSettings(ActivationSettings.STONE).setFenceSettings(FenceSettings.STONE).build();
    }

    create(BlockusBlocks.SHINGLES).markStoneCuttable().addExtraTag(BlockusBlockTags.SHINGLES).setActivationSettings(ActivationSettings.STONE).setFenceSettings(FenceSettings.STONE).build();
    for (var bssTypes : BlockusBlockCollections.TINTED_SHINGLES) {
      create(bssTypes).markStoneCuttable().addExtraTag(BlockusBlockTags.SHINGLES).setActivationSettings(ActivationSettings.STONE).setFenceSettings(FenceSettings.STONE).setRecipeGroup(blockShape -> "shingles_" + blockShape.asString()).build();
    }

    for (var woolTypes : Iterables.concat(BlockusBlockCollections.PATTERNED_WOOLS, BlockusBlockCollections.GINGHAM_WOOLS)) {
      FACTORY.createAllShapes(woolTypes.block)
          .addExtraTag(FabricMineableTags.SHEARS_MINEABLE)
          .addExtraTag(BlockusBlockTags.ALL_PATTERNED_WOOLS)
          .addExtraTag(addWallToUnmineableTag)
          .without(BlockShape.STAIRS, BlockShape.SLAB, BlockShape.BUTTON)
          .addPreBuildConsumer((blockShape, blockBuilder) -> {
            if (blockShape == BlockShape.PRESSURE_PLATE) {
              ((PressurePlateBuilder) blockBuilder).setInstanceSupplier(x -> new WoolPressurePlate(x.baseBlock, x.blockSettings, ((PressurePlateBuilder) x).activationSettings, woolTypes.carpet));
            }
          })
          .addPostBuildConsumer((blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 30, 60)).addPostBuildConsumer((blockShape, blockBuilder) -> FuelRegistry.INSTANCE.add(blockBuilder.instance, (int) (100 * blockShape.logicalCompleteness)))
          .setRecipeGroup(blockShape -> "patterned_wool_" + blockShape.asString())
          .setActivationSettings(ActivationSettings.WOOL)
          .setFenceSettings(FenceSettings.WOOL)
          .build();
    }
    for (var block : BlockusBlockCollections.GLAZED_TERRACOTTA_PILLARS) {
      FACTORY.createConstructionOnly(block).markStoneCuttable().with(BlockShape.WALL).addExtraTag(BlockusBlockTags.GLAZED_TERRACOTTA_PILLARS).setRecipeGroup(blockShape -> "glazed_terracotta_pillar_" + blockShape.asString()).setPillar().build();
    }

    create(BlockusBlocks.THATCH).addExtraTag(BlockusBlockTags.THATCH).addExtraTag(BlockTags.HOE_MINEABLE).addExtraTag(addWallToUnmineableTag).addPostBuildConsumer((blockShape4, blockBuilder3) -> {
      FlammableBlockRegistry.getDefaultInstance().add(blockBuilder3.instance, 60, 20);
      CompostingChanceRegistry.INSTANCE.add(blockBuilder3.instance, blockShape4.logicalCompleteness * 0.75f);
    }).setActivationSettings(ActivationSettings.soft(ExtShapeBlockusBlockTypes.GRASS_BLOCK_SET_TYPE)).setFenceSettings(new FenceSettings(Items.WHEAT, ExtShapeBlockusBlockTypes.GRASS_BLOCK_WOOD_TYPE)).build();
    create(BlockusBlocks.PAPER_BLOCK).addExtraTag(addWallToUnmineableTag).addPostBuildConsumer((blockShape3, blockBuilder2) -> {
      FlammableBlockRegistry.getDefaultInstance().add(blockBuilder2.instance, 30, 60);
      FuelRegistry.INSTANCE.add(blockBuilder2.instance, (int) (blockShape3.logicalCompleteness * 100));
    }).setActivationSettings(ActivationSettings.soft(ExtShapeBlockusBlockTypes.GRASS_BLOCK_SET_TYPE)).setFenceSettings(new FenceSettings(Items.PAPER, ExtShapeBlockusBlockTypes.GRASS_BLOCK_WOOD_TYPE)).build();
    create(BlockusBlocks.BURNT_PAPER_BLOCK).addExtraTag(addWallToUnmineableTag).addPostBuildConsumer((blockShape2, blockBuilder1) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder1.instance, 5, 60)).setActivationSettings(ActivationSettings.soft(ExtShapeBlockusBlockTypes.GRASS_BLOCK_SET_TYPE)).setFenceSettings(new FenceSettings(Items.GUNPOWDER, ExtShapeBlockusBlockTypes.GRASS_BLOCK_WOOD_TYPE)).build();

    create(BlockusBlocks.IRON_PLATING).markStoneCuttable().addExtraTag(BlockusBlockTags.PLATINGS).without(BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.BUTTON).setActivationSettings(ActivationSettings.IRON).build();
    create(BlockusBlocks.GOLD_PLATING).markStoneCuttable().addExtraTag(BlockusBlockTags.PLATINGS).addExtraTag(BlockTags.GUARDED_BY_PIGLINS).without(BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.BUTTON).setActivationSettings(ActivationSettings.GOLD).build();

    create(BlockusBlocks.CHARCOAL_BLOCK).markStoneCuttable().setStoneFenceSettings(Items.CHARCOAL).addExtraTag(BlockTags.INFINIBURN_OVERWORLD).addExtraTag(BlockTags.PICKAXE_MINEABLE).addPostBuildConsumer((blockShape1, blockBuilder) -> FuelRegistry.INSTANCE.add(blockBuilder.instance, (int) (blockShape1.logicalCompleteness * 16000))).build();
    create(BlockusBlocks.SUGAR_BLOCK).setStoneFenceSettings(Items.SUGAR).addExtraTag(BlockTags.SHOVEL_MINEABLE).addExtraTag(addWallToUnmineableTag).build();
    create(BlockusBlocks.ENDER_BLOCK).markStoneCuttable().setStoneFenceSettings(Items.ENDER_PEARL).addExtraTag(BlockTags.PICKAXE_MINEABLE).addExtraTag(BlockTags.NEEDS_STONE_TOOL).build();

    create(BlockusBlocks.ROTTEN_FLESH_BLOCK).addExtraTag(BlockTags.HOE_MINEABLE).addExtraTag(addWallToUnmineableTag).setActivationSettings(ActivationSettings.soft(ExtShapeBlockTypes.SLIME_BLOCK_SET_TYPE)).setFenceSettings(new FenceSettings(Items.ROTTEN_FLESH, ExtShapeBlockTypes.SLIME_WOOD_TYPE)).build();
    create(BlockusBlocks.MEMBRANE_BLOCK).setFenceSettings(new FenceSettings(Items.PHANTOM_MEMBRANE, ExtShapeBlockTypes.SLIME_WOOD_TYPE)).setActivationSettings(ActivationSettings.soft(ExtShapeBlockTypes.SLIME_BLOCK_SET_TYPE)).addExtraTag(addWallToUnmineableTag).build();
    FACTORY.createAllShapes(BlockusBlocks.NETHER_STAR_BLOCK).markStoneCuttable().withoutRedstone().setStoneFenceSettings(Items.NETHER_STAR).addExtraTag(BlockTags.PICKAXE_MINEABLE).addExtraTag(BlockTags.NEEDS_IRON_TOOL).addExtraTag(BlockTags.DRAGON_IMMUNE).withExtension(BlockExtension.builder().setSteppedOnCallback((world, pos, state, entity) -> {
      if (entity.getType() == EntityType.PLAYER) {
        ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 1, 4, true, false, false));
        ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 100, 3, true, false, true));
        ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 1200, 4, true, false, true));
      }
    }).build()).addPreBuildConsumer((blockShape, builder) -> {
      builder.setItemSettings(new FabricItemSettings().rarity(Rarity.RARE));
      builder.setItemInstanceSupplier(builder0 -> new ExtShapeBlockItem(builder0.instance, builder0.itemSettings.rarity(Rarity.UNCOMMON)) {
        @Override
        public boolean hasGlint(ItemStack stack) {
          return true;
        }

        @Override
        public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
          super.appendTooltip(stack, world, tooltip, context);
          this.getBlock().appendTooltip(stack, world, tooltip, context);
          tooltip.add(ScreenTexts.EMPTY);
          Text e = Blockus.STEPPED_ON_TEXT;
          tooltip.add(e);
          tooltip.add(ScreenTexts.space().append(StatusEffects.REGENERATION.getName()).append(" IV").formatted(Formatting.BLUE));
          tooltip.add(ScreenTexts.space().append(StatusEffects.ABSORPTION.getName()).append(" IV").formatted(Formatting.BLUE).append(" - 00:45"));
          tooltip.add(ScreenTexts.space().append(StatusEffects.STRENGTH.getName()).append(" III").formatted(Formatting.BLUE).append(" - 00:04"));
        }
      });
    }).build();

    create(BlockusBlocks.STARS_BLOCK).markStoneCuttable().setStoneFenceSettings(Items.COAL).addExtraTag(BlockTags.PICKAXE_MINEABLE).build();
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
