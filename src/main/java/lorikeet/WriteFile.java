
package lorikeet;

import lorikeet.lobe.DiskWrite;

import java.net.URI;
import java.util.Objects;

public class WriteFile implements DiskWrite<Tutorial> {

    private final URI uri;
    private final String content;

    public WriteFile(URI uri, String content) {
        this.uri = uri;;
        this.content = content;
    }

    @Override
    public URI uri() {
        return this.uri;
    }

    @Override
    public String content() {
        return this.content;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        WriteFile writeFile = (WriteFile) o;

        return Objects.equals(this.uri(), writeFile.uri())
            && Objects.equals(this.content(), writeFile.content());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uri(), this.content());
    }
}