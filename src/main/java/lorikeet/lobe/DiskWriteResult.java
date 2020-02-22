package lorikeet.lobe;

import java.net.URI;

import lorikeet.core.Fallible;

public interface DiskWriteResult extends Fallible<Boolean> {
    URI uri();
    long bytesWritten();
}