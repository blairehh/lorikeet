package lorikeet.http;

import lorikeet.lobe.IncomingHttpMsg;

public class DoubleQueryParam extends NumberQueryParam<Double> {
    public DoubleQueryParam(IncomingHttpMsg msg, String queryParamName) {
        super(msg, queryParamName, Double::parseDouble, Double.class);
    }
}
