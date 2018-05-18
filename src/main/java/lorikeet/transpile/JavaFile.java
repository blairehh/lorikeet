package lorikeet.transpile;

import java.util.Objects;

public class JavaFile {
    private final String subDirectory;
    private final String name;
    private final String contents;

    public JavaFile(String subDirectory, String name, String contents) {
        this.subDirectory = subDirectory;
        this.name = name;
        this.contents = contents;
    }

    public String getName() {
        return this.name;
    }

    public String getSubDirectory() {
        return this.subDirectory;
    }

    public String getContents() {
        return this.contents;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        JavaFile that = (JavaFile)o;

        return Objects.equals(this.getName(), that.getName())
            && Objects.equals(this.getContents(), that.getContents())
            && Objects.equals(this.getSubDirectory(), that.getSubDirectory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.subDirectory, this.contents);
    }
}
