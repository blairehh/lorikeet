package lorikeet.web.impl;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lorikeet.web.HttpHeaders;
import lorikeet.web.HttpMethod;
import lorikeet.web.IncomingRequest;
import lorikeet.web.WebEndpoint;
import lorikeet.web.WebRouter;
import lorikeet.web.WebServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SunHttpServerEngine {

    private final HttpServer server;

    public SunHttpServerEngine(WebServer webserver) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(1111), 0);
        this.server.createContext("/", new HttpEndpointHandler(webserver.getRouter()));
    }

    public void start() {
        this.server.start();
    }

    public static class HttpEndpointHandler implements HttpHandler {

        private final WebRouter router;

        public HttpEndpointHandler(WebRouter router) {
            this.router = router;
        }

        @Override
        public void handle(HttpExchange exchange) {
            HttpMethod.find(exchange.getRequestMethod())
                .map(method -> new StandardIncomingRequest(exchange.getRequestURI(), method, new HttpHeaders(exchange.getRequestHeaders())))
                .ifPresent(request -> this.handle(request, exchange));
        }

        private void handle(IncomingRequest request, HttpExchange exchange) {
            this.router.getDispatcher().getEndpoints()
                .filter(endpoint -> endpoint.getMethod() == request.getMethod())
                .filter(endpoint -> endpoint.getPath().equals(request.getURI().toASCIIString()))
                .first()
                .ifPresentOrElse(endpoint -> this.handle(endpoint, request, exchange), () -> this.handle404(request, exchange));
        }

        private void handle(WebEndpoint endpoint, IncomingRequest request, HttpExchange exchange) {
            endpoint.getHandler().handle(request, new SunHttpOutgoingResponse(exchange));
        }

        private void handle404(IncomingRequest request, HttpExchange exchange) {
            this.router.get404Handler().handle(request, new SunHttpOutgoingResponse(exchange));
        }
    }

}
