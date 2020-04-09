package lorikeet.http;

import lorikeet.lobe.IncomingHttpMsg;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringQueryParamTest {

    @Test
    public void testNotFound() {
        IncomingHttpMsg msg = new MockIncomingHttpMsg("/test?name=foo");

        boolean failed = new StringQueryParam(msg, "surname")
            .include()
            .failure();

        assertThat(failed).isTrue();
    }

    @Test
    public void testValid() {
        IncomingHttpMsg msg = new MockIncomingHttpMsg("/test?name=foo");

        String value = new StringQueryParam(msg, "name")
            .include()
            .orPanic();

        assertThat(value).isEqualTo("foo");
    }
}