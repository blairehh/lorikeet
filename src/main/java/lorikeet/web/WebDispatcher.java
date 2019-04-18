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

    public WebDispatcher get(String endpointPath, IncomingRequestHandler endpoint) {
        final WebEndpoint webEndpoint = new WebEndpoint(HttpMethod.GET, joinPath(this.path, endpointPath), endpoint);
        return new WebDispatcher(this.path, this.endpoints.push(webEndpoint));
    }

    public WebDispatcher post(String endpointPath, IncomingRequestHandler endpoint) {
        final WebEndpoint webEndpoint = new WebEndpoint(HttpMethod.POST, joinPath(this.path, endpointPath), endpoint);
        return new WebDispatcher(this.path, this.endpoints.push(webEndpoint));
    }

    public WebDispatcher put(String endpointPath, IncomingRequestHandler endpoint) {
        final WebEndpoint webEndpoint = new WebEndpoint(HttpMethod.PUT, joinPath(this.path, endpointPath), endpoint);
        return new WebDispatcher(this.path, this.endpoints.push(webEndpoint));
    }

    public WebDispatcher delete(String endpointPath, IncomingRequestHandler endpoint) {
        final WebEndpoint webEndpoint = new WebEndpoint(HttpMethod.DELETE, joinPath(this.path, endpointPath), endpoint);
        return new WebDispatcher(this.path, this.endpoints.push(webEndpoint));
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
