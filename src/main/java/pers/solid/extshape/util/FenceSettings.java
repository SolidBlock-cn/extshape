package pers.solid.extshape.util;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import pers.solid.extshape.ExtShape;

public record FenceSettings(Item secondIngredient, WoodType woodType) {
  public static final FenceSettings DEFAULT = common(Items.STICK);
  public static final BlockSetType AMETHYST_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).soundGroup(BlockSoundGroup.AMETHYST_BLOCK).register(new Identifier(ExtShape.MOD_ID, "amethyst"));
  public static final WoodType AMETHYST_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK).fenceGateOpenSound(SoundEvents.BLOCK_AMETHYST_BLOCK_HIT).fenceGateCloseSound(SoundEvents.BLOCK_AMETHYST_BLOCK_HIT).hangingSignSoundGroup(BlockSoundGroup.AMETHYST_BLOCK).soundGroup(BlockSoundGroup.AMETHYST_BLOCK).register(new Identifier(ExtShape.MOD_ID, "amethyst"), AMETHYST_BLOCK_SET_TYPE);
  public static final FenceSettings AMETHYST = new FenceSettings(Items.AMETHYST_SHARD, AMETHYST_WOOD_TYPE);

  public static FenceSettings common(Item secondIngredient) {
    return new FenceSettings(secondIngredient, WoodType.OAK);
  }
}
