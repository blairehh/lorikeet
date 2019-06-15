package lorikeet.error;

public class CouldNotDeserializeExceptionClassInToThrowStanza extends LorikeetException {
    public CouldNotDeserializeExceptionClassInToThrowStanza() {
        super(CouldNotDeserializeExceptionClassInToThrowStanza.class);
    }
}
