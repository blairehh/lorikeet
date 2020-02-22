package lorikeet.lobe;

import lorikeet.api.FileRef;

public interface DiskWrite<R extends UsesDisk> extends LorikeetWrite<R, DiskWriteResult> {
    
    FileRef fileRef();
    String content();

    default DiskWriteResult junction(R resources) {
        return resources.useDisk().write(this);
    }
}