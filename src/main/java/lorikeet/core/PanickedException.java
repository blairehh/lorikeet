package lorikeet.core;

// @TODO delcare serialVersionUID
public class PanickedException extends RuntimeException {
    public PanickedException(Exception e) {
        super(e);
    }
}