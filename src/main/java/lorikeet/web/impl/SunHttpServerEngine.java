package lorikeet.web.impl;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lorikeet.Seq;
import lorikeet.web.HttpHeaders;
import lorikeet.web.HttpMethod;
import lorikeet.web.IncomingRequest;
import lorikeet.web.WebEndpoint;
import lorikeet.web.WebServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SunHttpServerEngine {

    private final HttpServer server;

    public SunHttpServerEngine(WebServer webserver) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(1111), 0);
        this.server.createContext("/", new HttpEndpointHandler(webserver.getRouter().getDispatcher().getEndpoints()));
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
        public void handle(HttpExchange httpExchange) {
            HttpMethod.find(httpExchange.getRequestMethod())
                .ifPresent(method -> this.handle(method, httpExchange));
        }

        private void handle(HttpMethod method, HttpExchange exchange) {
            System.out.println(exchange.getRequestURI());
            this.endpoints
                .filter(endpoint -> endpoint.getMethod() == method)
                .filter(endpoint -> endpoint.getPath().equals(exchange.getRequestURI().toASCIIString()))
                .first()
                .ifPresent(endpoint -> this.handle(endpoint, method, exchange));
        }

        private void handle(WebEndpoint endpoint, HttpMethod method, HttpExchange exchange) {
            final IncomingRequest request = new StandardIncomingRequest(
                exchange.getRequestURI(),
                method,
                new HttpHeaders(exchange.getRequestHeaders())
            );
            endpoint.getHandler().handle(request, new SunHttpOutgoingResponse(exchange));
        }
    }

}
