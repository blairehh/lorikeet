package lorikeet.http;

public class HttpNegate implements HttpDirective {
    @Override
    public boolean reject() {
        return false;
    }

    @Override
    public HttpReply perform() {
        return new HttpNoOp();
    }

    @Override
    public boolean wrongMethod() {
        return false;
    }
}
