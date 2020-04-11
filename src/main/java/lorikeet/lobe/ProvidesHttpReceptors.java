package lorikeet.lobe;

import lorikeet.core.Seq;
import lorikeet.http.ReceptorBundle;

public interface ProvidesHttpReceptors<R extends UsesLogging> {
    ReceptorBundle<R> provideHttpReceptors();
}
