package lorikeet.http.internal;

import lorikeet.http.HttpMsg;
import lorikeet.http.HttpMsgReceptor;
import lorikeet.lobe.HttpLigand;
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
    public HttpLigand ligand(Tract<R> tract, IncomingHttpMsg request) {
        new HttpMsg<>(request, this.msgClass)
            .include()
            .onSuccess((msg) -> this.msgReceptor.accept(tract, msg));
        return null;
    }

    @Override
    public void receive(Tract<R> tract, IncomingHttpMsg signal) {

    }
}
