package lorikeet.lobe;

import lorikeet.core.Seq;

public interface ProvidesHttpReceptors<R extends UsesLogging> {
    Seq<HttpReceptor<R>> provideHttpReceptors();
}
