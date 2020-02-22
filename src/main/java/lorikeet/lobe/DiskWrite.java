package lorikeet.lobe;

import java.net.URI;

public interface DiskWrite<R extends UsesDisk> extends LorikeetWrite<R, DiskWriteResult<?>> {
    
    URI uri();
    String content();

    default DiskWriteResult<?> junction(R resources) {
        return resources.useDisk().write(this);
    }
}