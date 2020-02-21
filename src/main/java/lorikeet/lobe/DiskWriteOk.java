package lorikeet.lobe;

import lorikeet.core.AnOk;

import java.net.URI;

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
}