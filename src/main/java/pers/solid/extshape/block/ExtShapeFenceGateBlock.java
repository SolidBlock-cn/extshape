package pers.solid.extshape.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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

public class ExtShapeFenceGateBlock extends FenceGateBlock implements RegistrableSubBlock {
    public final Identifier identifier;
    public final BlockItem blockItem;
    public final BlockState baseBlockState;
    public final Item craftingIngredient;

    public ExtShapeFenceGateBlock(@NotNull BlockState baseBlockState, @NotNull Item craftingIngredient, @Nullable Identifier identifier,
                                  @Nullable Settings settings, @Nullable FabricItemSettings itemSettings) {
        super(settings == null ? FabricBlockSettings.copyOf(baseBlockState.getBlock()) : settings);
        this.craftingIngredient = craftingIngredient;
        this.baseBlockState = baseBlockState;
        this.identifier = identifier == null ? SubBlock.convertIdentifier(Registry.BLOCK.getId(baseBlockState.getBlock()),
                "_fence_gate") : identifier;
        this.blockItem = new ExtShapeFenceGateBlockItem(this, itemSettings == null ? new FabricItemSettings() : itemSettings);
    }

    public ExtShapeFenceGateBlock(Block block, Item craftingIngredient, Identifier identifier, Settings settings,
                                  FabricItemSettings itemSettings) {
        this(block.getDefaultState(), craftingIngredient, identifier, settings, itemSettings);
    }

    public ExtShapeFenceGateBlock(Block baseBlock, Item craftingIngredient) {
        this(baseBlock, craftingIngredient, null, null, null);
    }

    public ExtShapeFenceGateBlock addToTag() {
        this.addToTag(ExtShapeBlockTag.FENCE_GATES);
        return this;
    }

    public Item getCraftingIngredient() {
        return this.craftingIngredient;
    }

    public Identifier getCraftingIngredientIdentifier() {
        return Registry.ITEM.getId(this.craftingIngredient);
    }

    @Override
    public Identifier getIdentifier() {
        return this.identifier;
    }

    @Override
    public BlockItem getBlockItem() {
        return this.blockItem;
    }

    @Override
    public BlockState getBaseBlockState() {
        return this.baseBlockState;
    }

    @Override
    public MutableText getName() {
        return new TranslatableText("block.extshape.?_fence_gate", this.getNamePrefix());
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
    public List<Pair<Identifier, String>> getBlockModelCollection() {
        final Block baseBlock = this.getBaseBlock();
        final Identifier baseIdentifier = Registry.BLOCK.getId(baseBlock);
        List<Pair<Identifier, String>> modelCollection = new ArrayList<>();
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier(), this.getBlockModelString()));
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_open"), this.getOpenBlockModelString()));
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_wall"), this.getWallBlockModelString()));
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_wall_open"), this.getWallOpenBlockModelString()));
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

    // 内部类
    public static class ExtShapeFenceGateBlockItem extends ExtShapeBlockItem {

        public ExtShapeFenceGateBlockItem(Block block, Settings settings) {
            super(block, settings);
        }
    }
}
