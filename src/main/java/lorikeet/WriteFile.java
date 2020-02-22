
package lorikeet;

import lorikeet.api.FileRef;
import lorikeet.lobe.DiskWrite;

import java.util.Objects;

public class WriteFile implements DiskWrite<Tutorial> {

    private final FileRef fileId;
    private final String content;

    public WriteFile(FileRef fileId, String content) {
        this.fileId = fileId;;
        this.content = content;
    }

    @Override
    public FileRef fileRef() {
        return this.fileId;
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

        return Objects.equals(this.fileRef(), writeFile.fileRef())
            && Objects.equals(this.content(), writeFile.content());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fileRef(), this.content());
    }
}