package lorikeet.web.impl;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lorikeet.Dict;
import lorikeet.NinjaException;
import lorikeet.spring.PathContainer;
import lorikeet.spring.PathPattern;
import lorikeet.spring.PathPatternParser;
import lorikeet.web.HttpHeaders;
import lorikeet.web.HttpMethod;
import lorikeet.web.IncomingRequest;
import lorikeet.web.IncomingRequestInterceptor;
import lorikeet.web.OutgoingResponse;
import lorikeet.web.WebEndpoint;
import lorikeet.web.WebRouter;
import lorikeet.web.WebServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

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
                .ifPresent(method -> this.handle(exchange, method));
        }

        private void handle(HttpExchange exchange, HttpMethod method) {
            this.invokeInterceptors(exchange, method);
            this.router.getDispatcher().getEndpoints()
                .filter(endpoint -> endpoint.getMethod() == method)
                .filter(endpoint -> matchesEndpoint(endpoint, exchange.getRequestURI().toASCIIString()))
                .first()
                .ifPresentOrElse(endpoint -> this.handle(exchange, method, endpoint), () -> this.handle404(exchange, method));
        }

        private void invokeInterceptors(HttpExchange exchange, HttpMethod method) {
            final String path = exchange.getRequestURI().toASCIIString();
            this.router.getIncomingRequestInterceptors()
                .filter(interceptor -> interceptor.getFilter().isApplicable(method, path))
                .forEach(interceptor -> this.intercept(interceptor, exchange, method));
        }

        private void intercept(IncomingRequestInterceptor interceptor, HttpExchange exchange, HttpMethod method) {
            final IncomingRequest request = new StandardIncomingRequest(
                exchange.getRequestURI(),
                method,
                new HttpHeaders(exchange.getRequestHeaders()),
                Dict.empty()
            );
            interceptor.intercept(request, new SunHttpOutgoingResponse(exchange));
        }

        private boolean matchesEndpoint(WebEndpoint endpoint, String incomingRequestPath) {
            return new PathPatternParser().parse(endpoint.getPath()).matches(PathContainer.parsePath(incomingRequestPath));
        }

        private void handle(HttpExchange exchange, HttpMethod method, WebEndpoint endpoint) {
            final IncomingRequest request = createIncomingRequest(exchange, method, endpoint.getPath());
            final OutgoingResponse response =new SunHttpOutgoingResponse(exchange);
            try {
                endpoint.getHandler().handle(request, response);
            } catch (NinjaException ne) {
                this.router.getServerErrorHandler().handle(request, response, ne.getCause());
            } catch (Exception e) {
                this.router.getServerErrorHandler().handle(request, response, e);
            }
        }

        private IncomingRequest createIncomingRequest(HttpExchange exchange, HttpMethod method, String uriPattern) {
            final Map<String, String> variables = new PathPatternParser().parse(uriPattern)
                .matchAndExtract(PathContainer.parsePath(exchange.getRequestURI().toASCIIString()))
                .getUriVariables();
            return new StandardIncomingRequest(
                exchange.getRequestURI(),
                method,
                new HttpHeaders(exchange.getRequestHeaders()),
                new Dict<>(variables)
            );
        }

        private void handle404(HttpExchange exchange, HttpMethod method) {
            final IncomingRequest request = new StandardIncomingRequest(
                exchange.getRequestURI(),
                method,
                new HttpHeaders(exchange.getRequestHeaders()),
                Dict.empty()
            );
            this.router.get404Handler().handle(request, new SunHttpOutgoingResponse(exchange));
        }
    }

}
