package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
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
public class FenceGenerator extends AbstractBlockGenerator<FenceBlock> {
    protected FenceGenerator(Path path, @NotNull FenceBlock block) {
        super(path, block);
    }


    @Override
    public String getBlockStatesString() {
        Identifier identifier = this.getIdentifier();
        return String.format("""
                {
                  "multipart": [
                    {
                      "apply": {
                        "model": "%1$s:block/%2$s_post"
                      }
                    },
                    {
                      "when": {
                        "north": "true"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side",
                        "uvlock": true
                      }
                    },
                    {
                      "when": {
                        "east": "true"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side",
                        "y": 90,
                        "uvlock": true
                      }
                    },
                    {
                      "when": {
                        "south": "true"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side",
                        "y": 180,
                        "uvlock": true
                      }
                    },
                    {
                      "when": {
                        "west": "true"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side",
                        "y": 270,
                        "uvlock": true
                      }
                    }
                  ]
                }""", identifier.getNamespace(), identifier.getPath());
    }

    public String getInventoryBlockModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/fence_inventory",
                  "textures": {
                    "texture": "%1$s"
                  }
                }""", this.getBaseTexture());
    }

    public String getSideBlockModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/fence_side",
                  "textures": {
                    "texture": "%1$s"
                  }
                }""", this.getBaseTexture());
    }

    public String getPostBlockModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/fence_post",
                  "textures": {
                    "texture": "%1$s"
                  }
                }
                """, this.getBaseTexture());
    }

    @Override
    public Map<Identifier, String> getBlockModelCollection() {
        Map<Identifier, String> modelCollection = new LinkedHashMap<>();
        modelCollection.put(this.getBlockModelIdentifier("_inventory"), this.getInventoryBlockModelString());
        modelCollection.put(this.getBlockModelIdentifier("_side"), this.getSideBlockModelString());
        modelCollection.put(this.getBlockModelIdentifier("_post"), this.getPostBlockModelString());
        return modelCollection;
    }

    @Override
    public String getItemModelString() {
        Identifier identifier = this.getIdentifier();
        return String.format("""
                {
                  "parent": "%1$s:block/%2$s_inventory"
                }""", identifier.getNamespace(), identifier.getPath());
    }


    @Override
    public String getCraftingRecipeString() {
        return String.format("""
                {
                  "type": "minecraft:crafting_shaped",
                  "group": "%s",
                  "pattern": [
                    "W#W",
                    "W#W"
                  ],
                  "key": {
                    "W": {
                      "item": "%s"
                    },
                    "#": {
                      "item": "%s"
                    }
                  },
                  "result": {
                    "item": "%s",
                    "count": 3
                  }
                }""", this.getRecipeGroup(), this.getBaseBlockIdentifier(), this.getCraftingIngredientIdentifier(), this.getIdentifier());
    }

    @Override
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_fence";
        if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_fence";
        if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return "stained_terracotta_fence";
        if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return "glazed_terracotta_fence";
        return "";
    }


    public Item getCraftingIngredient() {
        return IngredientMappings.MAPPING_OF_FENCE_INGREDIENTS.get(this.getBlock());
    }

    public Identifier getCraftingIngredientIdentifier() {
        return Registry.ITEM.getId(this.getCraftingIngredient());
    }
}
