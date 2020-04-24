package lorikeet.http;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UriPathTest {


    @Test
    public void testMatchesUri() {
        var request = new MockIncomingHttpSgnl("/orders/1475/parcels");

        boolean matched = new UriPath("/orders/{id}/parcels")
            .include(request)
            .success();

        assertThat(matched).isTrue();
    }

    @Test
    public void testNotMatchesUri() {
        var request = new MockIncomingHttpSgnl("/orders/1475/updates");

        boolean matched = new UriPath("/orders/{id}/parcels")
            .include(request)
            .success();

        assertThat(matched).isFalse();
    }

    @Test
    public void testInvalidUriPattern() {
        var request = new MockIncomingHttpSgnl("/orders/1475/updates");

        boolean matched = new UriPath("/orders/{id/parcels")
            .include(request)
            .success();

        assertThat(matched).isFalse();
    }
}