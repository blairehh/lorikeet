package lorikeet.lobe;

import lorikeet.core.AnErr;

import java.net.URI;
import java.util.Objects;

public class DiskWriteErr implements AnErr<Boolean>, DiskWriteResult {
    
    private final URI uri;
    private final Exception exception;

    public DiskWriteErr(URI uri, Exception exception) {
        this.uri = uri;
        this.exception = exception;
    }

    @Override
    public Exception exception() {
        return this.exception;
    }

    @Override
    public URI uri() {
        return this.uri;
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

        return Objects.equals(this.uri(), that.uri())
            && Objects.equals(this.exception(), that.exception());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uri(), this.exception());
    }
}