package lorikeet.http;

import lorikeet.lobe.IncomingHttpMsg;

public class DoubleHeader extends NumberHeader<Double> {
    public DoubleHeader(IncomingHttpMsg msg, String headerName, Double defaultValue) {
        super(msg, headerName, Double::parseDouble, defaultValue);
    }

    public DoubleHeader(IncomingHttpMsg msg, String headerName) {
        super(msg, headerName, Double::parseDouble);
    }
}
