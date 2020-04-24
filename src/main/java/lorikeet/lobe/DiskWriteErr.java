package lorikeet.lobe;

import lorikeet.api.FileRef;
import lorikeet.core.BasicErr;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;

import java.util.Objects;

public class DiskWriteErr extends BasicErr<Boolean> implements DiskWriteResult {
    
    private final FileRef fileRef;
    private final Seq<? extends Exception> exceptions;

    public DiskWriteErr(FileRef fileRef, Exception exception) {
        this.fileRef = fileRef;
        this.exceptions = new SeqOf<>(exception);
    }

    @Override
    public Exception exception() {
        return this.exceptions.first().orElseThrow();
    }

    @Override
    public Seq<? extends Exception> errors() {
        return this.exceptions;
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