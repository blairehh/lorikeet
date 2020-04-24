package lorikeet.coding.error;

public class InternetMediaTypeTextPlainMustBeDecodedToString extends RuntimeException {
    private final Class<?> attemptedType;

    public InternetMediaTypeTextPlainMustBeDecodedToString(Class<?> attemptedType) {
        super(String.format(
            "Internet Media Type text/plain must be decoded to a String, not to '%s'",
            attemptedType.getCanonicalName())
        );
        this.attemptedType = attemptedType;
    }
}
