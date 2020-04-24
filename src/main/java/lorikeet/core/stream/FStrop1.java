package lorikeet.core.stream;

import lorikeet.core.FallibleResult;

public interface FStrop1<I, O, E extends Exception> {
    FallibleResult<O, E> include(I input);
}
