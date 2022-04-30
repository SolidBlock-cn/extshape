package pers.solid.extshape.builder;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTags;

import java.util.Collection;

public class ButtonBuilder extends AbstractBlockBuilder<AbstractButtonBlock> {
  /**
   * 该集合内的方块将不会生成按钮配方。
   */
  public static final @Unmodifiable Collection<Block> REFUSE_RECIPES = ImmutableSet.of(Blocks.EMERALD_BLOCK, Blocks.IRON_BLOCK, Blocks.GOLD_BLOCK, Blocks.DIAMOND_BLOCK, Blocks.LAPIS_BLOCK, Blocks.MELON, Blocks.PUMPKIN);

  public ButtonBuilder(@NotNull ExtShapeButtonBlock.ButtonType type, Block baseBlock) {
    super(baseBlock, FabricBlockSettings.copyOf(baseBlock).noCollision().strength(baseBlock.getHardness() / 4f), builder -> new ExtShapeButtonBlock(baseBlock, type, builder.blockSettings));
    this.defaultTag = ExtShapeBlockTags.BUTTONS;
    this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.BUTTON);
  }

  @Override
  protected String getSuffix() {
    return "_button";
  }
}
