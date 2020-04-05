package lorikeet.core;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class FallibleStream {

    interface F1<T> extends Supplier<Fallible<T>> {}
    interface O1<T> extends Supplier<Optional<T>> {}

    public <T> FS1<T> include(T value) {
        return new FS1<>(new Ok<>(value));
    }

    public <T> FS1<T> include(Fallible<T> value) {
        return new FS1<>(value);
    }

    public <T> FS1<T> include(Optional<T> value) {
        return new FS1<>(new OptOf<>(value));
    }

    public <T> FS1<T> include(Supplier<T> supplier) {
        return new FS1<>(new Ok<>(supplier.get()));
    }

    public <T> FS1<T> include(Includable<T> includable) {
        return this.include(includable.include());
    }

    public <T> FS1<T> include(IncludableFallible<T> includable) {
        return this.include(includable.include());
    }

    public <T> FS1<T> include(F1<T> supplier) {
        return this.include(supplier.get());
    }

    public <T> FS1<T> include(O1<T> supplier) {
        return this.include(supplier.get());
    }

    public static class FS1<A> {
        private final Fallible<A> a;

        public FS1(Fallible<A> a) {
            this.a = a;
        }

        public <B> Fallible<B> coalesce(Function<A, B> coalesce) {
            return this.a.map(coalesce::apply);
        }

        public <B> Fallible<B> coalescef(Function<A, Fallible<B>> coalesce) {
            return this.a.then(coalesce::apply);
        }

        public <B> Fallible<B> coalesceo(Function<A, Optional<B>> coalesce) {
            return this.a.then((aValue) -> new OptOf<>(coalesce.apply(aValue)));
        }

        public <B> FS2<A, B> include(Includable<B> includable) {
            return this.include(new Ok<>(includable.include()));
        }

        public <B> FS2<A, B> include(IncludableFallible<B> includable) {
            return this.include(includable.include());
        }

        public <B> FS2<A, B> include(Fallible<B> value) {
            return new FS2<>(this.a, value);
        }

        public <B> FS2<A, B> include(Optional<B> value) {
            return new FS2<>(this.a, new OptOf<>(value));
        }

        public <B> FS2<A, B> include(Supplier<B> supplier) {
            return this.include((a) -> supplier.get());
        }

        public <B> FS2<A, B> include(F1<B> supplier) {
            return this.includef((a) -> supplier.get());
        }

        public <B> FS2<A, B> include(O1<B> supplier) {
            return this.includeo((a) -> supplier.get());
        }

        public <B> FS2<A, B> include(Function<A, B> func) {
            final Fallible<B> fallible = new FallibleStream()
                .include(this.a)
                .coalesce(func);
            return new FS2<>(this.a, fallible);
        }

        public <B> FS2<A, B> includef(Function<A, Fallible<B>> func) {
            final Fallible<B> fallible = new FallibleStream()
                .include(this.a)
                .coalescef(func);
            return new FS2<>(this.a, fallible);
        }

        public <B> FS2<A, B> includeo(Function<A, Optional<B>> func) {
            final Fallible<B> fallible = new FallibleStream()
                .include(this.a)
                .coalesceo(func);
            return new FS2<>(this.a, fallible);
        }
    }

    public static class FS2<A, B> {
        private final Fallible<A> a;
        private final Fallible<B> b;

        FS2(Fallible<A> a, Fallible<B> b) {
            this.a = a;
            this.b = b;
        }

        public <C> Fallible<C> coalesce(BiFunction<A, B, C> coalesce) {
            return this.a.then((aValue) ->
                this.b.map((bValue) -> coalesce.apply(aValue, bValue))
            );
        }

        public <C> Fallible<C> coalescef(BiFunction<A, B, Fallible<C>> coalesce) {
            return this.a.then((aValue) ->
                this.b.then((bValue) -> coalesce.apply(aValue, bValue))
            );
        }

        public <C> Fallible<C> coalesceo(BiFunction<A, B, Optional<C>> coalesce) {
            return this.a.then((aValue) ->
                this.b.then((bValue) -> new OptOf<>(coalesce.apply(aValue, bValue)))
            );
        }

        public <C> FS3<A, B, C> include(Includable<C> includable) {
            return this.include(new Ok<>(includable.include()));
        }

        public <C> FS3<A, B, C> include(IncludableFallible<C> includable) {
            return this.include(includable.include());
        }

        public <C> FS3<A, B, C> include(Fallible<C> value) {
            return new FS3<>(this.a, this.b, value);
        }

        public <C> FS3<A, B, C> include(Optional<C> value) {
            return new FS3<>(this.a, this.b, new OptOf<>(value));
        }

        public <C> FS3<A, B, C> include(Supplier<C> supplier) {
            return this.include((a, b) -> supplier.get());
        }

        public <C> FS3<A, B, C> include(F1<C> supplier) {
            return this.includef((a, b) -> supplier.get());
        }

        public <C> FS3<A, B, C> include(O1<C> supplier) {
            return this.includeo((a, b) -> supplier.get());
        }

        public <C> FS3<A, B, C> include(BiFunction<A, B, C> func) {
            final Fallible<C> fallible = new FallibleStream()
                .include(this.a)
                .include(this.b)
                .coalesce(func);
            return new FS3<>(this.a, this.b, fallible);
        }

        public <C> FS3<A, B, C> includef(BiFunction<A, B, Fallible<C>> func) {
            final Fallible<C> fallible = new FallibleStream()
                .include(this.a)
                .include(this.b)
                .coalescef(func);
            return new FS3<>(this.a, this.b, fallible);
        }

        public <C> FS3<A, B, C> includeo(BiFunction<A, B, Optional<C>> func) {
            final Fallible<C> fallible = new FallibleStream()
                .include(this.a)
                .include(this.b)
                .coalesceo(func);
            return new FS3<>(this.a, this.b, fallible);
        }
    }

    public static class FS3<A, B, C> {
        interface FN3<A, B, C, D> {
            D apply(A a, B b, C c);
        }

        private final Fallible<A> a;
        private final Fallible<B> b;
        private final Fallible<C> c;

        FS3(Fallible<A> a, Fallible<B> b, Fallible<C> c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public <D> Fallible<D> coalesce(FN3<A, B, C, D> coalesce) {
            return this.a.then((aValue) ->
                this.b.then((bValue) ->
                    this.c.map((cValue) -> coalesce.apply(aValue, bValue, cValue))
                )
            );
        }

        public <D> Fallible<D> coalescef(FN3<A, B, C, Fallible<D>> coalesce) {
            return this.a.then((aValue) ->
                this.b.then((bValue) ->
                    this.c.then((cValue) -> coalesce.apply(aValue, bValue, cValue))
                )
            );
        }

        public <D> Fallible<D> coalesceo(FN3<A, B, C, Optional<D>> coalesce) {
            return this.a.then((aValue) ->
                this.b.then((bValue) ->
                    this.c.then((cValue) -> new OptOf<>(coalesce.apply(aValue, bValue, cValue)))
                )
            );
        }

        public <D> FS4<A, B, C, D> include(Includable<D> includable) {
            return this.include(new Ok<>(includable.include()));
        }

        public <D> FS4<A, B, C, D> include(IncludableFallible<D> includable) {
            return this.include(includable.include());
        }

        public <D> FS4<A, B, C, D> include(Fallible<D> value) {
            return new FS4<>(this.a, this.b, this.c, value);
        }

        public <D> FS4<A, B, C, D> include(Optional<D> value) {
            return new FS4<>(this.a, this.b, this.c, new OptOf<>(value));
        }

        public <D> FS4<A, B, C, D> include(Supplier<D> supplier) {
            return this.include((a, b, c) -> supplier.get());
        }

        public <D> FS4<A, B, C, D> include(F1<D> supplier) {
            return this.includef((a, b, c) -> supplier.get());
        }

        public <D> FS4<A, B, C, D> include(O1<D> supplier) {
            return this.includeo((a, b, c) -> supplier.get());
        }

        public <D> FS4<A, B, C, D> include(FN3<A, B, C, D> func) {
            final Fallible<D> fallible = new FallibleStream()
                .include(this.a)
                .include(this.b)
                .include(this.c)
                .coalesce(func);
            return new FS4<>(this.a, this.b, this.c, fallible);
        }

        public <D> FS4<A, B, C, D> includef(FN3<A, B, C, Fallible<D>> func) {
            final Fallible<D> fallible = new FallibleStream()
                .include(this.a)
                .include(this.b)
                .include(this.c)
                .coalescef(func);
            return new FS4<>(this.a, this.b, this.c, fallible);
        }

        public <D> FS4<A, B, C, D> includeo(FN3<A, B, C, Optional<D>> func) {
            final Fallible<D> fallible = new FallibleStream()
                .include(this.a)
                .include(this.b)
                .include(this.c)
                .coalesceo(func);
            return new FS4<>(this.a, this.b, this.c, fallible);
        }
    }

    public static class FS4<A, B, C, D> {
        interface FN4<A, B, C, D, E> {
            E apply(A a, B b, C c, D d);
        }

        private final Fallible<A> a;
        private final Fallible<B> b;
        private final Fallible<C> c;
        private final Fallible<D> d;

        FS4(Fallible<A> a, Fallible<B> b, Fallible<C> c, Fallible<D> d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        public <E> Fallible<E> coalesce(FN4<A, B, C, D, E> coalesce) {
            return this.a.then((aValue) ->
                this.b.then((bValue) ->
                    this.c.then((cValue) ->
                        this.d.map((dValue) -> coalesce.apply(aValue, bValue, cValue, dValue))
                    )
                )
            );
        }

        public <E> Fallible<E> coalescef(FN4<A, B, C, D, Fallible<E>> coalesce) {
            return this.a.then((aValue) ->
                this.b.then((bValue) ->
                    this.c.then((cValue) ->
                        this.d.then((dValue) -> coalesce.apply(aValue, bValue, cValue, dValue))
                    )
                )
            );
        }

        public <E> Fallible<E> coalesceo(FN4<A, B, C, D, Optional<E>> coalesce) {
            return this.a.then((aValue) ->
                this.b.then((bValue) ->
                    this.c.then((cValue) ->
                        this.d.then((dValue) -> new OptOf<>(coalesce.apply(aValue, bValue, cValue, dValue)))
                    )
                )
            );
        }

        public <E> FS5<A, B, C, D, E> include(Includable<E> includable) {
            return this.include(new Ok<>(includable.include()));
        }

        public <E> FS5<A, B, C, D, E> include(IncludableFallible<E> includable) {
            return this.include(includable.include());
        }

        public <E> FS5<A, B, C, D, E> include(Fallible<E> value) {
            return new FS5<>(this.a, this.b, this.c, this.d, value);
        }

        public <E> FS5<A, B, C, D, E> include(Optional<E> value) {
            return new FS5<>(this.a, this.b, this.c, this.d, new OptOf<>(value));
        }

        public <E> FS5<A, B, C, D, E> include(Supplier<E> supplier) {
            return this.include((a, b, c, d) -> supplier.get());
        }

        public <E> FS5<A, B, C, D, E> include(F1<E> supplier) {
            return this.includef((a, b, c, d) -> supplier.get());
        }

        public <E> FS5<A, B, C, D, E> include(O1<E> supplier) {
            return this.includeo((a, b, c, d) -> supplier.get());
        }

        public <E> FS5<A, B, C, D, E> include(FN4<A, B, C, D, E> func) {
            final Fallible<E> fallible = new FallibleStream()
                .include(this.a)
                .include(this.b)
                .include(this.c)
                .include(this.d)
                .coalesce(func);
            return new FS5<>(this.a, this.b, this.c, this.d, fallible);
        }

        public <E> FS5<A, B, C, D, E> includef(FN4<A, B, C, D, Fallible<E>> func) {
            final Fallible<E> fallible = new FallibleStream()
                .include(this.a)
                .include(this.b)
                .include(this.c)
                .include(this.d)
                .coalescef(func);
            return new FS5<>(this.a, this.b, this.c, this.d, fallible);
        }

        public <E> FS5<A, B, C, D, E> includeo(FN4<A, B, C, D, Optional<E>> func) {
            final Fallible<E> fallible = new FallibleStream()
                .include(this.a)
                .include(this.b)
                .include(this.c)
                .include(this.d)
                .coalesceo(func);
            return new FS5<>(this.a, this.b, this.c, this.d, fallible);
        }
    }

    public static class FS5<A, B, C, D, E> {
        interface FN5<A, B, C, D, E, F> {
            F apply(A a, B b, C c, D d, E e);
        }

        private final Fallible<A> a;
        private final Fallible<B> b;
        private final Fallible<C> c;
        private final Fallible<D> d;
        private final Fallible<E> e;

        public FS5(Fallible<A> a, Fallible<B> b, Fallible<C> c, Fallible<D> d, Fallible<E> e) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.e = e;
        }

        public <F> Fallible<F> coalesce(FN5<A, B, C, D, E, F> coalesce) {
            return this.a.then((aValue) ->
                this.b.then((bValue) ->
                    this.c.then((cValue) ->
                        this.d.then((dValue) ->
                            this.e.map((eValue) -> coalesce.apply(aValue, bValue, cValue, dValue, eValue))
                        )
                    )
                )
            );
        }

        public <F> Fallible<F> coalescef(FN5<A, B, C, D, E, Fallible<F>> coalesce) {
            return this.a.then((aValue) ->
                this.b.then((bValue) ->
                    this.c.then((cValue) ->
                        this.d.then((dValue) ->
                            this.e.then((eValue) ->
                                coalesce.apply(aValue, bValue, cValue, dValue, eValue)
                            )
                        )
                    )
                )
            );
        }

        public <F> Fallible<F> coalesceo(FN5<A, B, C, D, E, Optional<F>> coalesce) {
            return this.a.then((aValue) ->
                this.b.then((bValue) ->
                    this.c.then((cValue) ->
                        this.d.then((dValue) ->
                            this.e.then((eValue) -> new OptOf<>(coalesce.apply(aValue, bValue, cValue, dValue, eValue)))
                        )
                    )
                )
            );
        }
    }
}
