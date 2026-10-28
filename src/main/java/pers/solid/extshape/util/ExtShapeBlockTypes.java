package pers.solid.extshape.util;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.sounds.BlockSoundSets;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import pers.solid.extshape.ExtShape;

/**
 * 本模组中会使用到的一些 {@code BlockSetType} 和 {@code WoodType}，用于完整地实现一些栅栏门、按钮等方块的功能。
 */
public final class ExtShapeBlockTypes {
  private static SoundEvent of(String name) {
    final Identifier id = ExtShape.id(name);
    final SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(id);
    return Registry.register(BuiltInRegistries.SOUND_EVENT, id, soundEvent);
  }

  public static final WoodType STONE_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .blockSoundSet(BlockSoundSets.STONE)
      .build(ExtShape.id("stone"), BlockSetType.STONE);

  public static final BlockSetType AMETHYST_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK)
      .blockSoundSet(BlockSoundSets.AMETHYST_BLOCK)
      .build(ExtShape.id("amethyst"));
  public static final WoodType AMETHYST_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .fenceGateOpenSound(of("block.amethyst_fence_gate.open"))
      .fenceGateCloseSound(of("block.amethyst_fence_gate.close"))
      .blockSoundSet(BlockSoundSets.AMETHYST_BLOCK)
      .build(ExtShape.id("amethyst"), AMETHYST_BLOCK_SET_TYPE);

  public static final BlockSetType SOFT_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .buttonActivatedByArrows(true)
      .openableByHand(true)
      .openableByWindCharge(true)
      .pressurePlateActivationRule(BlockSetType.PressurePlateSensitivity.EVERYTHING)
      .build(ExtShape.id("soft"));

  public static final BlockSetType GRAVEL_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK)
      .blockSoundSet(BlockSoundSets.GRAVEL)
      .pressurePlateActivationRule(BlockSetType.PressurePlateSensitivity.EVERYTHING)
      .build(ExtShape.id("gravel"));
  public static final WoodType GRAVEL_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .blockSoundSet(BlockSoundSets.GRAVEL)
      .build(ExtShape.id("gravel"), GRAVEL_BLOCK_SET_TYPE);

  public static final BlockSetType WOOL_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(SOFT_BLOCK_SET_TYPE)
      .blockSoundSet(BlockSoundSets.WOOL)
      .buttonClickOnSound(of("block.wool_button.click_on"))
      .buttonClickOffSound(of("block.wool_button.click_off"))
      .pressurePlateClickOnSound(of("block.wool_pressure_plate.click_on"))
      .pressurePlateClickOffSound(of("block.wool_pressure_plate.click_off"))
      .build(ExtShape.id("wool"));
  public static final WoodType WOOL_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .blockSoundSet(BlockSoundSets.WOOL)
      .fenceGateOpenSound(of("block.wool_fence_gate.open"))
      .fenceGateCloseSound(of("block.wool_fence_gate.close"))
      .build(ExtShape.id("wool"), WOOL_BLOCK_SET_TYPE);

  public static final BlockSetType QUARTZ_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .build(ExtShape.id("quartz"));
  public static final WoodType QUARTZ_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .build(ExtShape.id("quartz"), QUARTZ_BLOCK_SET_TYPE);

  public static final BlockSetType METAL_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.METAL)
      .build(ExtShape.id("metal"));
  public static final WoodType METAL_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.METAL)
      .build(ExtShape.id("metal"), METAL_BLOCK_SET_TYPE);
  public static final WoodType GOLD_WOOD_TYPE = WoodTypeBuilder.copyOf(METAL_WOOD_TYPE)
      .build(ExtShape.id("gold"), BlockSetType.GOLD);
  public static final WoodType IRON_WOOD_TYPE = WoodTypeBuilder.copyOf(METAL_WOOD_TYPE)
      .build(ExtShape.id("iron"), BlockSetType.IRON);

  public static final BlockSetType HARD_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .openableByHand(false)
      .openableByWindCharge(false)
      .buttonActivatedByArrows(false)
      .build(ExtShape.id("hard"));
  public static final WoodType HARD_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .build(ExtShape.id("hard"), HARD_BLOCK_SET_TYPE);

  public static final WoodType DIAMOND_WOOD_TYPE = WoodTypeBuilder.copyOf(HARD_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.METAL)
      .build(ExtShape.id("diamond"), HARD_BLOCK_SET_TYPE);

  public static final BlockSetType SNOW_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(SOFT_BLOCK_SET_TYPE)
      .blockSoundSet(BlockSoundSets.SNOW)
      .buttonClickOnSound(of("block.snow_button.click_on"))
      .buttonClickOffSound(of("block.snow_button.click_off"))
      .pressurePlateClickOnSound(of("block.snow_pressure_plate.click_on"))
      .pressurePlateClickOffSound(of("block.snow_pressure_plate.click_off"))
      .build(ExtShape.id("snow"));
  public static final WoodType SNOW_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .blockSoundSet(BlockSoundSets.SNOW)
      .fenceGateOpenSound(of("block.snow_fence_gate.open"))
      .fenceGateCloseSound(of("block.snow_fence_gate.close"))
      .build(ExtShape.id("snow"), SNOW_BLOCK_SET_TYPE);

  public static final BlockSetType NETHERRACK_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.NETHERRACK)
      .build(ExtShape.id("netherrack"));
  public static final WoodType NETHERRACK_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.NETHERRACK)
      .build(ExtShape.id("netherrack"), NETHERRACK_BLOCK_SET_TYPE);

  public static final BlockSetType GLOWSTONE_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK)
      .blockSoundSet(BlockSoundSets.GLASS)
      .build(ExtShape.id("glowstone"));
  public static final WoodType GLOWSTONE_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .blockSoundSet(BlockSoundSets.GLASS)
      .build(ExtShape.id("glowstone"), ExtShapeBlockTypes.SOFT_BLOCK_SET_TYPE);

  public static final BlockSetType PACKED_MUD_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.PACKED_MUD)
      .build(ExtShape.id("packed_mud"));
  public static final WoodType PACKED_MUD = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.PACKED_MUD)
      .build(ExtShape.id("packed_mud"), PACKED_MUD_BLOCK_SET_TYPE);

  public static final BlockSetType MUD_BRICKS_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.MUD_BRICKS)
      .build(ExtShape.id("mud_bricks"));
  public static final WoodType MUD_BRICKS = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.MUD_BRICKS)
      .build(ExtShape.id("mud_bricks"), MUD_BRICKS_BLOCK_SET_TYPE);

  public static final BlockSetType NETHER_BRICKS_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.NETHER_BRICKS)
      .build(ExtShape.id("nether_bricks"));
  public static final WoodType NETHER_BRICKS_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.NETHER_BRICKS)
      .build(ExtShape.id("nether_bricks"), NETHER_BRICKS_BLOCK_SET_TYPE);

  public static final BlockSetType WART_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(SOFT_BLOCK_SET_TYPE)
      .blockSoundSet(BlockSoundSets.WART_BLOCK)
      .buttonClickOnSound(of("block.wart_button.click_on"))
      .buttonClickOffSound(of("block.wart_button.click_off"))
      .pressurePlateClickOnSound(of("block.wart_pressure_plate.click_on"))
      .pressurePlateClickOffSound(of("block.wart_pressure_plate.click_off"))
      .build(ExtShape.id("wart_block"));
  public static final WoodType WART_BLOCK_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .blockSoundSet(BlockSoundSets.WART_BLOCK)
      .fenceGateOpenSound(of("block.wart_fence_gate.open"))
      .fenceGateCloseSound(of("block.wart_fence_gate.close"))
      .build(ExtShape.id("wart_block"), WART_BLOCK_SET_TYPE);

  public static final BlockSetType SHROMLIGHT_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK)
      .blockSoundSet(BlockSoundSets.SHROOMLIGHT)
      .build(ExtShape.id("shroomlight"));
  public static final WoodType SHROOMLIGHT_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .blockSoundSet(BlockSoundSets.SHROOMLIGHT)
      .build(ExtShape.id("shroomlight"), SHROMLIGHT_BLOCK_SET_TYPE);

  public static final BlockSetType HONEYCOMB_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK)
      .blockSoundSet(BlockSoundSets.CORAL_BLOCK)
      .build(ExtShape.id("honeycomb"));
  public static final WoodType HONEYCOMB_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .blockSoundSet(BlockSoundSets.CORAL_BLOCK)
      .build(ExtShape.id("honeycomb"), HONEYCOMB_BLOCK_SET_TYPE);

  public static final BlockSetType NETHERITE_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(HARD_BLOCK_SET_TYPE)
      .blockSoundSet(BlockSoundSets.NETHERITE_BLOCK)
      .build(ExtShape.id("netherite"));
  public static final WoodType NETHERITE_WOOD_TYPE = WoodTypeBuilder.copyOf(HARD_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.NETHERITE_BLOCK)
      .build(ExtShape.id("netherite"), NETHERITE_BLOCK_SET_TYPE);

  public static final BlockSetType ANCIENT_DEBRIS_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(HARD_BLOCK_SET_TYPE)
      .blockSoundSet(BlockSoundSets.ANCIENT_DEBRIS)
      .build(ExtShape.id("ancient_debris"));
  public static final WoodType ANCIENT_DEBRIS_WOOD_TYPE = WoodTypeBuilder.copyOf(HARD_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.ANCIENT_DEBRIS)
      .build(ExtShape.id("ancient_debris"), ANCIENT_DEBRIS_BLOCK_SET_TYPE);

  public static final BlockSetType TUFF_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.TUFF)
      .build(ExtShape.id("tuff"));
  public static final BlockSetType POLISHED_TUFF_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.POLISHED_TUFF)
      .build(ExtShape.id("polished_tuff"));
  public static final BlockSetType TUFF_BRICKS_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.TUFF_BRICKS)
      .build(ExtShape.id("tuff_bricks"));
  public static final BlockSetType CALCITE_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.CALCITE)
      .build(ExtShape.id("calcite"));
  public static final WoodType TUFF_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.TUFF)
      .build(ExtShape.id("tuff"), TUFF_BLOCK_SET_TYPE);
  public static final WoodType POLISHED_TUFF_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.POLISHED_TUFF)
      .build(ExtShape.id("polished_tuff"), POLISHED_TUFF_BLOCK_SET_TYPE);
  public static final WoodType TUFF_BRICKS_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.TUFF_BRICKS)
      .build(ExtShape.id("tuff_bricks"), TUFF_BRICKS_BLOCK_SET_TYPE);
  public static final WoodType CALCITE_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.CALCITE)
      .build(ExtShape.id("calcite"), CALCITE_BLOCK_SET_TYPE);

  public static final BlockSetType SCULK_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(SOFT_BLOCK_SET_TYPE)
      .blockSoundSet(BlockSoundSets.SCULK)
      .buttonClickOnSound(of("block.sculk_button.click_on"))
      .buttonClickOffSound(of("block.sculk_button.click_off"))
      .pressurePlateClickOnSound(of("block.sculk_pressure_plate.click_on"))
      .pressurePlateClickOffSound(of("block.sculk_pressure_plate.click_off"))
      .build(ExtShape.id("sculk"));
  public static final WoodType SCULK_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .blockSoundSet(BlockSoundSets.SCULK)
      .fenceGateOpenSound(of("block.sculk_fence_gate.open"))
      .fenceGateCloseSound(of("block.sculk_fence_gate.close"))
      .build(ExtShape.id("sculk"), SCULK_BLOCK_SET_TYPE);

  public static final BlockSetType DRIPSTONE_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.DRIPSTONE_BLOCK)
      .build(ExtShape.id("dripstone"));
  public static final WoodType DRIPSTONE_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.DRIPSTONE_BLOCK)
      .build(ExtShape.id("dripstone"), DRIPSTONE_BLOCK_SET_TYPE);

  public static final BlockSetType MOSS_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(SOFT_BLOCK_SET_TYPE)
      .blockSoundSet(BlockSoundSets.MOSS)
      .buttonClickOnSound(of("block.moss_button.click_on"))
      .buttonClickOffSound(of("block.moss_button.click_off"))
      .pressurePlateClickOnSound(of("block.moss_pressure_plate.click_on"))
      .pressurePlateClickOffSound(of("block.moss_pressure_plate.click_off"))
      .build(ExtShape.id("moss_block"));
  public static final WoodType MOSS_BLOCK_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .blockSoundSet(BlockSoundSets.MOSS)
      .fenceGateOpenSound(of("block.moss_fence_gate.open"))
      .fenceGateCloseSound(of("block.moss_fence_gate.close"))
      .build(ExtShape.id("moss_block"), MOSS_BLOCK_SET_TYPE);

  public static final BlockSetType SLIME_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(SOFT_BLOCK_SET_TYPE)
      .blockSoundSet(BlockSoundSets.SLIME_BLOCK)
      .buttonClickOnSound(of("block.slime_button.click_on"))
      .buttonClickOffSound(of("block.slime_button.click_off"))
      .pressurePlateClickOnSound(of("block.slime_pressure_plate.click_on"))
      .pressurePlateClickOffSound(of("block.slime_pressure_plate.click_off"))
      .build(ExtShape.id("slime"));
  public static final WoodType SLIME_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .blockSoundSet(BlockSoundSets.SLIME_BLOCK)
      .fenceGateOpenSound(of("block.slime_fence_gate.open"))
      .fenceGateCloseSound(of("block.slime_fence_gate.close"))
      .build(ExtShape.id("slime"), SLIME_BLOCK_SET_TYPE);

  public static final BlockSetType DEEPSLATE_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.DEEPSLATE)
      .build(ExtShape.id("deepslate"));
  public static final WoodType DEEPSLATE_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.DEEPSLATE)
      .build(ExtShape.id("deepslate"), DEEPSLATE_BLOCK_SET_TYPE);
  public static final BlockSetType DEEPSLATE_BRICKS_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.DEEPSLATE_BRICKS)
      .build(ExtShape.id("deepslate_bricks"));
  public static final WoodType DEEPSLATE_BRICKS_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.DEEPSLATE_BRICKS)
      .build(ExtShape.id("deepslate_bricks"), DEEPSLATE_BRICKS_BLOCK_SET_TYPE);
  public static final BlockSetType DEEPSLATE_TILES_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.DEEPSLATE_TILES)
      .build(ExtShape.id("deepslate_tiles"));
  public static final WoodType DEEPSLATE_TILES_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.DEEPSLATE_TILES)
      .build(ExtShape.id("deepslate_tiles"), DEEPSLATE_TILES_BLOCK_SET_TYPE);
  public static final BlockSetType POLISHED_DEEPSLATE_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.POLISHED_DEEPSLATE)
      .build(ExtShape.id("polished_deepslate"));
  public static final WoodType POLISHED_DEEPSLATE_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.POLISHED_DEEPSLATE)
      .build(ExtShape.id("polished_deepslate"), POLISHED_DEEPSLATE_BLOCK_SET_TYPE);

  public static final BlockSetType BASALT_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.BASALT)
      .build(ExtShape.id("basalt"));
  public static final WoodType BASALT_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.BASALT)
      .build(ExtShape.id("basalt"), BASALT_BLOCK_SET_TYPE);

  public static final BlockSetType FROGLIGHT_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK)
      .blockSoundSet(BlockSoundSets.FROGLIGHT)
      .build(ExtShape.id("froglight"));
  public static final WoodType FROGLIGHT_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .blockSoundSet(BlockSoundSets.FROGLIGHT)
      .build(ExtShape.id("froglight"), FROGLIGHT_BLOCK_SET_TYPE);

  public static final WoodType COPPER_WOOD_TYPE = WoodTypeBuilder.copyOf(METAL_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.COPPER)
      .build(ExtShape.id("copper"), BlockSetType.COPPER);

  public static final BlockSetType RESIN_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.RESIN)
      .register(ExtShape.id("resin"));
  public static final WoodType RESIN_BLOCK_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.RESIN)
      .register(ExtShape.id("resin"), RESIN_BLOCK_SET_TYPE);

  public static final BlockSetType RESIN_BRICKS_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.RESIN_BRICKS)
      .register(ExtShape.id("resin_bricks"));
  public static final WoodType RESIN_BRICKS_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.RESIN_BRICKS)
      .register(ExtShape.id("resin_bricks"), RESIN_BRICKS_SET_TYPE);

  public static final BlockSetType SULFUR_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.SULFUR)
      .register(ExtShape.id("sulfur"));
  public static final WoodType SULFUR_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.SULFUR)
      .register(ExtShape.id("sulfur"), SULFUR_BLOCK_SET_TYPE);

  public static final BlockSetType CINNABAR_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.CINNABAR)
      .register(ExtShape.id("cinnabar"));
  public static final WoodType CINNABAR_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.CINNABAR)
      .register(ExtShape.id("cinnabar"), CINNABAR_BLOCK_SET_TYPE);

  public static final BlockSetType PACKED_ICE_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .blockSoundSet(BlockSoundSets.PACKED_ICE)
      .register(ExtShape.id("packed_ice"));
  public static final WoodType PACKED_ICE_WOOD_TYPE = WoodTypeBuilder.copyOf(STONE_WOOD_TYPE)
      .blockSoundSet(BlockSoundSets.PACKED_ICE)
      .register(ExtShape.id("packed_ice"), PACKED_ICE_BLOCK_SET_TYPE);

  private ExtShapeBlockTypes() {
  }
}
