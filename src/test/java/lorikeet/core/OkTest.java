package lorikeet.core;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OkTest {

    @Test
    public void testOrPanic() {
        assertThat(new Ok<>(23).orPanic()).isEqualTo(23);
    }

    @Test
    public void test() {
        new Ok<>(23)
            .onSuccess((number) -> System.out.println(number));

        Fallible<Integer> f = new Ok<>(78);
        f.onSuccess(i -> System.out.println(i))
            .onFailure(i -> System.out.println(i));
    }
}