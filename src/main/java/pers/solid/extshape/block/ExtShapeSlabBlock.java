package pers.solid.extshape.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.item.BlockItem;
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

public class ExtShapeSlabBlock extends SlabBlock implements Waterloggable, RegistrableSubBlock {
    public final Identifier identifier;
    public final ExtShapeBlockItem blockItem;
    public BlockState baseBlockState;

    public ExtShapeSlabBlock(@NotNull BlockState baseBlockState, @Nullable Identifier identifier,
                             @Nullable Settings settings, @Nullable FabricItemSettings itemSettings) {
        super(settings == null ? FabricBlockSettings.copyOf(baseBlockState.getBlock()) : settings);
        this.baseBlockState = baseBlockState;
        this.identifier = identifier == null ? SubBlock.convertIdentifier(this.getBaseBlockIdentifier(), "_slab") :
                identifier;
        this.blockItem = new ExtShapeBlockItem(this, itemSettings == null ? new FabricItemSettings() : itemSettings);
    }

    public ExtShapeSlabBlock(@NotNull Block baseBlock, @Nullable Identifier identifier,
                             @Nullable Settings settings, @Nullable FabricItemSettings itemSettings) {
        this(baseBlock.getDefaultState(), identifier, settings, itemSettings);
    }

    public ExtShapeSlabBlock(@NotNull Block baseBlock) {
        this(baseBlock, null, null, null);
    }

    @Override
    public BlockState getBaseBlockState() {
        return this.baseBlockState;
    }


    @Override
    public ExtShapeSlabBlock addToTag() {
        this.addToTag(ExtShapeBlockTag.SLABS);
        return this;
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
    public MutableText getName() {
        return new TranslatableText("block.extshape.?_slab", this.getNamePrefix());
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
    public List<Pair<Identifier, String>> getBlockModelCollection() {
        final Block baseBlock = this.getBaseBlock();
        final Identifier baseIdentifier = Registry.BLOCK.getId(baseBlock);
        List<Pair<Identifier, String>> modelCollection = new ArrayList<>();
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier(), this.getBlockModelString()));
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_top"), this.getTopBlockModelString()));
        return modelCollection;
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

    public static class ExtShapeSlabBlockItem extends ExtShapeBlockItem {
        public ExtShapeSlabBlockItem(Block block, Settings settings) {
            super(block, settings);
        }
    }
}
