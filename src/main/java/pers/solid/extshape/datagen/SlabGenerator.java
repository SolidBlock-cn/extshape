package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

@Deprecated
public class SlabGenerator extends AbstractBlockGenerator<SlabBlock> {
    protected SlabGenerator(Path path, @NotNull SlabBlock block) {
        super(path, block);
    }


    @Override
    public String getBlockStatesString() {
        Identifier baseIdentifier = Registry.BLOCK.getId(this.getBaseBlock());
        return String.format("""
                        {
                          "variants": {
                            "type=bottom": {
                              "model": "%1$s:block/%2$s"
                            },
                            "type=double": {
                              "model": "%3$s:block/%4$s"
                            },
                            "type=top": {
                              "model": "%1$s:block/%2$s_top"
                            }
                          }
                        }
                        """, this.getIdentifier().getNamespace(), this.getIdentifier().getPath(), baseIdentifier.getNamespace(),
                baseIdentifier.getPath());
    }

    @Override
    public String getBlockModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/slab",
                  "textures": {
                    "bottom": "%s",
                    "top": "%s",
                    "side": "%s"
                  }
                }
                """, this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    public String getTopBlockModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/slab_top",
                  "textures": {
                    "bottom": "%s",
                    "top": "%s",
                    "side": "%s"
                  }
                }
                """, this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    @Override
    public Map<Identifier, String> getBlockModelCollection() {
        Map<Identifier, String> modelCollection = new LinkedHashMap<>();
        modelCollection.put(this.getBlockModelIdentifier(), this.getBlockModelString());
        modelCollection.put(this.getBlockModelIdentifier("_top"), this.getTopBlockModelString());
        return modelCollection;
    }

    @Override
    public String getCraftingRecipeString() {
        return String.format("""
                {
                  "type": "minecraft:crafting_shaped",
                  "group": "%3$s",
                  "pattern": [
                    "###"
                  ],
                  "key": {
                    "#": {
                      "item": "%s"
                    }
                  },
                  "result": {
                    "item": "%s",
                    "count": 6
                  }
                }""", getBaseBlockIdentifier(), getIdentifier().toString(), this.getRecipeGroup());
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
                  "count": 2
                }""", this.getBaseBlockIdentifier(), this.getIdentifier());
    }

    @Override
    public String getLootTableString() {
        Identifier identifier = this.getIdentifier();
        return String.format("""
                {
                  "type": "minecraft:block",
                  "pools": [
                    {
                      "rolls": 1.0,
                      "bonus_rolls": 0.0,
                      "entries": [
                        {
                          "type": "minecraft:item",
                          "functions": [
                            {
                              "function": "minecraft:set_count",
                              "conditions": [
                                {
                                  "condition": "minecraft:block_state_property",
                                  "block": "%1$s",
                                  "properties": {
                                    "type": "double"
                                  }
                                }
                              ],
                              "count": 2.0,
                              "add": false
                            },
                            {
                              "function": "minecraft:explosion_decay"
                            }
                          ],
                          "name": "%1$s"
                        }
                      ]
                    }
                  ]
                }""", identifier.toString());
    }


    @Override
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_slab";
        if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_slab";
        if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return "stained_terracotta_slab";
        if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return "glazed_terracotta_slab";
        return "";
    }
}
