package lorikeet.http.internal;

import lorikeet.core.SeqOf;
import lorikeet.http.HttpDirective;
import lorikeet.http.HttpMsg;
import lorikeet.http.HttpEndpoint;
import lorikeet.http.HttpReject;
import lorikeet.http.HttpResolve;
import lorikeet.http.HttpController;
import lorikeet.http.IncomingHttpSgnl;
import lorikeet.lobe.Tract;
import lorikeet.lobe.UsesCoding;
import lorikeet.lobe.UsesLogging;

import java.util.function.Function;

public class HttpMsgControllerWrapper<R extends UsesLogging & UsesCoding, Msg> implements HttpController<R> {
    private final Function<Msg, HttpEndpoint<R>> endpoint;
    private final Class<Msg> msgClass;

    public HttpMsgControllerWrapper(Function<Msg, HttpEndpoint<R>> endpoint, Class<Msg> msgClass) {
        this.endpoint = endpoint;
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
            .map((msg) -> (HttpDirective)new HttpResolve(() -> this.endpoint.apply(msg).accept(tract)))
            .orGive((errors) -> new HttpReject(new SeqOf<>(errors)));
    }
}
