package lorikeet.web;

import lorikeet.Seq;

public final class IncomingRequestFilter {
    private final Seq<Filter> filters;

    public IncomingRequestFilter() {
        this.filters = Seq.empty();
    }

    private IncomingRequestFilter(Seq<Filter> filters) {
        this.filters = filters;
    }

    public IncomingRequestFilter filter(String path, HttpMethod... methods) {
        final Seq<Filter> filts  = Seq.of(methods)
            .map(method -> new Filter(method, path));
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

    public boolean isApplicable(HttpMethod method, String path) {
        return !this.applicable(method, path).isEmpty();
    }

    public Seq<Filter> applicable(HttpMethod method, String path) {
        return this.filters.filter(filter -> filter.getMethod() == method && Utils.uriMatches(filter.getPath(), path));
    }
}
