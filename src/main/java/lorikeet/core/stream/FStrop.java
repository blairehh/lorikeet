package lorikeet.core.stream;

import lorikeet.core.FallibleResult;

import java.util.function.Supplier;

public interface FStrop<T, E extends Exception> extends Supplier<FallibleResult<T, E>> {
    FallibleResult<T, E> include();

    @Override
    default FallibleResult<T, E> get() {
        return this.include();
    }
}
