package lorikeet.core;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Seq<T> extends List<T> {
    long count();
    long count(Predicate<? super T> countPredicate);

    Seq<T> affix(T element);
    Seq<T> affix(Collection<? extends T> elements);

    Optional<T> pick(int index);
    Seq<T> pick(Predicate<? super T> pickPredicate);

    Seq<T> drop(int index);
    Seq<T> drop(T element);
    Seq<T> drop(Collection<? extends T> elements);
    Seq<T> drop(Predicate<? super T> dropPredicate);

    Take<T, Optional<T>> take(int index);
    Take<T, Seq<T>> take(Predicate<? super T> takePredicate);

    <X> Seq<X> remodel(Function<? super T, ? extends X> remodeller);
    Seq<T> modify(Function<T, T> modifier);
    Seq<T> modify(Predicate<T> modifyPredicate, Function<T, T> modifier);

    Optional<T> first();

    List<T> forkMutable();
}