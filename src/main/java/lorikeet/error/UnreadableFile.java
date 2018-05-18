package lorikeet.error;

import lorikeet.util.Console;

import java.io.File;
import java.util.Objects;

public class UnreadableFile implements CompileError {

    private final File file;

    public UnreadableFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    @Override
    public void outputTo(Console console) {
        console.print("Could not read file %s", this.file.getAbsolutePath());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        UnreadableFile that = (UnreadableFile)o;

        return Objects.equals(this.getFile(), that.getFile());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.file);
    }

}
