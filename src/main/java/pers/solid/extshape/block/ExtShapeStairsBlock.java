package pers.solid.extshape.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.ExtShapeBlockItem;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.ArrayList;
import java.util.List;

public class ExtShapeStairsBlock extends StairsBlock implements Waterloggable, RegistrableSubBlock {
    public final Identifier identifier;
    public final ExtShapeBlockItem blockItem;
    public BlockState baseBlockState;

    public ExtShapeStairsBlock(@NotNull BlockState baseBlockState, @Nullable Identifier identifier,
                               @Nullable Settings settings, @Nullable FabricItemSettings itemSettings) {
        super(baseBlockState, settings == null ? FabricBlockSettings.copyOf(baseBlockState.getBlock()) : settings);
        this.baseBlockState = baseBlockState;
        this.identifier = identifier == null ? SubBlock.convertIdentifier(this.getBaseBlockIdentifier(), "_stairs") :
                identifier;
        this.blockItem = new ExtShapeBlockItem(this, itemSettings == null ? new FabricItemSettings() : itemSettings);
    }

    public ExtShapeStairsBlock(Block baseBlock, Identifier identifier, Settings settings, FabricItemSettings itemSettings) {
        this(baseBlock.getDefaultState(), identifier, settings, itemSettings);
    }

    public ExtShapeStairsBlock(Block baseBlock) {
        this(baseBlock, null, null, null);
    }

    public ExtShapeStairsBlock addToTag() {
        this.addToTag(ExtShapeBlockTag.STAIRS);
        return this;
    }

    @Override
    public Identifier getIdentifier() {
        return this.identifier;
    }

    @Override
    public ExtShapeBlockItem getBlockItem() {
        return this.blockItem;
    }

    @Override
    public BlockState getBaseBlockState() {
        return this.baseBlockState;
    }

