package lorikeet.lobe;

import lorikeet.api.FileId;

public interface DiskWrite<R extends UsesDisk> extends LorikeetWrite<R, DiskWriteResult> {
    
    FileId fileId();
    String content();

    default DiskWriteResult junction(R resources) {
        return resources.useDisk().write(this);
    }
}