package pers.solid.extshape.data;

import net.minecraft.data.DataGenerator;

import java.nio.file.Path;
import java.util.Collection;

@Deprecated
public class ExtShapeDataGenerator extends DataGenerator {
    public ExtShapeDataGenerator(Path output, Collection<Path> inputs) {
        super(output, inputs);
    }
}
