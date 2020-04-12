package lorikeet.http;

public class LongHeader extends NumberHeader<Long> {
    public LongHeader(IncomingHttpSgnl msg, String headerName, Long defaultValue) {
        super(msg, headerName, Long::parseLong, defaultValue);
    }

    public LongHeader(IncomingHttpSgnl msg, String headerName) {
        super(msg, headerName, Long::parseLong);
    }
}
