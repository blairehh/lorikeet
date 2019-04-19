package lorikeet.web;

import lorikeet.Dict;
import lorikeet.Seq;

public class WebDispatcher {

    private final String path;
    private final Seq<WebEndpoint> endpoints;

    public WebDispatcher() {
        this.path = "/";
        this.endpoints = Seq.empty();
    }

    private WebDispatcher(String path, Seq<WebEndpoint> webEndpoints) {
        this.path = path;
        this.endpoints = webEndpoints;
    }

    public WebDispatcher path(String path) {
        return new WebDispatcher(normalizePath(path), this.endpoints);
    }

    public WebDispatcher serve(String endpointPath, IncomingRequestHandler handler, HttpMethod... methods) {
        return this.register(Seq.of(methods), endpointPath, handler);
    }

    public WebDispatcher get(IncomingRequestHandler handler) {
        return this.register(Seq.of(HttpMethod.GET), "", handler);
    }

    public WebDispatcher get(String endpointPath, IncomingRequestHandler handler) {
        return this.register(Seq.of(HttpMethod.GET), endpointPath, handler);
    }

    public WebDispatcher post(IncomingRequestHandler handler) {
        return this.register(Seq.of(HttpMethod.POST), "", handler);
    }

    public WebDispatcher post(String endpointPath, IncomingRequestHandler handler) {
        return this.register(Seq.of(HttpMethod.POST), endpointPath, handler);
    }

    public WebDispatcher put(IncomingRequestHandler handler) {
        return this.register(Seq.of(HttpMethod.PUT), "", handler);
    }


    public WebDispatcher put(String endpointPath, IncomingRequestHandler handler) {
        return this.register(Seq.of(HttpMethod.PUT), endpointPath, handler);
    }

    public WebDispatcher delete(IncomingRequestHandler handler) {
        return this.register(Seq.of(HttpMethod.DELETE), "", handler);
    }

    public WebDispatcher delete(String endpointPath, IncomingRequestHandler handler) {
        return this.register(Seq.of(HttpMethod.DELETE), endpointPath, handler);
    }

    private WebDispatcher register(Seq<HttpMethod> methods, String endpointPath, IncomingRequestHandler handler) {
        final Seq<WebEndpoint> endpoints = methods.map(method -> new WebEndpoint(method, normalizePath(this.path, endpointPath), handler));
        return new WebDispatcher(this.path, this.endpoints.push(endpoints));
    }

    public Seq<WebEndpoint> getEndpoints() {
        return this.endpoints;
    }

    public static String normalizePath(String part1, String part2) {
        final String endPart = part2.startsWith("/")
            ? part2.substring(1)
            : part2;

        final String path =  part1 + endPart;
        if (path.equals("/")) {
            return path;
        }
        return path.endsWith("/") ? path.substring(0, path.length() - 1) : path;
    }

    private static String normalizePath(String path) {
        final String start = path.startsWith("/")
            ? ""
            : "/";
        final String end = path.endsWith("/")
            ? ""
            : "/";
        return start + path + end;
    }
}
