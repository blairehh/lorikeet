package lorikeet.http;

import lorikeet.coding.TextEncode;
import lorikeet.core.Fallible;
import lorikeet.http.error.HttpAgentIsMissingSession;
import lorikeet.http.error.InvalidHttpSessionObject;
import lorikeet.lobe.EncodeAgent;
import lorikeet.lobe.UsesCoding;
import lorikeet.lobe.UsesHttpServer;
import lorikeet.lobe.UsesLogging;
import lorikeet.lobe.WriteAgent;

public class Http200<R extends UsesHttpServer & UsesLogging & UsesCoding> implements HttpWrite<R> {
    private final Object session;
    private final EncodeAgent<R, Fallible<String>> body;

    public Http200(String body) {
        this.session = null;
        this.body = new TextEncode<>(body);
    }

    public Http200(EncodeAgent<R, Fallible<String>> body) {
        this.session = null;
        this.body = body;
    }

    private Http200(Object session, EncodeAgent<R, Fallible<String>> body) {
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
        this.body.mediaType().ifPresent((value) -> outgoing.header(HeaderField.CONTENT_TYPE, value));
        this.body.junction(resources).onSuccess(outgoing::writeBody);

        return new HttpWriteOk(200);
    }

    @Override
    public Http200<R> withSession(Object session) {
        return new Http200<>(session, this.body);
    }
}
