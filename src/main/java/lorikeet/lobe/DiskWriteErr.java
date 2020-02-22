package lorikeet.lobe;

import lorikeet.api.FileRef;
import lorikeet.core.AnErr;

import java.net.URI;
import java.util.Objects;

public class DiskWriteErr implements AnErr<Boolean>, DiskWriteResult {
    
    private final FileRef fileRef;
    private final Exception exception;

    public DiskWriteErr(FileRef fileRef, Exception exception) {
        this.fileRef = fileRef;
        this.exception = exception;
    }

    @Override
    public Exception exception() {
        return this.exception;
    }

    @Override
    public FileRef fileRef() {
        return this.fileRef;
    }

    @Override
    public long bytesWritten() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        DiskWriteErr that = (DiskWriteErr) o;

        return Objects.equals(this.fileRef(), that.fileRef())
            && Objects.equals(this.exception(), that.exception());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fileRef(), this.exception());
    }
}