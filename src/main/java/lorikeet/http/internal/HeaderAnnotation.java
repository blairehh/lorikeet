package lorikeet.http.internal;

import java.util.Objects;

public class HeaderAnnotation {
    private final String headerName;

    public HeaderAnnotation(String headerName) {
        this.headerName = headerName;
    }

    public String headerName() {
        return this.headerName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        HeaderAnnotation that = (HeaderAnnotation) o;

        return Objects.equals(this.headerName(), that.headerName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.headerName());
    }
}
