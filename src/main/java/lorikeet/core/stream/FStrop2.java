package lorikeet.core.stream;

import lorikeet.core.FallibleResult;

public interface FStrop2<I1, I2, O, E extends Exception> {
    FallibleResult<O, E> include(I1 a, I2 b);
}
