package lorikeet.lobe;

public interface DiskResource {
    <R extends UsesDisk> DiskWriteResult write(DiskWrite<R> write);
}