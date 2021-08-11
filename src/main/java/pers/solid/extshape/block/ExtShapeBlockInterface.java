package pers.solid.extshape.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import pers.solid.extshape.ExtShapeBlockItem;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.ArrayList;
import java.util.List;

public interface ExtShapeBlockInterface {
    default Identifier getIdentifier() {
        if (!(this instanceof Block)) return null;
        return Registry.BLOCK.getId((Block) this);
    }

    default Item getBlockItem() {
        if (!(this instanceof Block)) return null;
        return Item.BLOCK_ITEMS.get(this);
    }

    default ExtShapeBlockInterface registerBlock(Identifier identifier) {
        Registry.register(Registry.BLOCK, identifier, (Block) this);
        return this;
    }

    default ExtShapeBlockInterface registerBlock() {
        this.registerBlock(this.getDefaultIdentifier());
        return this;
    }

    default ExtShapeBlockInterface registerItem(Item.Settings settings, Identifier identifier) {
        // 创建并注册物品。
        BlockItem blockItem = new ExtShapeBlockItem((Block) this,settings==null ? new FabricItemSettings() : settings);
        Registry.register(Registry.ITEM, identifier, blockItem);
        return this;
    }

    default ExtShapeBlockInterface registerItem(Item.Settings settings) {
        return this.registerItem(settings,this.getDefaultIdentifier());
    }

    default ExtShapeBlockInterface registerItem() {
        return this.registerItem(new FabricItemSettings());
    }

    default ExtShapeBlockInterface register(Identifier identifier, Item.Settings itemSettings) {
        // 同时注册方块和物品
        return this.registerBlock(identifier).registerItem(itemSettings,identifier);
    }

    default ExtShapeBlockInterface register(Identifier identifier) {
        return this.register(identifier, new FabricItemSettings());
    }

    default ExtShapeBlockInterface register(Item.Settings settings) {
        return this.register(this.getDefaultIdentifier(),settings);
    }

    default ExtShapeBlockInterface register() {
        return this.register(this.getDefaultIdentifier());
    }

    Identifier getDefaultIdentifier();

    default Identifier getBlockModelIdentifier() {
        Identifier identifier = this.getIdentifier();
        return new Identifier(identifier.getNamespace(), "block/" + identifier.getPath());
    }

    default Identifier getBlockModelIdentifier(String suffix) {
        Identifier identifier = this.getIdentifier();
        return new Identifier(identifier.getNamespace(), "block/" + identifier.getPath() + suffix);
    }

    default List<Pair<Identifier, String>> getBlockModelCollection() {
        List<Pair<Identifier, String>> modelCollection = new ArrayList<>();
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier(), this.getBlockModelString()));
        return modelCollection;
    }

    default Identifier getItemModelIdentifier() {
        Identifier identifier = this.getIdentifier();
        return new Identifier(identifier.getNamespace(), "item/" + identifier.getPath());
    }

    default String getBlockModelString() {
        return """
                {
                    "parent": "block/block"
                }
                """;
    }

    default String getItemModelString() {
        return String.format("""
                {
                    "parent": "%s"
                }
                """, this.getBlockModelIdentifier().toString());
    }

    default String getBlockStatesString() {
        return String.format("""
                {
                    "variants": {
                        "": {"model": "%s"}
                    }
                }
                """, this.getBlockModelIdentifier().toString());
    }


    default String getCraftingRecipeString() {
        return null;
    }

    default String getStoneCuttingRecipeString() {
        return null;
    }

    default String getLootTableString() {
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
                          "name": "%s"
                        }
                      ],
                      "conditions": [
                        {
                          "condition": "minecraft:survives_explosion"
                        }
                      ]
                    }
                  ]
                }""", identifier.toString());
    }


    default String getRecipeGroup() {
        // 合成配方中的group参数。
        return "";
    }

    ExtShapeBlockInterface addToTag();

    default ExtShapeBlockInterface addToTag(ExtShapeBlockTag tag) {
        assert this instanceof Block;
        tag.add((Block) this);
        return this;
    }

    default String toStringInterface() {
        // 实现该接口时，请设置public String getString() this.toStringInterface()
        return "Block{" + this.getIdentifier().toString() + "}";
    }
}
