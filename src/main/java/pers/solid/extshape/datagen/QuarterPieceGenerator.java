package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.QuarterPieceBlock;
import pers.solid.extshape.builder.Shape;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;
import java.util.List;

public class QuarterPieceGenerator extends AbstractBlockGenerator<QuarterPieceBlock> {
    protected QuarterPieceGenerator(Path path, @NotNull QuarterPieceBlock block) {
        super(path, block);
    }

    public static void init(Generator generator) {
        generator.writeModelFile("extshape", "block/quarter_piece", """
                {   "parent": "block/block",
                    "textures": {
                        "particle": "#side"
                    },
                    "elements": [
                        {   "from": [ 0, 0, 8 ],
                            "to": [  16, 8, 16 ],
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
        generator.writeModelFile("extshape", "block/quarter_piece_top", """
                {   "parent": "block/block",
                    "textures": {
                        "particle": "#side"
                    },
                    "elements": [
                        {   "from": [ 0, 8, 8 ],
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
                    "half=top,facing=south": { "model": "%1$s_top" , "uvlock":true },
                    "half=top,facing=west":  { "model": "%1$s_top", "y":  90 , "uvlock":true},
                    "half=top,facing=north": { "model": "%1$s_top", "y": 180 , "uvlock":true },
                    "half=top,facing=east":  { "model": "%1$s_top", "y": 270 , "uvlock":true },
                    "half=bottom,facing=south": { "model": "%1$s" , "uvlock":true },
                    "half=bottom,facing=west":  { "model": "%1$s", "y":  90 , "uvlock":true},
                    "half=bottom,facing=north": { "model": "%1$s", "y": 180 , "uvlock":true },
                    "half=bottom,facing=east":  { "model": "%1$s", "y": 270 , "uvlock":true }
                  }
                }""", this.getBlockModelIdentifier());
    }

    @Override
    public String getBlockModelString() {
        return String.format("""
                {
                    "parent": "extshape:block/quarter_piece",
                    "textures": {
                        "bottom": "%s",
                        "top": "%s",
                        "side": "%s"
                    }
                }""", this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    public String getTopBlockModelString() {
        return String.format("""
                {
                    "parent": "extshape:block/quarter_piece_top",
                    "textures": {
                        "bottom": "%s",
                        "top": "%s",
                        "side": "%s"
                    }
                }""", this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    @Override
    public List<Pair<Identifier, String>> getBlockModelCollection() {
        var modelCollection = super.getBlockModelCollection();
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_top"), this.getTopBlockModelString()));
        return modelCollection;
    }

    /**
     * @return 由3个台阶生成6个横条的合成配方。
     */
    public @Nullable String getCraftingFromSlabRecipeString() {
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
                        }""", Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.slab, this.getBaseBlock())),
                getIdentifier().toString(), this.getRecipeGroup());
    }

    /**
     * @return 由1个纵条生成1个横条的合成配方。
     */
    public @Nullable String getCraftingFromVerticalQuarterPieceRecipeString() {
        return String.format("""
                        {
                          "type": "minecraft:crafting_shaped",
                          "group": "%3$s",
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
                        }""", Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.verticalQuarterPiece, this.getBaseBlock())),
                getIdentifier().toString(), this.getRecipeGroup());
    }

    /**
     * @return 由完整方块制作4个横条的切石配方。
     */
    @Override
    public @Nullable String getStoneCuttingRecipeString() {
        return String.format("""
                {
                  "type": "minecraft:stonecutting",
                  "ingredient": {
                    "item": "%s"
                  },
                  "result": "%s",
                  "count": 4
                }""", this.getBaseBlockIdentifier(), this.getIdentifier());
    }

    /**
     * @return 由楼梯切石制作3个横条的切石配方。
     */
    public @Nullable String getStoneCuttingFromStairsRecipeString() {
        return String.format("""
                        {
                          "type": "minecraft:stonecutting",
                          "ingredient": {
                            "item": "%s"
                          },
                          "result": "%s",
                          "count": 3
                        }""", Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.stairs, this.getBaseBlock())),
                this.getIdentifier());
    }

    /**
     * @return 由台阶切石制作2个横条的切石配方。
     */
    public @Nullable String getStoneCuttingFromSlabRecipeString() {
        return String.format("""
                {
                  "type": "minecraft:stonecutting",
                  "ingredient": {
                    "item": "%s"
                  },
                  "result": "%s",
                  "count": 2
                }""", Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.slab, this.getBaseBlock())), this.getIdentifier());
    }

    /**
     * @return 由纵台阶切石制作2个横条的切石配方。
     */
    public @Nullable String getStoneCuttingFromVerticalSlabRecipeString() {
        return String.format("""
                        {
                          "type": "minecraft:stonecutting",
                          "ingredient": {
                            "item": "%s"
                          },
                          "result": "%s",
                          "count": 2
                        }""", Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.verticalSlab, this.getBaseBlock())),
                this.getIdentifier());
    }

    @Override
    public void writeCraftingRecipeFiles() {
        Identifier identifier = this.getIdentifier();
        this.writeRecipeFile(identifier.getNamespace(), identifier.getPath() + "_from_slab", this.getCraftingFromSlabRecipeString());
        this.writeRecipeFile(identifier.getNamespace(), identifier.getPath() + "_from_vertical_quarter_piece",
                this.getCraftingFromVerticalQuarterPieceRecipeString());
    }

    @Override
    public void writeStoneCuttingRecipeFiles() {
        super.writeStoneCuttingRecipeFiles();
        Identifier identifier = this.getIdentifier();
        this.writeRecipeFile(identifier.getNamespace(), identifier.getPath() + "_from_stairs_stonecutting",
                this.getStoneCuttingFromStairsRecipeString());
        this.writeRecipeFile(identifier.getNamespace(), identifier.getPath() + "_from_slab_stonecutting",
                this.getStoneCuttingFromSlabRecipeString());
        this.writeRecipeFile(identifier.getNamespace(), identifier.getPath() + "_from_vertical_slab_stonecutting",
                this.getStoneCuttingFromVerticalSlabRecipeString());
    }

    @Override
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if ((ExtShapeBlockTag.PLANKS).contains(baseBlock)) return "wooden_quarter_piece";
        if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_quarter_piece";
        if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_quarter_piece";
        if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return "stained_terracotta_quarter_piece";
        if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return "glazed_terracotta_quarter_piece";
        return "";
    }
}
