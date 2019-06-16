package lorikeet.error;

public class NotAValidBooleanValue extends LorikeetException {
    private final String invalidValue;

    public NotAValidBooleanValue() {
        super(NotAValidBooleanValue.class);
        this.invalidValue = null;
    }

    public NotAValidBooleanValue(String invalidValue) {
        super(NotAValidBooleanValue.class);
        this.invalidValue = invalidValue;
    }

    public String getInvalidValue() {
        return this.invalidValue;
    }
}
