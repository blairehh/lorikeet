package lorikeet.lobe;

import lorikeet.api.FileRef;
import lorikeet.core.Fallible;

public interface DiskWriteResult extends Fallible<Boolean> {
    FileRef fileRef();
    long bytesWritten();
}