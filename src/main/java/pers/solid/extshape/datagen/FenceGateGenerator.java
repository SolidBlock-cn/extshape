package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.mappings.IngredientMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

@Deprecated
public class FenceGateGenerator extends AbstractBlockGenerator<FenceGateBlock> {
    protected FenceGateGenerator(Path path, @NotNull FenceGateBlock block) {
        super(path, block);
    }


    public Item getCraftingIngredient() {
        return IngredientMappings.MAPPING_OF_FENCE_GATE_INGREDIENTS.get(this.getBlock());
    }

    public Identifier getCraftingIngredientIdentifier() {
        return Registry.ITEM.getId(this.getCraftingIngredient());
    }


    @Override
    public String getBlockStatesString() {
        Identifier identifier = this.getIdentifier();
        return String.format("""
                {
                   "variants": {
                     "facing=east,in_wall=false,open=false": {
                       "uvlock": true,
                       "y": 270,
                       "model": "%1$s:block/%2$s"
                     },
                     "facing=east,in_wall=false,open=true": {
                       "uvlock": true,
                       "y": 270,
                       "model": "%1$s:block/%2$s_open"
                     },
                     "facing=east,in_wall=true,open=false": {
                       "uvlock": true,
                       "y": 270,
                       "model": "%1$s:block/%2$s_wall"
                     },
                     "facing=east,in_wall=true,open=true": {
                       "uvlock": true,
                       "y": 270,
                       "model": "%1$s:block/%2$s_wall_open"
                     },
                     "facing=north,in_wall=false,open=false": {
                       "uvlock": true,
                       "y": 180,
                       "model": "%1$s:block/%2$s"
                     },
                     "facing=north,in_wall=false,open=true": {
                       "uvlock": true,
                       "y": 180,
                       "model": "%1$s:block/%2$s_open"
                     },
                     "facing=north,in_wall=true,open=false": {
                       "uvlock": true,
                       "y": 180,
                       "model": "%1$s:block/%2$s_wall"
                     },
                     "facing=north,in_wall=true,open=true": {
                       "uvlock": true,
                       "y": 180,
                       "model": "%1$s:block/%2$s_wall_open"
                     },
                     "facing=south,in_wall=false,open=false": {
                       "uvlock": true,
                       "model": "%1$s:block/%2$s"
                     },
                     "facing=south,in_wall=false,open=true": {
                       "uvlock": true,
                       "model": "%1$s:block/%2$s_open"
                     },
                     "facing=south,in_wall=true,open=false": {
                       "uvlock": true,
                       "model": "%1$s:block/%2$s_wall"
                     },
                     "facing=south,in_wall=true,open=true": {
                       "uvlock": true,
                       "model": "%1$s:block/%2$s_wall_open"
                     },
                     "facing=west,in_wall=false,open=false": {
                       "uvlock": true,
                       "y": 90,
                       "model": "%1$s:block/%2$s"
                     },
                     "facing=west,in_wall=false,open=true": {
                       "uvlock": true,
                       "y": 90,
                       "model": "%1$s:block/%2$s_open"
                     },
                     "facing=west,in_wall=true,open=false": {
                       "uvlock": true,
                       "y": 90,
                       "model": "%1$s:block/%2$s_wall"
                     },
                     "facing=west,in_wall=true,open=true": {
                       "uvlock": true,
                       "y": 90,
                       "model": "%1$s:block/%2$s_wall_open"
                     }
                   }
                 }""", identifier.getNamespace(), identifier.getPath());
    }

    @Override
    public String getBlockModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/template_fence_gate",
                  "textures": {
                    "texture": "%1$s"
                  }
                }""", this.getBaseTexture());
    }

    public String getOpenBlockModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/template_fence_gate_open",
                  "textures": {
                    "texture": "%1$s"
                  }
                }
                """, this.getBaseTexture());
    }

    public String getWallBlockModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/template_fence_gate_wall",
                  "textures": {
                    "texture": "%1$s"
                  }
                }
                """, this.getBaseTexture());
    }

    public String getWallOpenBlockModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/template_fence_gate_wall_open",
                  "textures": {
                    "texture": "%1$s"
                  }
                }
                """, this.getBaseTexture());
    }

    @Override
    public Map<Identifier, String> getBlockModelCollection() {
        Map<Identifier, String> modelCollection = new LinkedHashMap<>();
        modelCollection.put(this.getBlockModelIdentifier(), this.getBlockModelString());
        modelCollection.put(this.getBlockModelIdentifier("_open"), this.getOpenBlockModelString());
        modelCollection.put(this.getBlockModelIdentifier("_wall"), this.getWallBlockModelString());
        modelCollection.put(this.getBlockModelIdentifier("_wall_open"), this.getWallOpenBlockModelString());
        return modelCollection;
    }

    @Override
    public String getCraftingRecipeString() {
        return String.format("""
                        {
                            "type": "minecraft:crafting_shaped",
                            "group": "%s",
                            "pattern": [
                              "#W#",
                              "#W#"
                            ],
                            "key": {
                              "#": {
                                "item": "%s"
                              },
                              "W": {
                                "item": "%s"
                              }
                            },
                            "result": {
                              "item": "%s"
                            }
                          }""", this.getRecipeGroup(), this.getCraftingIngredientIdentifier(), this.getBaseBlockIdentifier(),
                this.getIdentifier());
    }

    @Override
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_fence_gate";
        if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_fence_gate";
        if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return
                "stained_terracotta_fence_gate";
        if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return
                "glazed_terracotta_fence_gate";
        return "";
    }
}
