package lorikeet.lobe;

public interface ProvidesDisk extends UsesDisk {
    
    @Override
    default DiskResource useDisk() {
        return new DefaultDiskResource();
    }
}