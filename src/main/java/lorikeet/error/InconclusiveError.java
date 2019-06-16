package lorikeet.error;

public class InconclusiveError extends LorikeetException {
    public InconclusiveError(LorikeetException error) {
        super(error.getClass());
    }
}
