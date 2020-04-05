package lorikeet.http.error;

import java.util.Objects;

public class BadHeaderValue extends RuntimeException {
    private final String headerName;
    private final String expectedValuePattern;

    public BadHeaderValue(String headerName, String expectedValuePattern) {
        super(String.format("Expected header '%s' to have value of pattern '%s'", headerName, expectedValuePattern));
        this.headerName = headerName;
        this.expectedValuePattern = expectedValuePattern;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        BadHeaderValue that = (BadHeaderValue) o;

        return Objects.equals(this.headerName, that.headerName)
            && Objects.equals(this.expectedValuePattern, that.expectedValuePattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.headerName, this.expectedValuePattern);
    }
}
