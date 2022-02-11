package pers.solid.extshape.builder;

import com.google.common.collect.Sets;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JIngredient;
import net.devtech.arrp.json.recipe.JIngredients;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JResult;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.Collection;

public class ButtonBuilder extends AbstractBlockBuilder<AbstractButtonBlock> {
  /**
   * 该集合内的方块将不会生成按钮配方。
   */
  private static final Collection<Block> REFUSE_RECIPES = Sets.newHashSet(Blocks.EMERALD_BLOCK, Blocks.IRON_BLOCK, Blocks.GOLD_BLOCK, Blocks.DIAMOND_BLOCK, Blocks.LAPIS_BLOCK, Blocks.MELON, Blocks.PUMPKIN);
  public final @NotNull ExtShapeButtonBlock.ButtonType type;

  public ButtonBuilder(@NotNull ExtShapeButtonBlock.ButtonType type, Block baseBlock) {
    super(baseBlock, FabricBlockSettings.copyOf(baseBlock).noCollision().strength(baseBlock.getHardness() / 4f), builder -> new ExtShapeButtonBlock(type, builder.blockSettings));
    this.type = type;
    this.defaultTag = ExtShapeBlockTag.BUTTONS;
    this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.BUTTON);
  }

  @Override
  protected String getSuffix() {
    return "_button";
  }

  @Environment(EnvType.CLIENT)
  @Override
  public JModel getBlockModel() {
    return simpleTextureModel("block/button");
  }

  @Environment(EnvType.CLIENT)
  public JModel getInventoryBlockModel() {
    return simpleTextureModel("block/button_inventory");
  }

  @Environment(EnvType.CLIENT)
  public JModel getPressedBlockModel() {
    return simpleTextureModel("block/button_pressed");
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void writeBlockModel(RuntimeResourcePack pack) {
    super.writeBlockModel(pack);
    final Identifier identifier = this.getIdentifier();
    pack.addModel(getInventoryBlockModel(), blockIdentifier(identifier, "_inventory"));
    pack.addModel(getPressedBlockModel(), blockIdentifier(identifier, "_pressed"));
  }

  /**
   * 按钮的方块状态定义。参考格式：<pre>
   * {
   *   "variants": {
   *     "face=ceiling,facing=east,powered=false": {
   *       "model": "%1$s:block/%2$s",
   *       "y": 270,
   *       "x": 180
   *     },
   *     "face=ceiling,facing=east,powered=true": {
   *       "model": "%1$s:block/%2$s_pressed",
   *       "y": 270,
   *       "x": 180
   *     },
   *     "face=ceiling,facing=north,powered=false": {
   *       "model": "%1$s:block/%2$s",
   *       "y": 180,
   *       "x": 180
   *     },
   *     "face=ceiling,facing=north,powered=true": {
   *       "model": "%1$s:block/%2$s_pressed",
   *       "y": 180,
   *       "x": 180
   *     },
   *     "face=ceiling,facing=south,powered=false": {
   *       "model": "%1$s:block/%2$s",
   *       "x": 180
   *     },
   *     "face=ceiling,facing=south,powered=true": {
   *       "model": "%1$s:block/%2$s_pressed",
   *       "x": 180
   *     },
   *     "face=ceiling,facing=west,powered=false": {
   *       "model": "%1$s:block/%2$s",
   *       "y": 90,
   *       "x": 180
   *     },
   *     "face=ceiling,facing=west,powered=true": {
   *       "model": "%1$s:block/%2$s_pressed",
   *       "y": 90,
   *       "x": 180
   *     },
   *     "face=floor,facing=east,powered=false": {
   *       "model": "%1$s:block/%2$s",
   *       "y": 90
   *     },
   *     "face=floor,facing=east,powered=true": {
   *       "model": "%1$s:block/%2$s_pressed",
   *       "y": 90
   *     },
   *     "face=floor,facing=north,powered=false": {
   *       "model": "%1$s:block/%2$s"
   *     },
   *     "face=floor,facing=north,powered=true": {
   *       "model": "%1$s:block/%2$s_pressed"
   *     },
   *     "face=floor,facing=south,powered=false": {
   *       "model": "%1$s:block/%2$s",
   *       "y": 180
   *     },
   *     "face=floor,facing=south,powered=true": {
   *       "model": "%1$s:block/%2$s_pressed",
   *       "y": 180
   *     },
   *     "face=floor,facing=west,powered=false": {
   *       "model": "%1$s:block/%2$s",
   *       "y": 270
   *     },
   *     "face=floor,facing=west,powered=true": {
   *       "model": "%1$s:block/%2$s_pressed",
   *       "y": 270
   *     },
   *     "face=wall,facing=east,powered=false": {
   *       "model": "%1$s:block/%2$s",
   *       "y": 90,
   *       "x": 90,
   *       "uvlock": true
   *     },
   *     "face=wall,facing=east,powered=true": {
   *       "model": "%1$s:block/%2$s_pressed",
   *       "y": 90,
   *       "x": 90,
   *       "uvlock": true
   *     },
   *     "face=wall,facing=north,powered=false": {
   *       "model": "%1$s:block/%2$s",
   *       "x": 90,
   *       "uvlock": true
   *     },
   *     "face=wall,facing=north,powered=true": {
   *       "model": "%1$s:block/%2$s_pressed",
   *       "x": 90,
   *       "uvlock": true
   *     },
   *     "face=wall,facing=south,powered=false": {
   *       "model": "%1$s:block/%2$s",
   *       "y": 180,
   *       "x": 90,
   *       "uvlock": true
   *     },
   *     "face=wall,facing=south,powered=true": {
   *       "model": "%1$s:block/%2$s_pressed",
   *       "y": 180,
   *       "x": 90,
   *       "uvlock": true
   *     },
   *     "face=wall,facing=west,powered=false": {
   *       "model": "%1$s:block/%2$s",
   *       "y": 270,
   *       "x": 90,
   *       "uvlock": true
   *     },
   *     "face=wall,facing=west,powered=true": {
   *       "model": "%1$s:block/%2$s_pressed",
   *       "y": 270,
   *       "x": 90,
   *       "uvlock": true
   *     }
   *   }
   * }
   * </pre>
   */
  @Override
  @Environment(EnvType.CLIENT)
  public @Nullable JState getBlockStates() {
    final JVariant variant = new JVariant();
    final Identifier identifier = getIdentifier();
    for (Direction direction : Direction.Type.HORIZONTAL) {
      final int rotation = (int) direction.getOpposite().asRotation();
      variant
          .put("face=ceiling,powered=false,facing", direction, new JBlockModel(blockIdentifier(identifier)).uvlock().x(180).y(rotation))
          .put("face=ceiling,powered=true,facing", direction, new JBlockModel(blockIdentifier(identifier, "_pressed")).uvlock().x(180).y(rotation))
          .put("face=floor,powered=false,facing", direction, new JBlockModel(blockIdentifier(identifier)).uvlock().y(rotation))
          .put("face=floor,powered=true,facing", direction, new JBlockModel(blockIdentifier(identifier, "_pressed")).uvlock().y(rotation))
          .put("face=wall,powered=false,facing", direction, new JBlockModel(blockIdentifier(identifier)).uvlock().x(90).y(rotation))
          .put("face=wall,powered=true,facing", direction, new JBlockModel(blockIdentifier(identifier, "_pressed")).uvlock().x(90).y(rotation));
    }
    return JState.state(variant);
  }

  @Environment(EnvType.CLIENT)
  @Override
  public @Nullable JModel getItemModel() {
    return JModel.model(blockIdentifier(getIdentifier(), "_inventory"));
  }

  /**
   * 按钮的合成配方。参考格式：<pre>
   * {
   *   "type": "minecraft:crafting_shapeless",
   *   "group": "%s",
   *   "ingredients": [{"item": "%s"}],
   *   "result": {"item": "%s"}
   * }
   * </pre>
   * 需要注意的是，为了避免冲突，我们禁用了部分按钮的合成。
   */
  @Override
  public @Nullable JRecipe getCraftingRecipe() {
    if (REFUSE_RECIPES.contains(baseBlock)) {
      return null;
    }
    return JRecipe.shapeless(JIngredients.ingredients().add(JIngredient.ingredient().item(getBaseIdentifier().toString())), JResult.result(getIdentifier().toString()));
  }

  @Override
  public String getRecipeGroup() {
    if (ExtShapeBlockTag.WOOLS.contains(baseBlock)) return "wool_button";
    if (ExtShapeBlockTag.CONCRETES.contains(baseBlock)) return "concrete_button";
    if (ExtShapeBlockTag.STAINED_TERRACOTTAS.contains(baseBlock)) return "stained_terracotta_button";
    if (ExtShapeBlockTag.GLAZED_TERRACOTTAS.contains(baseBlock)) return "glazed_terracotta_button";
    if (ExtShapeBlockTag.PLANKS.contains(baseBlock)) return "wooden_button";
    return "";
  }
}
