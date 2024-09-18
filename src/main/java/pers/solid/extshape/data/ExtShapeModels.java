package pers.solid.extshape.data;

import com.google.common.base.Preconditions;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.math.Direction;
import pers.solid.extshape.ExtShape;

import java.util.Map;
import java.util.Optional;

/**
 * @see Models
 */
public final class ExtShapeModels {
  private static Model block(String parent, TextureKey... requiredTextureKeys) {
    return new Model(Optional.of(ExtShape.id("block/" + parent)), Optional.empty(), requiredTextureKeys);
  }

  private static Model item(String parent, TextureKey... requiredTextureKeys) {
    return new Model(Optional.of(ExtShape.id("item/" + parent)), Optional.empty(), requiredTextureKeys);
  }

  private static Model block(String parent, String variant, TextureKey... requiredTextureKeys) {
    Preconditions.checkArgument(variant.startsWith("_") || variant.startsWith("-"), "variant must start with underscore");
    return new Model(Optional.of(ExtShape.id("block/" + parent)), Optional.of(variant), requiredTextureKeys);
  }


  public static final Model CIRCULAR_PAVING_SLAB = block("glazed_terracotta_slab", TextureKey.BOTTOM, TextureKey.TOP, TextureKey.SIDE);
  public static final Model CIRCULAR_PAVING_SLAB_TOP = block("glazed_terracotta_slab_top", "_top", TextureKey.BOTTOM, TextureKey.TOP, TextureKey.SIDE);
  public static final Model GLAZED_TERRACOTTA_SLAB = block("glazed_terracotta_slab", TextureKey.BOTTOM, TextureKey.TOP, TextureKey.SIDE);
  public static final Model GLAZED_TERRACOTTA_SLAB_TOP = block("glazed_terracotta_slab_top", "_top", TextureKey.BOTTOM, TextureKey.TOP, TextureKey.SIDE);
  public static final Model SLAB_COLUMN = block("slab_column", "_horizontal", TextureKey.END, TextureKey.SIDE);
  public static final Model SLAB_COLUMN_TOP = block("slab_column_top", "_horizontal_top", TextureKey.END, TextureKey.SIDE);
  public static final Model SLAB_COLUMN_HORIZONTAL = block("slab_column_horizontal", "_horizontal", TextureKey.END, TextureKey.SIDE);
  public static final Model SLAB_COLUMN_HORIZONTAL_TOP = block("slab_column_horizontal_top", "_horizontal_top", TextureKey.END, TextureKey.SIDE);
  public static final Model SLAB_COLUMN_UV_LOCKED_X = block("slab_column_uv_locked_x", "_x", TextureKey.END, TextureKey.SIDE);
  public static final Model SLAB_COLUMN_UV_LOCKED_Y = block("slab_column_uv_locked_y", "_y", TextureKey.END, TextureKey.SIDE);
  public static final Model SLAB_COLUMN_UV_LOCKED_Z = block("slab_column_uv_locked_z", "_z", TextureKey.END, TextureKey.SIDE);
  public static final Map<Direction.Axis, Model> SLAB_COLUMN_UV_LOCKED = Map.of(
      Direction.Axis.X, SLAB_COLUMN_UV_LOCKED_X,
      Direction.Axis.Y, SLAB_COLUMN_UV_LOCKED_Y,
      Direction.Axis.Z, SLAB_COLUMN_UV_LOCKED_Z
  );
  public static final Model SLAB_COLUMN_UV_LOCKED_X_TOP = block("slab_column_uv_locked_x_top", "_x_top", TextureKey.END, TextureKey.SIDE);
  public static final Model SLAB_COLUMN_UV_LOCKED_Y_TOP = block("slab_column_uv_locked_y_top", "_y_top", TextureKey.END, TextureKey.SIDE);
  public static final Model SLAB_COLUMN_UV_LOCKED_Z_TOP = block("slab_column_uv_locked_z_top", "_z_top", TextureKey.END, TextureKey.SIDE);
  public static final Map<Direction.Axis, Model> SLAB_COLUMN_UV_LOCKED_TOP = Map.of(
      Direction.Axis.X, SLAB_COLUMN_UV_LOCKED_X_TOP,
      Direction.Axis.Y, SLAB_COLUMN_UV_LOCKED_Y_TOP,
      Direction.Axis.Z, SLAB_COLUMN_UV_LOCKED_Z_TOP
  );

  public static final Model VERTICAL_SLAB = block("vertical_slab", TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM);
  public static final Model VERTICAL_SLAB_COLUMN = block("vertical_slab_column", "_horizontal", TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM);
  public static final Model VERTICAL_SLAB_COLUMN_TOP = block("vertical_slab_column_top", "_horizontal_top", TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM);
  public static final Model VERTICAL_SLAB_COLUMN_UNORDERED = block("vertical_slab_column_unordered", "_horizontal_unordered", TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM);
  public static final Model VERTICAL_SLAB_COLUMN_UNORDERED_TOP = block("vertical_slab_column_unordered_top", "_horizontal_unordered_top", TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM);
  public static final Model VERTICAL_SLAB_COLUMN_HORIZONTAL = block("vertical_slab_column_horizontal", "_horizontal", TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM);
  public static final Model VERTICAL_SLAB_COLUMN_HORIZONTAL_TOP = block("vertical_slab_column_horizontal_top", "_horizontal_top", TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM);
  public static final Model VERTICAL_SLAB_COLUMN_HORIZONTAL_UNORDERED = block("vertical_slab_column_horizontal_unordered", "_horizontal_unordered", TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM);
  public static final Model VERTICAL_SLAB_COLUMN_HORIZONTAL_UNORDERED_TOP = block("vertical_slab_column_horizontal_unordered_top", "_horizontal_unordered_top", TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM);

  public static final Model VERTICAL_STAIRS = block("vertical_stairs", TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM);

  public static final Model QUARTER_PIECE = block("quarter_piece", TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM);
  public static final Model QUARTER_PIECE_TOP = block("quarter_piece_top", "_top", TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM);
  public static final Model VERTICAL_QUARTER_PIECE = block("vertical_quarter_piece", TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM);
}
