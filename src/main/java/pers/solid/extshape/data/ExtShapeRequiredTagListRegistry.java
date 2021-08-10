package pers.solid.extshape.data;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.tag.RequiredTagList;
import net.minecraft.tag.RequiredTagListRegistry;
import net.minecraft.util.registry.RegistryKey;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Deprecated
public class ExtShapeRequiredTagListRegistry extends RequiredTagListRegistry {

    private static final Set<RegistryKey<?>> REQUIRED_LIST_KEYS = Sets.newHashSet();
    private static final List<RequiredTagList<?>> ALL = Lists.newArrayList();

    private static Set<RequiredTagList<?>> getBuiltinTags() {
        return ImmutableSet.of(ExtShapeBlockTags.REQUIRED_TAGS);
    }

    private static void validate() {
        Set set = getBuiltinTags().stream().map(RequiredTagList::getRegistryKey).collect(Collectors.toSet());
        if (!Sets.difference(REQUIRED_LIST_KEYS, set).isEmpty()) {
            throw new IllegalStateException("Missing helper registrations");
        }
    }
}
