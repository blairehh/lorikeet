package lorikeet.http;

import lorikeet.core.FallibleStream;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.http.internal.HttpMsgControllerWrapper;
import lorikeet.lobe.UsesCoding;
import lorikeet.lobe.UsesLogging;

import java.util.Objects;

public class HttpRouter<R extends UsesLogging & UsesCoding> {
    private final Seq<HttpController<R>> controllers;

    public HttpRouter() {
        this.controllers = new SeqOf<>();
    }

    public HttpRouter(Seq<HttpController<R>> controllers) {
        this.controllers = controllers;
    }

    public HttpRouter<R> route(HttpController<R> controller) {
        return new HttpRouter<>(this.controllers.affix(controller));
    }

    public <Msg> HttpRouter<R> route(HttpEndpoint<R, Msg> msgEndpoint, Class<Msg> msgClass) {
        return new HttpRouter<>(this.controllers.affix(new HttpMsgControllerWrapper<>(msgEndpoint, msgClass)));
    }

    public HttpRouter<R> route(HttpRouter<R> router) {
        return new HttpRouter<>(this.controllers.affix(router.controllers()));
    }

    public HttpRouter<R> route(HttpRouteProvider<R> provider) {
        return this.route(provider.router());
    }

    public HttpRouter<R> get(String path, HttpEndpoint<R, IncomingHttpSgnl> endpoint) {
        return this.route(HttpMethod.GET, path, endpoint);
    }

    public HttpRouter<R> post(String path, HttpEndpoint<R, IncomingHttpSgnl> endpoint) {
        return this.route(HttpMethod.POST, path, endpoint);
    }

    public HttpRouter<R> put(String path, HttpEndpoint<R, IncomingHttpSgnl> endpoint) {
        return this.route(HttpMethod.PUT, path, endpoint);
    }

    public HttpRouter<R> patch(String path, HttpEndpoint<R, IncomingHttpSgnl> endpoint) {
        return this.route(HttpMethod.PATCH, path, endpoint);
    }

    public HttpRouter<R> delete(String path, HttpEndpoint<R, IncomingHttpSgnl> endpoint) {
        return this.route(HttpMethod.DELETE, path, endpoint);
    }

    public HttpRouter<R> route(HttpMethod method, String path, HttpEndpoint<R, IncomingHttpSgnl> endpoint) {
        final HttpController<R> controller =  (tract, sgnl) -> new FallibleStream<IncomingHttpSgnlError>()
            .include(sgnl)
            .include((a) -> tract)
            .include(new UriPath(path))
            .include(new RequestMethod(method))
            .coalesce((a, b, c, d) -> (HttpDirective)new HttpResolve(() -> endpoint.accept(tract, sgnl)))
            .orGive((e) -> new HttpReject(new SeqOf<>(e)));

        return this.route(controller);
    }

    public Seq<HttpController<R>> controllers() {
        return this.controllers;
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

        return Objects.equals(this.controllers(), that.controllers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.controllers());
    }
}
