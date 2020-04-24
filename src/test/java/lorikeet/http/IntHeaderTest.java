package lorikeet.http;


import lorikeet.core.DictOf;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IntHeaderTest {
    private final IncomingHttpSgnl sgnl = new MockIncomingHttpSgnl(
        "/test", new DictOf<String, String>().push("good", "1").push("bad", "a")
    );

    @Test
    public void testNotFound() {
        final IntHeader notFound = new IntHeader("not-there");

        assertThat(notFound.include(sgnl).failure()).isTrue();
    }

    @Test
    public void testBadValue() {
        final IntHeader notFound = new IntHeader("bad");

        assertThat(notFound.include(sgnl).failure()).isTrue();
    }

    @Test
    public void testIsPresent() {
        final IntHeader notFound = new IntHeader("good");

        assertThat(notFound.include(sgnl).orPanic()).isEqualTo(1);
    }

    @Test
    public void testUsesDefaultIfNotPresent() {
        final IntHeader notFound = new IntHeader("not-here-but-default", 100);

        assertThat(notFound.include(sgnl).orPanic()).isEqualTo(100);
    }

    @Test
    public void testDoesNotUseDefaultIfBadValue() {
        final IntHeader notFound = new IntHeader("bad", 100);

        assertThat(notFound.include(sgnl).failure()).isTrue();
    }
}