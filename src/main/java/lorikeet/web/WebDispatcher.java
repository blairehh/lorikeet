package lorikeet.web;

import lorikeet.Seq;

public class WebDispatcher {

    private final String path;
    private final Seq<Mapping> mappings;

    public WebDispatcher() {
        this.path = "";
        this.mappings = new Seq<>();
    }

    private WebDispatcher(String path, Seq<Mapping> mappings) {
        this.path = path;
        this.mappings = mappings;
    }

    public WebDispatcher path(String path) {
        if (!path.startsWith("/")) {
            return new WebDispatcher(this.path + "/" + path, this.mappings);
        }
        return new WebDispatcher(this.path + path, this.mappings);
    }

    public WebDispatcher get(String endpointPath, WebEndpoint endpoint) {
        final Mapping mapping = new Mapping(HttpMethod.GET, joinPath(this.path, endpointPath), endpoint);
        return new WebDispatcher(this.path, this.mappings.plus(mapping));
    }

    public WebDispatcher post(String endpointPath, WebEndpoint endpoint) {
        final Mapping mapping = new Mapping(HttpMethod.POST, joinPath(this.path, endpointPath), endpoint);
        return new WebDispatcher(this.path, this.mappings.plus(mapping));
    }

    public WebDispatcher put(String endpointPath, WebEndpoint endpoint) {
        final Mapping mapping = new Mapping(HttpMethod.PUT, joinPath(this.path, endpointPath), endpoint);
        return new WebDispatcher(this.path, this.mappings.plus(mapping));
    }

    public WebDispatcher delete(String endpointPath, WebEndpoint endpoint) {
        final Mapping mapping = new Mapping(HttpMethod.DELETE, joinPath(this.path, endpointPath), endpoint);
        return new WebDispatcher(this.path, this.mappings.plus(mapping));
    }

    public WebDispatcher done() {
        final int lastIndex = this.path.lastIndexOf('/');
        if (lastIndex < 0) {
            return this;
        }
        return new WebDispatcher(path.substring(0, lastIndex), this.mappings);
    }

    public Seq<Mapping> getMappings() {
        return this.mappings;
    }

    private static String joinPath(String a, String b) {
        return !b.startsWith("/")
            ? a + "/" + b
            : a + b;
    }
}
