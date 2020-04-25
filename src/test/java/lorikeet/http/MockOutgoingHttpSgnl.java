package lorikeet.http;

public class MockOutgoingHttpSgnl implements OutgoingHttpSgnl {
    int statusCode;
    HeaderSet headers = new HeaderSet();

    @Override
    public OutgoingHttpSgnl statusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    @Override
    public OutgoingHttpSgnl header(String name, String value) {
        this.headers = headers.set(name, value);
        return this;
    }

    @Override
    public OutgoingHttpSgnl header(HeaderField header, String value) {
        return this.header(header.key(), value);
    }

    @Override
    public OutgoingHttpSgnl writeBody(String content) {
        return this;
    }
}
