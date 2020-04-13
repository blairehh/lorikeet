package lorikeet.http;

import lorikeet.http.error.HttpAgentIsMissingSession;
import lorikeet.http.error.InvalidHttpSessionObject;
import lorikeet.lobe.UsesHttpServer;

public class Http204<R extends UsesHttpServer> implements HttpWrite<R> {
    private final Object session;

    public Http204() {
        this.session = null;
    }

    private Http204(Object session) {
        this.session = session;
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
        outgoing.statusCode(204);
        return new HttpWriteOk(204);
    }

    @Override
    public Http204<R> withSession(Object session) {
        return new Http204<>(session);
    }
}
