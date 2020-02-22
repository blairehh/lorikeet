package lorikeet.lobe;

import lorikeet.core.AnOk;

import java.net.URI;
import java.util.Objects;

public class DiskWriteOk implements AnOk<Boolean, DiskWriteOk>, DiskWriteResult<DiskWriteOk> {

    private final URI uri;
    private final long bytesWritten;

    public DiskWriteOk(URI uri, long bytesWritten) {
        this.uri = uri;
        this.bytesWritten = bytesWritten;
    }

    @Override
    public DiskWriteOk self() {
        return this;
    }

    @Override
    public Boolean value() {
        return true;
    }

    @Override
    public URI uri() {
        return this.uri;
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
            && Objects.equals(this.uri(), that.uri());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uri(), this.bytesWritten());
    }
}