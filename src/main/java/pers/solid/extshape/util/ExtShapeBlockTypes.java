package pers.solid.extshape.util;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import pers.solid.extshape.ExtShape;

public final class ExtShapeBlockTypes {
  public static final WoodType STONE_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .soundGroup(BlockSoundGroup.STONE)
      .build(new Identifier(ExtShape.MOD_ID, "stone"), BlockSetType.STONE);

  public static final BlockSetType AMETHYST_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK)
      .soundGroup(BlockSoundGroup.AMETHYST_BLOCK)
      .build(new Identifier(ExtShape.MOD_ID, "amethyst"));
  public static final WoodType AMETHYST_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .fenceGateOpenSound(SoundEvents.BLOCK_AMETHYST_BLOCK_HIT)
      .fenceGateCloseSound(SoundEvents.BLOCK_AMETHYST_BLOCK_HIT)
      .hangingSignSoundGroup(BlockSoundGroup.AMETHYST_BLOCK)
      .soundGroup(BlockSoundGroup.AMETHYST_BLOCK)
      .build(new Identifier(ExtShape.MOD_ID, "amethyst"), AMETHYST_BLOCK_SET_TYPE);

  public static final BlockSetType SOFT_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .build(new Identifier(ExtShape.MOD_ID, "soft"));

  public static final BlockSetType GRAVEL_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK)
      .soundGroup(BlockSoundGroup.GRAVEL)
      .build(new Identifier(ExtShape.MOD_ID, "gravel"));
  public static final WoodType GRAVEL_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .soundGroup(BlockSoundGroup.GRAVEL)
      .build(new Identifier(ExtShape.MOD_ID, "gravel"), GRAVEL_BLOCK_SET_TYPE);

  public static final BlockSetType WOOL_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(SOFT_BLOCK_SET_TYPE)
      .soundGroup(BlockSoundGroup.WOOL)
      .buttonClickOnSound(SoundEvents.BLOCK_WOOL_HIT)
      .buttonClickOffSound(SoundEvents.BLOCK_WOOL_HIT)
      .pressurePlateClickOnSound(SoundEvents.BLOCK_WOOL_HIT)
      .pressurePlateClickOffSound(SoundEvents.BLOCK_WOOL_HIT)
      .build(new Identifier(ExtShape.MOD_ID, "wool"));
  public static final WoodType WOOL_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .soundGroup(BlockSoundGroup.WOOL)
      .fenceGateOpenSound(SoundEvents.BLOCK_WOOL_HIT)
      .fenceGateCloseSound(SoundEvents.BLOCK_WOOL_HIT)
      .build(new Identifier(ExtShape.MOD_ID, "wool"), WOOL_BLOCK_SET_TYPE);

  public static final BlockSetType QUARTZ_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .build(new Identifier(ExtShape.MOD_ID, "quartz"));
  public static final WoodType QUARTZ_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .build(new Identifier(ExtShape.MOD_ID, "quartz"), QUARTZ_BLOCK_SET_TYPE);

  public static final BlockSetType METAL_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .soundGroup(BlockSoundGroup.METAL)
      .build(new Identifier(ExtShape.MOD_ID, "metal"));
  public static final WoodType METAL_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .soundGroup(BlockSoundGroup.METAL)
      .build(new Identifier(ExtShape.MOD_ID, "metal"), METAL_BLOCK_SET_TYPE);
  public static final WoodType GOLD_WOOD_TYPE = WoodTypeBuilder.copyOf(METAL_WOOD_TYPE)
      .build(new Identifier(ExtShape.MOD_ID, "gold"), BlockSetType.GOLD);
  public static final WoodType IRON_WOOD_TYPE = WoodTypeBuilder.copyOf(METAL_WOOD_TYPE)
      .build(new Identifier(ExtShape.MOD_ID, "iron"), BlockSetType.IRON);

  public static final BlockSetType HARD_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .build(new Identifier(ExtShape.MOD_ID, "hard"));
  public static final WoodType HARD_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .build(new Identifier(ExtShape.MOD_ID, "hard"), HARD_BLOCK_SET_TYPE);

  public static final WoodType DIAMOND_WOOD_TYPE = WoodTypeBuilder.copyOf(HARD_WOOD_TYPE)
      .soundGroup(BlockSoundGroup.METAL)
      .build(new Identifier(ExtShape.MOD_ID, "diamond"), HARD_BLOCK_SET_TYPE);

  public static final BlockSetType SNOW_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(SOFT_BLOCK_SET_TYPE)
      .soundGroup(BlockSoundGroup.SNOW)
      .buttonClickOnSound(SoundEvents.BLOCK_SNOW_HIT)
      .buttonClickOffSound(SoundEvents.BLOCK_SNOW_HIT)
      .pressurePlateClickOnSound(SoundEvents.BLOCK_SNOW_HIT)
      .pressurePlateClickOffSound(SoundEvents.BLOCK_SNOW_HIT)
      .build(new Identifier(ExtShape.MOD_ID, "snow"));
  public static final WoodType SNOW_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .soundGroup(BlockSoundGroup.SNOW)
      .fenceGateOpenSound(SoundEvents.BLOCK_SNOW_HIT)
      .fenceGateCloseSound(SoundEvents.BLOCK_SNOW_HIT)
      .build(new Identifier(ExtShape.MOD_ID, "snow"), SNOW_BLOCK_SET_TYPE);

  public static final BlockSetType NETHERRACK_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .soundGroup(BlockSoundGroup.NETHERRACK)
      .build(new Identifier(ExtShape.MOD_ID, "netherrack"));
  public static final WoodType NETHERRACK_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .soundGroup(BlockSoundGroup.NETHERRACK)
      .build(new Identifier(ExtShape.MOD_ID, "netherrack"), NETHERRACK_BLOCK_SET_TYPE);

  public static final BlockSetType GLOWSTONE_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK)
      .soundGroup(BlockSoundGroup.GLASS)
      .build(new Identifier(ExtShape.MOD_ID, "glowstone"));
  public static final WoodType GLOWSTONE_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .soundGroup(BlockSoundGroup.GLASS)
      .build(new Identifier(ExtShape.MOD_ID, "glowstone"), ExtShapeBlockTypes.SOFT_BLOCK_SET_TYPE);

  public static final BlockSetType PACKED_MUD_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .soundGroup(BlockSoundGroup.PACKED_MUD)
      .build(new Identifier(ExtShape.MOD_ID, "packed_mud"));
  public static final WoodType PACKED_MUD = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .soundGroup(BlockSoundGroup.PACKED_MUD)
      .build(new Identifier(ExtShape.MOD_ID, "packed_mud"), PACKED_MUD_BLOCK_SET_TYPE);

  public static final BlockSetType MUD_BRICKS_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .soundGroup(BlockSoundGroup.MUD_BRICKS)
      .build(new Identifier(ExtShape.MOD_ID, "mud_bricks"));
  public static final WoodType MUD_BRICKS = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .soundGroup(BlockSoundGroup.MUD_BRICKS)
      .build(new Identifier(ExtShape.MOD_ID, "mud_bricks"), MUD_BRICKS_BLOCK_SET_TYPE);

  public static final BlockSetType NETHER_BRICKS_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .soundGroup(BlockSoundGroup.NETHER_BRICKS)
      .build(new Identifier(ExtShape.MOD_ID, "nether_bricks"));
  public static final WoodType NETHER_BRICKS_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .soundGroup(BlockSoundGroup.NETHER_BRICKS)
      .build(new Identifier(ExtShape.MOD_ID, "nether_bricks"), NETHER_BRICKS_BLOCK_SET_TYPE);

  public static final BlockSetType WART_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(SOFT_BLOCK_SET_TYPE)
      .soundGroup(BlockSoundGroup.WART_BLOCK)
      .buttonClickOnSound(SoundEvents.BLOCK_WART_BLOCK_HIT)
      .buttonClickOffSound(SoundEvents.BLOCK_WART_BLOCK_HIT)
      .pressurePlateClickOnSound(SoundEvents.BLOCK_WART_BLOCK_HIT)
      .pressurePlateClickOffSound(SoundEvents.BLOCK_WART_BLOCK_HIT)
      .build(new Identifier(ExtShape.MOD_ID, "wart_block"));
  public static final WoodType WART_BLOCK_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .soundGroup(BlockSoundGroup.WART_BLOCK)
      .fenceGateOpenSound(SoundEvents.BLOCK_WART_BLOCK_HIT)
      .fenceGateCloseSound(SoundEvents.BLOCK_WART_BLOCK_HIT)
      .build(new Identifier(ExtShape.MOD_ID, "wart_block"), WART_BLOCK_SET_TYPE);

  public static final BlockSetType SHROMLIGHT_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK)
      .soundGroup(BlockSoundGroup.SHROOMLIGHT)
      .build(new Identifier(ExtShape.MOD_ID, "shroomlight"));
  public static final WoodType SHROOMLIGHT_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .soundGroup(BlockSoundGroup.SHROOMLIGHT)
      .build(new Identifier(ExtShape.MOD_ID, "shroomlight"), SHROMLIGHT_BLOCK_SET_TYPE);

  public static final BlockSetType HONEYCOMB_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK)
      .soundGroup(BlockSoundGroup.CORAL)
      .build(new Identifier(ExtShape.MOD_ID, "honeycomb"));
  public static final WoodType HONEYCOMB_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .soundGroup(BlockSoundGroup.CORAL)
      .build(new Identifier(ExtShape.MOD_ID, "honeycomb"), HONEYCOMB_BLOCK_SET_TYPE);

  public static final BlockSetType NETHERITE_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(HARD_BLOCK_SET_TYPE)
      .soundGroup(BlockSoundGroup.NETHERITE)
      .build(new Identifier(ExtShape.MOD_ID, "netherite"));
  public static final WoodType NETHERITE_WOOD_TYPE = WoodTypeBuilder.copyOf(HARD_WOOD_TYPE)
      .soundGroup(BlockSoundGroup.NETHERITE)
      .build(new Identifier(ExtShape.MOD_ID, "netherite"), NETHERITE_BLOCK_SET_TYPE);

  public static final BlockSetType ANCIENT_DEBRIS_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(HARD_BLOCK_SET_TYPE)
      .soundGroup(BlockSoundGroup.ANCIENT_DEBRIS)
      .build(new Identifier(ExtShape.MOD_ID, "ancient_debris"));
  public static final WoodType ANCIENT_DEBRIS_WOOD_TYPE = WoodTypeBuilder.copyOf(HARD_WOOD_TYPE)
      .soundGroup(BlockSoundGroup.ANCIENT_DEBRIS)
      .build(new Identifier(ExtShape.MOD_ID, "ancient_debris"), ANCIENT_DEBRIS_BLOCK_SET_TYPE);

  public static final BlockSetType TUFF_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .soundGroup(BlockSoundGroup.TUFF)
      .build(new Identifier(ExtShape.MOD_ID, "tuff"));
  public static final BlockSetType CALCITE_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .soundGroup(BlockSoundGroup.CALCITE)
      .build(new Identifier(ExtShape.MOD_ID, "calcite"));
  public static final WoodType TUFF_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .soundGroup(BlockSoundGroup.TUFF)
      .build(new Identifier(ExtShape.MOD_ID, "tuff"), TUFF_BLOCK_SET_TYPE);
  public static final WoodType CALCITE_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .soundGroup(BlockSoundGroup.CALCITE)
      .build(new Identifier(ExtShape.MOD_ID, "calcite"), CALCITE_BLOCK_SET_TYPE);

  public static final BlockSetType SCULK_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(SOFT_BLOCK_SET_TYPE)
      .soundGroup(BlockSoundGroup.SCULK)
      .buttonClickOnSound(SoundEvents.BLOCK_SCULK_HIT)
      .buttonClickOffSound(SoundEvents.BLOCK_SCULK_HIT)
      .pressurePlateClickOnSound(SoundEvents.BLOCK_SCULK_HIT)
      .pressurePlateClickOffSound(SoundEvents.BLOCK_SCULK_HIT)
      .build(new Identifier(ExtShape.MOD_ID, "sculk"));
  public static final WoodType SCULK_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .soundGroup(BlockSoundGroup.SCULK)
      .fenceGateOpenSound(SoundEvents.BLOCK_SCULK_HIT)
      .fenceGateCloseSound(SoundEvents.BLOCK_SCULK_HIT)
      .build(new Identifier(ExtShape.MOD_ID, "sculk"), SCULK_BLOCK_SET_TYPE);

  public static final BlockSetType DRIPSTONE_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .soundGroup(BlockSoundGroup.DRIPSTONE_BLOCK)
      .build(new Identifier(ExtShape.MOD_ID, "dripstone"));
  public static final WoodType DRIPSTONE_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .soundGroup(BlockSoundGroup.DRIPSTONE_BLOCK)
      .build(new Identifier(ExtShape.MOD_ID, "dripstone"), DRIPSTONE_BLOCK_SET_TYPE);

  public static final BlockSetType MOSS_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(SOFT_BLOCK_SET_TYPE)
      .soundGroup(BlockSoundGroup.MOSS_BLOCK)
      .buttonClickOnSound(SoundEvents.BLOCK_MOSS_HIT)
      .buttonClickOffSound(SoundEvents.BLOCK_MOSS_HIT)
      .pressurePlateClickOnSound(SoundEvents.BLOCK_MOSS_HIT)
      .pressurePlateClickOffSound(SoundEvents.BLOCK_MOSS_HIT)
      .build(new Identifier(ExtShape.MOD_ID, "moss_block"));
  public static final WoodType MOSS_BLOCK_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .soundGroup(BlockSoundGroup.MOSS_BLOCK)
      .fenceGateOpenSound(SoundEvents.BLOCK_MOSS_HIT)
      .fenceGateCloseSound(SoundEvents.BLOCK_MOSS_HIT)
      .build(new Identifier(ExtShape.MOD_ID, "moss_block"), MOSS_BLOCK_SET_TYPE);

  public static final BlockSetType DEEPSLATE_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .soundGroup(BlockSoundGroup.DEEPSLATE)
      .build(new Identifier(ExtShape.MOD_ID, "deepslate"));
  public static final WoodType DEEPSLATE_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .soundGroup(BlockSoundGroup.DEEPSLATE)
      .build(new Identifier(ExtShape.MOD_ID, "deepslate"), DEEPSLATE_BLOCK_SET_TYPE);
  public static final BlockSetType DEEPSLATE_BRICKS_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .soundGroup(BlockSoundGroup.DEEPSLATE_BRICKS)
      .build(new Identifier(ExtShape.MOD_ID, "deepslate_bricks"));
  public static final WoodType DEEPSLATE_BRICKS_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .soundGroup(BlockSoundGroup.DEEPSLATE_BRICKS)
      .build(new Identifier(ExtShape.MOD_ID, "deepslate_bricks"), DEEPSLATE_BRICKS_BLOCK_SET_TYPE);
  public static final BlockSetType DEEPSLATE_TILES_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .soundGroup(BlockSoundGroup.DEEPSLATE_TILES)
      .build(new Identifier(ExtShape.MOD_ID, "deepslate_tiles"));
  public static final WoodType DEEPSLATE_TILES_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .soundGroup(BlockSoundGroup.DEEPSLATE_TILES)
      .build(new Identifier(ExtShape.MOD_ID, "deepslate_tiles"), DEEPSLATE_TILES_BLOCK_SET_TYPE);
  public static final BlockSetType POLISHED_DEEPSLATE_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .soundGroup(BlockSoundGroup.POLISHED_DEEPSLATE)
      .build(new Identifier(ExtShape.MOD_ID, "polished_deepslate"));
  public static final WoodType POLISHED_DEEPSLATE_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .soundGroup(BlockSoundGroup.POLISHED_DEEPSLATE)
      .build(new Identifier(ExtShape.MOD_ID, "polished_deepslate"), POLISHED_DEEPSLATE_BLOCK_SET_TYPE);

  public static final BlockSetType BASALT_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .soundGroup(BlockSoundGroup.BASALT)
      .build(new Identifier(ExtShape.MOD_ID, "basalt"));
  public static final WoodType BASALT_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .soundGroup(BlockSoundGroup.BASALT)
      .build(new Identifier(ExtShape.MOD_ID, "basalt"), BASALT_BLOCK_SET_TYPE);

  public static final BlockSetType FROGLIGHT_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK)
      .soundGroup(BlockSoundGroup.FROGLIGHT)
      .build(new Identifier(ExtShape.MOD_ID, "froglight"));
  public static final WoodType FROGLIGHT_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .soundGroup(BlockSoundGroup.FROGLIGHT)
      .build(new Identifier(ExtShape.MOD_ID, "froglight"), FROGLIGHT_BLOCK_SET_TYPE);

  public static final BlockSetType COPPER_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(METAL_BLOCK_SET_TYPE)
      .soundGroup(BlockSoundGroup.COPPER)
      .build(new Identifier(ExtShape.MOD_ID, "copper"));
  public static final WoodType COPPER_WOOD_TYPE = WoodTypeBuilder.copyOf(METAL_WOOD_TYPE)
      .soundGroup(BlockSoundGroup.COPPER)
      .build(new Identifier(ExtShape.MOD_ID, "copper"), COPPER_BLOCK_SET_TYPE);

  private ExtShapeBlockTypes() {
  }
}
