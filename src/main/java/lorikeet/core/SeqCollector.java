package lorikeet.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class SeqCollector<T> implements Collector<T, List<T>, Seq<T>> {
    @Override
    public Supplier<List<T>> supplier() {
        return () -> new ArrayList<>();
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return (list, item) -> list.add(item);
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        return (a, b) -> {
            a.addAll(b);
            return a;
        };
    }

    @Override
    public Function<List<T>, Seq<T>> finisher() {
        return (list) -> new SeqOf<>(list);
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }
}
