package lorikeet.resource;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class UndertowResource {
    private final UndertowConfig config;

    public UndertowResource(ConfiguresUndertow config) {
        this.config = config.configureUndertowServer();
    }

    public void start() {
        Undertow server = Undertow.builder()
            .addHttpListener(config.port(), config.host())
            .setHandler((final HttpServerExchange exchange) -> {
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                exchange.getResponseSender().send("Hello World");
            }).build();
        server.start();
    }
}
