package lorikeet.core;

import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class FallibleStreamTest {


    @Test
    public void testYieldOkCoalesce() {
        var r = new FallibleStream()
            .include(new Ok<>(2))
            .coalesce((num) -> num * 2);

        assertThat(r.orPanic()).isEqualTo(4);
    }

    @Test
    public void testYieldErrCoalesce() {
        AtomicInteger counter = new AtomicInteger(0);
        var r = new FallibleStream()
            .include(new Err<Integer>(new RuntimeException()))
            .coalesce((num) -> {
                counter.incrementAndGet();
                return num * 2;
            });

        assertThat(r.failure()).isTrue();
        assertThat(counter.get()).isEqualTo(0);
    }

    @Test
    public void testYieldOptionalCoalesce() {
        var r = new FallibleStream()
            .include(Optional.of(2))
            .coalesce((num) -> num * 2);

        assertThat(r.orPanic()).isEqualTo(4);
    }

    @Test
    public void testCoalesceFallibleOk() {
        var r = new FallibleStream()
            .include(Optional.of(2))
            .coalescef((num) -> new Ok<>(num * 2));

        assertThat(r.orPanic()).isEqualTo(4);
    }

    @Test
    public void testCoalesceFallibleErr() {
        var r = new FallibleStream()
            .include(Optional.of(2))
            .coalescef((num) -> new Err<>(new RuntimeException()));

        assertThat(r.failure()).isTrue();
    }

    @Test
    public void testCoalesceOptionalOf() {
        var r = new FallibleStream()
            .include(Optional.of(2))
            .coalesceo((num) -> Optional.of(num * 2));

        assertThat(r.orPanic()).isEqualTo(4);
    }

    @Test
    public void testCoalesceOptionalEmpty() {
        var r = new FallibleStream()
            .include(Optional.of(2))
            .coalesceo((num) -> Optional.empty());

        assertThat(r.failure()).isTrue();
    }

    @Test
    public void testCoalesce2FallibleOk() {
        var r = new FallibleStream()
            .include(new Ok<>(4))
            .include((a) -> a * 2)
            .coalesce(Integer::sum)
            .orPanic();

        assertThat(r).isEqualTo(12);
    }

    @Test
    public void testCoalesce2FallibleErr() {
        var r = new FallibleStream()
            .include(new Ok<>(4))
            .include((a) -> a * 2)
            .coalescef((a, b) -> new Err<Integer>(new RuntimeException()))
            .failure();

        assertThat(r).isTrue();
    }

    @Test
    public void testCoalesce2OptionalOf() {
        var r = new FallibleStream()
            .include(new Ok<>(4))
            .include((a) -> a * 2)
            .coalesceo((a, b) -> Optional.of(a + b))
            .orPanic();

        assertThat(r).isEqualTo(12);
    }

    @Test
    public void testInclude3() {
        var r = new FallibleStream()
            .include(new Ok<>(4))
            .include((a) -> a * 2)
            .includef((a, b) -> new Ok<>(a + b))
            .coalescef((a, b, c) -> new Ok<>(a + b + c))
            .orPanic();

        assertThat(r).isEqualTo(24);
    }

    @Test
    public void testInclude3CoalesceValue() {
        var r = new FallibleStream()
            .include(new Ok<>(4))
            .includeo((a) -> Optional.of(a * 2))
            .includef((a, b) -> new Ok<>(a + b))
            .coalesce((a, b, c) -> a + b + c)
            .orPanic();

        assertThat(r).isEqualTo(24);
    }
}