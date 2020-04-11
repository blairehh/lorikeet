package lorikeet.http;

public class HttpNegate implements HttpDirective {
    @Override
    public boolean reject() {
        return false;
    }

    @Override
    public void perform() {

    }
}
