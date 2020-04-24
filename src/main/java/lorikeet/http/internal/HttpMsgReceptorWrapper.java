package lorikeet.http.internal;

import lorikeet.core.SeqOf;
import lorikeet.http.HttpDirective;
import lorikeet.http.HttpMsg;
import lorikeet.http.HttpMsgReceptor;
import lorikeet.http.HttpReject;
import lorikeet.http.HttpResolve;
import lorikeet.http.HttpReceptor;
import lorikeet.http.IncomingHttpSgnl;
import lorikeet.lobe.Tract;
import lorikeet.lobe.UsesCoding;
import lorikeet.lobe.UsesLogging;

import java.util.List;

public class HttpMsgReceptorWrapper<R extends UsesLogging & UsesCoding, Msg> implements HttpReceptor<R> {
    private final HttpMsgReceptor<R, Msg> msgReceptor;
    private final Class<Msg> msgClass;

    public HttpMsgReceptorWrapper(HttpMsgReceptor<R, Msg> msgReceptor, Class<Msg> msgClass) {
        this.msgReceptor = msgReceptor;
        this.msgClass = msgClass;
    }

    // @TODO see if there is a way to do this without casting
    @SuppressWarnings("unchecked")
    private <RT extends UsesLogging & UsesCoding> Tract<RT> tract(Tract<R> tract) {
        // not sure why this results in unchecked
        return (Tract<RT>)tract;
    }

    @Override
    public HttpDirective junction(Tract<R> tract, IncomingHttpSgnl request) {
        return new HttpMsg<>(this.msgClass)
            .include(request, tract(tract))
            .map((msg) -> (HttpDirective)new HttpResolve(() -> this.msgReceptor.accept(tract, msg)))
            .orGive((errors) -> new HttpReject(new SeqOf<>(errors)));
    }
}
