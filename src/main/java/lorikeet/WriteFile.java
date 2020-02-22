
package lorikeet;

import lorikeet.api.FileId;
import lorikeet.lobe.DiskWrite;

import java.util.Objects;

public class WriteFile implements DiskWrite<Tutorial> {

    private final FileId fileId;
    private final String content;

    public WriteFile(FileId fileId, String content) {
        this.fileId = fileId;;
        this.content = content;
    }

    @Override
    public FileId fileId() {
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

        return Objects.equals(this.fileId(), writeFile.fileId())
            && Objects.equals(this.content(), writeFile.content());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fileId(), this.content());
    }
}