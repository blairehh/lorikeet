package lorikeet.lobe;

import lorikeet.api.FileRef;
import lorikeet.core.AnOk;

import java.net.URI;
import java.util.Objects;

public class DiskWriteOk implements AnOk<Boolean>, DiskWriteResult {

    private final FileRef fileRef;
    private final long bytesWritten;

    public DiskWriteOk(FileRef fileRef, long bytesWritten) {
        this.fileRef = fileRef;
        this.bytesWritten = bytesWritten;
    }

    @Override
    public Boolean value() {
        return true;
    }

    @Override
    public FileRef fileRef() {
        return this.fileRef;
    }

    @Override
    public long bytesWritten() {
        return this.bytesWritten;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        DiskWriteOk that = (DiskWriteOk) o;

        return Objects.equals(this.bytesWritten(), that.bytesWritten())
            && Objects.equals(this.fileRef(), that.fileRef());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fileRef(), this.bytesWritten());
    }
}