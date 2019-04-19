package lorikeet;

public class NinjaException extends RuntimeException {
    public NinjaException(Exception e) {
        super(e);
    }
}
