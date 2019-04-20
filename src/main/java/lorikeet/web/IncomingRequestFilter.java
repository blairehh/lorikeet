package lorikeet.web;

import lorikeet.Seq;

public final class IncomingRequestFilter {

    private static final int DEFAULT_RANK = -1;

    private final Seq<Filter> filters;

    public IncomingRequestFilter() {
        this.filters = Seq.empty();
    }

    private IncomingRequestFilter(Seq<Filter> filters) {
        this.filters = filters;
    }

    public IncomingRequestFilter filter(int rank, String path, HttpMethod... methods) {
        final Seq<Filter> filts  = Seq.of(methods)
            .map(method -> new Filter(method, path, rank));
        return new IncomingRequestFilter(this.filters.push(filts));
    }

    public IncomingRequestFilter filter(String path, HttpMethod... methods) {
        return this.filter(DEFAULT_RANK, path, methods);
    }

    public IncomingRequestFilter get(int rank, String path) {
        return this.filter(rank, path, HttpMethod.GET);
    }

    public IncomingRequestFilter get(String path) {
        return this.filter(path, HttpMethod.GET);
    }

    public IncomingRequestFilter post(int rank, String path) {
        return this.filter(rank, path, HttpMethod.POST);
    }

    public IncomingRequestFilter post(String path) {
        return this.filter(path, HttpMethod.POST);
    }

    public IncomingRequestFilter put(int rank, String path) {
        return this.filter(rank, path, HttpMethod.PUT);
    }

    public IncomingRequestFilter put(String path) {
        return this.filter(path, HttpMethod.PUT);
    }

    public IncomingRequestFilter delete(int rank, String path) {
        return this.filter(rank, path, HttpMethod.DELETE);
    }

    public IncomingRequestFilter delete(String path) {
        return this.filter(path, HttpMethod.DELETE);
    }

    public boolean isApplicable(HttpMethod method, String path) {
        return !this.applicable(method, path).isEmpty();
    }

    public Seq<Filter> applicable(HttpMethod method, String path) {
        return this.filters
            .stream()
            .filter(filter -> filter.getMethod() == method && Utils.uriMatches(filter.getPath(), path))
            .sorted()
            .collect(Seq.collector());
    }
}
