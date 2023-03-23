package pers.solid.extshape.blockus;

import com.brand.blockus.content.BlockusBlocks;
import com.brand.blockus.content.BlockusItems;
import com.brand.blockus.content.types.BSSTypes;
import com.brand.blockus.content.types.BSSWTypes;
import com.brand.blockus.content.types.WoodTypesF;
import com.brand.blockus.content.types.WoodTypesFP;
import com.brand.blockus.tags.BlockusBlockTags;
import com.brand.blockus.tags.BlockusItemTags;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Rarity;
import net.minecraft.util.Util;
import pers.solid.extshape.ExtShapeBlockItem;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.builder.BlocksBuilder;
import pers.solid.extshape.builder.BlocksBuilderFactory;
import pers.solid.extshape.tag.BlockTagPreparation;
import pers.solid.extshape.tag.ExtShapeTags;
import pers.solid.extshape.util.ButtonSettings;
import pers.solid.extshape.util.FenceSettings;

import static pers.solid.extshape.blockus.ExtShapeBlockusTags.ofBlockAndItem;
import static pers.solid.extshape.blockus.ExtShapeBlockusTags.ofBlockOnly;

public final class ExtShapeBlockusBlocks {
  static final ObjectSet<Block> BLOCKUS_BLOCKS = new ObjectLinkedOpenHashSet<>();
  static final ObjectSet<Block> BLOCKUS_BASE_BLOCKS = new ObjectLinkedOpenHashSet<>();
  private static final BlocksBuilderFactory FACTORY = Util.make(new BlocksBuilderFactory(), blocksBuilderFactory -> {
    blocksBuilderFactory.defaultNamespace = ExtShapeBlockus.NAMESPACE;
    blocksBuilderFactory.instanceCollection = BLOCKUS_BLOCKS;
    blocksBuilderFactory.baseBlockCollection = BLOCKUS_BASE_BLOCKS;
    blocksBuilderFactory.defaultTags = ImmutableMap.<BlockShape, BlockTagPreparation>builder()
        .put(BlockShape.STAIRS, ExtShapeBlockusTags.STAIRS)
        .put(BlockShape.SLAB, ExtShapeBlockusTags.SLAB)
        .put(BlockShape.FENCE, ExtShapeBlockusTags.FENCES)
        .put(BlockShape.FENCE_GATE, ExtShapeBlockusTags.FENCE_GATES)
        .put(BlockShape.WALL, ExtShapeBlockusTags.WALLS)
        .put(BlockShape.BUTTON, ExtShapeBlockusTags.BUTTONS)
        .put(BlockShape.PRESSURE_PLATE, ExtShapeBlockusTags.PRESSURE_PLATES)
        .put(BlockShape.VERTICAL_SLAB, ExtShapeBlockusTags.VERTICAL_SLABS)
        .put(BlockShape.QUARTER_PIECE, ExtShapeBlockusTags.QUARTER_PIECES)
        .put(BlockShape.VERTICAL_STAIRS, ExtShapeBlockusTags.VERTICAL_STAIRS)
        .put(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeBlockusTags.VERTICAL_QUARTER_PIECES)
        .build();
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
    // TODO: 2023/3/23, 023 tags for stone buttons
    BlockusBlockBiMaps.importFromBlockus();
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.STONE_TILES, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.STONE_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_STONE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.STONE_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.STONE_CIRCULAR_PAVING, baseBlock9 -> buildCircularPavingBlock(FACTORY.createEmpty(baseBlock9).addTagToAddEach(ofBlockOnly(BlockusBlockTags.STONE_BLOCKS))));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ANDESITE_BRICKS, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.ANDESITE_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_ANDESITE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.ANDESITE_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_ANDESITE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.ANDESITE_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ANDESITE_CIRCULAR_PAVING, block -> buildCircularPavingBlock(FACTORY.createEmpty(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.ANDESITE_BLOCKS))));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.DIORITE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.DIORITE_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_DIORITE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.DIORITE_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_DIORITE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.DIORITE_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.DIORITE_CIRCULAR_PAVING, baseBlock7 -> buildCircularPavingBlock(FACTORY.createEmpty(baseBlock7).addTagToAddEach(ofBlockOnly(BlockusBlockTags.DIORITE_BLOCKS))));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.GRANITE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.GRANITE_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_GRANITE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.GRANITE_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_GRANITE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.GRANITE_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.GRANITE_CIRCULAR_PAVING, baseBlock6 -> buildCircularPavingBlock(FACTORY.createEmpty(baseBlock6).addTagToAddEach(ofBlockOnly(BlockusBlockTags.GRANITE_BLOCKS))));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_MUD_BRICKS, block -> create(block).addTagToAddEach(ExtShapeBlockusTags.PICKAXE_MINEABLE)
        .without(BlockShape.BUTTON)
        .setCommonFenceSettings(Items.MUD).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_DRIPSTONE, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.DRIPSTONE_BLOCKS))
        .setCommonFenceSettings(Items.POINTED_DRIPSTONE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.DRIPSTONE_BRICKS, bsswTypes -> create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.DRIPSTONE_BLOCKS))
        .without(BlockShape.BUTTON)
        .setCommonFenceSettings(Items.POINTED_DRIPSTONE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_DRIPSTONE, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.DRIPSTONE_BLOCKS))
        .without(BlockShape.BUTTON)
        .setCommonFenceSettings(Items.POINTED_DRIPSTONE).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_TUFF, bssTypes -> buildStoneBlocksWithButton(create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.TUFF_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.TUFF_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.TUFF_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_TUFF, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.TUFF_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_TUFF_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.TUFF_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.TUFF_CIRCULAR_PAVING, baseBlock5 -> buildCircularPavingBlock(FACTORY.createEmpty(baseBlock5).addTagToAddEach(ofBlockOnly(BlockusBlockTags.TUFF_BLOCKS))));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.MOSSY_DEEPSLATE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.DEEPSLATE_BLOCKS))
    ));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_DEEPSLATE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.DEEPSLATE_BLOCKS))
    ));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.DEEPSLATE_CIRCULAR_PAVING, baseBlock4 -> buildCircularPavingBlock(FACTORY.createEmpty(baseBlock4).addTagToAddEach(ofBlockOnly(BlockusBlockTags.DEEPSLATE_BLOCKS))));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_SCULK, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.SCULK_BLOCKS))
        .setCommonFenceSettings(Items.SCULK)
        .build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SCULK_BRICKS, bsswTypes -> create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.SCULK_BLOCKS))
        .without(BlockShape.BUTTON)
        .setCommonFenceSettings(Items.SCULK)
        .build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_SCULK_BRICKS, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.SCULK_BLOCKS))
        .without(BlockShape.BUTTON)
        .setCommonFenceSettings(Items.SCULK)
        .build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_AMETHYST, block -> FACTORY.createConstructionOnly(block)
        .addTagToAddEach(ofBlockOnly(BlockusBlockTags.AMETHYST_BLOCKS))
        .withExtension(BlockExtension.AMETHYST)
        .build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.AMETHYST_BRICKS, block -> FACTORY.createConstructionOnly(block)
        .addTagToAddEach(ofBlockOnly(BlockusBlockTags.AMETHYST_BLOCKS))
        .withExtension(BlockExtension.AMETHYST)
        .build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_BLACKSTONE_TILES, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).setBlockSetType(BlockSetType.POLISHED_BLACKSTONE).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BLACKSTONE_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CRIMSON_WARTY_BLACKSTONE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BLACKSTONE_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.WARPED_WARTY_BLACKSTONE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BLACKSTONE_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_POLISHED_BLACKSTONE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BLACKSTONE_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_BLACKSTONE_CIRCULAR_PAVING, block -> buildCircularPavingBlock(FACTORY.createEmpty(block)
        .setBlockSetType(BlockSetType.POLISHED_BLACKSTONE).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BLACKSTONE_BLOCKS))));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ROUGH_BASALT, bssTypes -> buildStoneBlocksWithButton(create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BASALT_BLOCKS))
    ));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_BASALT_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BASALT_BLOCKS)))
    );
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_POLISHED_BASALT_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BASALT_BLOCKS)))
    );
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CRIMSON_WART_BRICKS, bsswTypes -> create(bsswTypes).addTagToAddEach(ExtShapeBlockusTags.PICKAXE_MINEABLE)
        .setCommonFenceSettings(Items.BRICK)
        .without(BlockShape.BUTTON)
        .build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.WARPED_WART_BRICKS, bsswTypes -> create(bsswTypes).addTagToAddEach(ExtShapeBlockusTags.PICKAXE_MINEABLE)
        .setCommonFenceSettings(Items.BRICK)
        .without(BlockShape.BUTTON)
        .build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.LIMESTONE, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.LIMESTONE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_LIMESTONE, bssTypes -> buildStoneBlocksWithButton(create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.LIMESTONE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.LIMESTONE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.LIMESTONE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_LIMESTONE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.LIMESTONE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.LIMESTONE_TILES, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.LIMESTONE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_LIMESTONE, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.LIMESTONE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.LIMESTONE_SQUARES, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.LIMESTONE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.LIMESTONE_CIRCULAR_PAVING, baseBlock3 -> buildCircularPavingBlock(FACTORY.createEmpty(baseBlock3).addTagToAddEach(ofBlockOnly(BlockusBlockTags.LIMESTONE))));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.MARBLE, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.MARBLE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_MARBLE, bssTypes -> buildStoneBlocksWithButton(create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.MARBLE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.MARBLE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.MARBLE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_MARBLE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.MARBLE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.MARBLE_TILES, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.MARBLE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_MARBLE, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.MARBLE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.MARBLE_SQUARES, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.MARBLE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.MARBLE_CIRCULAR_PAVING, baseBlock2 -> buildCircularPavingBlock(FACTORY.createEmpty(baseBlock2).addTagToAddEach(ofBlockOnly(BlockusBlockTags.MARBLE))));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.BLUESTONE, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BLUESTONE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_BLUESTONE, bssTypes -> buildStoneBlocksWithButton(create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BLUESTONE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.BLUESTONE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BLUESTONE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_BLUESTONE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BLUESTONE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.BLUESTONE_TILES, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BLUESTONE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_BLUESTONE, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BLUESTONE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.BLUESTONE_SQUARES, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BLUESTONE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.BLUESTONE_CIRCULAR_PAVING, baseBlock1 -> buildCircularPavingBlock(FACTORY.createEmpty(baseBlock1).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BLUESTONE))));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.VIRIDITE, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.VIRIDITE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_VIRIDITE, bssTypes -> buildStoneBlocksWithButton(create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.VIRIDITE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.VIRIDITE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.VIRIDITE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_VIRIDITE_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.VIRIDITE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.VIRIDITE_TILES, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.VIRIDITE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_VIRIDITE, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.VIRIDITE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.VIRIDITE_SQUARES, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.VIRIDITE))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.VIRIDITE_CIRCULAR_PAVING, baseBlock -> buildCircularPavingBlock(FACTORY.createEmpty(baseBlock).addTagToAddEach(ofBlockOnly(BlockusBlockTags.VIRIDITE))));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.LAVA_BRICKS, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.LAVA_BRICKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_LAVA_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.LAVA_BRICKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.LAVA_POLISHED_BLACKSTONE_BRICKS, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.LAVA_POLISHED_BLACKSTONE_BRICKS)).setBlockSetType(BlockSetType.POLISHED_BLACKSTONE).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BLACKSTONE_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_LAVA_POLISHED_BLACKSTONE, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.LAVA_POLISHED_BLACKSTONE_BRICKS)).setBlockSetType(BlockSetType.POLISHED_BLACKSTONE).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BLACKSTONE_BLOCKS))));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.WATER_BRICKS, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.WATER_BRICKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_WATER_BRICKS, block -> buildStoneBlocksWithButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.WATER_BRICKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SNOW_BRICKS, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.SNOW_BRICKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ICE_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.ICE_BRICKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.OBSIDIAN_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.OBSIDIAN)).setButtonSettings(ButtonSettings.HARD)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_OBSIDIAN_BRICKS, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.OBSIDIAN)).setButtonSettings(ButtonSettings.HARD)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.OBSIDIAN_CIRCULAR_PAVING, block -> buildCircularPavingBlock(FACTORY.createEmpty(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.OBSIDIAN)).setButtonSettings(ButtonSettings.HARD)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.GLOWING_OBSIDIAN, block -> buildStoneBlocksWithButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.OBSIDIAN)).setButtonSettings(ButtonSettings.HARD)));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_NETHERRACK, bssTypes -> buildStoneBlocksWithButton(create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.NETHERRACK_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.NETHERRACK_BRICKS, bssTypes -> buildStoneBlocksWithoutButton(create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.NETHERRACK_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.NETHERRACK_CIRCULAR_PAVING, block -> buildCircularPavingBlock(FACTORY.createEmpty(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.NETHERRACK_BLOCKS))));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.QUARTZ_TILES, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.QUARTZ_BLOCKS)).setCommonFenceSettings(Items.QUARTZ)));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.QUARTZ_CIRCULAR_PAVING, block -> buildCircularPavingBlock(FACTORY.createEmpty(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.QUARTZ_BLOCKS)).setCommonFenceSettings(Items.QUARTZ)));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.MAGMA_BRICKS, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.MAGMA_BRICKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_MAGMA_BRICKS, bsswTypes -> buildStoneBlocksWithButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.MAGMA_BRICKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_MAGMA_BRICKS, block -> buildStoneBlocksWithoutButton(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.MAGMA_BRICKS)).without(BlockShape.FENCE, BlockShape.FENCE_GATE)));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.BLAZE_BRICKS, bsswTypes -> create(bsswTypes).setCommonFenceSettings(Items.BLAZE_ROD).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BLAZE_BRICKS)).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.BLAZE_LANTERN, block -> create(block).setCommonFenceSettings(Items.BLAZE_ROD).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_NETHER_BRICKS, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.NETHER_BRICKS)).setCommonFenceSettings(Items.NETHER_BRICK).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_RED_NETHER_BRICKS, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.NETHER_BRICKS)).setCommonFenceSettings(Items.NETHER_BRICK).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_NETHER_BRICKS, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.NETHER_BRICKS)).setCommonFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_RED_NETHER_BRICKS, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.NETHER_BRICKS)).setCommonFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.NETHER_TILES, block -> create(block).setCommonFenceSettings(Items.NETHER_BRICK).addTagToAddEach(ExtShapeBlockusTags.PICKAXE_MINEABLE).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHARRED_NETHER_BRICKS, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.NETHER_BRICKS)).setCommonFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_CHARRED_NETHER_BRICKS, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.NETHER_BRICKS)).setCommonFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.TEAL_NETHER_BRICKS, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.NETHER_BRICKS)).setCommonFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_TEAL_NETHER_BRICKS, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.NETHER_BRICKS)).setCommonFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_TEAL_NETHER_BRICKS, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.NETHER_BRICKS)).setCommonFenceSettings(Items.NETHER_BRICK).without(BlockShape.BUTTON).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_PRISMARINE, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.PRISMARINE_BLOCKS)).setCommonFenceSettings(Items.PRISMARINE_SHARD).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.PRISMARINE_CIRCULAR_PAVING, block -> buildCircularPavingBlock(create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.PRISMARINE_BLOCKS))));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_DARK_PRISMARINE, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.PRISMARINE_BLOCKS)).setCommonFenceSettings(Items.PRISMARINE_SHARD).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.LARGE_BRICKS, bsswTypes -> create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BRICKS_BLOCKS)).setCommonFenceSettings(Items.BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_BRICKS, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BRICKS_BLOCKS)).setCommonFenceSettings(Items.BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SOAKED_BRICKS, bsswTypes -> create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BRICKS_BLOCKS)).setCommonFenceSettings(Items.BRICK).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_SOAKED_BRICKS, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BRICKS_BLOCKS)).setCommonFenceSettings(Items.BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHARRED_BRICKS, bsswTypes -> create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BRICKS_BLOCKS)).setCommonFenceSettings(Items.BRICK).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_CHARRED_BRICKS, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.BRICKS_BLOCKS)).setCommonFenceSettings(Items.BRICK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SANDY_BRICKS, bsswTypes -> create(bsswTypes).setCommonFenceSettings(Items.BRICK).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_SANDY_BRICKS, block -> create(block).setCommonFenceSettings(Items.BRICK).without(BlockShape.BUTTON).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ROUGH_SANDSTONE, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.SANDSTONE)).setCommonFenceSettings(Items.SAND).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SANDSTONE_BRICKS, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.SANDSTONE)).setCommonFenceSettings(Items.SAND).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_SANDSTONE_BRICKS, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.SANDSTONE)).setCommonFenceSettings(Items.SAND).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ROUGH_RED_SANDSTONE, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.RED_SANDSTONE)).setCommonFenceSettings(Items.RED_SAND).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.RED_SANDSTONE_BRICKS, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.RED_SANDSTONE)).setCommonFenceSettings(Items.RED_SAND).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_RED_SANDSTONE_BRICKS, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.RED_SANDSTONE)).setCommonFenceSettings(Items.RED_SAND).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SOUL_SANDSTONE, bsswTypes -> FACTORY.createConstructionOnly(bsswTypes.block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.SOUL_SANDSTONE)).setCommonFenceSettings(Items.SOUL_SAND).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ROUGH_SOUL_SANDSTONE, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.SOUL_SANDSTONE)).setCommonFenceSettings(Items.SOUL_SAND).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SOUL_SANDSTONE_BRICKS, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.SOUL_SANDSTONE)).setCommonFenceSettings(Items.SOUL_SAND).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_SOUL_SANDSTONE_BRICKS, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.SOUL_SANDSTONE)).setCommonFenceSettings(Items.SOUL_SAND).without(BlockShape.BUTTON).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMOOTH_SOUL_SANDSTONE, bssTypes -> create(bssTypes).setCommonFenceSettings(Items.SOUL_SAND).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CUT_SOUL_SANDSTONE, bssTypes -> create(bssTypes).setCommonFenceSettings(Items.SOUL_SAND).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_SOUL_SANDSTONE, bssTypes -> create(bssTypes).setCommonFenceSettings(Items.SOUL_SAND).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.RAINBOW_BLOCK, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.RAINBOW_BLOCKS)).setCommonFenceSettings(BlockusItems.RAINBOW_PETAL).setPillar().build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.RAINBOW_BRICKS, bsswTypes -> create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.RAINBOW_BLOCKS)).setCommonFenceSettings(BlockusItems.RAINBOW_PETAL).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.RAINBOW_GLOWSTONE, block -> create(block).setCommonFenceSettings(BlockusItems.RAINBOW_PETAL).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HONEYCOMB_BRICKS, bsswTypes -> create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.HONEYCOMB_BLOCKS)).setCommonFenceSettings(Items.HONEYCOMB).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.PURPUR_BRICKS, bsswTypes -> create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.PURPUR_BLOCKS)).setCommonFenceSettings(Items.PURPUR_BLOCK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_PURPUR_BRICKS, bsswTypes -> create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.PURPUR_BLOCKS)).setCommonFenceSettings(Items.PURPUR_BLOCK).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_PURPUR, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.PURPUR_BLOCKS)).setCommonFenceSettings(Items.PURPUR_BLOCK).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_PURPUR, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.PURPUR_BLOCKS)).setCommonFenceSettings(Items.PURPUR_BLOCK).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.PURPUR_SQUARES, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.PURPUR_BLOCKS)).setCommonFenceSettings(Items.PURPUR_BLOCK).without(BlockShape.BUTTON).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.PHANTOM_PURPUR_BRICKS, bsswTypes -> create(bsswTypes).setCommonFenceSettings(Items.PHANTOM_MEMBRANE).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_PHANTOM_PURPUR_BRICKS, bsswTypes -> create(bsswTypes).setCommonFenceSettings(Items.PHANTOM_MEMBRANE).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.PHANTOM_PURPUR_BLOCK, bssTypes -> create(bssTypes).setCommonFenceSettings(Items.PHANTOM_MEMBRANE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_PHANTOM_PURPUR, bssTypes -> create(bssTypes).setCommonFenceSettings(Items.PHANTOM_MEMBRANE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_PHANTOM_PURPUR, block -> create(block).setCommonFenceSettings(Items.PHANTOM_MEMBRANE).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.PHANTOM_PURPUR_SQUARES, block -> create(block).setCommonFenceSettings(Items.PHANTOM_MEMBRANE).without(BlockShape.BUTTON).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.POLISHED_END_STONE, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.END_STONE_BLOCKS)).setCommonFenceSettings(Items.END_STONE_BRICKS).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMALL_END_STONE_BRICKS, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.END_STONE_BLOCKS)).setCommonFenceSettings(Items.END_STONE_BRICKS).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHISELED_END_STONE_BRICKS, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.END_STONE_BLOCKS)).setCommonFenceSettings(Items.END_STONE_BRICKS).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.HERRINGBONE_END_STONE_BRICKS, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.END_STONE_BLOCKS)).setCommonFenceSettings(Items.END_STONE_BRICKS).without(BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.END_TILES, bssTypes -> create(bssTypes).setCommonFenceSettings(Items.END_STONE_BRICKS).addTagToAddEach(ExtShapeBlockusTags.PICKAXE_MINEABLE).without(BlockShape.BUTTON).build());

    var logTags = ImmutableMap.<BlockShape, BlockTagPreparation>builder()
        .put(BlockShape.STAIRS, ofBlockAndItem(ExtShapeTags.LOG_STAIRS.identifier))
        .put(BlockShape.SLAB, ofBlockAndItem(ExtShapeTags.LOG_SLABS.identifier))
        .put(BlockShape.VERTICAL_SLAB, ofBlockAndItem(ExtShapeTags.LOG_VERTICAL_SLABS.identifier))
        .put(BlockShape.VERTICAL_STAIRS, ofBlockAndItem(ExtShapeTags.LOG_VERTICAL_STAIRS.identifier))
        .put(BlockShape.QUARTER_PIECE, ofBlockAndItem(ExtShapeTags.LOG_QUARTER_PIECES.identifier))
        .put(BlockShape.VERTICAL_QUARTER_PIECE, ofBlockAndItem(ExtShapeTags.LOG_VERTICAL_QUARTER_PIECES.identifier))
        .put(BlockShape.FENCE, ofBlockAndItem(ExtShapeTags.LOG_FENCES.identifier))
        .put(BlockShape.FENCE_GATE, ofBlockAndItem(ExtShapeTags.LOG_FENCE_GATES.identifier))
        .put(BlockShape.BUTTON, ofBlockAndItem(ExtShapeTags.LOG_BUTTONS.identifier))
        .put(BlockShape.PRESSURE_PLATE, ofBlockAndItem(ExtShapeTags.LOG_PRESSURE_PLATES.identifier))
        .put(BlockShape.WALL, ofBlockAndItem(ExtShapeTags.LOG_WALLS.identifier))
        .build();
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.WHITE_OAK_LOG, block -> FACTORY.createConstructionOnly(block).setTagToAddForShape(logTags).addTagToAddEach(ofBlockAndItem(ExtShapeTags.LOG_BLOCKS.identifier)).setPillar().build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.STRIPPED_WHITE_OAK_LOG, block -> FACTORY.createConstructionOnly(block).setTagToAddForShape(logTags).addTagToAddEach(ofBlockAndItem(ExtShapeTags.LOG_BLOCKS.identifier)).setPillar().build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.WHITE_OAK_WOOD, block -> FACTORY.createAllShapes(block).setTagToAddForShape(logTags).setButtonSettings(ButtonSettings.wood(BlockSetType.OAK))
        .setPillar().build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.STRIPPED_WHITE_OAK_WOOD, block -> FACTORY.createAllShapes(block).setTagToAddForShape(logTags).setButtonSettings(ButtonSettings.wood(BlockSetType.OAK)).setPillar().build());

    var woodenTags = ImmutableMap.<BlockShape, BlockTagPreparation>builder()
        .put(BlockShape.STAIRS, ofBlockAndItem(ExtShapeTags.WOODEN_STAIRS.identifier))
        .put(BlockShape.SLAB, ofBlockAndItem(ExtShapeTags.WOODEN_SLABS.identifier))
        .put(BlockShape.VERTICAL_SLAB, ofBlockAndItem(ExtShapeTags.WOODEN_VERTICAL_SLABS.identifier))
        .put(BlockShape.VERTICAL_STAIRS, ofBlockAndItem(ExtShapeTags.WOODEN_VERTICAL_STAIRS.identifier))
        .put(BlockShape.QUARTER_PIECE, ofBlockAndItem(ExtShapeTags.WOODEN_QUARTER_PIECES.identifier))
        .put(BlockShape.VERTICAL_QUARTER_PIECE, ofBlockAndItem(ExtShapeTags.WOODEN_VERTICAL_QUARTER_PIECES.identifier))
        .put(BlockShape.FENCE, ofBlockAndItem(ExtShapeTags.WOODEN_FENCES.identifier))
        .put(BlockShape.PRESSURE_PLATE, ofBlockOnly(ExtShapeTags.PRESSURE_PLATES.identifier))
        .put(BlockShape.BUTTON, ofBlockAndItem(ExtShapeTags.WOODEN_BUTTONS.identifier))
        .put(BlockShape.WALL, ofBlockAndItem(ExtShapeTags.WOODEN_WALLS.identifier))
        .build();
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.WHITE_OAK, woodTypesF -> create(woodTypesF).setTagToAddForShape(woodenTags).setButtonSettings(ButtonSettings.wood(BlockSetType.OAK)).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.BAMBOO, woodTypesF -> create(woodTypesF).setTagToAddForShape(woodenTags).setButtonSettings(ButtonSettings.BAMBOO).setCommonFenceSettings(Items.BAMBOO).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHARRED, woodTypesFP -> create(woodTypesFP).setTagToAddForShape(woodenTags).setButtonSettings(ButtonSettings.wood(BlockSetType.OAK)).setCommonFenceSettings(Items.BAMBOO).build());

    for (var supplier : BlockusBlockCollections.HERRINGBONE_PLANKS) {
      ExtShapeBlockus.tryConsume(supplier, block -> FACTORY.createConstructionOnly(block).with(BlockShape.WALL).setTagToAddForShape(woodenTags).build());
    }
    for (var supplier : BlockusBlockCollections.SMALL_LOGS) {
      ExtShapeBlockus.tryConsume(supplier, block -> FACTORY.createConstructionOnly(block).setPillar().setTagToAddForShape(logTags).build());
    }
    for (var supplier : BlockusBlockCollections.STAINED_STONE_BRICKS) {
      ExtShapeBlockus.tryConsume(supplier, bsswTypes -> buildStoneBlocksWithoutButton(create(bsswTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.STAINED_STONE_BRICKS))));
    }
    for (var supplier : BlockusBlockCollections.CONCRETE_BRICKS) {
      ExtShapeBlockus.tryConsume(supplier, concreteTypes -> FACTORY.createConstructionOnly(concreteTypes.block).without(BlockShape.BUTTON, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL).addTagToAddEach(ofBlockOnly(BlockusBlockTags.CONCRETE_BLOCKS)).build());
      ExtShapeBlockus.tryConsume(supplier, concreteTypes -> FACTORY.createConstructionOnly(concreteTypes.chiseled).without(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE).addTagToAddEach(ofBlockOnly(BlockusBlockTags.CONCRETE_BLOCKS)).build());
    }

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SHINGLES, block -> create(block).addTagToAddEach(ofBlockOnly(BlockusBlockTags.SHINGLES)).setCommonFenceSettings(Items.FLINT).build());
    for (var supplier : BlockusBlockCollections.TINTED_SHRINGLES) {
      ExtShapeBlockus.tryConsume(supplier, bssTypes -> create(bssTypes).setCommonFenceSettings(Items.FLINT).build());
    }

    for (var supplier : BlockusBlockCollections.PATTERNED_WOOLS) {
      ExtShapeBlockus.tryConsume(supplier, patternWoolTypes -> FACTORY.createConstructionOnly(patternWoolTypes.block).addTagToAddEach(ExtShapeBlockusTags.ofBlockAndItem(BlockusBlockTags.ALL_PATTERNED_WOOLS, BlockusItemTags.ALL_PATTERNED_WOOLS)).without(BlockShape.STAIRS, BlockShape.SLAB).with(BlockShape.WALL).build());
    }
    for (var supplier : BlockusBlockCollections.GLAZED_TERRACOTTA_PILLARS) {
      ExtShapeBlockus.tryConsume(supplier, block -> FACTORY.createConstructionOnly(block).with(BlockShape.WALL).addTagToAddEach(ofBlockOnly(BlockusBlockTags.GLAZED_TERRACOTTA_PILLARS)).setPillar().build());
    }

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.THATCH, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.THATCH)).addTagToAddEach(ExtShapeBlockusTags.HOE_MINEABLE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.PAPER_BLOCK, block -> create(block).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.BURNT_PAPER_BLOCK, block -> create(block).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.IRON_PLATING, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.PLATINGS)).without(BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.BUTTON).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.GOLD_PLATING, bssTypes -> create(bssTypes).addTagToAddEach(ofBlockOnly(BlockusBlockTags.PLATINGS)).addTagToAddEach(ofBlockOnly(BlockTags.GUARDED_BY_PIGLINS)).without(BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.BUTTON).build());

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.CHARCOAL_BLOCK, block -> create(block).setCommonFenceSettings(Items.CHARCOAL).addTagToAddEach(ofBlockOnly(BlockTags.INFINIBURN_OVERWORLD)).addTagToAddEach(ExtShapeBlockusTags.PICKAXE_MINEABLE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SUGAR_BLOCK, block -> create(block).setCommonFenceSettings(Items.SUGAR).addTagToAddEach(ExtShapeBlockusTags.SHOVEL_MINEABLE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ENDER_BLOCK, block -> create(block).setCommonFenceSettings(Items.ENDER_PEARL).addTagToAddEach(ExtShapeBlockusTags.PICKAXE_MINEABLE).addTagToAddEach(ofBlockOnly(BlockTags.NEEDS_STONE_TOOL)).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ROTTEN_FLESH_BLOCK, block -> create(block).setCommonFenceSettings(Items.ROTTEN_FLESH).addTagToAddEach(ExtShapeBlockusTags.HOE_MINEABLE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.MEMBRANE_BLOCK, block -> create(block).setCommonFenceSettings(Items.PHANTOM_MEMBRANE).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.NETHER_STAR_BLOCK, block -> FACTORY.createConstructionOnly(block).setCommonFenceSettings(Items.NETHER_STAR).addTagToAddEach(ExtShapeBlockusTags.PICKAXE_MINEABLE).addTagToAddEach(ofBlockOnly(BlockTags.NEEDS_IRON_TOOL)).addTagToAddEach(ofBlockOnly(BlockTags.DRAGON_IMMUNE)).withExtension(BlockExtension.builder().setSteppedOnCallback((world, pos, state, entity) -> {
      if (entity.getType() == EntityType.PLAYER) {
        ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 1, 4, true, false, false));
        ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 100, 3, true, false, true));
        ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 1200, 4, true, false, true));
      }
    }).build()).setConsumesEach((blockShape, builder) -> {
      builder.setItemSettings(new FabricItemSettings().rarity(Rarity.RARE));
      builder.setItemInstanceSupplier(builder0 -> new ExtShapeBlockItem(builder0.instance, builder0.itemSettings.rarity(Rarity.UNCOMMON)) {
        @Override
        public boolean hasGlint(ItemStack stack) {
          return true;
        }
      });
    }).build());
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.STARS_BLOCK, block -> create(block).setCommonFenceSettings(Items.COAL).addTagToAddEach(ExtShapeBlockusTags.PICKAXE_MINEABLE).build());
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
