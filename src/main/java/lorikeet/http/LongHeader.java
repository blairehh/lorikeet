package lorikeet.http;

import lorikeet.lobe.IncomingHttpMsg;

public class LongHeader extends NumberHeader<Long> {
    public LongHeader(IncomingHttpMsg msg, String headerName, Long defaultValue) {
        super(msg, headerName, Long::parseLong, defaultValue);
    }

    public LongHeader(IncomingHttpMsg msg, String headerName) {
        super(msg, headerName, Long::parseLong);
    }
}
