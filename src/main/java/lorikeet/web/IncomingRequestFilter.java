package lorikeet.web;

import lorikeet.Duo;
import lorikeet.Seq;

public final class IncomingRequestFilter {
    private final Seq<Duo<HttpMethod, String>> filters;

    public IncomingRequestFilter() {
        this.filters = Seq.empty();
    }

    private IncomingRequestFilter(Seq<Duo<HttpMethod, String>> filters) {
        this.filters = filters;
    }

    public IncomingRequestFilter filter(String path, HttpMethod... methods) {
        final Seq<Duo<HttpMethod, String>> filts  = Seq.of(methods)
            .map(method -> Duo.of(method, path) );
        return new IncomingRequestFilter(this.filters.push(filts));
    }

    public IncomingRequestFilter get(String path) {
        return this.filter(path, HttpMethod.GET);
    }

    public IncomingRequestFilter post(String path) {
        return this.filter(path, HttpMethod.POST);
    }

    public IncomingRequestFilter put(String path) {
        return this.filter(path, HttpMethod.PUT);
    }

    public IncomingRequestFilter delete(String path) {
        return this.filter(path, HttpMethod.DELETE);
    }

    public boolean applicable(HttpMethod method, String path) {
        return this.filters.anyMatch(duo -> duo.getLeft() == method && Utils.uriMatches(duo.getRight(), path));
    }
}
