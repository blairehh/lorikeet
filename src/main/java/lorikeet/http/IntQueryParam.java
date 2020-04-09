package lorikeet.http;

import lorikeet.lobe.IncomingHttpMsg;

public class IntQueryParam extends NumberQueryParam<Integer> {
    public IntQueryParam(IncomingHttpMsg msg, String queryParamName) {
        super(msg, queryParamName, Integer::parseInt, Integer.class);
    }
}
