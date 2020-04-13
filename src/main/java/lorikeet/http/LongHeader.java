package lorikeet.http;

public class LongHeader extends NumberHeader<Long> {
    public LongHeader(IncomingHttpSgnl msg, String headerName, Long defaultValue) {
        super(msg, headerName, Long::parseLong, defaultValue);
    }

    public LongHeader(IncomingHttpSgnl msg, String headerName) {
        super(msg, headerName, Long::parseLong);
    }

    public LongHeader(IncomingHttpSgnl msg, HeaderField header, Long defaultValue) {
        super(msg, header, Long::parseLong, defaultValue);
    }

    public LongHeader(IncomingHttpSgnl msg, HeaderField header) {
        super(msg, header, Long::parseLong);
    }
}
