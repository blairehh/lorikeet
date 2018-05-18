package lorikeet.compile;

import lorikeet.Settings;
import lorikeet.lang.SourceFile;
import lorikeet.lang.LorikeetSource;
import lorikeet.parse.LorikeetParser;
import lorikeet.transpile.JavaFile;
import lorikeet.transpile.JavaTranspiler;
import lorikeet.util.ErrorConsole;
import lorikeet.util.Disk;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class SourceCompiler {


    private final Settings settings;
    private final Disk disk = new Disk();
    private final ErrorConsole errorConsole = new ErrorConsole();
    private final LorikeetParser parser = new LorikeetParser();
    private final JavaTranspiler transpiler = new JavaTranspiler();

    public SourceCompiler(Settings settings) {
        this.settings = settings;
    }

    public void compile() {
        Collection<File> files = this.disk.listAllLorikeetFiles(this.settings.getSourceDirectory());

        LorikeetSource parse = this.parser.parse(files);
        if (parse.succeded()) {
            this.transpile(parse.getResults());
        } else {
            this.errorConsole.output(parse.getErrors());
        }
    }

    private void transpile(List<SourceFile> lorikeetFiles) {
        List<JavaFile> javaFiles = new ArrayList<JavaFile>();
        lorikeetFiles.forEach(f -> javaFiles.addAll(this.transpiler.transpile(f)));
        this.disk.writeAll(this.settings.getJavaDirectory(), javaFiles);
    }

}
