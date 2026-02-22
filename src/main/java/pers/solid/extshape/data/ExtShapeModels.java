package pers.solid.extshape.data;

import com.google.common.base.Preconditions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.core.Direction;
import pers.solid.extshape.ExtShape;

import java.util.Map;
import java.util.Optional;

/**
 * @see ModelTemplates
 */
@Environment(EnvType.CLIENT)
public final class ExtShapeModels {
  private static ModelTemplate block(String parent, TextureSlot... requiredTextureKeys) {
    return new ModelTemplate(Optional.of(ExtShape.id("block/" + parent)), Optional.empty(), requiredTextureKeys);
  }

  private static ModelTemplate item(String parent, TextureSlot... requiredTextureKeys) {
    return new ModelTemplate(Optional.of(ExtShape.id("item/" + parent)), Optional.empty(), requiredTextureKeys);
  }

  private static ModelTemplate block(String parent, String variant, TextureSlot... requiredTextureKeys) {
    Preconditions.checkArgument(variant.startsWith("_") || variant.startsWith("-"), "variant must start with underscore");
    return new ModelTemplate(Optional.of(ExtShape.id("block/" + parent)), Optional.of(variant), requiredTextureKeys);
  }


  public static final ModelTemplate CIRCULAR_PAVING_SLAB = block("glazed_terracotta_slab", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE);
  public static final ModelTemplate CIRCULAR_PAVING_SLAB_TOP = block("glazed_terracotta_slab_top", "_top", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE);
  public static final ModelTemplate GLAZED_TERRACOTTA_SLAB = block("glazed_terracotta_slab", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE);
  public static final ModelTemplate GLAZED_TERRACOTTA_SLAB_TOP = block("glazed_terracotta_slab_top", "_top", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE);
  public static final ModelTemplate SLAB_COLUMN = block("slab_column", "_horizontal", TextureSlot.END, TextureSlot.SIDE);
  public static final ModelTemplate SLAB_COLUMN_TOP = block("slab_column_top", "_horizontal_top", TextureSlot.END, TextureSlot.SIDE);
  public static final ModelTemplate SLAB_COLUMN_HORIZONTAL = block("slab_column_horizontal", "_horizontal", TextureSlot.END, TextureSlot.SIDE);
  public static final ModelTemplate SLAB_COLUMN_HORIZONTAL_TOP = block("slab_column_horizontal_top", "_horizontal_top", TextureSlot.END, TextureSlot.SIDE);
  public static final ModelTemplate SLAB_COLUMN_UV_LOCKED_X = block("slab_column_uv_locked_x", "_x", TextureSlot.END, TextureSlot.SIDE);
  public static final ModelTemplate SLAB_COLUMN_UV_LOCKED_Y = block("slab_column_uv_locked_y", "_y", TextureSlot.END, TextureSlot.SIDE);
  public static final ModelTemplate SLAB_COLUMN_UV_LOCKED_Z = block("slab_column_uv_locked_z", "_z", TextureSlot.END, TextureSlot.SIDE);
  public static final Map<Direction.Axis, ModelTemplate> SLAB_COLUMN_UV_LOCKED = Map.of(
      Direction.Axis.X, SLAB_COLUMN_UV_LOCKED_X,
      Direction.Axis.Y, SLAB_COLUMN_UV_LOCKED_Y,
      Direction.Axis.Z, SLAB_COLUMN_UV_LOCKED_Z
  );
  public static final ModelTemplate SLAB_COLUMN_UV_LOCKED_X_TOP = block("slab_column_uv_locked_x_top", "_x_top", TextureSlot.END, TextureSlot.SIDE);
  public static final ModelTemplate SLAB_COLUMN_UV_LOCKED_Y_TOP = block("slab_column_uv_locked_y_top", "_y_top", TextureSlot.END, TextureSlot.SIDE);
  public static final ModelTemplate SLAB_COLUMN_UV_LOCKED_Z_TOP = block("slab_column_uv_locked_z_top", "_z_top", TextureSlot.END, TextureSlot.SIDE);
  public static final Map<Direction.Axis, ModelTemplate> SLAB_COLUMN_UV_LOCKED_TOP = Map.of(
      Direction.Axis.X, SLAB_COLUMN_UV_LOCKED_X_TOP,
      Direction.Axis.Y, SLAB_COLUMN_UV_LOCKED_Y_TOP,
      Direction.Axis.Z, SLAB_COLUMN_UV_LOCKED_Z_TOP
  );

  public static final ModelTemplate VERTICAL_SLAB = block("vertical_slab", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM);
  public static final ModelTemplate VERTICAL_SLAB_COLUMN = block("vertical_slab_column", "_horizontal", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM);
  public static final ModelTemplate VERTICAL_SLAB_COLUMN_TOP = block("vertical_slab_column_top", "_horizontal_top", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM);
  public static final ModelTemplate VERTICAL_SLAB_COLUMN_UNORDERED = block("vertical_slab_column_unordered", "_horizontal_unordered", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM);
  public static final ModelTemplate VERTICAL_SLAB_COLUMN_UNORDERED_TOP = block("vertical_slab_column_unordered_top", "_horizontal_unordered_top", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM);
  public static final ModelTemplate VERTICAL_SLAB_COLUMN_HORIZONTAL = block("vertical_slab_column_horizontal", "_horizontal", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM);
  public static final ModelTemplate VERTICAL_SLAB_COLUMN_HORIZONTAL_TOP = block("vertical_slab_column_horizontal_top", "_horizontal_top", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM);
  public static final ModelTemplate VERTICAL_SLAB_COLUMN_HORIZONTAL_UNORDERED = block("vertical_slab_column_horizontal_unordered", "_horizontal_unordered", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM);
  public static final ModelTemplate VERTICAL_SLAB_COLUMN_HORIZONTAL_UNORDERED_TOP = block("vertical_slab_column_horizontal_unordered_top", "_horizontal_unordered_top", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM);

  public static final ModelTemplate VERTICAL_STAIRS = block("vertical_stairs", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM);

  public static final ModelTemplate QUARTER_PIECE = block("quarter_piece", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM);
  public static final ModelTemplate QUARTER_PIECE_TOP = block("quarter_piece_top", "_top", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM);
  public static final ModelTemplate VERTICAL_QUARTER_PIECE = block("vertical_quarter_piece", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM);
}
