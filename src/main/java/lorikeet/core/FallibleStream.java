package lorikeet.core;

import lorikeet.core.stream.*;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

// @TODO rename this Formula
// @TODO rename include(...) to yield(...)
// @TODO add check()
public class FallibleStream<ErrorT extends Exception> {

    interface F1<T, ErrorT extends Exception> extends Supplier<FallibleResult<T, ErrorT>> {}
    interface O1<T> extends Supplier<Optional<T>> {}

    public <T> FS1<T, ErrorT> include(T value) {
        return new FS1<>(new OkResult<>(value));
    }

    public <T> FS1<T, ErrorT> include(FallibleResult<T, ErrorT> value) {
        return new FS1<>(value);
    }

//    public <T> FS1<T, ErrorT> include(Optional<T> value) {
//        return new FS1<>(new OptOf<>(value));
//    }

    public <T> FS1<T, ErrorT> include(Supplier<T> supplier) {
        return new FS1<>(new OkResult<>(supplier.get()));
    }

    public <T> FS1<T, ErrorT> include(Strop<T> includable) {
        return this.include(includable.include());
    }

    public <T> FS1<T, ErrorT> include(FStrop<T, ErrorT> includable) {
        return this.include(includable.include());
    }

    public <T> FS1<T, ErrorT> include(F1<T, ErrorT> supplier) {
        return this.include(supplier.get());
    }

//    public <T> FS1<T, ErrorT> include(O1<T> supplier) {
//        return this.include(supplier.get());
//    }

    public static class FS1<A, ErrorT extends Exception> {
        private final FallibleResult<A, ErrorT> a;

        public FS1(FallibleResult<A, ErrorT> a) {
            this.a = a;
        }

        public <B> FallibleResult<B, ErrorT> coalesce(Function<A, B> coalesce) {
            return this.a.map(coalesce::apply);
        }

        public <B> FallibleResult<B, ErrorT> coalescef(Function<A, FallibleResult<B, ErrorT>> coalesce) {
            return this.a.proceed(coalesce::apply);
        }

//        public <B> Fallible<B> coalesceo(Function<A, Optional<B>> coalesce) {
//            return this.a.then((aValue) -> new OptOf<>(coalesce.apply(aValue)));
//        }

        public <B> FS2<A, B, ErrorT> include(Strop<B> includable) {
            return this.include(new OkResult<>(includable.include()));
        }

        public <B> FS2<A, B, ErrorT> include(FStrop<B, ErrorT> includable) {
            return this.include(includable.include());
        }

        public <B> FS2<A, B, ErrorT> include(FallibleResult<B, ErrorT> value) {
            return new FS2<>(this.a, value);
        }
//
//        public <B> FS2<A, B, ErrorT> include(Optional<B> value) {
//            return new FS2<>(this.a, new OptOf<>(value));
//        }

        public <B> FS2<A, B, ErrorT> include(Supplier<B> supplier) {
            return this.include((a) -> supplier.get());
        }

        public <B> FS2<A, B, ErrorT> include(F1<B, ErrorT> supplier) {
            return this.includef((a) -> supplier.get());
        }

//        public <B> FS2<A, B> include(O1<B> supplier) {
//            return this.includeo((a) -> supplier.get());
//        }

        public <B> FS2<A, B, ErrorT> include(Function<A, B> func) {
            final FallibleResult<B, ErrorT> fallible = new FallibleStream<ErrorT>()
                .include(this.a)
                .coalesce(func);
            return new FS2<>(this.a, fallible);
        }

        public <B> FS2<A, B, ErrorT> includef(Function<A, FallibleResult<B, ErrorT>> func) {
            final FallibleResult<B, ErrorT> fallible = new FallibleStream<ErrorT>()
                .include(this.a)
                .coalescef(func);
            return new FS2<>(this.a, fallible);
        }

//        public <B> FS2<A, B, ErrorT> includeo(Function<A, Optional<B>> func) {
//            final Fallible<B> fallible = new FallibleStream()
//                .include(this.a)
//                .coalesceo(func);
//            return new FS2<>(this.a, fallible);
//        }
    }

    public static class FS2<A, B, ErrorT extends Exception> {
        private final FallibleResult<A, ErrorT> a;
        private final FallibleResult<B, ErrorT> b;

        FS2(FallibleResult<A, ErrorT> a, FallibleResult<B, ErrorT> b) {
            this.a = a;
            this.b = b;
        }

        public <C> FallibleResult<C, ErrorT> coalesce(BiFunction<A, B, C> coalesce) {
            return this.a.proceed((aValue) ->
                this.b.map((bValue) -> coalesce.apply(aValue, bValue))
            );
        }

