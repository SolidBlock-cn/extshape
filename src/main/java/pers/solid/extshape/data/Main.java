package pers.solid.extshape.data;

import net.minecraft.data.DataGenerator;

import java.nio.file.Path;
import java.util.Collection;

@Deprecated
public class Main extends net.minecraft.data.Main {
    // 这是我尝试根据原版代码的数据生成器来生成数据的代码。
    // 实践证明，这个非常麻烦，且根本不利于模组编写，故废弃。
    // 执行数据生成器的主函数。
    // 参考 @link{net.minecraft.data.Main}

//    public static void main(String[] args) throws IOException {
//        SharedConstants.createGameVersion();
//        OptionParser optionParser = new OptionParser();
//        OptionSpec<Void> optionSpec = optionParser.accepts("help", "Show the help menu").forHelp();
//        OptionSpec<Void> optionSpec2 = optionParser.accepts("server", "Include server generators");
//        OptionSpec<Void> optionSpec3 = optionParser.accepts("client", "Include client generators");
//        OptionSpec<Void> optionSpec4 = optionParser.accepts("dev", "Include development tools");
//        OptionSpec<Void> optionSpec5 = optionParser.accepts("reports", "Include data reports");
//        OptionSpec<Void> optionSpec6 = optionParser.accepts("validate", "Validate inputs");
//        OptionSpec<Void> optionSpec7 = optionParser.accepts("all", "Include all generators");
//        OptionSpec<String> optionSpec8 = optionParser.accepts("output", "Output folder").withRequiredArg().defaultsTo("generated");
//        OptionSpec<String> optionSpec9 = optionParser.accepts("input", "Input folder").withRequiredArg();
//        OptionSet optionSet = optionParser.parse(args);
//        if (!optionSet.has(optionSpec) && optionSet.hasOptions()) {
//            Path path = Paths.get(optionSpec8.value(optionSet));
//            boolean bl = optionSet.has(optionSpec7);
//            boolean bl2 = bl || optionSet.has(optionSpec3);
//            boolean bl3 = bl || optionSet.has(optionSpec2);
//            boolean bl4 = bl || optionSet.has(optionSpec4);
//            boolean bl5 = bl || optionSet.has(optionSpec5);
//            boolean bl6 = bl || optionSet.has(optionSpec6);
//            DataGenerator dataGenerator = create(path, (Collection) optionSet.valuesOf((OptionSpec) optionSpec9).stream().map((string) -> {
//                return Paths.get((String) string);
//            }).collect(Collectors.toList()), bl2, bl3, bl4, bl5, bl6);
//            dataGenerator.run();
//        } else {
//            optionParser.printHelpOn((OutputStream) System.out);
//        }
//    }

    public static DataGenerator create(Path output, Collection<Path> inputs, boolean includeClient, boolean includeServer, boolean includeDev, boolean includeReports, boolean validate) {
        DataGenerator dataGenerator = new DataGenerator(output, inputs);
//        if (includeClient || includeServer) {
//            dataGenerator.install((new SnbtProvider(dataGenerator)).addWriter(new StructureValidatorProvider()));
//        }
//
//        if (includeClient) {
//            dataGenerator.install(new ExtShapeBlockStateDefinitionProvider(dataGenerator));
//        }
//        ExtShape.assertRegistration();
        if (includeServer) {
//            dataGenerator.install(new FluidTagsProvider(dataGenerator));


//            BlockTagsProvider blockTagsProvider = new BlockTagsProvider(dataGenerator);
//            dataGenerator.install(blockTagsProvider);

            ExtShapeBlockTagsProvider extshapeBlockTagsProvider = new ExtShapeBlockTagsProvider(dataGenerator);
            dataGenerator.install(extshapeBlockTagsProvider);

//            dataGenerator.install(new ItemTagsProvider(dataGenerator, blockTagsProvider));
//            dataGenerator.install(new EntityTypeTagsProvider(dataGenerator));
//            dataGenerator.install(new RecipesProvider(dataGenerator));
//            dataGenerator.install(new AdvancementsProvider(dataGenerator));
//            dataGenerator.install(new LootTablesProvider(dataGenerator));
//            dataGenerator.install(new GameEventTagsProvider(dataGenerator));
        }

//        if (includeDev) {
//            dataGenerator.install(new NbtProvider(dataGenerator));
//        }
//
//        if (includeReports) {
//            dataGenerator.install(new BlockListProvider(dataGenerator));
//            dataGenerator.install(new RegistryDumpProvider(dataGenerator));
//            dataGenerator.install(new CommandSyntaxProvider(dataGenerator));
//            dataGenerator.install(new BiomeListProvider(dataGenerator));
//        }

        return dataGenerator;
    }
}
