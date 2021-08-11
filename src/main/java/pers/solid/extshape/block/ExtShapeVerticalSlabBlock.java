package pers.solid.extshape.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.ExtShapeBlockItem;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class ExtShapeVerticalSlabBlock extends VerticalSlabBlock implements RegistrableSubBlock{
    public final Identifier identifier;
    public final ExtShapeBlockItem blockItem;
    public BlockState baseBlockState;

    public ExtShapeVerticalSlabBlock(@NotNull BlockState baseBlockState, @Nullable Identifier identifier,
                                     @Nullable Settings settings, @Nullable FabricItemSettings itemSettings) {
        super(settings == null ? FabricBlockSettings.copyOf(baseBlockState.getBlock()) : settings);
        this.baseBlockState = baseBlockState;
        this.identifier = identifier == null ? SubBlock.convertIdentifier(this.getBaseBlockIdentifier(),
                "_vertical_slab") :
                identifier;
        this.blockItem = new ExtShapeBlockItem(this, itemSettings == null ? new FabricItemSettings() : itemSettings);
    }

    public ExtShapeVerticalSlabBlock(@NotNull Block block, @Nullable Identifier identifier,
                                     @Nullable Settings settings, @Nullable FabricItemSettings itemSettings) {
        this(block.getDefaultState(),identifier,settings,itemSettings);
    }

    public ExtShapeVerticalSlabBlock(@NotNull Block block) {
        this(block,null,null,null);
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
        return this.addToTag(ExtShapeBlockTag.VERTICAL_SLABS);
    }

    @Override
    public BlockState getBaseBlockState() {
        return this.baseBlockState;
    }

    @Override
    public String getBlockStatesString() {
        return String.format("""
                {
                  "variants": {
                    "facing=north": { "model": "%1$s" , "uvlock":true },
                    "facing=east":  { "model": "%1$s", "y":  90 , "uvlock":true},
                    "facing=south": { "model": "%1$s", "y": 180 , "uvlock":true },
                    "facing=west":  { "model": "%1$s", "y": 270 , "uvlock":true }
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
                }""", this.getBottomTexture(),this.getTopTexture(),this.getSideTexture());
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
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_vertical_slab";
        if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_vertical_slab";
        if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return "stained_vertical_terracotta_slab";
        if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return "glazed_vertical_terracotta_slab";
        return "";
    }

    @Override
    public MutableText getName() {
        return new TranslatableText("block.extshape.?_vertical_slab", this.getNamePrefix());
    }
}
