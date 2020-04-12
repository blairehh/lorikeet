package lorikeet.http;

public class LongQueryParam extends NumberQueryParam<Long> {
    public LongQueryParam(IncomingHttpSgnl request, String queryParamName) {
        super(request, queryParamName, Long::parseLong, Long.class);
    }
}
