package lorikeet.http;

public class DoubleQueryParam extends NumberQueryParam<Double> {
    public DoubleQueryParam(IncomingHttpSgnl request, String queryParamName) {
        super(request, queryParamName, Double::parseDouble, Double.class);
    }
}
