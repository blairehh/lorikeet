package lorikeet.parse;

import lorikeet.error.CompileError;
import lorikeet.error.UnreadableFile;
import lorikeet.lang.LorikeetSource;
import lorikeet.lang.SourceFile;
import lorikeet.token.Tokenizer;
import lorikeet.util.Disk;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class LorikeetParser {

    private final Disk disk;
    private final Tokenizer tokenizer = new Tokenizer();

    public LorikeetParser() {
        this.disk = new Disk();
    }

    public LorikeetSource parse(Collection<File> files) {
        final List<CompileError> errors = new ArrayList<CompileError>();
        final List<SourceFile> sourceFiles = new ArrayList<SourceFile>();

        for (File file : files) {
            final Parse<SourceFile> sourceFile = this.parseFile(file);
            if (sourceFile.failed()) {
                errors.addAll(sourceFile.getErrors());
                break;
            } else {
                sourceFiles.add(sourceFile.getResult());
            }
        }
        return new LorikeetSource(sourceFiles, errors);
    }

    private Parse<SourceFile> parseFile(File file) {
        try {
            final String fileContents = this.disk.read(file);
            return new SourceFileParser()
                .parse(this.tokenizer.tokenize(file, fileContents));
        } catch (IOException ioe) {
            return new Parse<SourceFile>(new UnreadableFile(file));
        }
    }

}
