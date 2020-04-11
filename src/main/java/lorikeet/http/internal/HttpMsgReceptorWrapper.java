package lorikeet.http.internal;

import lorikeet.core.Fallible;
import lorikeet.http.HttpMsg;
import lorikeet.http.HttpMsgReceptor;
import lorikeet.lobe.HttpReceptor;
import lorikeet.lobe.IncomingHttpMsg;
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
    public Fallible<Runnable> ligand(Tract<R> tract, IncomingHttpMsg request) {
        return new HttpMsg<>(request, this.msgClass)
            .include()
            .map((msg) -> () -> this.msgReceptor.accept(tract, msg));
    }
}
