package lorikeet;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface May<T> {
    T orPanic();
    boolean isPresent();
    May<T> then(Consumer<? super T> action);
    May<T> filter(Predicate<? super T> predicate);
    <U> May<U> map(Function<? super T, ? extends U> mapper);
    May<T> or(Supplier<? extends May<? extends T>> supplier);
    T orElse(T other);
}
