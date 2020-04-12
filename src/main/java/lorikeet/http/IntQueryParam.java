package lorikeet.http;

public class IntQueryParam extends NumberQueryParam<Integer> {
    public IntQueryParam(IncomingHttpSgnl msg, String queryParamName) {
        super(msg, queryParamName, Integer::parseInt, Integer.class);
    }
}
