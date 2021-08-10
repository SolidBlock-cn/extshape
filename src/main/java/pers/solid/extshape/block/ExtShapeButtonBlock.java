package pers.solid.extshape.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.ExtShapeBlockItem;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.ArrayList;
import java.util.List;

public class ExtShapeButtonBlock extends AbstractButtonBlock implements RegistrableSubBlock {

    public final Identifier identifier;
    public final BlockItem blockItem;
    public final BlockState baseBlockState;
    public final ButtonType type;

    @Override
    protected SoundEvent getClickSound(boolean powered) {
        if (this.type == ButtonType.wooden)
            return powered ? SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON : SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_OFF;
        else return powered ? SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON : SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF;
    }

    public enum ButtonType {
        wooden,
        stone,
        hard,
        soft
    }

    protected ExtShapeButtonBlock(@NotNull ButtonType type, @NotNull BlockState baseBlockState,
                                  @Nullable Identifier identifier,
                                  @Nullable Settings settings, @Nullable FabricItemSettings itemSettings) {
        super(type == ButtonType.wooden, settings == null ?
                FabricBlockSettings.copyOf(baseBlockState.getBlock()).noCollision().strength(baseBlockState.getBlock().getHardness() / 4f) :
                settings);
        this.type = type;
        this.baseBlockState = baseBlockState;
        this.identifier = identifier == null ?
                SubBlock.convertIdentifier(this.getBaseBlockIdentifier(), "_button") : identifier;
        this.blockItem = new ExtShapeBlockItem(this, itemSettings == null ? new FabricItemSettings() : itemSettings);
    }

    protected ExtShapeButtonBlock(ButtonType type, Block block, Identifier identifier, Settings settings,
                                  FabricItemSettings itemSettings) {
        this(type, block.getDefaultState(), identifier, settings, itemSettings);
    }

    public ExtShapeButtonBlock(ButtonType type, Block baseBlock) {
        this(type, baseBlock, null, null, null);
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
    public RegistrableBlock addToTag() {
        return this.addToTag(ExtShapeBlockTag.BUTTONS);
    }

    @Override
    public BlockState getBaseBlockState() {
        return this.baseBlockState;
    }

    @Override
    public MutableText getName() {
        return new TranslatableText("block.extshape.?_button", this.getNamePrefix());
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
                }""", this.getRecipeGroup(), this.getBaseBlockIdentifier().toString(), this.getIdentifier().toString());
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
