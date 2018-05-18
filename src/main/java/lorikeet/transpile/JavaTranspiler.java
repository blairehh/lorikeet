package lorikeet.transpile;

import lorikeet.lang.SourceFile;
import lorikeet.lang.Module;
import lorikeet.lang.Struct;
import java.util.List;
import java.util.ArrayList;

public class JavaTranspiler {

    public List<JavaFile> transpile(SourceFile sourceFile) {
        List<JavaFile> files = new ArrayList<JavaFile>();
        sourceFile.getStructs().forEach(struct -> files.addAll(this.transpileStruct(struct)));
        sourceFile.getModules().forEach(module -> files.addAll(this.transpileModule(module)));
        return files;
    }

    private List<JavaFile> transpileStruct(Struct struct) {
        final StructTranspiler transpiler = new StructTranspiler();
        return transpiler.transpile(struct);
    }

    private List<JavaFile> transpileModule(Module module) {
        final ModuleTranspiler transpiler = new ModuleTranspiler();
        return transpiler.transpile(module);
    }
}
