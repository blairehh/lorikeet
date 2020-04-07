package lorikeet.http;

import lorikeet.core.DictOf;
import lorikeet.lobe.IncomingHttpMsg;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BoolHeaderTest {

    private final IncomingHttpMsg msg = new MockIncomingHttpMsg(
        "/test", new DictOf<String, String>().push("good", "true").push("bad", "falsk")
    );

    @Test
    public void testNotFound() {
        final BoolHeader notFound = new BoolHeader(msg, "not-there");

        assertThat(notFound.include().failure()).isTrue();
    }

    @Test
    public void testBadValue() {
        final BoolHeader notFound = new BoolHeader(msg, "bad");

        assertThat(notFound.include().failure()).isTrue();
    }

    @Test
    public void testIsPresent() {
        final BoolHeader notFound = new BoolHeader(msg, "good");

        assertThat(notFound.include().orPanic()).isTrue();
    }

    @Test
    public void testUsesDefaultIfNotPresent() {
        final BoolHeader notFound = new BoolHeader(msg, "not-here-but-default", false);

        assertThat(notFound.include().orPanic()).isFalse();
    }

    @Test
    public void testDoesNotUseDefaultIfBadValue() {
        final BoolHeader notFound = new BoolHeader(msg, "bad", true);

        assertThat(notFound.include().failure()).isTrue();
    }
}