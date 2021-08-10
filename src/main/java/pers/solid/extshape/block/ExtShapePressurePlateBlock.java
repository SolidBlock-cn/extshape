package pers.solid.extshape.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import pers.solid.extshape.ExtShapeBlockItem;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.List;

public class ExtShapePressurePlateBlock extends PressurePlateBlock implements RegistrableSubBlock {
    private Identifier identifier;
    private BlockItem blockItem;
    private BlockState baseBlockState;

    protected ExtShapePressurePlateBlock(ActivationRule type, Settings settings) {
        super(type, settings);
    }

    public ExtShapePressurePlateBlock(ActivationRule type, BlockState baseBlockState, Identifier identifier, Settings settings) {
        this(type, settings);
        this.baseBlockState = baseBlockState;
        this.identifier = identifier;
        this.blockItem = new ExtShapeBlockItem(this, new FabricItemSettings());
    }

    public ExtShapePressurePlateBlock(ActivationRule type, Block baseBlock, Identifier identifier, Settings settings) {
        this(type, baseBlock.getDefaultState(), identifier, settings);
    }

    public ExtShapePressurePlateBlock(ActivationRule type, Block baseBlock, Settings settings) {
        this(type, baseBlock, SubBlock.convertIdentifier(Registry.BLOCK.getId(baseBlock), "_pressure_plate"),
                settings);
    }

    public ExtShapePressurePlateBlock(ActivationRule type, Block baseBlock) {
        this(type, baseBlock, FabricBlockSettings.copyOf(baseBlock).noCollision().strength(baseBlock.getHardness() / 4f));
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
        return this.addToTag(ExtShapeBlockTag.PRESSURE_PLATES);
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
                    "powered=false": {
                      "model": "%1$s"
                    },
                    "powered=true": {
                      "model": "%1$s_down"
                    }
                  }
                }""", this.getBlockModelIdentifier());
    }

    @Override
    public String getBlockModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/pressure_plate_up",
                  "textures": {
                    "texture": "%s"
                  }
                }""", this.getBaseTexture());
    }

    public String getDownModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/pressure_plate_up",
                  "textures": {
                    "texture": "%s"
                  }
                }""", this.getBaseTexture());
    }

    @Override
    public List<Pair<Identifier, String>> getBlockModelCollection() {
        return List.of(
                new Pair<Identifier, String>(this.getBlockModelIdentifier(), this.getBlockModelString()),
                new Pair<Identifier, String>(this.getBlockModelIdentifier("_down"), this.getDownModelString())
        );
    }

    @Override
    public String getCraftingRecipeString() {
        return String.format("""
                {
                  "type": "minecraft:crafting_shaped",
                  "group": "%s",
                  "pattern": [
                    "##"
                  ],
                  "key": {
                    "#": {
                      "item": "%s"
                    }
                  },
                  "result": {
                    "item": "%s"
                  }
                }""", this.getRecipeGroup(), this.getBaseBlockIdentifier(), this.getIdentifier());
    }

    @Override
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if (ExtShapeBlockTag.WOOLS.contains(baseBlock)) return "wool_pressure_plate";
        if (ExtShapeBlockTag.CONCRETES.contains(baseBlock)) return "concrete_pressure_plate";
        if (ExtShapeBlockTag.STAINED_TERRACOTTAS.contains(baseBlock)) return "stained_terracotta_pressure_plate";
        if (ExtShapeBlockTag.GLAZED_TERRACOTTAS.contains(baseBlock)) return "glazed_terracotta_pressure_plate";
        if (ExtShapeBlockTag.PLANKS.contains(baseBlock)) return "wooden_pressure_plate";
        return "";
    }

    @Override
    public MutableText getName() {
        return new TranslatableText("block.extshape.?_pressure_plate", this.getNamePrefix());
    }
}
