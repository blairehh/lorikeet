package lorikeet.core.stream;

import java.util.function.Supplier;

public interface Strop<T> extends Supplier<T> {
    T include();

    @Override
    default T get() {
        return this.include();
    }
}
