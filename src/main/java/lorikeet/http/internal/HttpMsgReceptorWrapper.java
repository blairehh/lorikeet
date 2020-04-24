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
import lorikeet.lobe.UsesLogging;

public class HttpMsgReceptorWrapper<R extends UsesLogging, Msg> implements HttpReceptor<R> {
    private final HttpMsgReceptor<R, Msg> msgReceptor;
    private final Class<Msg> msgClass;

    public HttpMsgReceptorWrapper(HttpMsgReceptor<R, Msg> msgReceptor, Class<Msg> msgClass) {
        this.msgReceptor = msgReceptor;
        this.msgClass = msgClass;
    }

    @Override
    public HttpDirective junction(Tract<R> tract, IncomingHttpSgnl request) {
        return new HttpMsg<>(this.msgClass)
            .include(request)
            .map((msg) -> (HttpDirective)new HttpResolve(() -> this.msgReceptor.accept(tract, msg)))
            .orGive((errors) -> new HttpReject(new SeqOf<>(errors)));
    }
}
