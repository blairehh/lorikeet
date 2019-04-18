package lorikeet.web;

import lorikeet.Seq;

public class HttpDispatcher {

    private final String path;
    private final Seq<Mapping> mappings;

    public HttpDispatcher() {
        this.path = "";
        this.mappings = new Seq<>();
    }

    private HttpDispatcher(String path, Seq<Mapping> mappings) {
        this.path = path;
        this.mappings = mappings;
    }

    public HttpDispatcher path(String path) {
        if (!path.startsWith("/")) {
            return new HttpDispatcher(this.path + "/" + path, this.mappings);
        }
        return new HttpDispatcher(this.path + path, this.mappings);
    }

    public HttpDispatcher serve(String endpoint) {
        final String fullPath = !endpoint.startsWith("/")
            ? this.path + "/" + endpoint
            : this.path + endpoint;

        return new HttpDispatcher(this.path, this.mappings.plus(new Mapping(fullPath)));
    }

    public HttpDispatcher done() {
        final int lastIndex = this.path.lastIndexOf('/');
        if (lastIndex < 0) {
            return this;
        }
        return new HttpDispatcher(path.substring(0, lastIndex), this.mappings);
    }

    public Seq<Mapping> getMappings() {
        return this.mappings;
    }
}
