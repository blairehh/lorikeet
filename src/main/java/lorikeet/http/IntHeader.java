package lorikeet.http;

public class IntHeader extends NumberHeader<Integer> {

    public IntHeader(IncomingHttpSgnl msg, String headerName, Integer defaultValue) {
        super(msg, headerName, Integer::parseInt, defaultValue);
    }

    public IntHeader(IncomingHttpSgnl msg, String headerName) {
        super(msg, headerName, Integer::parseInt);
    }
}
