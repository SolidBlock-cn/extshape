package pers.solid.extshape.blockus;

import com.brand.blockus.Blockus;
import com.brand.blockus.content.BlockusBlocks;
import com.brand.blockus.content.BlockusItems;
import com.brand.blockus.content.types.BSSTypes;
import com.brand.blockus.content.types.BSSWTypes;
import com.brand.blockus.content.types.WoodTypesF;
import com.brand.blockus.content.types.WoodTypesFP;
import com.brand.blockus.tags.BlockusBlockTags;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Material;
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
import pers.solid.extshape.builder.AbstractBlockBuilder;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.builder.BlocksBuilder;
import pers.solid.extshape.builder.BlocksBuilderFactory;
import pers.solid.extshape.tag.ExtShapeTags;
import pers.solid.extshape.util.ButtonSettings;
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
    blocksBuilderFactory.tagPreparations = ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS;
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

  private static BlocksBuilder create(WoodTypesF woodTypesF) {
    return FACTORY.createAllShapes(woodTypesF.planks).without(BlockShape.STAIRS, BlockShape.SLAB, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.PRESSURE_PLATE, BlockShape.BUTTON);
  }

  private static BlocksBuilder create(WoodTypesFP woodTypesFp) {
    return FACTORY.createAllShapes(woodTypesFp.planks).without(BlockShape.STAIRS, BlockShape.SLAB, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.PRESSURE_PLATE, BlockShape.BUTTON);
  }

  private static final FenceSettings stoneFenceSettings = FenceSettings.common(Items.FLINT);

  static {
    BlockusBlockBiMaps.importFromBlockus();
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.STONE_TILES, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.STONE_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_STONE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.STONE_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.STONE_CIRCULAR_PAVING, baseBlock9 -> buildCircularPavingBlock(FACTORY.createEmpty(baseBlock9).addExtraTag(BlockusBlockTags.STONE_BLOCKS)));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ANDESITE_BRICKS, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addExtraTag(BlockusBlockTags.ANDESITE_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_ANDESITE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.ANDESITE_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_ANDESITE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.ANDESITE_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ANDESITE_CIRCULAR_PAVING, block -> buildCircularPavingBlock(FACTORY.createEmpty(block).addExtraTag(BlockusBlockTags.ANDESITE_BLOCKS)));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.DIORITE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.DIORITE_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_DIORITE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.DIORITE_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_DIORITE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.DIORITE_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.DIORITE_CIRCULAR_PAVING, baseBlock7 -> buildCircularPavingBlock(FACTORY.createEmpty(baseBlock7).addExtraTag(BlockusBlockTags.DIORITE_BLOCKS)));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.GRANITE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.GRANITE_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_GRANITE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.GRANITE_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_GRANITE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.GRANITE_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.GRANITE_CIRCULAR_PAVING, baseBlock6 -> buildCircularPavingBlock(FACTORY.createEmpty(baseBlock6).addExtraTag(BlockusBlockTags.GRANITE_BLOCKS)));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_MUD_BRICKS, block -> create(block).addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .without(BlockShape.BUTTON)
        .setCommonFenceSettings(Items.MUD).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_DRIPSTONE, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.DRIPSTONE_BLOCKS)
        .setCommonFenceSettings(Items.POINTED_DRIPSTONE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.DRIPSTONE_BRICKS, bsswTypes -> create(bsswTypes).addExtraTag(BlockusBlockTags.DRIPSTONE_BLOCKS)
        .without(BlockShape.BUTTON)
        .setCommonFenceSettings(Items.POINTED_DRIPSTONE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_DRIPSTONE, block -> create(block).addExtraTag(BlockusBlockTags.DRIPSTONE_BLOCKS)
        .without(BlockShape.BUTTON)
        .setCommonFenceSettings(Items.POINTED_DRIPSTONE).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_TUFF, bssTypes -> buildStoneBlocksWithButton(create(bssTypes).addExtraTag(BlockusBlockTags.TUFF_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.TUFF_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.TUFF_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_TUFF, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.TUFF_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_TUFF_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.TUFF_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.TUFF_CIRCULAR_PAVING, baseBlock5 -> buildCircularPavingBlock(FACTORY.createEmpty(baseBlock5).addExtraTag(BlockusBlockTags.TUFF_BLOCKS)));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.MOSSY_DEEPSLATE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.DEEPSLATE_BLOCKS)
    ));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_DEEPSLATE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.DEEPSLATE_BLOCKS)
    ));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.DEEPSLATE_CIRCULAR_PAVING, baseBlock4 -> buildCircularPavingBlock(FACTORY.createEmpty(baseBlock4).addExtraTag(BlockusBlockTags.DEEPSLATE_BLOCKS)));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_SCULK, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.SCULK_BLOCKS)
        .setCommonFenceSettings(Items.SCULK)
        .build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SCULK_BRICKS, bsswTypes -> create(bsswTypes).addExtraTag(BlockusBlockTags.SCULK_BLOCKS)
        .without(BlockShape.BUTTON)
        .setCommonFenceSettings(Items.SCULK)
        .build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_SCULK_BRICKS, block -> create(block).addExtraTag(BlockusBlockTags.SCULK_BLOCKS)
        .without(BlockShape.BUTTON)
        .setCommonFenceSettings(Items.SCULK)
        .build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_AMETHYST, block -> FACTORY.createAllShapes(block).withoutRedstone()
        .addExtraTag(BlockusBlockTags.AMETHYST_BLOCKS)
        .withExtension(BlockExtension.AMETHYST)
        .setFenceSettings(FenceSettings.AMETHYST)
        .build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.AMETHYST_BRICKS, block -> FACTORY.createAllShapes(block).withoutRedstone()
        .addExtraTag(BlockusBlockTags.AMETHYST_BLOCKS)
        .withExtension(BlockExtension.AMETHYST)
        .setFenceSettings(FenceSettings.AMETHYST)
        .without(BlockShape.BUTTON)
        .build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_AMETHYST, block -> FACTORY.createAllShapes(block).withoutRedstone()
        .addExtraTag(BlockusBlockTags.AMETHYST_BLOCKS)
        .withExtension(BlockExtension.AMETHYST)
        .setFenceSettings(FenceSettings.AMETHYST)
        .build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_BLACKSTONE_TILES, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).setBlockSetType(BlockSetType.POLISHED_BLACKSTONE).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CRIMSON_WARTY_BLACKSTONE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.WARPED_WARTY_BLACKSTONE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_POLISHED_BLACKSTONE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_BLACKSTONE_CIRCULAR_PAVING, block -> buildCircularPavingBlock(FACTORY.createEmpty(block)
        .setBlockSetType(BlockSetType.POLISHED_BLACKSTONE).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS)));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ROUGH_BASALT, bssTypes -> buildStoneBlocksWithButton(create(bssTypes).addExtraTag(BlockusBlockTags.BASALT_BLOCKS)
    ));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_BASALT_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.BASALT_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_POLISHED_BASALT_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.BASALT_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CRIMSON_WART_BRICKS, bsswTypes -> create(bsswTypes).addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setCommonFenceSettings(Items.BRICK)
        .without(BlockShape.BUTTON)
        .build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.WARPED_WART_BRICKS, bsswTypes -> create(bsswTypes).addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setCommonFenceSettings(Items.BRICK)
        .without(BlockShape.BUTTON)
        .build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.LIMESTONE, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addExtraTag(BlockusBlockTags.LIMESTONE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_LIMESTONE, bssTypes -> buildStoneBlocksWithButton(create(bssTypes).addExtraTag(BlockusBlockTags.LIMESTONE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.LIMESTONE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.LIMESTONE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_LIMESTONE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.LIMESTONE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.LIMESTONE_TILES, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.LIMESTONE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_LIMESTONE, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.LIMESTONE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.LIMESTONE_SQUARES, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.LIMESTONE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.LIMESTONE_CIRCULAR_PAVING, baseBlock3 -> buildCircularPavingBlock(FACTORY.createEmpty(baseBlock3).addExtraTag(BlockusBlockTags.LIMESTONE)));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.MARBLE, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addExtraTag(BlockusBlockTags.MARBLE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_MARBLE, bssTypes -> buildStoneBlocksWithButton(create(bssTypes).addExtraTag(BlockusBlockTags.MARBLE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.MARBLE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.MARBLE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_MARBLE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.MARBLE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.MARBLE_TILES, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.MARBLE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_MARBLE, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.MARBLE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.MARBLE_SQUARES, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.MARBLE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.MARBLE_CIRCULAR_PAVING, baseBlock2 -> buildCircularPavingBlock(FACTORY.createEmpty(baseBlock2).addExtraTag(BlockusBlockTags.MARBLE)));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.BLUESTONE, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addExtraTag(BlockusBlockTags.BLUESTONE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_BLUESTONE, bssTypes -> buildStoneBlocksWithButton(create(bssTypes).addExtraTag(BlockusBlockTags.BLUESTONE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.BLUESTONE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.BLUESTONE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_BLUESTONE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.BLUESTONE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.BLUESTONE_TILES, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.BLUESTONE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_BLUESTONE, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.BLUESTONE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.BLUESTONE_SQUARES, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.BLUESTONE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.BLUESTONE_CIRCULAR_PAVING, baseBlock1 -> buildCircularPavingBlock(FACTORY.createEmpty(baseBlock1).addExtraTag(BlockusBlockTags.BLUESTONE)));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.VIRIDITE, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addExtraTag(BlockusBlockTags.VIRIDITE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_VIRIDITE, bssTypes -> buildStoneBlocksWithButton(create(bssTypes).addExtraTag(BlockusBlockTags.VIRIDITE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.VIRIDITE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.VIRIDITE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_VIRIDITE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.VIRIDITE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.VIRIDITE_TILES, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.VIRIDITE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_VIRIDITE, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.VIRIDITE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.VIRIDITE_SQUARES, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.VIRIDITE)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.VIRIDITE_CIRCULAR_PAVING, baseBlock -> buildCircularPavingBlock(FACTORY.createEmpty(baseBlock).addExtraTag(BlockusBlockTags.VIRIDITE)));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.LAVA_BRICKS, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addExtraTag(BlockusBlockTags.LAVA_BRICKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_LAVA_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.LAVA_BRICKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.LAVA_POLISHED_BLACKSTONE_BRICKS, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addExtraTag(BlockusBlockTags.LAVA_POLISHED_BLACKSTONE_BRICKS).setBlockSetType(BlockSetType.POLISHED_BLACKSTONE).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_LAVA_POLISHED_BLACKSTONE, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.LAVA_POLISHED_BLACKSTONE_BRICKS).setBlockSetType(BlockSetType.POLISHED_BLACKSTONE).addExtraTag(BlockusBlockTags.BLACKSTONE_BLOCKS)));

    // because of issues related to tint index and render layers, water bricks do not have extende shapes
//    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.WATER_BRICKS, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addExtraTag(BlockusBlockTags.WATER_BRICKS)));
//    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_WATER_BRICKS, block -> buildStoneBlocksWithButton(create(block).addExtraTag(BlockusBlockTags.WATER_BRICKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SNOW_BRICKS, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addExtraTag(BlockusBlockTags.SNOW_BRICKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ICE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.ICE_BRICKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.OBSIDIAN_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.OBSIDIAN).setButtonSettings(ButtonSettings.HARD)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_OBSIDIAN_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.OBSIDIAN).setButtonSettings(ButtonSettings.HARD)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.OBSIDIAN_CIRCULAR_PAVING, block -> buildCircularPavingBlock(FACTORY.createEmpty(block).addExtraTag(BlockusBlockTags.OBSIDIAN).setButtonSettings(ButtonSettings.HARD)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.GLOWING_OBSIDIAN, block -> buildStoneBlocksWithButton(create(block).addExtraTag(BlockusBlockTags.OBSIDIAN).setButtonSettings(ButtonSettings.HARD)));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_NETHERRACK, bssTypes -> buildStoneBlocksWithButton(create(bssTypes).addExtraTag(BlockusBlockTags.NETHERRACK_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.NETHERRACK_BRICKS, bssTypes -> buildStoneBlocksWithoutButton(create(bssTypes).addExtraTag(BlockusBlockTags.NETHERRACK_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.NETHERRACK_CIRCULAR_PAVING, block -> buildCircularPavingBlock(FACTORY.createEmpty(block).addExtraTag(BlockusBlockTags.NETHERRACK_BLOCKS)));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.QUARTZ_TILES, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.QUARTZ_BLOCKS).setCommonFenceSettings(Items.QUARTZ)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.QUARTZ_CIRCULAR_PAVING, block -> buildCircularPavingBlock(FACTORY.createEmpty(block).addExtraTag(BlockusBlockTags.QUARTZ_BLOCKS).setCommonFenceSettings(Items.QUARTZ)));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.MAGMA_BRICKS, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addExtraTag(BlockusBlockTags.MAGMA_BRICKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_MAGMA_BRICKS, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addExtraTag(BlockusBlockTags.MAGMA_BRICKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_MAGMA_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addExtraTag(BlockusBlockTags.MAGMA_BRICKS).without(BlockShape.FENCE, BlockShape.FENCE_GATE)));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.BLAZE_BRICKS, bsswTypes -> create(bsswTypes).setCommonFenceSettings(Items.BLAZE_ROD).addExtraTag(BlockusBlockTags.BLAZE_BRICKS).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.BLAZE_LANTERN, block -> create(block).setCommonFenceSettings(Items.BLAZE_ROD).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_NETHER_BRICKS, block -> create(block).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setCommonFenceSettings(Items.NETHER_BRICK).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_RED_NETHER_BRICKS, block -> create(block).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setCommonFenceSettings(Items.NETHER_BRICK).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_NETHER_BRICKS, block -> create(block).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setCommonFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_RED_NETHER_BRICKS, block -> create(block).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setCommonFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.NETHER_TILES, block -> create(block).setCommonFenceSettings(Items.NETHER_BRICK).addExtraTag(BlockTags.PICKAXE_MINEABLE).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHARRED_NETHER_BRICKS, block -> create(block).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setCommonFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_CHARRED_NETHER_BRICKS, block -> create(block).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setCommonFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.TEAL_NETHER_BRICKS, block -> create(block).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setCommonFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_TEAL_NETHER_BRICKS, block -> create(block).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setCommonFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_TEAL_NETHER_BRICKS, block -> create(block).addExtraTag(BlockusBlockTags.NETHER_BRICKS).setCommonFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_PRISMARINE, block -> create(block).addExtraTag(BlockusBlockTags.PRISMARINE_BLOCKS).setCommonFenceSettings(Items.PRISMARINE_SHARD).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.PRISMARINE_CIRCULAR_PAVING, block -> buildCircularPavingBlock(create(block).addExtraTag(BlockusBlockTags.PRISMARINE_BLOCKS)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_DARK_PRISMARINE, block -> create(block).addExtraTag(BlockusBlockTags.PRISMARINE_BLOCKS).setCommonFenceSettings(Items.PRISMARINE_SHARD).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.LARGE_BRICKS, bsswTypes -> create(bsswTypes).addExtraTag(BlockusBlockTags.BRICKS_BLOCKS).setCommonFenceSettings(Items.BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_BRICKS, block -> create(block).addExtraTag(BlockusBlockTags.BRICKS_BLOCKS).setCommonFenceSettings(Items.BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SOAKED_BRICKS, bsswTypes -> create(bsswTypes).addExtraTag(BlockusBlockTags.BRICKS_BLOCKS).setCommonFenceSettings(Items.BRICK).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_SOAKED_BRICKS, block -> create(block).addExtraTag(BlockusBlockTags.BRICKS_BLOCKS).setCommonFenceSettings(Items.BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHARRED_BRICKS, bsswTypes -> create(bsswTypes).addExtraTag(BlockusBlockTags.BRICKS_BLOCKS).setCommonFenceSettings(Items.BRICK).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_CHARRED_BRICKS, block -> create(block).addExtraTag(BlockusBlockTags.BRICKS_BLOCKS).setCommonFenceSettings(Items.BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SANDY_BRICKS, bsswTypes -> create(bsswTypes).setCommonFenceSettings(Items.BRICK).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_SANDY_BRICKS, block -> create(block).setCommonFenceSettings(Items.BRICK).without(BlockShape.BUTTON).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ROUGH_SANDSTONE, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.SANDSTONE).setCommonFenceSettings(Items.SAND).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SANDSTONE_BRICKS, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.SANDSTONE).setCommonFenceSettings(Items.SAND).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_SANDSTONE_BRICKS, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.SANDSTONE).setCommonFenceSettings(Items.SAND).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ROUGH_RED_SANDSTONE, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.RED_SANDSTONE).setCommonFenceSettings(Items.RED_SAND).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.RED_SANDSTONE_BRICKS, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.RED_SANDSTONE).setCommonFenceSettings(Items.RED_SAND).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_RED_SANDSTONE_BRICKS, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.RED_SANDSTONE).setCommonFenceSettings(Items.RED_SAND).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SOUL_SANDSTONE, bsswTypes -> FACTORY.createConstructionOnly(bsswTypes.block).addExtraTag(BlockusBlockTags.SOUL_SANDSTONE).setCommonFenceSettings(Items.SOUL_SAND).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ROUGH_SOUL_SANDSTONE, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.SOUL_SANDSTONE).setCommonFenceSettings(Items.SOUL_SAND).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SOUL_SANDSTONE_BRICKS, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.SOUL_SANDSTONE).setCommonFenceSettings(Items.SOUL_SAND).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_SOUL_SANDSTONE_BRICKS, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.SOUL_SANDSTONE).setCommonFenceSettings(Items.SOUL_SAND).without(BlockShape.BUTTON).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMOOTH_SOUL_SANDSTONE, bssTypes -> create(bssTypes).setCommonFenceSettings(Items.SOUL_SAND).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CUT_SOUL_SANDSTONE, bssTypes -> create(bssTypes).setCommonFenceSettings(Items.SOUL_SAND).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_SOUL_SANDSTONE, bssTypes -> create(bssTypes).setCommonFenceSettings(Items.SOUL_SAND).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.RAINBOW_BLOCK, block -> create(block).addExtraTag(BlockusBlockTags.RAINBOW_BLOCKS).setCommonFenceSettings(BlockusItems.RAINBOW_PETAL).setPillar().build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.RAINBOW_BRICKS, bsswTypes -> create(bsswTypes).addExtraTag(BlockusBlockTags.RAINBOW_BLOCKS).setCommonFenceSettings(BlockusItems.RAINBOW_PETAL).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.RAINBOW_GLOWSTONE, block -> create(block).setCommonFenceSettings(BlockusItems.RAINBOW_PETAL).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HONEYCOMB_BRICKS, bsswTypes -> create(bsswTypes).addExtraTag(BlockusBlockTags.HONEYCOMB_BLOCKS).setCommonFenceSettings(Items.HONEYCOMB).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.PURPUR_BRICKS, bsswTypes -> create(bsswTypes).addExtraTag(BlockusBlockTags.PURPUR_BLOCKS).setCommonFenceSettings(Items.PURPUR_BLOCK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_PURPUR_BRICKS, bsswTypes -> create(bsswTypes).addExtraTag(BlockusBlockTags.PURPUR_BLOCKS).setCommonFenceSettings(Items.PURPUR_BLOCK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_PURPUR, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.PURPUR_BLOCKS).setCommonFenceSettings(Items.PURPUR_BLOCK).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_PURPUR, block -> create(block).addExtraTag(BlockusBlockTags.PURPUR_BLOCKS).setCommonFenceSettings(Items.PURPUR_BLOCK).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.PURPUR_SQUARES, block -> create(block).addExtraTag(BlockusBlockTags.PURPUR_BLOCKS).setCommonFenceSettings(Items.PURPUR_BLOCK).without(BlockShape.BUTTON).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.PHANTOM_PURPUR_BRICKS, bsswTypes -> create(bsswTypes).setCommonFenceSettings(Items.PHANTOM_MEMBRANE).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_PHANTOM_PURPUR_BRICKS, bsswTypes -> create(bsswTypes).setCommonFenceSettings(Items.PHANTOM_MEMBRANE).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.PHANTOM_PURPUR_BLOCK, bssTypes -> create(bssTypes).setCommonFenceSettings(Items.PHANTOM_MEMBRANE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_PHANTOM_PURPUR, bssTypes -> create(bssTypes).setCommonFenceSettings(Items.PHANTOM_MEMBRANE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_PHANTOM_PURPUR, block -> create(block).setCommonFenceSettings(Items.PHANTOM_MEMBRANE).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.PHANTOM_PURPUR_SQUARES, block -> create(block).setCommonFenceSettings(Items.PHANTOM_MEMBRANE).without(BlockShape.BUTTON).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_END_STONE, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.END_STONE_BLOCKS).setCommonFenceSettings(Items.END_STONE_BRICKS).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_END_STONE_BRICKS, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.END_STONE_BLOCKS).setCommonFenceSettings(Items.END_STONE_BRICKS).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_END_STONE_BRICKS, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.END_STONE_BLOCKS).setCommonFenceSettings(Items.END_STONE_BRICKS).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_END_STONE_BRICKS, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.END_STONE_BLOCKS).setCommonFenceSettings(Items.END_STONE_BRICKS).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.END_TILES, bssTypes -> create(bssTypes).setCommonFenceSettings(Items.END_STONE_BRICKS).addExtraTag(BlockTags.PICKAXE_MINEABLE).without(BlockShape.BUTTON).build());

    ImmutableMap<BlockShape, TagKey<? extends ItemConvertible>> logTags = ImmutableMap.copyOf(ExtShapeTags.SHAPE_TO_LOG_TAG);
    final BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> logFlammable = (blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 5, 5);
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.WHITE_OAK_LOG, block -> FACTORY.createConstructionOnly(block).setPrimaryTagForShape(logTags).addExtraTag(ofBlockAndItem(ExtShapeTags.LOG_BLOCKS)).setPillar().addPostBuildConsumer(logFlammable).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.STRIPPED_WHITE_OAK_LOG, block -> FACTORY.createConstructionOnly(block).setPrimaryTagForShape(logTags).addExtraTag(ofBlockAndItem(ExtShapeTags.LOG_BLOCKS)).setPillar().addPostBuildConsumer(logFlammable).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.WHITE_OAK_WOOD, block -> FACTORY.createAllShapes(block).setPrimaryTagForShape(logTags).setButtonSettings(ButtonSettings.wood(BlockSetType.OAK))
        .setPillar().addPostBuildConsumer(logFlammable).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.STRIPPED_WHITE_OAK_WOOD, block -> FACTORY.createAllShapes(block).setPrimaryTagForShape(logTags).setButtonSettings(ButtonSettings.wood(BlockSetType.OAK)).setPillar().addPostBuildConsumer(logFlammable).build());

    final ImmutableMap<BlockShape, TagKey<? extends ItemConvertible>> woodenTags = ImmutableMap.copyOf(ExtShapeTags.SHAPE_TO_WOODEN_TAG);
    final BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> plankFlammable = (blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 5, 20);
    final BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> plankFuel = (blockShape, blockBuilder) -> FuelRegistry.INSTANCE.add(blockBuilder.instance, (int) (blockShape.logicalCompleteness * 300));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.WHITE_OAK, woodTypesF -> create(woodTypesF).setPrimaryTagForShape(woodenTags).setButtonSettings(ButtonSettings.wood(BlockSetType.OAK)).addPostBuildConsumer(plankFlammable).addPostBuildConsumer(plankFuel).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.BAMBOO, woodTypesF -> create(woodTypesF).setPrimaryTagForShape(woodenTags).setButtonSettings(ButtonSettings.BAMBOO).setCommonFenceSettings(Items.BAMBOO).addPostBuildConsumer(plankFlammable).addPostBuildConsumer(plankFuel).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHARRED, woodTypesFP -> create(woodTypesFP).setPrimaryTagForShape(woodenTags).addExtraTag(ItemTags.NON_FLAMMABLE_WOOD).setButtonSettings(ButtonSettings.wood(BlockSetType.OAK)).build());

    for (var supplier : BlockusBlockCollections.HERRINGBONE_PLANKS) {
      ExtShapeBlockus.tryConsume(supplier, block -> {
        final boolean isNonFlammable = ExtShapeBlockus.tryTransform(() -> BlockusBlocks.HERRINGBONE_WARPED_PLANKS, b -> b == block, Suppliers.ofInstance(false)) || ExtShapeBlockus.tryTransform(() -> BlockusBlocks.HERRINGBONE_CRIMSON_PLANKS, b -> b == block, Suppliers.ofInstance(false)) || ExtShapeBlockus.tryTransform(() -> BlockusBlocks.HERRINGBONE_CHARRED_PLANKS, b -> b == block, Suppliers.ofInstance(false));
        if (isNonFlammable) {
          FACTORY.createConstructionOnly(block).with(BlockShape.WALL).setPrimaryTagForShape(woodenTags).addExtraTag(ItemTags.NON_FLAMMABLE_WOOD).setRecipeGroup(blockShape -> "herringbone_wooden_" + blockShape.asString()).build();
        } else {
          FACTORY.createConstructionOnly(block).with(BlockShape.WALL).setPrimaryTagForShape(woodenTags).addPostBuildConsumer(plankFlammable).setRecipeGroup(blockShape -> "herringbone_wooden_" + blockShape.asString()).build();
        }
      });
    }
    for (var supplier : BlockusBlockCollections.SMALL_LOGS) {
      ExtShapeBlockus.tryConsume(supplier, block -> {
        if (block.getDefaultState().getMaterial() == Material.NETHER_WOOD) {
          FACTORY.createConstructionOnly(block).setPillar().setPrimaryTagForShape(logTags).addExtraTag(ItemTags.NON_FLAMMABLE_WOOD).setRecipeGroup(blockShape -> "small_logs_" + blockShape.asString()).build();
        } else {
          FACTORY.createConstructionOnly(block).setPillar().setPrimaryTagForShape(logTags).addPostBuildConsumer(logFlammable).setRecipeGroup(blockShape -> "small_logs_" + blockShape.asString()).build();
        }
      });
    }

    final Function<BlockShape, @Nullable TagKey<? extends ItemConvertible>> addWallToUnmineableTag = blockShape -> blockShape == BlockShape.WALL ? ExtShapeTags.PICKAXE_UNMINEABLE : null;

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHORUS_BLOCK, block -> create(block).addExtraTag(BlockTags.HOE_MINEABLE).addExtraTag(addWallToUnmineableTag).setCommonFenceSettings(Items.CHORUS_PLANT).setPillar().build());

    for (var supplier : BlockusBlockCollections.STAINED_STONE_BRICKS) {
      ExtShapeBlockus.tryConsume(supplier, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addExtraTag(BlockusBlockTags.STAINED_STONE_BRICKS).setRecipeGroup(blockShape -> "stained_stone_brick_" + blockShape.asString())));
    }
    for (var supplier : BlockusBlockCollections.CONCRETE_BRICKS) {
      ExtShapeBlockus.tryConsume(supplier, concreteTypes -> FACTORY.createConstructionOnly(concreteTypes.block).without(BlockShape.BUTTON, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL).addExtraTag(BlockusBlockTags.CONCRETE_BLOCKS).setRecipeGroup(blockShape -> "concrete_brick_" + blockShape.asString()).build());
      ExtShapeBlockus.tryConsume(supplier, concreteTypes -> FACTORY.createConstructionOnly(concreteTypes.chiseled).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).addExtraTag(BlockusBlockTags.CONCRETE_BLOCKS).setRecipeGroup(blockShape -> "chiseled_concrete_brick_" + blockShape.asString()).build());
    }

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SHINGLES, block -> create(block).addExtraTag(BlockusBlockTags.SHINGLES).setCommonFenceSettings(Items.FLINT).build());
    for (var supplier : BlockusBlockCollections.TINTED_SHRINGLES) {
      ExtShapeBlockus.tryConsume(supplier, bssTypes -> create(bssTypes).setCommonFenceSettings(Items.FLINT).setRecipeGroup(blockShape -> "shingles_" + blockShape.asString()).build());
    }

    for (var supplier : BlockusBlockCollections.PATTERNED_WOOLS) {
      ExtShapeBlockus.tryConsume(supplier, patternWoolTypes -> FACTORY.createConstructionOnly(patternWoolTypes.block).addExtraTag(BlockusBlockTags.ALL_PATTERNED_WOOLS).without(BlockShape.STAIRS, BlockShape.SLAB).with(BlockShape.WALL).addPostBuildConsumer((blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 30, 60)).addPostBuildConsumer((blockShape, blockBuilder) -> FuelRegistry.INSTANCE.add(blockBuilder.instance, (int) (100 * blockShape.logicalCompleteness))).setRecipeGroup(blockShape -> "patterned_wool_" + blockShape.asString()).build());
    }
    for (var supplier : BlockusBlockCollections.GLAZED_TERRACOTTA_PILLARS) {
      ExtShapeBlockus.tryConsume(supplier, block -> FACTORY.createConstructionOnly(block).with(BlockShape.WALL).addExtraTag(BlockusBlockTags.GLAZED_TERRACOTTA_PILLARS).setRecipeGroup(blockShape -> "glazed_terracotta_pillar_" + blockShape.asString()).setPillar().build());
    }

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.THATCH, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.THATCH).addExtraTag(BlockTags.HOE_MINEABLE).addExtraTag(addWallToUnmineableTag).addPostBuildConsumer((blockShape, blockBuilder) -> {
      FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 60, 20);
      CompostingChanceRegistry.INSTANCE.add(blockBuilder.instance, blockShape.logicalCompleteness * 0.75f);
    }).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.PAPER_BLOCK, block -> create(block).addExtraTag(addWallToUnmineableTag).addPostBuildConsumer((blockShape, blockBuilder) -> {
      FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 30, 60);
      FuelRegistry.INSTANCE.add(blockBuilder.instance, (int) (blockShape.logicalCompleteness * 100));
    }).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.BURNT_PAPER_BLOCK, block -> create(block).addExtraTag(addWallToUnmineableTag).addPostBuildConsumer((blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 5, 60)).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.IRON_PLATING, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.PLATINGS).without(BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.GOLD_PLATING, bssTypes -> create(bssTypes).addExtraTag(BlockusBlockTags.PLATINGS).addExtraTag(BlockTags.GUARDED_BY_PIGLINS).without(BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.BUTTON).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHARCOAL_BLOCK, block -> create(block).setCommonFenceSettings(Items.CHARCOAL).addExtraTag(BlockTags.INFINIBURN_OVERWORLD).addExtraTag(BlockTags.PICKAXE_MINEABLE).addPostBuildConsumer((blockShape, blockBuilder) -> FuelRegistry.INSTANCE.add(blockBuilder.instance, (int) (blockShape.logicalCompleteness * 16000))).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SUGAR_BLOCK, block -> create(block).setCommonFenceSettings(Items.SUGAR).addExtraTag(BlockTags.SHOVEL_MINEABLE).addExtraTag(addWallToUnmineableTag).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ENDER_BLOCK, block -> create(block).setCommonFenceSettings(Items.ENDER_PEARL).addExtraTag(BlockTags.PICKAXE_MINEABLE).addExtraTag(BlockTags.NEEDS_STONE_TOOL).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ROTTEN_FLESH_BLOCK, block -> create(block).setCommonFenceSettings(Items.ROTTEN_FLESH).addExtraTag(BlockTags.HOE_MINEABLE).addExtraTag(addWallToUnmineableTag).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.MEMBRANE_BLOCK, block -> create(block).setCommonFenceSettings(Items.PHANTOM_MEMBRANE).addExtraTag(addWallToUnmineableTag).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.NETHER_STAR_BLOCK, block -> FACTORY.createAllShapes(block).withoutRedstone().setCommonFenceSettings(Items.NETHER_STAR).addExtraTag(BlockTags.PICKAXE_MINEABLE).addExtraTag(BlockTags.NEEDS_IRON_TOOL).addExtraTag(BlockTags.DRAGON_IMMUNE).withExtension(BlockExtension.builder().setSteppedOnCallback((world, pos, state, entity) -> {
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
          ExtShapeBlockus.tryConsume(() -> Blockus.STEPPED_ON_TEXT, tooltip::add);
          tooltip.add(ScreenTexts.space().append(StatusEffects.REGENERATION.getName()).append(" IV").formatted(Formatting.BLUE));
          tooltip.add(ScreenTexts.space().append(StatusEffects.ABSORPTION.getName()).append(" IV").formatted(Formatting.BLUE).append(" - 00:45"));
          tooltip.add(ScreenTexts.space().append(StatusEffects.STRENGTH.getName()).append(" III").formatted(Formatting.BLUE).append(" - 00:04"));
        }
      });
    }).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.STARS_BLOCK, block -> create(block).setCommonFenceSettings(Items.COAL).addExtraTag(BlockTags.PICKAXE_MINEABLE).build());
  }

  private static void buildStoneBlocksWithButton(BlocksBuilder blocksBuilder) {
    blocksBuilder.setFenceSettings(stoneFenceSettings).build();
  }

  private static void buildStoneBlocksWithoutButton(BlocksBuilder blocksBuilder) {
    blocksBuilder.setFenceSettings(stoneFenceSettings).without(BlockShape.BUTTON).build();
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
