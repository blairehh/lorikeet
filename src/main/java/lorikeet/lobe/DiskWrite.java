package lorikeet.lobe;

public interface DiskWrite<R extends UsesDisk> extends LorikeetWrite<R, DiskWriteResult<?>> {
    
    String fileName();
    String content();

    default DiskWriteResult<?> junction(R resources) {
        return resources.useDisk().write(this);
    }
}