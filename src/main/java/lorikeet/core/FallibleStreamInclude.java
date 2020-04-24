package lorikeet.core;

import java.util.function.Supplier;

public interface FallibleStreamInclude<T, E extends Exception> extends Supplier<FallibleResult<T, E>> {
    FallibleResult<T, E> include();

    @Override
    default FallibleResult<T, E> get() {
        return this.include();
    }
}
