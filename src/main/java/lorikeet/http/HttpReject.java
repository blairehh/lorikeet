package lorikeet.http;

public class HttpReject implements HttpDirective {
    @Override
    public boolean reject() {
        return true;
    }

    @Override
    public void perform() {

    }
}
