package lorikeet.resource;

import io.undertow.Undertow;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.util.Headers;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;
import lorikeet.http.HttpDirective;
import lorikeet.http.HttpReject;
import lorikeet.http.HttpResource;
import lorikeet.http.HttpServerInsignia;
import lorikeet.http.HttpReply;
import lorikeet.http.HttpStatus;
import lorikeet.http.HttpWrite;
import lorikeet.http.OutgoingHttpSgnl;
import lorikeet.http.HttpReceptor;
import lorikeet.http.IncomingHttpSgnl;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.http.internal.Utils;
import lorikeet.lobe.*;

public class UndertowResource<R extends UsesLogging & UsesCoding & UsesHttpServer, A extends ProvidesHttpReceptors<R> & ProvidesTract<R>>
    implements HttpResource {
    private final Utils utils = new Utils();
    private final UndertowConfig config;

    public UndertowResource(ConfiguresUndertow config) {
        this.config = config.configureUndertowServer();
    }

    public void start(R resources, A application) {
        Undertow server = Undertow.builder()
            .addHttpListener(config.port(), config.host())
            .setHandler(new BlockingHandler((exchange) -> this.handleRequest(exchange, resources, application)))
            .build();
        server.start();
    }

    @SuppressWarnings("unchecked")
    private void handleRequest(HttpServerExchange exchange, R resources, A application) {
        final IncomingHttpSgnl incoming = new UndertowIncomingMsg(exchange);
        final OutgoingHttpSgnl outgoing = new UndertowOutgoingSgnl(exchange);
        final Tract<R> tract = application.provideTract()
            .session(new TractSession(new HttpServerInsignia(), outgoing));

        final HttpDirective directive = this.directiveForSignal(application, incoming, tract);

        if (directive.failure()) {
            final HttpStatus status = this.utils.statusFor(directive.errors());
            exchange.setStatusCode(status.code());
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
            exchange.getResponseSender().send(status.name());
        } else {
            final HttpReply reply = directive.orPanic().reply();
            if (reply instanceof HttpWrite) {
                // unchecked here
                tract.write((HttpWrite<R>)reply);
            }
        }
    }

    private HttpDirective directiveForSignal(A application, IncomingHttpSgnl incoming, Tract<R> tract) {
        Seq<IncomingHttpSgnlError> errors = new SeqOf<>();

        for (HttpReceptor<R> receptor : application.httpRouter().receptors()) {
            final HttpDirective directive = receptor.junction(tract, incoming);
            if (!directive.errors().isEmpty()) {
                errors = errors.affix(directive.errors());
            }
            if (directive.failure()) {
                continue;
            }
            return directive;
        }

        return new HttpReject(errors);
    }
}
