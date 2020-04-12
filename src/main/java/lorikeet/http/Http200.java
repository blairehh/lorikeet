package lorikeet.http;

import lorikeet.http.error.HttpAgentIsMissingSession;
import lorikeet.http.error.InvalidHttpSessionObject;
import lorikeet.lobe.ResourceInsignia;
import lorikeet.lobe.UsesHttpServer;
import lorikeet.lobe.WriteAgent;

// @TODO accept body
public class Http200<R extends UsesHttpServer> implements WriteAgent<R, HttpWriteResult> {
    private final Object session;

    public Http200() {
        this.session = null;
    }

    private Http200(Object session) {
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
        outgoing.statusCode(200);
        outgoing.writeBody("Done!!!");
        return new HttpWriteOk(200);
    }

    @Override
    public ResourceInsignia resourceInsignia() {
        return new HttpServerInsignia();
    }

    @Override
    public WriteAgent<R, HttpWriteResult> withSession(Object session) {
        return new Http200<>(session);
    }
}
