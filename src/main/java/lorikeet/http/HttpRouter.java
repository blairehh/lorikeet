package lorikeet.http;

import lorikeet.core.FallibleStream;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.http.internal.HttpMsgControllerWrapper;
import lorikeet.lobe.UsesCoding;
import lorikeet.lobe.UsesLogging;

import java.util.Objects;
import java.util.function.Function;

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

    public <Msg> HttpRouter<R> msg(Class<Msg> msgClass, Function<Msg, HttpEndpoint<R>> msgEndpoint) {
        return new HttpRouter<>(this.controllers.affix(new HttpMsgControllerWrapper<>(msgEndpoint, msgClass)));
    }

    public HttpRouter<R> include(HttpRouter<R> router) {
        return new HttpRouter<>(this.controllers.affix(router.controllers()));
    }

    public HttpRouter<R> include(HttpRouteProvider<R> provider) {
        return this.include(provider.router());
    }

    public HttpRouter<R> get(String path, Function<IncomingHttpSgnl, HttpEndpoint<R>> endpoint) {
        return this.route(HttpMethod.GET, path, endpoint);
    }

    public HttpRouter<R> post(String path, Function<IncomingHttpSgnl, HttpEndpoint<R>> endpoint) {
        return this.route(HttpMethod.POST, path, endpoint);
    }

    public HttpRouter<R> put(String path, Function<IncomingHttpSgnl, HttpEndpoint<R>> endpoint) {
        return this.route(HttpMethod.PUT, path, endpoint);
    }

    public HttpRouter<R> patch(String path, Function<IncomingHttpSgnl, HttpEndpoint<R>> endpoint) {
        return this.route(HttpMethod.PATCH, path, endpoint);
    }

    public HttpRouter<R> delete(String path, Function<IncomingHttpSgnl, HttpEndpoint<R>> endpoint) {
        return this.route(HttpMethod.DELETE, path, endpoint);
    }

    public HttpRouter<R> route(HttpMethod method, String path, Function<IncomingHttpSgnl, HttpEndpoint<R>> endpoint) {
        final HttpController<R> controller =  (tract, sgnl) -> new FallibleStream<IncomingHttpSgnlError>()
            .include(sgnl)
            .include((a) -> tract)
            .include(new UriPath(path))
            .include(new RequestMethod(method))
            .coalesce((a, b, c, d) -> (HttpDirective)new HttpResolve(() -> endpoint.apply(sgnl).accept(tract)))
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
