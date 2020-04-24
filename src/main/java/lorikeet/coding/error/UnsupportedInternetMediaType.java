package lorikeet.coding.error;

public class UnsupportedInternetMediaType extends RuntimeException {
    private final String internetMediaType;

    public UnsupportedInternetMediaType(String internetMediaType) {
        super(String.format("Internet Media Type %s is not supported", internetMediaType));
        this.internetMediaType = internetMediaType;
    }
}
