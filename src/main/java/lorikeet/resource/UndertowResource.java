package lorikeet.resource;

import io.undertow.Undertow;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;
import lorikeet.http.HttpDirective;
import lorikeet.http.HttpReject;
import lorikeet.lobe.HttpReceptor;
import lorikeet.lobe.IncomingHttpMsg;
import lorikeet.lobe.ProvidesHttpReceptors;
import lorikeet.lobe.ProvidesTract;
import lorikeet.lobe.Tract;
import lorikeet.lobe.UsesLogging;

import java.util.Optional;
import java.util.function.Function;

public class UndertowResource<R extends UsesLogging, A extends ProvidesHttpReceptors<R> & ProvidesTract<R>> {
    private final UndertowConfig config;

    public UndertowResource(ConfiguresUndertow config) {
        this.config = config.configureUndertowServer();
    }

    public void start(A application) {
        Undertow server = Undertow.builder()
            .addHttpListener(config.port(), config.host())
            .setHandler((final HttpServerExchange exchange) -> {
                final IncomingHttpMsg msg = new UndertowIncomingMsg(exchange);
                final HttpDirective directive = this.directiveForSignal(application, msg);
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

    private HttpDirective directiveForSignal(A application, IncomingHttpMsg signal) {
        final Tract<R> tract = application.provideTract();
        final Function<HttpReceptor<R>, Optional<HttpDirective>> selector = (receptor) -> {
            final HttpDirective directive = receptor.junction(tract, signal);
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
