package lorikeet.core;

import java.util.function.Supplier;

public interface Includable<T> extends Supplier<T> {
    T include();

    @Override
    default T get() {
        return this.include();
    }
}
