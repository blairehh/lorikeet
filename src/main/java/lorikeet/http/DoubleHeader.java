package lorikeet.http;

public class DoubleHeader extends NumberHeader<Double> {
    public DoubleHeader(String headerName, Double defaultValue) {
        super(headerName, Double::parseDouble, defaultValue);
    }

    public DoubleHeader(String headerName) {
        super(headerName, Double::parseDouble);
    }

    public DoubleHeader(HeaderField header, Double defaultValue) {
        super(header, Double::parseDouble, defaultValue);
    }

    public DoubleHeader(HeaderField header) {
        super(header, Double::parseDouble);
    }
}
