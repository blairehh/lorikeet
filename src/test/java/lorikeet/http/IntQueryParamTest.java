package lorikeet.http;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IntQueryParamTest {

    @Test
    public void testValidNumber() {
        MockIncomingHttpSgnl incoming = new MockIncomingHttpSgnl("/test?max=456");

        final int max = new IntQueryParam(incoming, "max")
            .include()
            .orPanic();

        assertThat(max).isEqualTo(456);
    }

    @Test
    public void testGetsFirstValueIfMultipleFound() {
        MockIncomingHttpSgnl incoming = new MockIncomingHttpSgnl("/test?max=100&max=101");

        final int max = new IntQueryParam(incoming, "max")
            .include()
            .orPanic();

        assertThat(max).isEqualTo(100);
    }


    @Test
    public void testInvalidNumber() {
        MockIncomingHttpSgnl incoming = new MockIncomingHttpSgnl("/test?max=4a");

        boolean failed = new IntQueryParam(incoming, "max")
            .include()
            .failure();

        assertThat(failed).isTrue();
    }

    @Test
    public void testQueryParamNameNotFound() {
        MockIncomingHttpSgnl incoming = new MockIncomingHttpSgnl("/test?max=100");

        boolean failed = new IntQueryParam(incoming, "min")
            .include()
            .failure();

        assertThat(failed).isTrue();
    }
}