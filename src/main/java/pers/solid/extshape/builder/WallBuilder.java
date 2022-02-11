package pers.solid.extshape.builder;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JMultipart;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JWhen;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.devtech.arrp.json.recipe.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeWallBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class WallBuilder extends AbstractBlockBuilder<ExtShapeWallBlock> {
  protected WallBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeWallBlock(builder.blockSettings));
    this.defaultTag = ExtShapeBlockTag.WALLS;
    this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.WALL);
  }

  @Override
  protected String getSuffix() {
    return "_wall";
  }

  protected JModel simpleWallModel(String parent) {
    return JModel.model(parent).textures(new JTextures().var("wall", getBaseTexture().toString()));
  }

  /**
   * 墙在物品栏中的方块模型。参考格式：
   * <pre>
   * {
   *   "parent": "minecraft:block/wall_inventory",
   *   "textures": {
   *     "wall": "%s"
   *   }
   * }
   * </pre>
   */
  @Environment(EnvType.CLIENT)
  public JModel getInventoryModel() {
    return simpleWallModel("block/wall_inventory");
  }

  /**
   * 墙墩的方块模型。参考格式：
   * <pre>
   * {
   *   "parent": "minecraft:block/template_wall_post",
   *   "textures": {
   *     "wall": "%s"
   *   }
   * }
   * </pre>
   */
  @Environment(EnvType.CLIENT)
  public JModel getPostModel() {
    return simpleWallModel("block/template_wall_post");
  }

  /**
   * 墙侧的方块模型。参考格式：
   * <pre>
   * {
   *   "parent": "minecraft:block/template_wall_side",
   *   "textures": {
   *     "wall": "%s"
   *   }
   * }
   * </pre>
   */
  @Environment(EnvType.CLIENT)
  public JModel getSideModel() {
    return simpleWallModel("block/template_wall_side");
  }

  /**
   * 高墙侧的方块模型。参考格式：
   * <pre>
   * {
   *   "parent": "minecraft:block/template_wall_side_tall",
   *   "textures": {
   *     "wall": "%s"
   *   }
   * }
   * </pre>
   */
  @Environment(EnvType.CLIENT)
  public JModel getSideTallModel() {
    return simpleWallModel("block/template_wall_side_tall");
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void writeBlockModel(RuntimeResourcePack pack) {
    final Identifier identifier = getIdentifier();
    pack.addModel(getInventoryModel(), blockIdentifier(identifier, "_inventory"));
    pack.addModel(getPostModel(), blockIdentifier(identifier, "_post"));
    pack.addModel(getSideModel(), blockIdentifier(identifier, "_side"));
    pack.addModel(getSideTallModel(), blockIdentifier(identifier, "_side_tall"));
  }

  /**
   * 参考格式：
   * <pre>
   * {
   *   "multipart": [
   *     {
   *       "when": {
   *         "up": "true"
   *       },
   *       "apply": {
   *         "model": "%1$s:block/%2$s_post"
   *       }
   *     },
   *     {
   *       "when": {
   *         "north": "low"
   *       },
   *       "apply": {
   *         "model": "%1$s:block/%2$s_side",
   *         "uvlock": true
   *       }
   *     },
   *     {
   *       "when": {
   *         "east": "low"
   *       },
   *       "apply": {
   *         "model": "%1$s:block/%2$s_side",
   *         "y": 90,
   *         "uvlock": true
   *       }
   *     },
   *     {
   *       "when": {
   *         "south": "low"
   *       },
   *       "apply": {
   *         "model": "%1$s:block/%2$s_side",
   *         "y": 180,
   *         "uvlock": true
   *       }
   *     },
   *     {
   *       "when": {
   *         "west": "low"
   *       },
   *       "apply": {
   *         "model": "%1$s:block/%2$s_side",
   *         "y": 270,
   *         "uvlock": true
   *       }
   *     },
   *     {
   *       "when": {
   *         "north": "tall"
   *       },
   *       "apply": {
   *         "model": "%1$s:block/%2$s_side_tall",
   *         "uvlock": true
   *       }
   *     },
   *     {
   *       "when": {
   *         "east": "tall"
   *       },
   *       "apply": {
   *         "model": "%1$s:block/%2$s_side_tall",
   *         "y": 90,
   *         "uvlock": true
   *       }
   *     },
   *     {
   *       "when": {
   *         "south": "tall"
   *       },
   *       "apply": {
   *         "model": "%1$s:block/%2$s_side_tall",
   *         "y": 180,
   *         "uvlock": true
   *       }
   *     },
   *     {
   *       "when": {
   *         "west": "tall"
   *       },
   *       "apply": {
   *         "model": "%1$s:block/%2$s_side_tall",
   *         "y": 270,
   *         "uvlock": true
   *       }
   *     }
   *   ]
   * }
   * </pre>
   */
  @Environment(EnvType.CLIENT)
  @Override
  public @Nullable JState getBlockStates() {
    final JState state = JState.state();
    final Identifier identifier = getIdentifier();
    state.add(new JMultipart().when(new JWhen().add("up", "true")).addModel(new JBlockModel(blockIdentifier(identifier, "_post"))));
    for (Direction direction : Direction.Type.HORIZONTAL) {
      final int rotation = (int) direction.getOpposite().asRotation();
      state.add(new JMultipart().when(new JWhen().add(direction.asString(), "low")).addModel(new JBlockModel(blockIdentifier(identifier, "_side")).uvlock().y(rotation)));
      state.add(new JMultipart().when(new JWhen().add(direction.asString(), "tall")).addModel(new JBlockModel(blockIdentifier(identifier, "_side_tall")).uvlock().y(rotation)));
    }
    return state;
  }

  @Override
  @Environment(EnvType.CLIENT)
  public @Nullable JModel getItemModel() {
    final Identifier identifier = getIdentifier();
    return JModel.model(blockIdentifier(identifier, "_inventory"));
  }

  /**
   * 墙的合成配方。参考格式：
   * <pre>
   * { "type": "minecraft:crafting_shaped",
   *   "group": "%3$s",
   *   "pattern": [
   *     "###",
   *     "###"
   *   ],
   *   "key": {"#": {"item": "%1$s"}},
   *   "result": {"item": "%2$s", "count": 6}}
   * </pre>
   */
  @Override
  public @Nullable JRecipe getCraftingRecipe() {
    return JRecipe.shaped(JPattern.pattern("###", "###"), JKeys.keys().key("#", JIngredient.ingredient().item(getBaseIdentifier().toString())), JResult.stackedResult(getIdentifier().toString(), 6)).group(getRecipeGroup());
  }

  /**
   * 墙的切石配方。参考格式：
   * <pre>
   * {
   *   "type": "minecraft:stonecutting",
   *   "ingredient": {
   *     "item": "%s"
   *   },
   *   "result": "%s",
   *   "count": 1
   * }
   * </pre>
   */
  @Override
  public @Nullable JRecipe getStonecuttingRecipe() {
    return simpleStoneCuttingRecipe(1);
  }

  @Override
  public String getRecipeGroup() {
    if (ExtShapeBlockTag.WOOLS.contains(baseBlock)) return "wool_wall";
    if (ExtShapeBlockTag.CONCRETES.contains(baseBlock)) return "concrete_wall";
    if (ExtShapeBlockTag.STAINED_TERRACOTTAS.contains(baseBlock)) return "stained_terracotta_wall";
    if (ExtShapeBlockTag.GLAZED_TERRACOTTAS.contains(baseBlock)) return "glazed_terracotta_wall";
    if (ExtShapeBlockTag.PLANKS.contains(baseBlock)) return "wooden_wall";
    return "";
  }
}
