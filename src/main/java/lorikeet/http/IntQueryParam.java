package lorikeet.http;

public class IntQueryParam extends NumberQueryParam<Integer> {
    public IntQueryParam(String queryParamName) {
        super(queryParamName, Integer::parseInt, Integer.class);
    }
}
