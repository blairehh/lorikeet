package lorikeet.http;

import lorikeet.http.error.HttpAgentIsMissingSession;
import lorikeet.http.error.InvalidHttpSessionObject;
import lorikeet.lobe.UsesHttpServer;

public class Http200<R extends UsesHttpServer> implements HttpWrite<R> {
    private final Object session;
    private final String body;

    public Http200(String body) {
        this.session = null;
        this.body = body;
    }

    private Http200(Object session, String body) {
        this.session = session;
        this.body = body;
    }

    @Override
    public HttpWriteResult junction(R resources) {
        if (this.session == null) {
            return new HttpWriteErr(new HttpAgentIsMissingSession(Http200.class), 200);
        }
        if (!(this.session instanceof OutgoingHttpSgnl)) {
            return new HttpWriteErr(new InvalidHttpSessionObject(Http200.class, this.session.getClass()), 200);
        }
        final OutgoingHttpSgnl outgoing = (OutgoingHttpSgnl)this.session;
        outgoing.statusCode(200);
        outgoing.writeBody(this.body);
        return new HttpWriteOk(200);
    }

    @Override
    public Http200<R> withSession(Object session) {
        return new Http200<>(session, this.body);
    }
}
