package pers.solid.extshape.blockus;

import com.brand.blockus.Blockus;
import com.brand.blockus.content.BlockusBlocks;
import com.brand.blockus.content.BlockusItems;
import com.brand.blockus.content.types.BSSTypes;
import com.brand.blockus.content.types.BSSWTypes;
import com.brand.blockus.content.types.WoodTypesB;
import com.brand.blockus.content.types.WoodTypesNB;
import com.brand.blockus.tag.BlockusBlockTags;
import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.ExtShapeBlockItem;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapeButtonBlock.ButtonType;
import pers.solid.extshape.builder.AbstractBlockBuilder;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.builder.BlocksBuilder;
import pers.solid.extshape.builder.BlocksBuilderFactory;
import pers.solid.extshape.tag.ExtShapeTags;

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
    blocksBuilderFactory.itemGroupForShape = Maps.transformValues(BlocksBuilderFactory.DEFAULT_GROUP_FOR_SHAPE, Functions.forMap(ImmutableMap.of(ItemGroup.BUILDING_BLOCKS, Blockus.BLOCKUS_BUILDING_BLOCKS, ItemGroup.DECORATIONS, Blockus.BLOCKUS_DECORATIONS, ItemGroup.REDSTONE, Blockus.BLOCKUS_REDSTONE), null));
  });

  private ExtShapeBlockusBlocks() {
  }

  private static BlocksBuilder create(Block baseBlock) {
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

  private static BlocksBuilder create(WoodTypesB woodTypesF) {
    return FACTORY.createAllShapes(woodTypesF.planks).without(BlockShape.STAIRS, BlockShape.SLAB, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.PRESSURE_PLATE, BlockShape.BUTTON);
  }

  private static BlocksBuilder create(WoodTypesNB woodTypesFp) {
    return FACTORY.createAllShapes(woodTypesFp.planks).without(BlockShape.STAIRS, BlockShape.SLAB, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.PRESSURE_PLATE, BlockShape.BUTTON);
  }

  static {
    BlockusBlockBiMaps.importFromBlockus();
    buildStoneBlocksWithoutButton(create(BlockusBlocks.STONE_TILES).addExtraTag(BlockusBlockTags.STONE_BLOCKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.HERRINGBONE_STONE_BRICKS).addExtraTag(BlockusBlockTags.STONE_BLOCKS));
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.STONE_CIRCULAR_PAVING).addExtraTag(BlockusBlockTags.STONE_BLOCKS));

    buildStoneBlocksWithButton(create(BlockusBlocks.ANDESITE_BRICKS).addExtraTag(BlockusBlockTags.ANDESITE_BLOCKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.CHISELED_ANDESITE_BRICKS).addExtraTag(BlockusBlockTags.ANDESITE_BLOCKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.HERRINGBONE_ANDESITE_BRICKS).addExtraTag(BlockusBlockTags.ANDESITE_BLOCKS));
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.ANDESITE_CIRCULAR_PAVING).addExtraTag(BlockusBlockTags.ANDESITE_BLOCKS));

    buildStoneBlocksWithoutButton(create(BlockusBlocks.DIORITE_BRICKS).addExtraTag(BlockusBlockTags.DIORITE_BLOCKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.CHISELED_DIORITE_BRICKS).addExtraTag(BlockusBlockTags.DIORITE_BLOCKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.HERRINGBONE_DIORITE_BRICKS).addExtraTag(BlockusBlockTags.DIORITE_BLOCKS));
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.DIORITE_CIRCULAR_PAVING).addExtraTag(BlockusBlockTags.DIORITE_BLOCKS));

    buildStoneBlocksWithoutButton(create(BlockusBlocks.GRANITE_BRICKS).addExtraTag(BlockusBlockTags.GRANITE_BLOCKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.CHISELED_GRANITE_BRICKS).addExtraTag(BlockusBlockTags.GRANITE_BLOCKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.HERRINGBONE_GRANITE_BRICKS).addExtraTag(BlockusBlockTags.GRANITE_BLOCKS));
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.GRANITE_CIRCULAR_PAVING).addExtraTag(BlockusBlockTags.GRANITE_BLOCKS));

    create(BlockusBlocks.POLISHED_DRIPSTONE).addExtraTag(BlockusBlockTags.DRIPSTONE_BLOCKS)
        .setFenceCraftingIngredient(Items.POINTED_DRIPSTONE).build();
    create(BlockusBlocks.DRIPSTONE_BRICKS).addExtraTag(BlockusBlockTags.DRIPSTONE_BLOCKS)
        .without(BlockShape.BUTTON)
        .setFenceCraftingIngredient(Items.POINTED_DRIPSTONE).build();
    create(BlockusBlocks.CHISELED_DRIPSTONE).addExtraTag(BlockusBlockTags.DRIPSTONE_BLOCKS)
        .without(BlockShape.BUTTON)
        .setFenceCraftingIngredient(Items.POINTED_DRIPSTONE).build();

    buildStoneBlocksWithButton(create(BlockusBlocks.POLISHED_TUFF).addExtraTag(BlockusBlockTags.TUFF_BLOCKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.TUFF_BRICKS).addExtraTag(BlockusBlockTags.TUFF_BLOCKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.CHISELED_TUFF).addExtraTag(BlockusBlockTags.TUFF_BLOCKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.HERRINGBONE_TUFF_BRICKS).addExtraTag(BlockusBlockTags.TUFF_BLOCKS));
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.TUFF_CIRCULAR_PAVING).addExtraTag(BlockusBlockTags.TUFF_BLOCKS));

    buildStoneBlocksWithoutButton(create(BlockusBlocks.MOSSY_DEEPSLATE_BRICKS).addExtraTag(BlockusBlockTags.DEEPSLATE_BLOCKS)
    );
    buildStoneBlocksWithoutButton(create(BlockusBlocks.HERRINGBONE_DEEPSLATE_BRICKS).addExtraTag(BlockusBlockTags.DEEPSLATE_BLOCKS)
    );
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.DEEPSLATE_CIRCULAR_PAVING).addExtraTag(BlockusBlockTags.DEEPSLATE_BLOCKS));

    create(BlockusBlocks.POLISHED_AMETHYST).withoutRedstone()
        .addExtraTag(BlockusBlockTags.AMETHYST_BLOCKS)
        .withExtension(BlockExtension.AMETHYST)
        .setFenceCraftingIngredient(Items.AMETHYST_SHARD)
        .build();
    FACTORY.createAllShapes(BlockusBlocks.AMETHYST_BRICKS).withoutRedstone()
        .addExtraTag(BlockusBlockTags.AMETHYST_BLOCKS)
        .withExtension(BlockExtension.AMETHYST)
        .setFenceCraftingIngredient(Items.AMETHYST_SHARD)
        .without(BlockShape.BUTTON)
        .build();
    FACTORY.createAllShapes(BlockusBlocks.CHISELED_AMETHYST).withoutRedstone()
        .addExtraTag(BlockusBlockTags.AMETHYST_BLOCKS)
        .withExtension(BlockExtension.AMETHYST)
        .setFenceCraftingIngredient(Items.AMETHYST_SHARD)
        .build();

    buildStoneBlocksWithoutButton(create(BlockusBlocks.POLISHED_BLACKSTONE_TILES).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.CRIMSON_WARTY_BLACKSTONE_BRICKS).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.WARPED_WARTY_BLACKSTONE_BRICKS).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.HERRINGBONE_POLISHED_BLACKSTONE_BRICKS).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS));
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.POLISHED_BLACKSTONE_CIRCULAR_PAVING)
        .addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS));

    buildStoneBlocksWithButton(create(BlockusBlocks.ROUGH_BASALT).addExtraTag(BlockusBlockTags.BASALT_BLOCKS)
    );
    buildStoneBlocksWithoutButton(create(BlockusBlocks.POLISHED_BASALT_BRICKS).addExtraTag(BlockusBlockTags.BASALT_BLOCKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.HERRINGBONE_POLISHED_BASALT_BRICKS).addExtraTag(BlockusBlockTags.BASALT_BLOCKS));

    buildStoneBlocksWithButton(create(BlockusBlocks.LIMESTONE).addExtraTag(BlockusBlockTags.LIMESTONE));
    buildStoneBlocksWithButton(create(BlockusBlocks.POLISHED_LIMESTONE).addExtraTag(BlockusBlockTags.LIMESTONE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.LIMESTONE_BRICKS).addExtraTag(BlockusBlockTags.LIMESTONE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.SMALL_LIMESTONE_BRICKS).addExtraTag(BlockusBlockTags.LIMESTONE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.LIMESTONE_TILES).addExtraTag(BlockusBlockTags.LIMESTONE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.CHISELED_LIMESTONE).addExtraTag(BlockusBlockTags.LIMESTONE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.LIMESTONE_SQUARES).addExtraTag(BlockusBlockTags.LIMESTONE));
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.LIMESTONE_CIRCULAR_PAVING).addExtraTag(BlockusBlockTags.LIMESTONE));

    buildStoneBlocksWithButton(create(BlockusBlocks.MARBLE).addExtraTag(BlockusBlockTags.MARBLE));
    buildStoneBlocksWithButton(create(BlockusBlocks.POLISHED_MARBLE).addExtraTag(BlockusBlockTags.MARBLE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.MARBLE_BRICKS).addExtraTag(BlockusBlockTags.MARBLE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.SMALL_MARBLE_BRICKS).addExtraTag(BlockusBlockTags.MARBLE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.MARBLE_TILES).addExtraTag(BlockusBlockTags.MARBLE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.CHISELED_MARBLE).addExtraTag(BlockusBlockTags.MARBLE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.MARBLE_SQUARES).addExtraTag(BlockusBlockTags.MARBLE));
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.MARBLE_CIRCULAR_PAVING).addExtraTag(BlockusBlockTags.MARBLE));

    buildStoneBlocksWithButton(create(BlockusBlocks.BLUESTONE).addExtraTag(BlockusBlockTags.BLUESTONE));
    buildStoneBlocksWithButton(create(BlockusBlocks.POLISHED_BLUESTONE).addExtraTag(BlockusBlockTags.BLUESTONE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.BLUESTONE_BRICKS).addExtraTag(BlockusBlockTags.BLUESTONE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.SMALL_BLUESTONE_BRICKS).addExtraTag(BlockusBlockTags.BLUESTONE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.BLUESTONE_TILES).addExtraTag(BlockusBlockTags.BLUESTONE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.CHISELED_BLUESTONE).addExtraTag(BlockusBlockTags.BLUESTONE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.BLUESTONE_SQUARES).addExtraTag(BlockusBlockTags.BLUESTONE));
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.BLUESTONE_CIRCULAR_PAVING).addExtraTag(BlockusBlockTags.BLUESTONE));

    buildStoneBlocksWithButton(create(BlockusBlocks.VIRIDITE).addExtraTag(BlockusBlockTags.VIRIDITE));
    buildStoneBlocksWithButton(create(BlockusBlocks.POLISHED_VIRIDITE).addExtraTag(BlockusBlockTags.VIRIDITE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.VIRIDITE_BRICKS).addExtraTag(BlockusBlockTags.VIRIDITE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.SMALL_VIRIDITE_BRICKS).addExtraTag(BlockusBlockTags.VIRIDITE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.VIRIDITE_TILES).addExtraTag(BlockusBlockTags.VIRIDITE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.CHISELED_VIRIDITE).addExtraTag(BlockusBlockTags.VIRIDITE));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.VIRIDITE_SQUARES).addExtraTag(BlockusBlockTags.VIRIDITE));
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.VIRIDITE_CIRCULAR_PAVING).addExtraTag(BlockusBlockTags.VIRIDITE));

    buildStoneBlocksWithButton(create(BlockusBlocks.LAVA_BRICKS).addExtraTag(BlockusBlockTags.LAVA_BRICKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.CHISELED_LAVA_BRICKS).addExtraTag(BlockusBlockTags.LAVA_BRICKS));
    buildStoneBlocksWithButton(create(BlockusBlocks.LAVA_POLISHED_BLACKSTONE_BRICKS).addExtraTag(BlockusBlockTags.LAVA_POLISHED_BLACKSTONE_BRICKS).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.CHISELED_LAVA_POLISHED_BLACKSTONE).addExtraTag(BlockusBlockTags.LAVA_POLISHED_BLACKSTONE_BRICKS).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS));

    // because of issues related to tint index and render layers, water bricks do not have extended shapes
    buildStoneBlocksWithButton(create(BlockusBlocks.SNOW_BRICKS).addExtraTag(BlockusBlockTags.SNOW_BRICKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.ICE_BRICKS).addExtraTag(BlockusBlockTags.ICE_BRICKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.OBSIDIAN_BRICKS).addExtraTag(BlockusBlockTags.OBSIDIAN).setButtonType(ButtonType.HARD));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.SMALL_OBSIDIAN_BRICKS).addExtraTag(BlockusBlockTags.OBSIDIAN).setButtonType(ButtonType.HARD));
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.OBSIDIAN_CIRCULAR_PAVING).addExtraTag(BlockusBlockTags.OBSIDIAN).setButtonType(ButtonType.HARD));
    buildStoneBlocksWithButton(create(BlockusBlocks.GLOWING_OBSIDIAN).addExtraTag(BlockusBlockTags.OBSIDIAN).setButtonType(ButtonType.HARD));

    buildStoneBlocksWithButton(create(BlockusBlocks.POLISHED_NETHERRACK).addExtraTag(BlockusBlockTags.NETHERRACK_BLOCKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.NETHERRACK_BRICKS).addExtraTag(BlockusBlockTags.NETHERRACK_BLOCKS));
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.NETHERRACK_CIRCULAR_PAVING).addExtraTag(BlockusBlockTags.NETHERRACK_BLOCKS));

    buildStoneBlocksWithoutButton(create(BlockusBlocks.QUARTZ_TILES).addExtraTag(BlockusBlockTags.QUARTZ_BLOCKS).setFenceCraftingIngredient(Items.QUARTZ));
    buildCircularPavingBlock(FACTORY.createEmpty(BlockusBlocks.QUARTZ_CIRCULAR_PAVING).addExtraTag(BlockusBlockTags.QUARTZ_BLOCKS).setFenceCraftingIngredient(Items.QUARTZ));

    buildStoneBlocksWithButton(create(BlockusBlocks.MAGMA_BRICKS).addExtraTag(BlockusBlockTags.MAGMA_BRICKS));
    buildStoneBlocksWithButton(create(BlockusBlocks.SMALL_MAGMA_BRICKS).addExtraTag(BlockusBlockTags.MAGMA_BRICKS));
    buildStoneBlocksWithoutButton(create(BlockusBlocks.CHISELED_MAGMA_BRICKS).addExtraTag(BlockusBlockTags.MAGMA_BRICKS).without(BlockShape.FENCE, BlockShape.FENCE_GATE));

    create(BlockusBlocks.BLAZE_BRICKS).setFenceCraftingIngredient(Items.BLAZE_ROD).addExtraTag(BlockusBlockTags.BLAZE_BRICKS).build();
    create(BlockusBlocks.BLAZE_LANTERN).setFenceCraftingIngredient(Items.BLAZE_ROD).build();

    create(BlockusBlocks.POLISHED_NETHER_BRICKS).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setFenceCraftingIngredient(Items.NETHER_BRICK).build();
    create(BlockusBlocks.POLISHED_RED_NETHER_BRICKS).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setFenceCraftingIngredient(Items.NETHER_BRICK).build();
    create(BlockusBlocks.HERRINGBONE_NETHER_BRICKS).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setFenceCraftingIngredient(Items.NETHER_BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.HERRINGBONE_RED_NETHER_BRICKS).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setFenceCraftingIngredient(Items.NETHER_BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.CHARRED_NETHER_BRICKS).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setFenceCraftingIngredient(Items.NETHER_BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.POLISHED_CHARRED_NETHER_BRICKS).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setFenceCraftingIngredient(Items.NETHER_BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.TEAL_NETHER_BRICKS).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setFenceCraftingIngredient(Items.NETHER_BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.POLISHED_TEAL_NETHER_BRICKS).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setFenceCraftingIngredient(Items.NETHER_BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.HERRINGBONE_TEAL_NETHER_BRICKS).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setFenceCraftingIngredient(Items.NETHER_BRICK).without(BlockShape.BUTTON).build();

    create(BlockusBlocks.CHISELED_PRISMARINE).addExtraTag(BlockusBlockTags.PRISMARINE_BLOCKS).setFenceCraftingIngredient(Items.PRISMARINE_SHARD).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build();
    buildCircularPavingBlock(create(BlockusBlocks.PRISMARINE_CIRCULAR_PAVING).addExtraTag(BlockusBlockTags.PRISMARINE_BLOCKS));
    create(BlockusBlocks.CHISELED_DARK_PRISMARINE).addExtraTag(BlockusBlockTags.PRISMARINE_BLOCKS).setFenceCraftingIngredient(Items.PRISMARINE_SHARD).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build();

    create(BlockusBlocks.LARGE_BRICKS).addExtraTag(BlockusBlockTags.BRICKS_BLOCKS).setFenceCraftingIngredient(Items.BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.HERRINGBONE_BRICKS).addExtraTag(BlockusBlockTags.BRICKS_BLOCKS).setFenceCraftingIngredient(Items.BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SOAKED_BRICKS).addExtraTag(BlockusBlockTags.BRICKS_BLOCKS).setFenceCraftingIngredient(Items.BRICK).build();
    create(BlockusBlocks.HERRINGBONE_SOAKED_BRICKS).addExtraTag(BlockusBlockTags.BRICKS_BLOCKS).setFenceCraftingIngredient(Items.BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.CHARRED_BRICKS).addExtraTag(BlockusBlockTags.BRICKS_BLOCKS).setFenceCraftingIngredient(Items.BRICK).build();
    create(BlockusBlocks.HERRINGBONE_CHARRED_BRICKS).addExtraTag(BlockusBlockTags.BRICKS_BLOCKS).setFenceCraftingIngredient(Items.BRICK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SANDY_BRICKS).setFenceCraftingIngredient(Items.BRICK).build();
    create(BlockusBlocks.HERRINGBONE_SANDY_BRICKS).setFenceCraftingIngredient(Items.BRICK).without(BlockShape.BUTTON).build();

    create(BlockusBlocks.ROUGH_SANDSTONE).addExtraTag(BlockusBlockTags.SANDSTONE).setFenceCraftingIngredient(Items.SAND).build();
    create(BlockusBlocks.SANDSTONE_BRICKS).addExtraTag(BlockusBlockTags.SANDSTONE).setFenceCraftingIngredient(Items.SAND).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SMALL_SANDSTONE_BRICKS).addExtraTag(BlockusBlockTags.SANDSTONE).setFenceCraftingIngredient(Items.SAND).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.ROUGH_RED_SANDSTONE).addExtraTag(BlockusBlockTags.RED_SANDSTONE).setFenceCraftingIngredient(Items.RED_SAND).build();
    create(BlockusBlocks.RED_SANDSTONE_BRICKS).addExtraTag(BlockusBlockTags.RED_SANDSTONE).setFenceCraftingIngredient(Items.RED_SAND).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SMALL_RED_SANDSTONE_BRICKS).addExtraTag(BlockusBlockTags.RED_SANDSTONE).setFenceCraftingIngredient(Items.RED_SAND).without(BlockShape.BUTTON).build();
    FACTORY.createConstructionOnly(BlockusBlocks.SOUL_SANDSTONE.block).addExtraTag(BlockusBlockTags.SOUL_SANDSTONE).setFenceCraftingIngredient(Items.SOUL_SAND).build();
    create(BlockusBlocks.ROUGH_SOUL_SANDSTONE).addExtraTag(BlockusBlockTags.SOUL_SANDSTONE).setFenceCraftingIngredient(Items.SOUL_SAND).build();
    create(BlockusBlocks.SOUL_SANDSTONE_BRICKS).addExtraTag(BlockusBlockTags.SOUL_SANDSTONE).setFenceCraftingIngredient(Items.SOUL_SAND).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SMALL_SOUL_SANDSTONE_BRICKS).addExtraTag(BlockusBlockTags.SOUL_SANDSTONE).setFenceCraftingIngredient(Items.SOUL_SAND).without(BlockShape.BUTTON).build();

    create(BlockusBlocks.SMOOTH_SOUL_SANDSTONE).setFenceCraftingIngredient(Items.SOUL_SAND).build();
    create(BlockusBlocks.CUT_SOUL_SANDSTONE).setFenceCraftingIngredient(Items.SOUL_SAND).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.CHISELED_SOUL_SANDSTONE).setFenceCraftingIngredient(Items.SOUL_SAND).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build();

    create(BlockusBlocks.RAINBOW_BLOCK).addExtraTag(BlockusBlockTags.RAINBOW_BLOCKS).setFenceCraftingIngredient(BlockusItems.RAINBOW_PETAL).setPillar().build();
    create(BlockusBlocks.RAINBOW_BRICKS).addExtraTag(BlockusBlockTags.RAINBOW_BLOCKS).setFenceCraftingIngredient(BlockusItems.RAINBOW_PETAL).build();
    create(BlockusBlocks.RAINBOW_GLOWSTONE).setFenceCraftingIngredient(BlockusItems.RAINBOW_PETAL).build();
    create(BlockusBlocks.HONEYCOMB_BRICKS).addExtraTag(BlockusBlockTags.HONEYCOMB_BLOCKS).setFenceCraftingIngredient(Items.HONEYCOMB).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.PURPUR_BRICKS).addExtraTag(BlockusBlockTags.PURPUR_BLOCKS).setFenceCraftingIngredient(Items.PURPUR_BLOCK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SMALL_PURPUR_BRICKS).addExtraTag(BlockusBlockTags.PURPUR_BLOCKS).setFenceCraftingIngredient(Items.PURPUR_BLOCK).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.POLISHED_PURPUR).addExtraTag(BlockusBlockTags.PURPUR_BLOCKS).setFenceCraftingIngredient(Items.PURPUR_BLOCK).build();
    create(BlockusBlocks.CHISELED_PURPUR).addExtraTag(BlockusBlockTags.PURPUR_BLOCKS).setFenceCraftingIngredient(Items.PURPUR_BLOCK).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build();
    create(BlockusBlocks.PURPUR_SQUARES).addExtraTag(BlockusBlockTags.PURPUR_BLOCKS).setFenceCraftingIngredient(Items.PURPUR_BLOCK).without(BlockShape.BUTTON).build();

    create(BlockusBlocks.PHANTOM_PURPUR_BRICKS).setFenceCraftingIngredient(Items.PHANTOM_MEMBRANE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.SMALL_PHANTOM_PURPUR_BRICKS).setFenceCraftingIngredient(Items.PHANTOM_MEMBRANE).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.PHANTOM_PURPUR_BLOCK).setFenceCraftingIngredient(Items.PHANTOM_MEMBRANE).build();
    create(BlockusBlocks.POLISHED_PHANTOM_PURPUR).setFenceCraftingIngredient(Items.PHANTOM_MEMBRANE).build();
    create(BlockusBlocks.CHISELED_PHANTOM_PURPUR).setFenceCraftingIngredient(Items.PHANTOM_MEMBRANE).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build();
    create(BlockusBlocks.PHANTOM_PURPUR_SQUARES).setFenceCraftingIngredient(Items.PHANTOM_MEMBRANE).without(BlockShape.BUTTON).build();

    create(BlockusBlocks.POLISHED_END_STONE).addExtraTag(BlockusBlockTags.END_STONE_BLOCKS).setFenceCraftingIngredient(Items.END_STONE_BRICKS).build();
    create(BlockusBlocks.SMALL_END_STONE_BRICKS).addExtraTag(BlockusBlockTags.END_STONE_BLOCKS).setFenceCraftingIngredient(Items.END_STONE_BRICKS).without(BlockShape.BUTTON).build();
    create(BlockusBlocks.CHISELED_END_STONE_BRICKS).addExtraTag(BlockusBlockTags.END_STONE_BLOCKS).setFenceCraftingIngredient(Items.END_STONE_BRICKS).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build();
    create(BlockusBlocks.HERRINGBONE_END_STONE_BRICKS).addExtraTag(BlockusBlockTags.END_STONE_BLOCKS).setFenceCraftingIngredient(Items.END_STONE_BRICKS).without(BlockShape.BUTTON).build();

    ImmutableMap<BlockShape, TagKey<? extends ItemConvertible>> logTags = ImmutableMap.copyOf(ExtShapeTags.SHAPE_TO_LOG_TAG);
    final BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> logFlammable = (blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 5, 5);
    FACTORY.createConstructionOnly(BlockusBlocks.WHITE_OAK_LOG).setPrimaryTagForShape(logTags).addExtraTag(ofBlockAndItem(ExtShapeTags.LOG_BLOCKS)).setPillar().addPostBuildConsumer(logFlammable).build();
    FACTORY.createConstructionOnly(BlockusBlocks.STRIPPED_WHITE_OAK_LOG).setPrimaryTagForShape(logTags).addExtraTag(ofBlockAndItem(ExtShapeTags.LOG_BLOCKS)).setPillar().addPostBuildConsumer(logFlammable).build();
    FACTORY.createAllShapes(BlockusBlocks.WHITE_OAK_WOOD).setPrimaryTagForShape(logTags).setButtonType(ButtonType.WOODEN)
        .setPillar().addPostBuildConsumer(logFlammable).build();
    FACTORY.createAllShapes(BlockusBlocks.STRIPPED_WHITE_OAK_WOOD).setPrimaryTagForShape(logTags).setButtonType(ButtonType.WOODEN).setPillar().addPostBuildConsumer(logFlammable).build();

    final ImmutableMap<BlockShape, TagKey<? extends ItemConvertible>> woodenTags = ImmutableMap.copyOf(ExtShapeTags.SHAPE_TO_WOODEN_TAG);
    final BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> plankFlammable = (blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 5, 20);
    final BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> plankFuel = (blockShape, blockBuilder) -> FuelRegistry.INSTANCE.add(blockBuilder.instance, (int) (blockShape.logicalCompleteness * 300));
    create(BlockusBlocks.WHITE_OAK).setPrimaryTagForShape(woodenTags).setButtonType(ButtonType.WOODEN).addPostBuildConsumer(plankFlammable).addPostBuildConsumer(plankFuel).build();
    create(BlockusBlocks.BAMBOO).setPrimaryTagForShape(woodenTags).setButtonType(ButtonType.WOODEN).setFenceCraftingIngredient(Items.BAMBOO).addPostBuildConsumer(plankFlammable).addPostBuildConsumer(plankFuel).build();
    create(BlockusBlocks.CHARRED).setPrimaryTagForShape(woodenTags).addExtraTag(ItemTags.NON_FLAMMABLE_WOOD).setButtonType(ButtonType.WOODEN).build();

    for (var block : BlockusBlockCollections.HERRINGBONE_PLANKS) {
      final boolean isNonFlammable = BlockusBlocks.HERRINGBONE_WARPED_PLANKS == block || BlockusBlocks.HERRINGBONE_CRIMSON_PLANKS == block || BlockusBlocks.HERRINGBONE_CHARRED_PLANKS == block;
      if (isNonFlammable) {
        FACTORY.createConstructionOnly(block).with(BlockShape.WALL).setPrimaryTagForShape(woodenTags).addExtraTag(ItemTags.NON_FLAMMABLE_WOOD).setRecipeGroup(blockShape -> "herringbone_wooden_" + blockShape.asString()).build();
      } else {
        FACTORY.createConstructionOnly(block).with(BlockShape.WALL).setPrimaryTagForShape(woodenTags).addPostBuildConsumer(plankFlammable).setRecipeGroup(blockShape -> "herringbone_wooden_" + blockShape.asString()).build();
      }
    }
    for (var block : BlockusBlockCollections.SMALL_LOGS) {
      if (block.getDefaultState().getMaterial() == Material.NETHER_WOOD) {
        FACTORY.createConstructionOnly(block).setPillar().setPrimaryTagForShape(logTags).addExtraTag(ItemTags.NON_FLAMMABLE_WOOD).setRecipeGroup(blockShape -> "small_logs_" + blockShape.asString()).build();
      } else {
        FACTORY.createConstructionOnly(block).setPillar().setPrimaryTagForShape(logTags).addPostBuildConsumer(logFlammable).setRecipeGroup(blockShape -> "small_logs_" + blockShape.asString()).build();
      }
    }

    final Function<BlockShape, @Nullable TagKey<? extends ItemConvertible>> addWallToUnmineableTag = blockShape -> blockShape == BlockShape.WALL ? ExtShapeTags.PICKAXE_UNMINEABLE : null;

    create(BlockusBlocks.CHORUS_BLOCK).addExtraTag(BlockTags.HOE_MINEABLE).addExtraTag(addWallToUnmineableTag).setFenceCraftingIngredient(Items.CHORUS_PLANT).setPillar().build();

    for (var bsswTypes : BlockusBlockCollections.STAINED_STONE_BRICKS) {
      buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.STAINED_STONE_BRICKS).setRecipeGroup(blockShape -> "stained_stone_brick_" + blockShape.asString()));
    }
    for (var concreteTypes : BlockusBlockCollections.CONCRETE_BRICKS) {
      FACTORY.createConstructionOnly(concreteTypes.block).without(BlockShape.BUTTON, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL).addExtraTag(BlockusBlockTags.CONCRETE_BLOCKS).setRecipeGroup(blockShape1 -> "concrete_brick_" + blockShape1.asString()).build();
      FACTORY.createConstructionOnly(concreteTypes.chiseled).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).addExtraTag(BlockusBlockTags.CONCRETE_BLOCKS).setRecipeGroup(blockShape -> "chiseled_concrete_brick_" + blockShape.asString()).build();
    }

    create(BlockusBlocks.SHINGLES).addExtraTag(BlockusBlockTags.SHINGLES).setFenceCraftingIngredient(Items.FLINT).build();
    for (var bssTypes : BlockusBlockCollections.TINTED_SHINGLES) {
      create(bssTypes).setFenceCraftingIngredient(Items.FLINT).setRecipeGroup(blockShape -> "shingles_" + blockShape.asString()).build();
    }

    for (var block : BlockusBlockCollections.PATTERNED_WOOLS) {
      FACTORY.createConstructionOnly(block).without(BlockShape.STAIRS, BlockShape.SLAB).with(BlockShape.WALL).addPostBuildConsumer((blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 30, 60)).addPostBuildConsumer((blockShape, blockBuilder) -> FuelRegistry.INSTANCE.add(blockBuilder.instance, (int) (100 * blockShape.logicalCompleteness))).setRecipeGroup(blockShape -> "patterned_wool_" + blockShape.asString()).build();
    }
    for (var block : BlockusBlockCollections.GLAZED_TERRACOTTA_PILLARS) {
      FACTORY.createConstructionOnly(block).with(BlockShape.WALL).addExtraTag(BlockusBlockTags.GLAZED_TERRACOTTA_PILLARS).setRecipeGroup(blockShape -> "glazed_terracotta_pillar_" + blockShape.asString()).setPillar().build();
    }

    create(BlockusBlocks.THATCH).addExtraTag(BlockusBlockTags.THATCH).addExtraTag(BlockTags.HOE_MINEABLE).addExtraTag(addWallToUnmineableTag).addPostBuildConsumer((blockShape4, blockBuilder3) -> {
      FlammableBlockRegistry.getDefaultInstance().add(blockBuilder3.instance, 60, 20);
      CompostingChanceRegistry.INSTANCE.add(blockBuilder3.instance, blockShape4.logicalCompleteness * 0.75f);
    }).build();
    create(BlockusBlocks.PAPER_BLOCK).addExtraTag(addWallToUnmineableTag).addPostBuildConsumer((blockShape3, blockBuilder2) -> {
      FlammableBlockRegistry.getDefaultInstance().add(blockBuilder2.instance, 30, 60);
      FuelRegistry.INSTANCE.add(blockBuilder2.instance, (int) (blockShape3.logicalCompleteness * 100));
    }).build();
    create(BlockusBlocks.BURNT_PAPER_BLOCK).addExtraTag(addWallToUnmineableTag).addPostBuildConsumer((blockShape2, blockBuilder1) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder1.instance, 5, 60)).build();

    create(BlockusBlocks.IRON_PLATING).addExtraTag(BlockusBlockTags.PLATINGS).without(BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.BUTTON).build();
    create(BlockusBlocks.GOLD_PLATING).addExtraTag(BlockusBlockTags.PLATINGS).addExtraTag(BlockTags.GUARDED_BY_PIGLINS).without(BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.BUTTON).build();

    create(BlockusBlocks.CHARCOAL_BLOCK).setFenceCraftingIngredient(Items.CHARCOAL).addExtraTag(BlockTags.INFINIBURN_OVERWORLD).addExtraTag(BlockTags.PICKAXE_MINEABLE).addPostBuildConsumer((blockShape, blockBuilder) -> FuelRegistry.INSTANCE.add(blockBuilder.instance, (int) (blockShape.logicalCompleteness * 16000))).build();
    create(BlockusBlocks.SUGAR_BLOCK).setFenceCraftingIngredient(Items.SUGAR).addExtraTag(BlockTags.SHOVEL_MINEABLE).addExtraTag(addWallToUnmineableTag).build();
    create(BlockusBlocks.ENDER_BLOCK).setFenceCraftingIngredient(Items.ENDER_PEARL).addExtraTag(BlockTags.PICKAXE_MINEABLE).addExtraTag(BlockTags.NEEDS_STONE_TOOL).build();
    create(BlockusBlocks.ROTTEN_FLESH_BLOCK).setFenceCraftingIngredient(Items.ROTTEN_FLESH).addExtraTag(BlockTags.HOE_MINEABLE).addExtraTag(addWallToUnmineableTag).build();
    create(BlockusBlocks.MEMBRANE_BLOCK).setFenceCraftingIngredient(Items.PHANTOM_MEMBRANE).addExtraTag(addWallToUnmineableTag).build();
    FACTORY.createAllShapes(BlockusBlocks.NETHER_STAR_BLOCK).withoutRedstone().setFenceCraftingIngredient(Items.NETHER_STAR).addExtraTag(BlockTags.PICKAXE_MINEABLE).addExtraTag(BlockTags.NEEDS_IRON_TOOL).addExtraTag(BlockTags.DRAGON_IMMUNE).withExtension(BlockExtension.builder().setSteppedOnCallback((world, pos, state, entity) -> {
      if (entity.getType() == EntityType.PLAYER) {
        ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 1, 4, true, false, false));
        ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 100, 3, true, false, true));
        ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 1200, 4, true, false, true));
      }
    }).build()).addPreBuildConsumer((blockShape, builder) -> {
      builder.setItemSettings(new FabricItemSettings().group(Blockus.BLOCKUS_BUILDING_BLOCKS));
      builder.setItemInstanceSupplier(builder0 -> new ExtShapeBlockItem(builder0.instance, builder0.itemSettings) {
        @Override
        public boolean hasGlint(ItemStack stack) {
          return true;
        }
      });
    }).build();

    create(BlockusBlocks.STARS_BLOCK).setFenceCraftingIngredient(Items.COAL).addExtraTag(BlockTags.PICKAXE_MINEABLE).build();
  }

  private static void buildStoneBlocksWithButton(BlocksBuilder blocksBuilder) {
    blocksBuilder.setFenceCraftingIngredient(Items.FLINT).build();
  }

  private static void buildStoneBlocksWithoutButton(BlocksBuilder blocksBuilder) {
    blocksBuilder.setFenceCraftingIngredient(Items.FLINT).without(BlockShape.BUTTON).build();
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
