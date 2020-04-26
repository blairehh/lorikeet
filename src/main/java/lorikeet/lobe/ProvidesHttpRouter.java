package lorikeet.lobe;

import lorikeet.http.HttpRouter;

public interface ProvidesHttpRouter<R extends UsesLogging & UsesCoding> {
    HttpRouter<R> httpRouter();
}
