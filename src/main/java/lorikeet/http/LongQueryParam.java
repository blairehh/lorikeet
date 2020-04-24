package lorikeet.http;

public class LongQueryParam extends NumberQueryParam<Long> {
    public LongQueryParam(String queryParamName) {
        super(queryParamName, Long::parseLong, Long.class);
    }
}
