package lorikeet;

public class Expr {

    public static <A,B> Err<B> weave(Err<A> aErr, Fun1<A, Err<B>> bFun) {
        if (aErr.failed()) {
            return Err.of(aErr);
        }

        return bFun.apply(aErr.orPanic());
    }

    public static <A,B,C> Err<C> weave(Err<A> aErr, Fun1<A, Err<B>> bFun, Fun2<A, B, Err<C>> cFun) {
        if (aErr.failed()) {
            return Err.of(aErr);
        }

        final Err<B> bErr =  bFun.apply(aErr.orPanic());
        if (bErr.failed()) {
            return Err.of(bErr);
        }

        return cFun.apply(aErr.orPanic(), bErr.orPanic());
    }

    public static <A,B,C,D> Err<D> weave(
        Err<A> aErr,
        Fun1<A, Err<B>> bFun,
        Fun2<A, B, Err<C>> cFun,
        Fun3<A, B, C, Err<D>> dFun
    ) {
        if (aErr.failed()) {
            return Err.of(aErr);
        }

        final Err<B> bErr =  bFun.apply(aErr.orPanic());
        if (bErr.failed()) {
            return Err.of(bErr);
        }

        final Err<C> cErr = cFun.apply(aErr.orPanic(), bErr.orPanic());
        if (cErr.failed()) {
            return Err.of(cErr);
        }

        return dFun.apply(aErr.orPanic(), bErr.orPanic(), cErr.orPanic());
    }

}
