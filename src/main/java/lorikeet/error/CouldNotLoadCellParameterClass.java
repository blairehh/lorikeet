package lorikeet.error;

public class CouldNotLoadCellParameterClass extends LorikeetException {
    public CouldNotLoadCellParameterClass() {
        super(CouldNotLoadCellParameterClass.class);
    }

    public CouldNotLoadCellParameterClass(Exception cause) {
        super(cause, CouldNotLoadCellParameterClass.class);
    }
}