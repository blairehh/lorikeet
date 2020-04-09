package lorikeet.http;

import lorikeet.lobe.IncomingHttpMsg;

public class LongQueryParam extends NumberQueryParam<Long> {
    public LongQueryParam(IncomingHttpMsg msg, String queryParamName) {
        super(msg, queryParamName, Long::parseLong, Long.class);
    }
}
