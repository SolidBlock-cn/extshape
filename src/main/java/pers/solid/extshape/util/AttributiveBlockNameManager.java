package pers.solid.extshape.util;

import com.google.common.collect.ImmutableMap;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Language;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AttributiveBlockNameManager {
  public static final Pattern END_WITH_BLOCK = Pattern.compile("\\b Block$");
  public static final Pattern END_WITH_BRICKS = Pattern.compile("\\b Bricks$");
  public static final Pattern END_WITH_TILES = Pattern.compile("\\b Tiles$");
  public static final Pattern END_WITH_PLANKS = Pattern.compile("\\b Planks$");
  public static final Pattern BEGIN_WITH_BLOCK_OF = Pattern.compile("^Block [Oo]f \\b");

  public static final Map<Pattern, String> EN_REPLACEMENT = ImmutableMap.<Pattern, String>builder()
      .put(BEGIN_WITH_BLOCK_OF, StringUtils.EMPTY)
      .put(END_WITH_BLOCK, StringUtils.EMPTY)
      .put(END_WITH_BRICKS, " Brick")
      .put(END_WITH_TILES, " Tile")
      .put(END_WITH_PLANKS, StringUtils.EMPTY)
      .build();

  public static final Map<Pattern, String> ZH_REPLACEMENT = ImmutableMap.<Pattern, String>builder()
      .put(Pattern.compile("方块$"), StringUtils.EMPTY)
      .put(Pattern.compile("方塊$"), StringUtils.EMPTY)
      .put(Pattern.compile("块$"), StringUtils.EMPTY)
      .put(Pattern.compile("塊$"), StringUtils.EMPTY)
      .put(Pattern.compile("木+板$"), "木")
      .put(Pattern.compile("竹板"), "竹")
      .put(Pattern.compile("木材$"), StringUtils.EMPTY)
      .put(Pattern.compile("竹材"), "竹")
      .build();

  public static final Map<Pattern, String> LZH_REPLACEMENT = ImmutableMap.<Pattern, String>builder()
      .put(Pattern.compile("塊$"), StringUtils.EMPTY)
      .put(Pattern.compile("材$"), StringUtils.EMPTY)
      .build();

  public static final Map<Pattern, String> ZH_HK_REPLACEMENT = ImmutableMap.of(Pattern.compile("磚$"), StringUtils.EMPTY);

  public static final Map<Pattern, String> JA_REPLACEMENT = ImmutableMap.<Pattern, String>builder()
      .put(Pattern.compile("ブロック$"), StringUtils.EMPTY)
      .put(Pattern.compile("の板材$"), StringUtils.EMPTY)
      .put(Pattern.compile("板材$"), StringUtils.EMPTY)
      .build();
  public static final String ATTRIBUTIVE_KEY = "extshape.special.attributive";

  @Contract("_, _, !null -> !null")
  public static @Nullable String replace(@NotNull String from, @NotNull Map<? extends Pattern, ? extends String> replacement, @Nullable String defaultValue) {
    for (Map.Entry<? extends Pattern, ? extends String> entry : replacement.entrySet()) {
      final Matcher matcher = entry.getKey().matcher(from);
      if (matcher.find()) {
        return matcher.replaceFirst(entry.getValue());
      }
    }
    return defaultValue;
  }


  public static @NotNull String replace(@NotNull String from, @NotNull Map<? extends Pattern, ? extends String> replacement) {
    return replace(from, replacement, from);
  }

  public static MutableText getAttributiveBlockName(MutableText text) {
    if (text.getContent() instanceof TranslatableTextContent translatableTextContent) {
      final MutableText translatable = Text.translatable(ATTRIBUTIVE_KEY, ArrayUtils.insert(0, translatableTextContent.getArgs(), translatableTextContent.getKey()));
      translatable.setStyle(text.getStyle());
      text.getSiblings().forEach(translatable::append);
      return translatable;
    } else {
      return text;
    }
  }

  @Contract("_, _, !null -> !null")
  public static String convertToAttributive(@NotNull String from, @NotNull Language language, @Nullable String defaultValue) {
    final String code = language.get("language.code");
    String result = replace(from, EN_REPLACEMENT, null);
    if (result == null && code.startsWith("zho")) {
      result = replace(from, ZH_REPLACEMENT, null);
    }
    if (result == null && code.startsWith("jpn")) {
      result = replace(from, JA_REPLACEMENT, null);
    }

    if (result == null && code.endsWith("_HK")) {
      result = replace(from, ZH_HK_REPLACEMENT, null);
    }
    if (result == null && code.equals("lzh")) {
      result = replace(from, LZH_REPLACEMENT, null);
    }

    // 此时 result 还有可能是 null，表示没有替换过。

    if (result == null) result = defaultValue;

    if (code.startsWith("jpn")) {
      if (result != null) {
        if (result.endsWith("歪ん")) {
          result += "だ";
        } else if (!result.endsWith("歪んだ")) {
          result += "の";
        }
      }
    }
    return result;
  }

  public static @NotNull String convertToAttributive(@NotNull String from, @NotNull Language language) {
    return convertToAttributive(from, language, from);
  }
}