        public <C> FallibleResult<C, ErrorT> coalescef(BiFunction<A, B, FallibleResult<C, ErrorT>> coalesce) {
            return this.a.proceed((aValue) ->
                this.b.proceed((bValue) -> coalesce.apply(aValue, bValue))
            );
        }
//
//        public <C> FallibleResult<C, ErrorT> coalesceo(BiFunction<A, B, Optional<C>> coalesce) {
//            return this.a.then((aValue) ->
//                this.b.then((bValue) -> new OptOf<>(coalesce.apply(aValue, bValue)))
//            );
//        }

        public <C> FS3<A, B, C, ErrorT> include(Strop<C> includable) {
            return this.include(new OkResult<>(includable.include()));
        }

        public <C> FS3<A, B, C, ErrorT> include(FStrop<C, ErrorT> includable) {
            return this.include(includable.include());
        }

        public <C> FS3<A, B, C, ErrorT> include(Strop1<A, C> include) {
            return this.include((BiFunction<A, B, C>)(a, b) -> include.include(a));
        }

        public <C> FS3<A, B, C, ErrorT> include(FStrop1<A, C, ErrorT> include) {
            return this.includef((a, b) -> include.include(a));
        }

        public <C> FS3<A, B, C, ErrorT> include(FStrop2<A, B, C, ErrorT> strop) {
            return this.includef(strop::include);
        }

        public <C> FS3<A, B, C, ErrorT> include(FallibleResult<C, ErrorT> value) {
            return new FS3<>(this.a, this.b, value);
        }

//        public <C> FS3<A, B, C> include(Optional<C> value) {
//            return new FS3<>(this.a, this.b, new OptOf<>(value));
//        }

        public <C> FS3<A, B, C, ErrorT> include(Supplier<C> supplier) {
            return this.include((BiFunction<A, B, C>)(a, b) -> supplier.get());
        }

        public <C> FS3<A, B, C, ErrorT> include(F1<C, ErrorT> supplier) {
            return this.includef((a, b) -> supplier.get());
        }
//
//        public <C> FS3<A, B, C> include(O1<C> supplier) {
//            return this.includeo((a, b) -> supplier.get());
//        }

        public <C> FS3<A, B, C, ErrorT> include(BiFunction<A, B, C> func) {
            final FallibleResult<C, ErrorT> fallible = new FallibleStream<ErrorT>()
                .include(this.a)
                .include(this.b)
                .coalesce(func);
            return new FS3<>(this.a, this.b, fallible);
        }

        public <C> FS3<A, B, C, ErrorT> includef(BiFunction<A, B, FallibleResult<C, ErrorT>> func) {
            final FallibleResult<C, ErrorT> fallible = new FallibleStream<ErrorT>()
                .include(this.a)
                .include(this.b)
                .coalescef(func);
            return new FS3<>(this.a, this.b, fallible);
        }

//        public <C> FS3<A, B, C, ErrorT> includeo(BiFunction<A, B, Optional<C>> func) {
//            final Fallible<C> fallible = new FallibleStream()
//                .include(this.a)
//                .include(this.b)
//                .coalesceo(func);
//            return new FS3<>(this.a, this.b, fallible);
//        }
    }

    public static class FS3<A, B, C, ErrorT extends Exception> {
        public interface FN3<A, B, C, D> {
            D apply(A a, B b, C c);
        }

        private final FallibleResult<A, ErrorT> a;
        private final FallibleResult<B, ErrorT> b;
        private final FallibleResult<C, ErrorT> c;

