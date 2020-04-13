package lorikeet.http;

import lorikeet.core.DictOf;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BoolHeaderTest {

    private final IncomingHttpSgnl sgnl = new MockIncomingHttpSgnl(
        "/test", new DictOf<String, String>().push("good", "true").push("bad", "falsk")
    );

    @Test
    public void testNotFound() {
        final BoolHeader notFound = new BoolHeader(sgnl, "not-there");

        assertThat(notFound.include().failure()).isTrue();
    }

    @Test
    public void testBadValue() {
        final BoolHeader notFound = new BoolHeader(sgnl, "bad");

        assertThat(notFound.include().failure()).isTrue();
    }

    @Test
    public void testIsPresent() {
        final BoolHeader notFound = new BoolHeader(sgnl, "good");

        assertThat(notFound.include().orPanic()).isTrue();
    }

    @Test
    public void testUsesDefaultIfNotPresent() {
        final BoolHeader notFound = new BoolHeader(sgnl, "not-here-but-default", false);

        assertThat(notFound.include().orPanic()).isFalse();
    }

    @Test
    public void testDoesNotUseDefaultIfBadValue() {
        final BoolHeader notFound = new BoolHeader(sgnl, "bad", true);

        assertThat(notFound.include().failure()).isTrue();
    }
}