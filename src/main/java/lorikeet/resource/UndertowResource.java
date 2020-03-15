package lorikeet.resource;

import io.undertow.Undertow;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;
import lorikeet.lobe.HttpReceptor;
import lorikeet.lobe.HttpSignal;
import lorikeet.lobe.ProvidesHttpReceptors;
import lorikeet.lobe.Tract;
import lorikeet.lobe.UsesLogging;

public class UndertowResource<T extends Tract<? extends UsesLogging> & ProvidesHttpReceptors<? extends UsesLogging>> {
    private final UndertowConfig config;

    public UndertowResource(ConfiguresUndertow config) {
        this.config = config.configureUndertowServer();
    }

    public void start(T tract) {
        Undertow server = Undertow.builder()
            .addHttpListener(config.port(), config.host())
            .setHandler((final HttpServerExchange exchange) -> {
                final HttpSignal signal = new UndertowHttpSignal();
                final Seq<HttpReceptor<? extends UsesLogging>> receptors = new SeqOf<>();
                /*tract.provideHttpReceptors()
                    .pick(receptor -> receptor.filter().matches(signal));*/
                if (receptors.isEmpty()) {
                    exchange.setStatusCode(404);
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                    exchange.getResponseSender().send("not found");
                } else {
                    //receptors.pick(0).get().process(tract, signal);
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                    exchange.getResponseSender().send("found");
                }
//                receptors.pick(0).get().process(tract, signal);
//                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
//                exchange.getResponseSender().send("Hello World");
            }).build();
        server.start();
    }
}
