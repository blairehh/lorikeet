package lorikeet.core;

import lorikeet.http.error.HttpMethodDoesNotMatchRequest;
import org.junit.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class FallibleTest {

    private static Err<String> ERR = new Err<>(new RuntimeException());
    private static Ok<String> OK = new Ok<>("hello");

    @Test
    public void testSuccessAndFailure() {
        assertThat(OK.success()).isTrue();
        assertThat(OK.failure()).isFalse();

        assertThat(ERR.success()).isFalse();
        assertThat(ERR.failure()).isTrue();
    }

    @Test
    public void testOkDoesNotPanic() {
        assertThat(new Ok<>("hello").orPanic()).isEqualTo("hello");
    }

    @Test(expected = RuntimeException.class)
    public void testErrDoesPanic() {
        new Err<>(new RuntimeException()).orPanic();
    }


    @Test
    public void testOkGivesOutput() {
        assertThat(new Ok<>("hello").orGive("good-bye")).isEqualTo("hello");
        assertThat(new Ok<>("hello").orGive(() -> "good-bye")).isEqualTo("hello");
        assertThat(new Ok<>("hello").orGive((e) -> "good-bye")).isEqualTo("hello");
    }

    @Test
    public void testErrGivesAlternate() {
        assertThat(ERR.orGive("good-bye")).isEqualTo("good-bye");
        assertThat(ERR.orGive(() -> "good-bye")).isEqualTo("good-bye");
        assertThat(ERR.orGive((e) -> "good-bye")).isEqualTo("good-bye");
    }

    @Test
    public void testOkMapsOutput() {
        assertThat(new Ok<>(77).map(Objects::toString)).isEqualTo(new Ok<>("77"));
    }

    @Test
    public void testErrMapReturnsErr() {
        assertThat(ERR.map(String::toLowerCase).failure()).isTrue();
    }

    @Test
    public void testOkInvokesOperation() {
        assertThat(new Ok<>(77).then(number -> new Ok<>(number.toString()))).isEqualTo(new Ok<>("77"));
    }

    @Test
    public void testErrThenReturnsErr() {
        assertThat(ERR.then(str -> new Ok<>(str.toLowerCase())).failure()).isTrue();
    }
}