        FS3(FallibleResult<A, ErrorT> a, FallibleResult<B, ErrorT> b, FallibleResult<C, ErrorT> c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public <D> FallibleResult<D, ErrorT> coalesce(FN3<A, B, C, D> coalesce) {
            return this.a.proceed((aValue) ->
                this.b.proceed((bValue) ->
                    this.c.map((cValue) -> coalesce.apply(aValue, bValue, cValue))
                )
            );
        }

        public <D> FallibleResult<D, ErrorT> coalescef(FN3<A, B, C, FallibleResult<D, ErrorT>> coalesce) {
            return this.a.proceed((aValue) ->
                this.b.proceed((bValue) ->
                    this.c.proceed((cValue) -> coalesce.apply(aValue, bValue, cValue))
                )
            );
        }
//
//        public <D> Fallible<D> coalesceo(FN3<A, B, C, Optional<D>> coalesce) {
//            return this.a.then((aValue) ->
//                this.b.then((bValue) ->
//                    this.c.then((cValue) -> new OptOf<>(coalesce.apply(aValue, bValue, cValue)))
//                )
//            );
//        }

        public <D> FS4<A, B, C, D, ErrorT> include(Strop<D> includable) {
            return this.include(new OkResult<>(includable.include()));
        }

        public <D> FS4<A, B, C, D, ErrorT> include(FStrop<D, ErrorT> includable) {
            return this.include(includable.include());
        }

        public <D> FS4<A, B, C, D, ErrorT> include(Strop1<A, D> include) {
            return this.include((a, b, c) -> include.include(a));
        }

        public <D> FS4<A, B, C, D, ErrorT> include(FStrop1<A, D, ErrorT> include) {
            return this.includef((a, b, c) -> include.include(a));
        }

        public <D> FS4<A, B, C, D, ErrorT> include(FStrop2<A, B, D, ErrorT> strop) {
            return this.includef((a, b, c) -> strop.include(a, b));
        }

        public <D> FS4<A, B, C, D, ErrorT> include(FallibleResult<D, ErrorT> value) {
            return new FS4<>(this.a, this.b, this.c, value);
        }
//
//        public <D> FS4<A, B, C, D, ErrorT> include(Optional<D> value) {
//            return new FS4<>(this.a, this.b, this.c, new OptOf<>(value));
//        }

        public <D> FS4<A, B, C, D, ErrorT> include(Supplier<D> supplier) {
            return this.include((a, b, c) -> supplier.get());
        }

        public <D> FS4<A, B, C, D, ErrorT> include(F1<D, ErrorT> supplier) {
            return this.includef((a, b, c) -> supplier.get());
        }

//        public <D> FS4<A, B, C, D, ErrorT> include(O1<D> supplier) {
//            return this.includeo((a, b, c) -> supplier.get());
//        }

        public <D> FS4<A, B, C, D, ErrorT> include(FN3<A, B, C, D> func) {
            final FallibleResult<D, ErrorT> fallible = new FallibleStream<ErrorT>()
                .include(this.a)
                .include(this.b)
                .include(this.c)
                .coalesce(func);
            return new FS4<>(this.a, this.b, this.c, fallible);
        }

        public <D> FS4<A, B, C, D, ErrorT> includef(FN3<A, B, C, FallibleResult<D, ErrorT>> func) {
            final FallibleResult<D, ErrorT> fallible = new FallibleStream<ErrorT>()
                .include(this.a)
                .include(this.b)
                .include(this.c)
                .coalescef(func);
            return new FS4<>(this.a, this.b, this.c, fallible);
        }

//        public <D> FS4<A, B, C, D, ErrorT> includeo(FN3<A, B, C, Optional<D>> func) {
//            final Fallible<D> fallible = new FallibleStream()
//                .include(this.a)
//                .include(this.b)
//                .include(this.c)
//                .coalesceo(func);
//            return new FS4<>(this.a, this.b, this.c, fallible);
//        }
    }

    public static class FS4<A, B, C, D, ErrorT extends Exception> {
        public interface FN4<A, B, C, D, E> {
            E apply(A a, B b, C c, D d);
        }

        private final FallibleResult<A, ErrorT> a;
        private final FallibleResult<B, ErrorT> b;
        private final FallibleResult<C, ErrorT> c;
        private final FallibleResult<D, ErrorT> d;

        FS4(
            FallibleResult<A, ErrorT> a,
            FallibleResult<B, ErrorT> b,
            FallibleResult<C, ErrorT> c,
            FallibleResult<D, ErrorT> d
        ) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        public <E> FallibleResult<E, ErrorT> coalesce(FN4<A, B, C, D, E> coalesce) {
            return this.a.proceed((aValue) ->
                this.b.proceed((bValue) ->
                    this.c.proceed((cValue) ->
                        this.d.map((dValue) -> coalesce.apply(aValue, bValue, cValue, dValue))
                    )
                )
            );
        }

        public <E> FallibleResult<E, ErrorT> coalescef(FN4<A, B, C, D, FallibleResult<E, ErrorT>> coalesce) {
            return this.a.proceed((aValue) ->
                this.b.proceed((bValue) ->
                    this.c.proceed((cValue) ->
                        this.d.proceed((dValue) -> coalesce.apply(aValue, bValue, cValue, dValue))
                    )
                )
            );
        }

//        public <E> Fallible<E> coalesceo(FN4<A, B, C, D, Optional<E>> coalesce) {
//            return this.a.then((aValue) ->
//                this.b.then((bValue) ->
//                    this.c.then((cValue) ->
//                        this.d.then((dValue) -> new OptOf<>(coalesce.apply(aValue, bValue, cValue, dValue)))
//                    )
//                )
//            );
//        }

        public <E> FS5<A, B, C, D, E, ErrorT> include(Strop<E> includable) {
            return this.include(new OkResult<>(includable.include()));
        }

        public <E> FS5<A, B, C, D, E, ErrorT> include(FStrop<E, ErrorT> includable) {
            return this.include(includable.include());
        }

