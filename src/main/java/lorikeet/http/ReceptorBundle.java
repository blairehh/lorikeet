package lorikeet.http;

import lorikeet.core.Seq;
import lorikeet.core.SeqOf;
import lorikeet.http.internal.HttpMsgReceptorWrapper;
import lorikeet.lobe.UsesLogging;

import java.util.Objects;

public class ReceptorBundle<R extends UsesLogging> {
    private final Seq<HttpReceptor<R>> receptors;

    public ReceptorBundle() {
        this.receptors = new SeqOf<>();
    }

    public ReceptorBundle(Seq<HttpReceptor<R>> receptors) {
        this.receptors = receptors;
    }

    public ReceptorBundle<R> add(HttpReceptor<R> receptor) {
        return new ReceptorBundle<>(this.receptors.affix(receptor));
    }

    public <Msg> ReceptorBundle<R> add(HttpMsgReceptor<R, Msg> msgReceptor, Class<Msg> msgClass) {
        return new ReceptorBundle<>(this.receptors.affix(new HttpMsgReceptorWrapper<>(msgReceptor, msgClass)));
    }

    public ReceptorBundle<R> add(ReceptorBundle<R> bundle) {
        return new ReceptorBundle<>(this.receptors.affix(bundle.receptors()));
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

        ReceptorBundle<?> that = (ReceptorBundle<?>) o;

        return Objects.equals(this.receptors(), that.receptors());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.receptors());
    }
}
