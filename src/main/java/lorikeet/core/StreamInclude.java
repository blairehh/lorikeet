package lorikeet.core;

import java.util.function.Supplier;

public interface StreamInclude<T> extends Supplier<T> {
    T include();

    @Override
    default T get() {
        return this.include();
    }
}
