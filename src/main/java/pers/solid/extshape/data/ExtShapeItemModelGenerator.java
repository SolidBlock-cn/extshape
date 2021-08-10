package pers.solid.extshape.data;

import com.google.gson.JsonElement;
import net.minecraft.block.Block;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.Model;
import net.minecraft.data.client.model.ModelIds;
import net.minecraft.data.client.model.Models;
import net.minecraft.data.client.model.Texture;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import pers.solid.extshape.block.RegistrableSubBlock;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

@Deprecated
public class ExtShapeItemModelGenerator extends ItemModelGenerator {
    private final BiConsumer<Identifier, Supplier<JsonElement>> writer;

    public ExtShapeItemModelGenerator(BiConsumer<Identifier, Supplier<JsonElement>> writer) {
        super(writer);
        this.writer = writer;
    }


    private void register(Item item, Model model) {
        model.upload(ModelIds.getItemModelId(item), Texture.layer0(item), this.writer);
    }

    private void register(Item item, String suffix, Model model) {
        model.upload(ModelIds.getItemSubModelId(item, suffix), Texture.layer0(Texture.getSubId(item, suffix)), this.writer);
    }

    private void register(Item item, Item texture, Model model) {
        model.upload(ModelIds.getItemModelId(item), Texture.layer0(texture), this.writer);
    }

    public void register() {
        for (final Block BLOCK : ExtShapeBlockTag.EXTSHAPE_REGISTERED_BLOCKS) {
            final BlockItem BLOCKITEM = ((RegistrableSubBlock) BLOCK).getBlockItem();
            if (BLOCKITEM != null) this.register(BLOCKITEM, Models.GENERATED);
        }
    }
}
