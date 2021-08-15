package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.ArrayList;
import java.util.List;

public class ExtShapeWallBlock extends WallBlock implements ExtShapeSubBlockInterface {
    public ExtShapeWallBlock(Settings settings) {
        super(settings);
    }
    // 特别注意：目前只要是有#walls标签的方块都会在#mineable/pickaxe标签内。
    // 为修复此问题，我们不注册羊毛墙！！！


    public ExtShapeWallBlock addToTag() {
        this.addToTag(ExtShapeBlockTag.WALLS);
        return this;
    }

    @Override
    public MutableText getName() {
        return new TranslatableText("block.extshape.?_wall", this.getNamePrefix());
    }

    public String getInventoryModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/wall_inventory",
                  "textures": {
                    "wall": "%s"
                  }
                }""", this.getBaseTexture());
    }

    public String getPostModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/template_wall_post",
                  "textures": {
                    "wall": "%s"
                  }
                }""", this.getBaseTexture());
    }

    public String getSideModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/template_wall_side",
                  "textures": {
                    "wall": "%s"
                  }
                }""", this.getBaseTexture());
    }

    public String getSideTallModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/template_wall_side_tall",
                  "textures": {
                    "wall": "%s"
                  }
                }""", this.getBaseTexture());
    }

    public List<Pair<Identifier, String>> getBlockModelCollection() {
        List<Pair<Identifier, String>> modelCollection = new ArrayList<>();
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_inventory"), this.getInventoryModelString()));
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_post"), this.getPostModelString()));
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_side"), this.getSideModelString()));
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_side_tall"), this.getSideTallModelString()));
        return modelCollection;
    }

    @Override
    public String getBlockStatesString() {
        Identifier identifier = this.getIdentifier();
        return String.format("""
                {
                  "multipart": [
                    {
                      "when": {
                        "up": "true"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_post"
                      }
                    },
                    {
                      "when": {
                        "north": "low"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side",
                        "uvlock": true
                      }
                    },
                    {
                      "when": {
                        "east": "low"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side",
                        "y": 90,
                        "uvlock": true
                      }
                    },
                    {
                      "when": {
                        "south": "low"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side",
                        "y": 180,
                        "uvlock": true
                      }
                    },
                    {
                      "when": {
                        "west": "low"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side",
                        "y": 270,
                        "uvlock": true
                      }
                    },
                    {
                      "when": {
                        "north": "tall"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side_tall",
                        "uvlock": true
                      }
                    },
                    {
                      "when": {
                        "east": "tall"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side_tall",
                        "y": 90,
                        "uvlock": true
                      }
                    },
                    {
                      "when": {
                        "south": "tall"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side_tall",
                        "y": 180,
                        "uvlock": true
                      }
                    },
                    {
                      "when": {
                        "west": "tall"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side_tall",
                        "y": 270,
                        "uvlock": true
                      }
                    }
                  ]
                }""", identifier.getNamespace(), identifier.getPath());
    }

    @Override
    public String getItemModelString() {
        return String.format("""
                {
                  "parent": "%s_inventory"
                }""", this.getBlockModelIdentifier().toString());
    }

    @Override
    public String getCraftingRecipeString() {
        return String.format("""
                {
                  "type": "minecraft:crafting_shaped",
                  "group": "%3$s",
                  "pattern": [
                    "###",
                    "###"
                  ],
                  "key": {
                    "#": {
                      "item": "%1$s"
                    }
                  },
                  "result": {
                    "item": "%2$s",
                    "count": 6
                  }
                }""", this.getBaseBlockIdentifier(), this.getIdentifier().toString(), this.getRecipeGroup());
    }

    @Override
    public String getStoneCuttingRecipeString() {
        return String.format("""
                {
                  "type": "minecraft:stonecutting",
                  "ingredient": {
                    "item": "%s"
                  },
                  "result": "%s",
                  "count": 1
                }""", this.getBaseBlockIdentifier(), this.getIdentifier());
    }

    @Override
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if (ExtShapeBlockTag.WOOLS.contains(baseBlock)) return "wool_wall";
        if (ExtShapeBlockTag.CONCRETES.contains(baseBlock)) return "concrete_wall";
        if (ExtShapeBlockTag.STAINED_TERRACOTTAS.contains(baseBlock)) return "stained_terracotta_wall";
        if (ExtShapeBlockTag.GLAZED_TERRACOTTAS.contains(baseBlock)) return "glazed_terracotta_wall";
        if (ExtShapeBlockTag.PLANKS.contains(baseBlock)) return "wooden_wall";
        return "";
    }
}
