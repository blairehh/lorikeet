
package lorikeet;

import lorikeet.lobe.DiskWrite;

import java.util.Objects;

public class WriteFile implements DiskWrite<Tutorial> {

    private final String fileName;
    private final String content;

    public WriteFile(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
    }

    @Override
    public String fileName() {
        return this.fileName;
    }

    @Override
    public String content() {
        return this.content;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        WriteFile writeFile = (WriteFile) o;

        return Objects.equals(this.fileName(), writeFile.fileName())
            && Objects.equals(this.content(), writeFile.content());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fileName(), this.content());
    }
}