package lorikeet.http.internal;

import lorikeet.core.Fallible;
import lorikeet.http.HttpDirective;
import lorikeet.http.HttpMsg;
import lorikeet.http.HttpMsgReceptor;
import lorikeet.http.HttpReject;
import lorikeet.http.HttpResolve;
import lorikeet.http.HttpReceptor;
import lorikeet.http.IncomingHttpSgnl;
import lorikeet.http.error.HttpMethodDoesNotMatchRequest;
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
        final Fallible<HttpDirective> directive = new HttpMsg<>(request, this.msgClass)
            .include()
            .map((msg) -> new HttpResolve(() -> this.msgReceptor.accept(tract, msg)));

        return directive.orGive(new HttpReject(directive.hasError(new HttpMethodDoesNotMatchRequest())));
    }
}
