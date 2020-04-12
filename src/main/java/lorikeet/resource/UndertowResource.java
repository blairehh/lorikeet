package lorikeet.resource;

import io.undertow.Undertow;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.session.Session;
import io.undertow.util.Headers;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;
import lorikeet.http.HttpDirective;
import lorikeet.http.HttpReject;
import lorikeet.http.HttpResource;
import lorikeet.http.HttpServerInsignia;
import lorikeet.http.OutgoingHttpSgnl;
import lorikeet.lobe.DefaultTract;
import lorikeet.lobe.HttpReceptor;
import lorikeet.lobe.IncomingHttpMsg;
import lorikeet.lobe.LoggingResource;
import lorikeet.lobe.ProvidesHttpReceptors;
import lorikeet.lobe.ProvidesTract;
import lorikeet.lobe.ResourceInsignia;
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
            .setHandler((final HttpServerExchange exchange) -> {
                final HttpDirective directive = this.directiveForSignal(application, exchange);
                if (directive.reject()) {
                    exchange.setStatusCode(404);
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                    exchange.getResponseSender().send("not found");
                } else {
                    directive.perform();
                }
            }).build();
        server.start();
    }

    // @TODO remove and close this session
    private HttpDirective directiveForSignal(A application, HttpServerExchange exchange) {
        final IncomingHttpMsg incoming = new UndertowIncomingMsg(exchange);
        final OutgoingHttpSgnl outgoing = new UndertowOutgoingSgnl(exchange);

        final Tract<R> tract = application.provideTract()
            .session(new TractSession(new HttpServerInsignia(), outgoing));

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
