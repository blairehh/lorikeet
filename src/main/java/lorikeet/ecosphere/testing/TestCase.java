package lorikeet.ecosphere.testing;


import lorikeet.ecosphere.Action1;

import java.util.function.Supplier;

public class TestCase<T> {

    private final Supplier<T> executor;

    public TestCase(Supplier<T> executor) {
        this.executor = executor;
    }

    public static <R, P1> TestCase<R> test(Action1<R, P1> action, P1 parameter) {
        return new TestCase<>(() -> action.invoke(parameter));
    }

    public T execute() {
        return this.executor.get();
    }

}