    @Override
    public String getBlockModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/stairs",
                  "textures": {
                    "bottom": "%s",
                    "top": "%s",
                    "side": "%s"
                  }
                }
                """, this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    public String getInnerBlockModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/inner_stairs",
                  "textures": {
                    "bottom": "%s",
                    "top": "%s",
                    "side": "%s"
                  }
                }
                """, this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    public String getOuterBlockModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/outer_stairs",
                  "textures": {
                    "bottom": "%s",
                    "top": "%s",
                    "side": "%s"
                  }
                }
                """, this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    @Override
    public List<Pair<Identifier, String>> getBlockModelCollection() {
        final Block baseBlock = this.getBaseBlock();
        final Identifier baseIdentifier = Registry.BLOCK.getId(baseBlock);
        List<Pair<Identifier, String>> modelCollection = new ArrayList<>();
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier(), this.getBlockModelString()));
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_inner"), this.getInnerBlockModelString()));
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_outer"), this.getOuterBlockModelString()));
        return modelCollection;
    }

    @Override
    public String getBlockStatesString() {
        Identifier identifier = this.getIdentifier();
        return String.format(
                """
                        {
                          "variants": {
                            "facing=east,half=bottom,shape=inner_left": {
                              "model": "%1$s:block/%2$s_inner",
                              "y": 270,
                              "uvlock": true
                            },
                            "facing=east,half=bottom,shape=inner_right": {
                              "model": "%1$s:block/%2$s_inner"
                            },
                            "facing=east,half=bottom,shape=outer_left": {
                              "model": "%1$s:block/%2$s_outer",
                              "y": 270,
                              "uvlock": true
                            },
                            "facing=east,half=bottom,shape=outer_right": {
                              "model": "%1$s:block/%2$s_outer"
                            },
                            "facing=east,half=bottom,shape=straight": {
                              "model": "%1$s:block/%2$s"
                            },
                            "facing=east,half=top,shape=inner_left": {
                              "model": "%1$s:block/%2$s_inner",
                              "x": 180,
                              "uvlock": true
                            },
                            "facing=east,half=top,shape=inner_right": {
                              "model": "%1$s:block/%2$s_inner",
                              "x": 180,
                              "y": 90,
                              "uvlock": true
                            },
                            "facing=east,half=top,shape=outer_left": {
                              "model": "%1$s:block/%2$s_outer",
                              "x": 180,
                              "uvlock": true
                            },
                            "facing=east,half=top,shape=outer_right": {
                              "model": "%1$s:block/%2$s_outer",
                              "x": 180,
                              "y": 90,
                              "uvlock": true
                            },
                            "facing=east,half=top,shape=straight": {
                              "model": "%1$s:block/%2$s",
                              "x": 180,
                              "uvlock": true
                            },
                            "facing=north,half=bottom,shape=inner_left": {
                              "model": "%1$s:block/%2$s_inner",
                              "y": 180,
                              "uvlock": true
                            },
                            "facing=north,half=bottom,shape=inner_right": {
                              "model": "%1$s:block/%2$s_inner",
                              "y": 270,
                              "uvlock": true
                            },
                            "facing=north,half=bottom,shape=outer_left": {
                              "model": "%1$s:block/%2$s_outer",
                              "y": 180,
                              "uvlock": true
                            },
                            "facing=north,half=bottom,shape=outer_right": {
                              "model": "%1$s:block/%2$s_outer",
                              "y": 270,
                              "uvlock": true
                            },
                            "facing=north,half=bottom,shape=straight": {
                              "model": "%1$s:block/%2$s",
                              "y": 270,
                              "uvlock": true
                            },
                            "facing=north,half=top,shape=inner_left": {
                              "model": "%1$s:block/%2$s_inner",
                              "x": 180,
                              "y": 270,
                              "uvlock": true
                            },
                            "facing=north,half=top,shape=inner_right": {
                              "model": "%1$s:block/%2$s_inner",
                              "x": 180,
                              "uvlock": true
                            },
                            "facing=north,half=top,shape=outer_left": {
                              "model": "%1$s:block/%2$s_outer",
                              "x": 180,
                              "y": 270,
                              "uvlock": true
                            },
                            "facing=north,half=top,shape=outer_right": {
                              "model": "%1$s:block/%2$s_outer",
                              "x": 180,
                              "uvlock": true
                            },
                            "facing=north,half=top,shape=straight": {
                              "model": "%1$s:block/%2$s",
                              "x": 180,
                              "y": 270,
                              "uvlock": true
                            },
                            "facing=south,half=bottom,shape=inner_left": {
                              "model": "%1$s:block/%2$s_inner"
                            },
                            "facing=south,half=bottom,shape=inner_right": {
                              "model": "%1$s:block/%2$s_inner",
                              "y": 90,
                              "uvlock": true
                            },
                            "facing=south,half=bottom,shape=outer_left": {
                              "model": "%1$s:block/%2$s_outer"
                            },
                            "facing=south,half=bottom,shape=outer_right": {
                              "model": "%1$s:block/%2$s_outer",
                              "y": 90,
                              "uvlock": true
                            },
                            "facing=south,half=bottom,shape=straight": {
                              "model": "%1$s:block/%2$s",
                              "y": 90,
                              "uvlock": true
                            },
                            "facing=south,half=top,shape=inner_left": {
                              "model": "%1$s:block/%2$s_inner",
                              "x": 180,
                              "y": 90,
                              "uvlock": true
                            },
                            "facing=south,half=top,shape=inner_right": {
                              "model": "%1$s:block/%2$s_inner",
                              "x": 180,
                              "y": 180,
                              "uvlock": true
                            },
                            "facing=south,half=top,shape=outer_left": {
                              "model": "%1$s:block/%2$s_outer",
                              "x": 180,
                              "y": 90,
                              "uvlock": true
                            },
                            "facing=south,half=top,shape=outer_right": {
                              "model": "%1$s:block/%2$s_outer",
                              "x": 180,
                              "y": 180,
                              "uvlock": true
                            },
                            "facing=south,half=top,shape=straight": {
                              "model": "%1$s:block/%2$s",
                              "x": 180,
                              "y": 90,
                              "uvlock": true
                            },
                            "facing=west,half=bottom,shape=inner_left": {
                              "model": "%1$s:block/%2$s_inner",
                              "y": 90,
                              "uvlock": true
                            },
                            "facing=west,half=bottom,shape=inner_right": {
                              "model": "%1$s:block/%2$s_inner",
                              "y": 180,
                              "uvlock": true
                            },
                            "facing=west,half=bottom,shape=outer_left": {
                              "model": "%1$s:block/%2$s_outer",
                              "y": 90,
                              "uvlock": true
                            },
                            "facing=west,half=bottom,shape=outer_right": {
                              "model": "%1$s:block/%2$s_outer",
                              "y": 180,
                              "uvlock": true
                            },
                            "facing=west,half=bottom,shape=straight": {
                              "model": "%1$s:block/%2$s",
                              "y": 180,
                              "uvlock": true
                            },
                            "facing=west,half=top,shape=inner_left": {
                              "model": "%1$s:block/%2$s_inner",
                              "x": 180,
                              "y": 180,
                              "uvlock": true
                            },
                            "facing=west,half=top,shape=inner_right": {
                              "model": "%1$s:block/%2$s_inner",
                              "x": 180,
                              "y": 270,
                              "uvlock": true
                            },
                            "facing=west,half=top,shape=outer_left": {
                              "model": "%1$s:block/%2$s_outer",
                              "x": 180,
                              "y": 180,
                              "uvlock": true
                            },
                            "facing=west,half=top,shape=outer_right": {
                              "model": "%1$s:block/%2$s_outer",
                              "x": 180,
                              "y": 270,
                              "uvlock": true
                            },
                            "facing=west,half=top,shape=straight": {
                              "model": "%1$s:block/%2$s",
                              "x": 180,
                              "y": 180,
                              "uvlock": true
                            }
                          }
                        }
                        """, identifier.getNamespace(), identifier.getPath());
    }

    @Override
    public String getCraftingRecipeString() {
        Identifier identifier = this.getIdentifier();
        Identifier baseIdentifier = Registry.BLOCK.getId(this.getBaseBlock());
        return String.format("""
                {
                  "type": "minecraft:crafting_shaped",
                  "group": "%3$s",
                  "pattern": [
                    "#  ",
                    "## ",
                    "###"
                  ],
                  "key": {
                    "#": {
                      "item": "%s"
                    }
                  },
                  "result": {
                    "item": "%s",
                    "count": 4
                  }
                }""", baseIdentifier, identifier.toString(), this.getRecipeGroup());
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
    public MutableText getName() {
        return new TranslatableText("block.extshape.?_stairs", this.getNamePrefix());
    }

    public String getTranslationKey() {
        return "_getTranslationKeyCalled_";
    }

    @Override
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_stairs";
        if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_stairs";
        if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return "stained_terracotta_stairs";
        if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return "glazed_terracotta_stairs";
        return "";
    }

}
