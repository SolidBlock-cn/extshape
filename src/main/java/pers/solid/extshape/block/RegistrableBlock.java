package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.ArrayList;
import java.util.List;

public interface RegistrableBlock {
    // 用于方块自行进行注册。

    String basePath = "../src/main/resources";

    Identifier getIdentifier();

    BlockItem getBlockItem();

    default void registerBlock() {
        Registry.register(Registry.BLOCK, this.getIdentifier(), (Block) this);
    }

    default void registerItem() {
        Registry.register(Registry.ITEM, this.getIdentifier(), this.getBlockItem());
    }

    default void register() {
        // 同时注册方块和物品
        this.registerBlock();
        this.registerItem();
    }


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

    RegistrableBlock addToTag();

    default RegistrableBlock addToTag(ExtShapeBlockTag tag) {
        assert this instanceof Block;
        tag.add((Block) this);
        return this;
    }

    default String toStringInterface() {
        // 实现该接口时，请设置public String getString() this.toStringInterface()
        return "Block{" + this.getIdentifier().toString() + "}";
    }
}
