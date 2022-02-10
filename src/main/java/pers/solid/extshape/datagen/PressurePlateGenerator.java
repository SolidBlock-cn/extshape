package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

@Deprecated
public class PressurePlateGenerator extends AbstractBlockGenerator<PressurePlateBlock> {
    protected PressurePlateGenerator(Path path, @NotNull PressurePlateBlock block) {
        super(path, block);
    }

    @Override
    public String getBlockStatesString() {
        return String.format("""
                {
                  "variants": {
                    "powered=false": {
                      "model": "%1$s"
                    },
                    "powered=true": {
                      "model": "%1$s_down"
                    }
                  }
                }""", this.getBlockModelIdentifier());
    }

    @Override
    public String getBlockModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/pressure_plate_up",
                  "textures": {
                    "texture": "%s"
                  }
                }""", this.getBaseTexture());
    }

    public String getDownModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/pressure_plate_up",
                  "textures": {
                    "texture": "%s"
                  }
                }""", this.getBaseTexture());
    }

    @Override
    public Map<Identifier, String> getBlockModelCollection() {
        Map<Identifier, String> map = new LinkedHashMap<>();
        map.put(this.getBlockModelIdentifier(), this.getBlockModelString());
        map.put(this.getBlockModelIdentifier("_down"), this.getDownModelString());
        return map;
    }

    @Override
    public String getCraftingRecipeString() {
        if (!ExtShapeBlockTag.WOOLS.contains(this.getBaseBlock()))
            return String.format("""
                    {
                      "type": "minecraft:crafting_shaped",
                      "group": "%s",
                      "pattern": [
                        "##"
                      ],
                      "key": {
                        "#": {
                          "item": "%s"
                        }
                      },
                      "result": {
                        "item": "%s"
                      }
                    }""", this.getRecipeGroup(), this.getBaseBlockIdentifier(), this.getIdentifier());
        else return String.format("""
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
                }""", this.getRecipeGroup(), this.getBaseBlockIdentifier().toString().replaceAll("_wool\\b", "_carpet"), this.getIdentifier());
    }

    @Override
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if (ExtShapeBlockTag.WOOLS.contains(baseBlock)) return "wool_pressure_plate";
        if (ExtShapeBlockTag.CONCRETES.contains(baseBlock)) return "concrete_pressure_plate";
        if (ExtShapeBlockTag.STAINED_TERRACOTTAS.contains(baseBlock)) return "stained_terracotta_pressure_plate";
        if (ExtShapeBlockTag.GLAZED_TERRACOTTAS.contains(baseBlock)) return "glazed_terracotta_pressure_plate";
        if (ExtShapeBlockTag.PLANKS.contains(baseBlock)) return "wooden_pressure_plate";
        return "";
    }
}
