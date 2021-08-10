package pers.solid.extshape.data;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.BlockTagsProvider;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import pers.solid.extshape.block.RegistrableSubBlock;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class ExtShapeBlockTagsProvider extends BlockTagsProvider {

    public ExtShapeBlockTagsProvider(DataGenerator root) {
        super(root);
    }

    @Override
    protected void configure() {

        ExtShapeBlockTag.STAIRS.forEach(this.getOrCreateTagBuilder(BlockTags.STAIRS)::add);
        ExtShapeBlockTag.SLABS.forEach(this.getOrCreateTagBuilder(BlockTags.SLABS)::add);
        ExtShapeBlockTag.FENCES.forEach(this.getOrCreateTagBuilder(BlockTags.FENCES)::add);
        ExtShapeBlockTag.FENCE_GATES.forEach(this.getOrCreateTagBuilder(BlockTags.FENCE_GATES)::add);
        try {
            for (final Block BLOCK : ExtShapeBlockTag.EXTSHAPE_REGISTERED_BLOCKS) {
                // 根据其基础方块所在标签，将其加入该标签。
                // 请注意这里的ExtShapeBlock是接口。
                final Block baseBlock = ((RegistrableSubBlock) BLOCK).getBaseBlock();
                final List<Tag.Identified<Block>> list = new ArrayList<Tag.Identified<Block>>();
                list.add(BlockTags.AXE_MINEABLE);
                list.add(BlockTags.HOE_MINEABLE);
                list.add(BlockTags.PICKAXE_MINEABLE);
                list.add(BlockTags.SHOVEL_MINEABLE);
                for (final Tag.Identified<Block> BLOCK_TAG : list) {
                    final ObjectBuilder<Block> BUILDER = this.getOrCreateTagBuilder(BLOCK_TAG);
                    if (baseBlock.getDefaultState().isIn(BLOCK_TAG))
                        BUILDER.add(BLOCK);
                }
            }
        } catch (IllegalStateException e) {
            System.out.printf("由于以下原因，暂不注册部分方块标签：%s。\n", e);
        }
    }

    @Override
    public String getName() {
        return "扩展方块形状模组的方块标签";
    }
}
