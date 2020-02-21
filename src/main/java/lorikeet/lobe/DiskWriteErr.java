package lorikeet.lobe;

import lorikeet.core.AnErr;

import java.net.URI;

public class DiskWriteErr implements AnErr<Boolean, DiskWriteErr>, DiskWriteResult<DiskWriteErr> {
    
    private final URI uri;
    private final Exception exception;

    public DiskWriteErr(URI uri, Exception exception) {
        this.uri = uri;
        this.exception = exception;
    }

    @Override
    public DiskWriteErr self() {
        return this;
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
}