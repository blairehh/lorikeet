package lorikeet.http;


import lorikeet.core.DictOf;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IntHeaderTest {
    private final IncomingHttpSgnl msg = new MockIncomingHttpMsg(
        "/test", new DictOf<String, String>().push("good", "1").push("bad", "a")
    );

    @Test
    public void testNotFound() {
        final IntHeader notFound = new IntHeader(msg, "not-there");

        assertThat(notFound.include().failure()).isTrue();
    }

    @Test
    public void testBadValue() {
        final IntHeader notFound = new IntHeader(msg, "bad");

        assertThat(notFound.include().failure()).isTrue();
    }

    @Test
    public void testIsPresent() {
        final IntHeader notFound = new IntHeader(msg, "good");

        assertThat(notFound.include().orPanic()).isEqualTo(1);
    }

    @Test
    public void testUsesDefaultIfNotPresent() {
        final IntHeader notFound = new IntHeader(msg, "not-here-but-default", 100);

        assertThat(notFound.include().orPanic()).isEqualTo(100);
    }

    @Test
    public void testDoesNotUseDefaultIfBadValue() {
        final IntHeader notFound = new IntHeader(msg, "bad", 100);

        assertThat(notFound.include().failure()).isTrue();
    }
}