package lorikeet.core;

// @TODO delcare serialVersionUID
public class PanickedException extends RuntimeException {
    public PanickedException(Throwable e) {
        super(e);
    }
}