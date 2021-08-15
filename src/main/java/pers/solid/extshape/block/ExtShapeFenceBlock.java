package pers.solid.extshape.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
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

public class ExtShapeFenceBlock extends FenceBlock implements ExtShapeSubBlockInterface {
    protected final Item craftingIngredient;

    public ExtShapeFenceBlock(@NotNull Block baseBlock,
                              @NotNull Item craftingIngredient,
                              @Nullable Settings settings) {
        super(settings == null ? FabricBlockSettings.copyOf(baseBlock) : settings);
        this.craftingIngredient = craftingIngredient;
    }


    public ExtShapeFenceBlock(Block baseBlock, Item craftingIngredient) {
        this(baseBlock, craftingIngredient,
                null);
    }

    public ExtShapeFenceBlock addToTag() {
        this.addToTag(ExtShapeBlockTag.FENCES);
        return this;
    }

    public Item getCraftingIngredient() {
        return this.craftingIngredient;
    }

    public Identifier getCraftingIngredientIdentifier() {
        return Registry.ITEM.getId(this.craftingIngredient);
    }

    @Override
    public MutableText getName() {
        return new TranslatableText("block.extshape.?_fence", this.getNamePrefix());
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
    public List<Pair<Identifier, String>> getBlockModelCollection() {
        final Block baseBlock = this.getBaseBlock();
        final Identifier baseIdentifier = Registry.BLOCK.getId(baseBlock);
        List<Pair<Identifier, String>> modelCollection = new ArrayList<>();
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_inventory"), this.getInventoryBlockModelString()));
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_side"), this.getSideBlockModelString()));
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_post"), this.getPostBlockModelString()));
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

    // 内部类
    public static class ExtShapeFenceBlockItem extends ExtShapeBlockItem {
        public ExtShapeFenceBlockItem(Block block, Settings settings) {
            super(block, settings);
        }
    }
}
