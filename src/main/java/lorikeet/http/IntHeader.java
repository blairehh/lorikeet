package lorikeet.http;

import lorikeet.lobe.IncomingHttpMsg;

public class IntHeader extends NumberHeader<Integer> {

    public IntHeader(IncomingHttpMsg msg, String headerName, Integer defaultValue) {
        super(msg, headerName, Integer::parseInt, defaultValue);
    }

    public IntHeader(IncomingHttpMsg msg, String headerName) {
        super(msg, headerName, Integer::parseInt);
    }
}
