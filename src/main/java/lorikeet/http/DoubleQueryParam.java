package lorikeet.http;

public class DoubleQueryParam extends NumberQueryParam<Double> {
    public DoubleQueryParam(String queryParamName) {
        super(queryParamName, Double::parseDouble, Double.class);
    }
}
