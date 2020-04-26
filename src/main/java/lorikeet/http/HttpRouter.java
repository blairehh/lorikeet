package lorikeet.http;

import lorikeet.core.Seq;
import lorikeet.core.SeqOf;
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
