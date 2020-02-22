package lorikeet.api;

import lorikeet.core.Fallible;
import lorikeet.core.Ok;

import java.io.File;
import java.net.URI;
import java.util.Objects;

public class FileUri implements FileId {
    private final URI uri;

    public FileUri(URI uri) {
        this.uri = uri;
    }

    public URI uri() {
        return this.uri;
    }

    @Override
    public Fallible<File, ?> generateFile() {
        return new Ok<>(new File(this.uri));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        FileUri fileUri = (FileUri) o;

        return Objects.equals(this.uri(), fileUri.uri());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uri());
    }
}
