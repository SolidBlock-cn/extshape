package pers.solid.extshape.util;

import net.minecraft.block.BlockSetType;

public record ButtonSettings(BlockSetType blockSetType, int time, boolean wooden) {
  public static ButtonSettings stone(BlockSetType blockSetType) {
    return new ButtonSettings(blockSetType, 20, false);
  }

  public static ButtonSettings soft(BlockSetType blockSetType) {
    return new ButtonSettings(blockSetType, 60, true);
  }

  public static ButtonSettings wood(BlockSetType blockSetType) {
    return new ButtonSettings(blockSetType, 30, true);
  }

  public static ButtonSettings hard(BlockSetType blockSetType) {
    return new ButtonSettings(blockSetType, 5, false);
  }

  public static final ButtonSettings PSUDO_WOODEN = wood(BlockSetType.STONE);
  public static final ButtonSettings STONE = stone(BlockSetType.STONE);
  public static final ButtonSettings HARD = hard(BlockSetType.STONE);
  public static final ButtonSettings SOFT = soft(BlockSetType.STONE);
  public static final ButtonSettings BAMBOO = wood(BlockSetType.BAMBOO);
  public static final ButtonSettings CHERRY = wood(BlockSetType.CHERRY);
}
