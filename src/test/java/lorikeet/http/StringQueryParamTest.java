package lorikeet.http;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringQueryParamTest {

    @Test
    public void testNotFound() {
        IncomingHttpSgnl request = new MockIncomingHttpMsg("/test?name=foo");

        boolean failed = new StringQueryParam(request, "surname")
            .include()
            .failure();

        assertThat(failed).isTrue();
    }

    @Test
    public void testValid() {
        IncomingHttpSgnl request = new MockIncomingHttpMsg("/test?name=foo");

        String value = new StringQueryParam(request, "name")
            .include()
            .orPanic();

        assertThat(value).isEqualTo("foo");
    }
}