package pers.solid.extshape.datagen;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ButtonGenerator extends AbstractBlockGenerator<AbstractButtonBlock> {
    protected ButtonGenerator(Path path, @NotNull AbstractButtonBlock block) {
        super(path, block);
    }


    public String getModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/button",
                  "textures": {
                    "texture": "%s"
                  }
                }""", this.getBaseTexture());
    }

    public String getInventoryModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/button_inventory",
                  "textures": {
                    "texture": "%s"
                  }
                }""", this.getBaseTexture());
    }

    public String getPressedModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/button_pressed",
                  "textures": {
                    "texture": "%s"
                  }
                }""", this.getBaseTexture());
    }


    public List<Pair<Identifier, String>> getBlockModelCollection() {
        List<Pair<Identifier, String>> modelCollection = new ArrayList<>();
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier(), this.getModelString()));
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_inventory"), this.getInventoryModelString()));
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_pressed"), this.getPressedModelString()));
        return modelCollection;
    }

    @Override
    public String getBlockStatesString() {
        Identifier identifier = this.getIdentifier();
        return String.format("""
                {
                  "variants": {
                    "face=ceiling,facing=east,powered=false": {
                      "model": "%1$s:block/%2$s",
                      "y": 270,
                      "x": 180
                    },
                    "face=ceiling,facing=east,powered=true": {
                      "model": "%1$s:block/%2$s_pressed",
                      "y": 270,
                      "x": 180
                    },
                    "face=ceiling,facing=north,powered=false": {
                      "model": "%1$s:block/%2$s",
                      "y": 180,
                      "x": 180
                    },
                    "face=ceiling,facing=north,powered=true": {
                      "model": "%1$s:block/%2$s_pressed",
                      "y": 180,
                      "x": 180
                    },
                    "face=ceiling,facing=south,powered=false": {
                      "model": "%1$s:block/%2$s",
                      "x": 180
                    },
                    "face=ceiling,facing=south,powered=true": {
                      "model": "%1$s:block/%2$s_pressed",
                      "x": 180
                    },
                    "face=ceiling,facing=west,powered=false": {
                      "model": "%1$s:block/%2$s",
                      "y": 90,
                      "x": 180
                    },
                    "face=ceiling,facing=west,powered=true": {
                      "model": "%1$s:block/%2$s_pressed",
                      "y": 90,
                      "x": 180
                    },
                    "face=floor,facing=east,powered=false": {
                      "model": "%1$s:block/%2$s",
                      "y": 90
                    },
                    "face=floor,facing=east,powered=true": {
                      "model": "%1$s:block/%2$s_pressed",
                      "y": 90
                    },
                    "face=floor,facing=north,powered=false": {
                      "model": "%1$s:block/%2$s"
                    },
                    "face=floor,facing=north,powered=true": {
                      "model": "%1$s:block/%2$s_pressed"
                    },
                    "face=floor,facing=south,powered=false": {
                      "model": "%1$s:block/%2$s",
                      "y": 180
                    },
                    "face=floor,facing=south,powered=true": {
                      "model": "%1$s:block/%2$s_pressed",
                      "y": 180
                    },
                    "face=floor,facing=west,powered=false": {
                      "model": "%1$s:block/%2$s",
                      "y": 270
                    },
                    "face=floor,facing=west,powered=true": {
                      "model": "%1$s:block/%2$s_pressed",
                      "y": 270
                    },
                    "face=wall,facing=east,powered=false": {
                      "model": "%1$s:block/%2$s",
                      "y": 90,
                      "x": 90,
                      "uvlock": true
                    },
                    "face=wall,facing=east,powered=true": {
                      "model": "%1$s:block/%2$s_pressed",
                      "y": 90,
                      "x": 90,
                      "uvlock": true
                    },
                    "face=wall,facing=north,powered=false": {
                      "model": "%1$s:block/%2$s",
                      "x": 90,
                      "uvlock": true
                    },
                    "face=wall,facing=north,powered=true": {
                      "model": "%1$s:block/%2$s_pressed",
                      "x": 90,
                      "uvlock": true
                    },
                    "face=wall,facing=south,powered=false": {
                      "model": "%1$s:block/%2$s",
                      "y": 180,
                      "x": 90,
                      "uvlock": true
                    },
                    "face=wall,facing=south,powered=true": {
                      "model": "%1$s:block/%2$s_pressed",
                      "y": 180,
                      "x": 90,
                      "uvlock": true
                    },
                    "face=wall,facing=west,powered=false": {
                      "model": "%1$s:block/%2$s",
                      "y": 270,
                      "x": 90,
                      "uvlock": true
                    },
                    "face=wall,facing=west,powered=true": {
                      "model": "%1$s:block/%2$s_pressed",
                      "y": 270,
                      "x": 90,
                      "uvlock": true
                    }
                  }
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
                  "type": "minecraft:crafting_shapeless",
                  "group": "%s",
                  "ingredients": [
                    {
                      "item": "%s"
                    }
                  ],
                  "result": {
                    "item": "%s"
                  }
                }""", this.getRecipeGroup(), this.getBaseBlockIdentifier(), this.getIdentifier().toString());
    }

    @Override
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if (ExtShapeBlockTag.WOOLS.contains(baseBlock)) return "wool_button";
        if (ExtShapeBlockTag.CONCRETES.contains(baseBlock)) return "concrete_button";
        if (ExtShapeBlockTag.STAINED_TERRACOTTAS.contains(baseBlock)) return "stained_terracotta_button";
        if (ExtShapeBlockTag.GLAZED_TERRACOTTAS.contains(baseBlock)) return "glazed_terracotta_button";
        if (ExtShapeBlockTag.PLANKS.contains(baseBlock)) return "wooden_button";
        return "";
    }
}