        public <E> FS5<A, B, C, D, E, ErrorT> include(FallibleResult<E, ErrorT> value) {
            return new FS5<>(this.a, this.b, this.c, this.d, value);
        }

        public <E> FS5<A, B, C, D, E, ErrorT> include(Strop1<A, E> include) {
            return this.include((a, b, c, d) -> include.include(a));
        }

        public <E> FS5<A, B, C, D, E, ErrorT> include(FStrop1<A, E, ErrorT> include) {
            return this.includef((a, b, c, e) -> include.include(a));
        }

        public <E> FS5<A, B, C, D, E, ErrorT> include(FStrop2<A, B, E, ErrorT> strop) {
            return this.includef((a, b, c, d) -> strop.include(a, b));
        }


//        public <E> FS5<A, B, C, D, E> include(Optional<E> value) {
//            return new FS5<>(this.a, this.b, this.c, this.d, new OptOf<>(value));
//        }

        public <E> FS5<A, B, C, D, E, ErrorT> include(Supplier<E> supplier) {
            return this.include((a, b, c, d) -> supplier.get());
        }

        public <E> FS5<A, B, C, D, E, ErrorT> include(F1<E, ErrorT> supplier) {
            return this.includef((a, b, c, d) -> supplier.get());
        }

//        public <E> FS5<A, B, C, D, E> include(O1<E> supplier) {
//            return this.includeo((a, b, c, d) -> supplier.get());
//        }

        public <E> FS5<A, B, C, D, E, ErrorT> include(FN4<A, B, C, D, E> func) {
            final FallibleResult<E, ErrorT> fallible = new FallibleStream<ErrorT>()
                .include(this.a)
                .include(this.b)
                .include(this.c)
                .include(this.d)
                .coalesce(func);
            return new FS5<>(this.a, this.b, this.c, this.d, fallible);
        }

        public <E> FS5<A, B, C, D, E, ErrorT> includef(FN4<A, B, C, D, FallibleResult<E, ErrorT>> func) {
            final FallibleResult<E, ErrorT> fallible = new FallibleStream<ErrorT>()
                .include(this.a)
                .include(this.b)
                .include(this.c)
                .include(this.d)
                .coalescef(func);
            return new FS5<>(this.a, this.b, this.c, this.d, fallible);
        }

//        public <E> FS5<A, B, C, D, E> includeo(FN4<A, B, C, D, Optional<E>> func) {
//            final Fallible<E> fallible = new FallibleStream()
//                .include(this.a)
//                .include(this.b)
//                .include(this.c)
//                .include(this.d)
//                .coalesceo(func);
//            return new FS5<>(this.a, this.b, this.c, this.d, fallible);
//        }
    }

    public static class FS5<A, B, C, D, E, ErrorT extends Exception> {
        interface FN5<A, B, C, D, E, F> {
            F apply(A a, B b, C c, D d, E e);
        }

        private final FallibleResult<A, ErrorT> a;
        private final FallibleResult<B, ErrorT> b;
        private final FallibleResult<C, ErrorT> c;
        private final FallibleResult<D, ErrorT> d;
        private final FallibleResult<E, ErrorT> e;

        public FS5(
            FallibleResult<A, ErrorT> a,
            FallibleResult<B, ErrorT> b,
            FallibleResult<C, ErrorT> c,
            FallibleResult<D, ErrorT> d,
            FallibleResult<E, ErrorT> e
        ) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.e = e;
        }

        public <F> FallibleResult<F, ErrorT> coalesce(FN5<A, B, C, D, E, F> coalesce) {
            return this.a.proceed((aValue) ->
                this.b.proceed((bValue) ->
                    this.c.proceed((cValue) ->
                        this.d.proceed((dValue) ->
                            this.e.map((eValue) -> coalesce.apply(aValue, bValue, cValue, dValue, eValue))
                        )
                    )
                )
            );
        }

        public <F> FallibleResult<F, ErrorT> coalescef(FN5<A, B, C, D, E, FallibleResult<F, ErrorT>> coalesce) {
            return this.a.proceed((aValue) ->
                this.b.proceed((bValue) ->
                    this.c.proceed((cValue) ->
                        this.d.proceed((dValue) ->
                            this.e.proceed((eValue) ->
                                coalesce.apply(aValue, bValue, cValue, dValue, eValue)
                            )
                        )
                    )
                )
            );
        }
//
//        public <F> Fallible<F> coalesceo(FN5<A, B, C, D, E, Optional<F>> coalesce) {
//            return this.a.then((aValue) ->
//                this.b.then((bValue) ->
//                    this.c.then((cValue) ->
//                        this.d.then((dValue) ->
//                            this.e.then((eValue) -> new OptOf<>(coalesce.apply(aValue, bValue, cValue, dValue, eValue)))
//                        )
//                    )
//                )
//            );
//        }
    }
}
