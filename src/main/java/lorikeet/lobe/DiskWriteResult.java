package lorikeet.lobe;

import java.net.URI;

import lorikeet.core.Fallible;

public interface DiskWriteResult<S extends DiskWriteResult<S>> extends Fallible<Boolean, S> {
    URI uri();
    long bytesWritten();
}