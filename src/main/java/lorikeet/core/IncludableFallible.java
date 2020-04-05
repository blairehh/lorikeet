package lorikeet.core;

import java.util.function.Supplier;

public interface IncludableFallible<T> extends Supplier<Fallible<T>> {
    Fallible<T> include();

    @Override
    default Fallible<T> get() {
        return this.include();
    }
}
