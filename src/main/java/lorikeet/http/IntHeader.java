package lorikeet.http;

public class IntHeader extends NumberHeader<Integer> {
    public IntHeader(String headerName, Integer defaultValue) {
        super(headerName, Integer::parseInt, defaultValue);
    }

    public IntHeader(String headerName) {
        super(headerName, Integer::parseInt);
    }

    public IntHeader(HeaderField header, Integer defaultValue) {
        super(header, Integer::parseInt, defaultValue);
    }

    public IntHeader(HeaderField header) {
        super(header, Integer::parseInt);
    }
}
