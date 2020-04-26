package lorikeet.http;

import lorikeet.core.FallibleStream;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.http.internal.HttpMsgReceptorWrapper;
import lorikeet.lobe.UsesCoding;
import lorikeet.lobe.UsesLogging;

import java.util.Objects;

public class HttpRouter<R extends UsesLogging & UsesCoding> {
    private final Seq<HttpReceptor<R>> receptors;

    public HttpRouter() {
        this.receptors = new SeqOf<>();
    }

    public HttpRouter(Seq<HttpReceptor<R>> receptors) {
        this.receptors = receptors;
    }

    public HttpRouter<R> route(HttpReceptor<R> receptor) {
        return new HttpRouter<>(this.receptors.affix(receptor));
    }

    public <Msg> HttpRouter<R> route(HttpMsgReceptor<R, Msg> msgReceptor, Class<Msg> msgClass) {
        return new HttpRouter<>(this.receptors.affix(new HttpMsgReceptorWrapper<>(msgReceptor, msgClass)));
    }

    public HttpRouter<R> route(HttpRouter<R> router) {
        return new HttpRouter<>(this.receptors.affix(router.receptors()));
    }

    public HttpRouter<R> route(HttpRouteProvider<R> provider) {
        return this.route(provider.router());
    }

    public HttpRouter<R> get(String path, HttpReceptor<R> handler) {
        return this.route(HttpMethod.GET, path, handler);
    }

    public HttpRouter<R> post(String path, HttpReceptor<R> handler) {
        return this.route(HttpMethod.POST, path, handler);
    }

    public HttpRouter<R> put(String path, HttpReceptor<R> handler) {
        return this.route(HttpMethod.PUT, path, handler);
    }

    public HttpRouter<R> patch(String path, HttpReceptor<R> handler) {
        return this.route(HttpMethod.PATCH, path, handler);
    }

    public HttpRouter<R> delete(String path, HttpReceptor<R> handler) {
        return this.route(HttpMethod.DELETE, path, handler);
    }

    public HttpRouter<R> route(HttpMethod method, String path, HttpReceptor<R> handler) {
        final HttpReceptor<R> receptor =  (tract, sgnl) -> new FallibleStream<IncomingHttpSgnlError>()
            .include(sgnl)
            .include((a) -> tract)
            .include(new UriPath(path))
            .include(new RequestMethod(method))
            .coalesce((a, b, c, d) -> handler.junction(tract, sgnl))
            .orGive((e) -> new HttpReject(new SeqOf<>(e)));

        return this.route(receptor);
    }

    public Seq<HttpReceptor<R>> receptors() {
        return this.receptors;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        HttpRouter<?> that = (HttpRouter<?>) o;

        return Objects.equals(this.receptors(), that.receptors());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.receptors());
    }
}
