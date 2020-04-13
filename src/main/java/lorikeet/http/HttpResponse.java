package lorikeet.http;

import lorikeet.core.Dict;
import lorikeet.core.DictOf;
import lorikeet.core.Seq;
import lorikeet.lobe.UsesHttpServer;

public class HttpResponse<R extends UsesHttpServer> implements HttpWrite<R> {
    private final Object session;
    private final int statusCode;
    private final Dict<String, Seq<String>> headers;

    public HttpResponse(HttpStatus status) {
        this.session = null;
        this.statusCode = status.code();
        this.headers = new DictOf<>();
    }

    private HttpResponse(
        Object session,
        int statusCode,
        Dict<String, Seq<String>> headers
    ) {
        this.session = session;
        this.statusCode = statusCode;
        this.headers = headers;
    }

    @Override
    public HttpWriteResult junction(R resources) {
        return null;
    }

    @Override
    public HttpResponse<R> withSession(Object session) {
        return new HttpResponse<>(session, this.statusCode, this.headers);
    }
}
