package lorikeet.web;

import lorikeet.Dict;
import lorikeet.Seq;

public class WebDispatcher {

    private final String path;
    private final Seq<WebEndpoint> endpoints;

    public WebDispatcher() {
        this.path = "";
        this.endpoints = Seq.empty();
    }

    private WebDispatcher(String path, Seq<WebEndpoint> webEndpoints) {
        this.path = path;
        this.endpoints = webEndpoints;
    }

    public WebDispatcher path(String path) {
        if (!path.startsWith("/")) {
            return new WebDispatcher(this.path + "/" + path, this.endpoints);
        }
        return new WebDispatcher(this.path + path, this.endpoints);
    }

    public WebDispatcher serve(String endpointPath, IncomingRequestHandler handler, HttpMethod... methods) {
        return this.register(Seq.of(methods), endpointPath, handler);
    }

    public WebDispatcher get(String endpointPath, IncomingRequestHandler handler) {
        return this.register(Seq.of(HttpMethod.DELETE), endpointPath, handler);
    }

    public WebDispatcher post(String endpointPath, IncomingRequestHandler handler) {
        return this.register(Seq.of(HttpMethod.DELETE), endpointPath, handler);
    }

    public WebDispatcher put(String endpointPath, IncomingRequestHandler handler) {
        return this.register(Seq.of(HttpMethod.PUT), endpointPath, handler);
    }

    public WebDispatcher delete(String endpointPath, IncomingRequestHandler handler) {
        return this.register(Seq.of(HttpMethod.DELETE), endpointPath, handler);
    }

    private WebDispatcher register(Seq<HttpMethod> methods, String endpointPath, IncomingRequestHandler handler) {
        final Seq<WebEndpoint> endpoints = methods.map(method -> new WebEndpoint(method, endpointPath, handler));
        return new WebDispatcher(this.path, this.endpoints.push(endpoints));
    }

    public WebDispatcher done() {
        final int lastIndex = this.path.lastIndexOf('/');
        if (lastIndex < 0) {
            return this;
        }
        return new WebDispatcher(path.substring(0, lastIndex), this.endpoints);
    }

    public Seq<WebEndpoint> getEndpoints() {
        return this.endpoints;
    }

    public Dict<String, Seq<WebEndpoint>> getEndpointsByPath() {
        return Seq.unique(this.endpoints, WebEndpoint::getPath)
            .mapify(path -> this.endpoints.filter(endpoint -> endpoint.getPath().equals(path)));
    }

    private static String joinPath(String a, String b) {
        return !b.startsWith("/")
            ? a + "/" + b
            : a + b;
    }
}
