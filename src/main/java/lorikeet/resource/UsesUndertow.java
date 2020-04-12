package lorikeet.resource;

import lorikeet.http.HttpResource;
import lorikeet.lobe.ProvidesHttpReceptors;
import lorikeet.lobe.ProvidesTract;
import lorikeet.lobe.UsesHttpServer;
import lorikeet.lobe.UsesLogging;

public interface UsesUndertow<R extends UsesLogging & UsesHttpServer, A extends ProvidesHttpReceptors<R> & ProvidesTract<R>> extends UsesHttpServer {
    UndertowResource<R, A> useUndertow();

    @Override
    default HttpResource useHttpServerResource() {
        return this.useUndertow();
    }
}
