package lorikeet.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lorikeet.Seq;
import lorikeet.web.impl.StandardIncomingRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Optional;

public class SunHttpServerEngine {

    private final HttpServer server;

    public SunHttpServerEngine(WebServer webserver) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(1111), 0);
        webserver.getRouter().getDispatcher().getEndpointsByPath().forEach((path, endpoints) -> {
            this.server.createContext(path, new HttpEndpointHandler(endpoints));
        });
    }

    public void start() {
        this.server.start();
    }

    public static class HttpEndpointHandler implements HttpHandler {

        private final Seq<WebEndpoint> endpoints;

        public HttpEndpointHandler(Seq<WebEndpoint> endpoints) {
            this.endpoints = endpoints;
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            final Optional<HttpMethod> method = HttpMethod.find(httpExchange.getRequestMethod());
            if (method.isPresent()) {
                this.handle(method.get(), httpExchange);
            }
        }

        private void handle(HttpMethod method, HttpExchange exchange) throws IOException {
            this.endpoints
                .filter(endpoint -> endpoint.getMethod() == method)
                .first()
                .ifPresent(endpoint -> {
                    try {
                        endpoint.getHandler().handle(toIncomingRequest(method, exchange), null);
                        exchange.sendResponseHeaders(200, "".length());
                        OutputStream os = exchange.getResponseBody();
                        os.write("".getBytes());
                        os.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }

                });
        }

        private IncomingRequest toIncomingRequest(HttpMethod method, HttpExchange exchange) {
            return new StandardIncomingRequest(
                exchange.getRequestURI(),
                method,
                new HttpHeaders(exchange.getRequestHeaders())
            );
        }
    }

}
