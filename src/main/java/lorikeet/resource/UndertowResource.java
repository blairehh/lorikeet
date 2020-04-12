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
        if (directive.reject()) {
            exchange.setStatusCode(404);
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
            exchange.getResponseSender().send("not found");
        } else {
            HttpReply reply = directive.perform();
            if (reply instanceof HttpWrite) {
                // unchecked here
                tract.write((HttpWrite<R>)reply);
            }
        }
    }

    // @TODO remove and close this session
    private HttpDirective directiveForSignal(A application, IncomingHttpSgnl incoming, Tract<R> tract) {
        final Function<HttpReceptor<R>, Optional<HttpDirective>> selector = (receptor) -> {
            final HttpDirective directive = receptor.junction(tract, incoming);
            if (directive.reject()) {
                return Optional.empty();
            }
            return Optional.of(directive);
        };
        return application.provideHttpReceptors()
            .receptors()
            .select(selector)
            .orElse(new HttpReject());
    }
}
