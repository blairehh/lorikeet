package lorikeet.lobe;

import lorikeet.http.HttpRouter;

public interface ProvidesHttpReceptors<R extends UsesLogging & UsesCoding> {
    HttpRouter<R> httpRouter();
}
