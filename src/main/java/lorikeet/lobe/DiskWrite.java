package lorikeet.lobe;

import lorikeet.api.FileRef;
import lorikeet.api.FileUri;

import java.net.URI;
import java.util.Objects;

public class DiskWrite<R extends UsesDisk> implements WriteAgent<R, DiskWriteResult> {

    private final FileRef fileRef;
    private final String content;

    public DiskWrite(URI uri, String content) {
        this(new FileUri(uri), content);
    }

    public DiskWrite(FileRef fileRef, String content) {
        this.fileRef = fileRef;
        this.content = content;
    }

    public FileRef fileRef() {
        return this.fileRef;
    }

    public String content() {
        return this.content;
    }

    @Override
    public DiskWriteResult junction(R resources) {
        return resources.useDisk().write(this);
    }

    @Override
    public ResourceInsignia resourceInsignia() {
        return new DiskInsignia();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        DiskWrite<?> diskWrite = (DiskWrite<?>) o;

        return Objects.equals(this.fileRef(), diskWrite.fileRef())
            && Objects.equals(this.content(), diskWrite.content());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fileRef(), this.content());
    }
}