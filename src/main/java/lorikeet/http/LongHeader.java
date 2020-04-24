package lorikeet.http;

public class LongHeader extends NumberHeader<Long> {
    public LongHeader(String headerName, Long defaultValue) {
        super(headerName, Long::parseLong, defaultValue);
    }

    public LongHeader(String headerName) {
        super(headerName, Long::parseLong);
    }

    public LongHeader(HeaderField header, Long defaultValue) {
        super(header, Long::parseLong, defaultValue);
    }

    public LongHeader(HeaderField header) {
        super(header, Long::parseLong);
    }
}
