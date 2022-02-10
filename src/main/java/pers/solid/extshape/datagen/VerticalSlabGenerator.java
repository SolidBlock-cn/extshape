package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.VerticalSlabBlock;
import pers.solid.extshape.builder.Shape;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;

@Deprecated
public class VerticalSlabGenerator extends AbstractBlockGenerator<VerticalSlabBlock> {
    protected VerticalSlabGenerator(Path path, @NotNull VerticalSlabBlock block) {
        super(path, block);
    }

    public static void init(Generator generator) {
        generator.writeModelFile("extshape", "block/vertical_slab", """
                {   "parent": "block/block",
                    "textures": {
                        "particle": "#side"
                    },
                    "elements": [
                        {   "from": [ 0, 0, 8 ],
                            "to": [  16, 16, 16 ],
                            "faces": {
                                "down":  {"texture": "#bottom", "cullface": "down" },
                                "up":    {"texture": "#top",    "cullface": "up" },
                                "north": {"texture": "#side",   "cullface": "north" },
                                "south": {"texture": "#side",   "cullface": "south" },
                                "west":  { "texture": "#side",   "cullface": "west" },
                                "east":  { "texture": "#side",   "cullface": "east" }
                            }
                        }
                    ]
                }""");
    }

    @Override
    public String getBlockStatesString() {
        return String.format("""
                {
                  "variants": {
                    "facing=south": { "model": "%1$s" , "uvlock":true },
                    "facing=west":  { "model": "%1$s", "y":  90 , "uvlock":true},
                    "facing=north": { "model": "%1$s", "y": 180 , "uvlock":true },
                    "facing=east":  { "model": "%1$s", "y": 270 , "uvlock":true }
                  }
                }""", this.getBlockModelIdentifier());
    }

    @Override
    public String getBlockModelString() {
        return String.format("""
                {
                    "parent": "extshape:block/vertical_slab",
                    "textures": {
                        "bottom": "%s",
                        "top": "%s",
                        "side": "%s"
                    }
                }""", this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    @Override
    public String getCraftingRecipeString() {
        return String.format("""
                        {
                          "type": "minecraft:crafting_shaped",
                          "group": "%s",
                          "pattern": [
                            "#"
                          ],
                          "key": {
                            "#": {
                              "item": "%s"
                            }
                          },
                          "result": {
                            "item": "%s",
                            "count": 1
                          }
                        }
                        """, this.getRecipeGroup(), Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.SLAB,
                        this.getBaseBlock())),
                this.getIdentifier());
    }

    /**
     * 由纵台阶生成台阶的合成表。考虑到要应用于原版，所以生成台阶的合成表由纵台阶生成器生成。
     *
     * @return 由纵台阶制作台阶的合成配方。
     */
    public String getInverseCraftingString() {
        return String.format("""
                        {
                          "type": "minecraft:crafting_shaped",
                          "group": "%s",
                          "pattern": [
                            "#"
                          ],
                          "key": {
                            "#": {
                              "item": "%s"
                            }
                          },
                          "result": {
                            "item": "%s",
                            "count": 1
                          }
                        }
                        """, this.getRecipeGroup().replace("vertical_", ""), this.getIdentifier(),
                Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.SLAB,
                        this.getBaseBlock())));
    }

    @Override
    public void writeCraftingRecipeFiles() {
        super.writeCraftingRecipeFiles();
        this.writeRecipeFile("extshape", Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.SLAB,
                this.getBaseBlock())).getPath() + "_from_vertical_slab", this.getInverseCraftingString());
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
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if ((ExtShapeBlockTag.PLANKS).contains(baseBlock)) return "wooden_vertical_slab";
        if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_vertical_slab";
        if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_vertical_slab";
        if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return "stained_terracotta_vertical_slab";
        if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return "glazed_terracotta_vertical_slab";
        return "";
    }
}
