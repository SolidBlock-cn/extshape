package pers.solid.extshape.block;

import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JBlockStates;
import net.devtech.arrp.json.blockstate.JVariants;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockCollections;

public class ExtShapeVerticalSlabBlock extends VerticalSlabBlock implements ExtShapeVariantBlockInterface {
  public final Block baseBlock;

  public ExtShapeVerticalSlabBlock(Block baseBlock, Settings settings) {
    super(settings);
    this.baseBlock = baseBlock;
  }

  @Override
  public MutableText getName() {
    return Text.translatable("block.extshape.?_vertical_slab", this.getNamePrefix());
  }

  @Environment(EnvType.CLIENT)
  @Override
  public @NotNull JBlockStates getBlockStates() {
    final Identifier identifier = getBlockModelId();
    return JBlockStates.ofVariants(new JVariants()
        .addVariant("facing", "south", new JBlockModel(identifier).uvlock())
        .addVariant("facing", "west", new JBlockModel(identifier).uvlock().y(90))
        .addVariant("facing", "north", new JBlockModel(identifier).uvlock().y(180))
        .addVariant("facing", "east", new JBlockModel(identifier).uvlock().y(270))
    );
  }


  @Environment(EnvType.CLIENT)
  @Override
  public @NotNull JModel getBlockModel() {
    return simpleModel("extshape:block/vertical_slab");
  }

  @Override
  public @NotNull JRecipe getStonecuttingRecipe() {
    return simpleStoneCuttingRecipe(2).recipeCategory(getRecipeCategory());
  }

  @Override
  public String getRecipeGroup() {
    if ((BlockCollections.PLANKS).contains(baseBlock)) return "wooden_vertical_slab";
    if ((BlockCollections.WOOLS).contains(baseBlock)) return "wool_vertical_slab";
    if ((BlockCollections.CONCRETES).contains(baseBlock)) return "concrete_vertical_slab";
    if ((BlockCollections.STAINED_TERRACOTTA).contains(baseBlock)) return "stained_terracotta_vertical_slab";
    if ((BlockCollections.GLAZED_TERRACOTTA).contains(baseBlock)) return "glazed_terracotta_vertical_slab";
    return "";
  }

  @Override
  public Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.VERTICAL_SLAB;
  }


  public static class WithExtension extends ExtShapeVerticalSlabBlock {
    private final BlockExtension extension;

    public WithExtension(Block baseBlock, Settings settings, BlockExtension extension) {
      super(baseBlock, settings);
      this.extension = extension;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean dropExperience) {
      super.onStacksDropped(state, world, pos, stack, dropExperience);
      extension.stacksDroppedCallback().onStackDropped(state, world, pos, stack, dropExperience);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
      super.onProjectileHit(world, state, hit, projectile);
      extension.projectileHitCallback().onProjectileHit(world, state, hit, projectile);
    }


    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
      super.onSteppedOn(world, pos, state, entity);
      extension.steppedOnCallback().onSteppedOn(world, pos, state, entity);
    }
  }
}
