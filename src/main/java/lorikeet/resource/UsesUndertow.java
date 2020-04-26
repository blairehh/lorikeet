package lorikeet.resource;

import lorikeet.http.HttpResource;
import lorikeet.lobe.*;

public interface UsesUndertow<R extends UsesLogging & UsesCoding & UsesHttpServer, A extends ProvidesHttpRouter<R> & ProvidesTract<R>> extends UsesHttpServer {
    UndertowResource<R, A> useUndertow();

    @Override
    default HttpResource useHttpServerResource() {
        return this.useUndertow();
    }
}
