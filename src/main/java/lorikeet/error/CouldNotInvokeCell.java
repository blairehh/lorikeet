package lorikeet.error;

public class CouldNotInvokeCell extends LorikeetException {
    public CouldNotInvokeCell() {
        super(CouldNotInvokeCell.class);
    }

    public CouldNotInvokeCell(Exception cause) {
        super(cause, CouldNotInvokeCell.class);
    }
}
