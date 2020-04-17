package lorikeet.resource;

import io.undertow.Undertow;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import lorikeet.http.HttpDirective;
import lorikeet.http.HttpReject;
import lorikeet.http.HttpResource;
import lorikeet.http.HttpServerInsignia;
import lorikeet.http.HttpReply;
import lorikeet.http.HttpWrite;
import lorikeet.http.OutgoingHttpSgnl;
import lorikeet.http.HttpReceptor;
import lorikeet.http.IncomingHttpSgnl;
import lorikeet.lobe.ProvidesHttpReceptors;
import lorikeet.lobe.ProvidesTract;
import lorikeet.lobe.Tract;
import lorikeet.lobe.TractSession;
import lorikeet.lobe.UsesHttpServer;
import lorikeet.lobe.UsesLogging;

import java.util.Optional;
import java.util.function.Function;

public class UndertowResource<R extends UsesLogging & UsesHttpServer, A extends ProvidesHttpReceptors<R> & ProvidesTract<R>>
    implements HttpResource {
    private final UndertowConfig config;

    public UndertowResource(ConfiguresUndertow config) {
        this.config = config.configureUndertowServer();
    }

    public void start(R resources, A application) {
        Undertow server = Undertow.builder()
            .addHttpListener(config.port(), config.host())
            .setHandler((exchange) -> this.handleRequest(exchange, resources, application))
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
        if (directive.reject() && !directive.wrongMethod()) {
            exchange.setStatusCode(404);
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
            exchange.getResponseSender().send("not found");
        } else if (directive.reject() && directive.wrongMethod()) {
            exchange.setStatusCode(405);
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
            exchange.getResponseSender().send("bad method");
        } else {
            final HttpReply reply = directive.perform();
            if (reply instanceof HttpWrite) {
                // unchecked here
                tract.write((HttpWrite<R>)reply);
            }
        }
    }

    private HttpDirective directiveForSignal(A application, IncomingHttpSgnl incoming, Tract<R> tract) {
        boolean matchedButWrongMethod = false;
        for (HttpReceptor<R> receptor : application.provideHttpReceptors().receptors()) {
            final HttpDirective directive = receptor.junction(tract, incoming);
            if (directive.wrongMethod()) {
                matchedButWrongMethod = true;
            }
            if (directive.reject()) {
                continue;
            }
            return directive;
        }

        return new HttpReject(matchedButWrongMethod);
    }
}
