package lorikeet.http;

public class DoubleHeader extends NumberHeader<Double> {
    public DoubleHeader(IncomingHttpSgnl msg, String headerName, Double defaultValue) {
        super(msg, headerName, Double::parseDouble, defaultValue);
    }

    public DoubleHeader(IncomingHttpSgnl msg, String headerName) {
        super(msg, headerName, Double::parseDouble);
    }
}
