package pers.solid.extshape.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class ExtShapeVerticalSlabBlock extends VerticalSlabBlock implements ExtShapeSubBlockInterface {

    public ExtShapeVerticalSlabBlock(@NotNull Block baseBlock,
                                     @Nullable Settings settings) {
        super(settings == null ? FabricBlockSettings.copyOf(baseBlock) : settings);
        BlockMappings.mappingOfVerticalSlabs.put(baseBlock,this);
    }

    public ExtShapeVerticalSlabBlock(@NotNull Block block) {
        this(block,null);
    }

    @Override
    public Identifier getDefaultIdentifier() {
        return SubBlock.convertIdentifier(this.getBaseBlockIdentifier(),"_vertical_slab");
    }

    @Override
    public ExtShapeBlockInterface addToTag() {
        return this.addToTag(ExtShapeBlockTag.VERTICAL_SLABS);
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
