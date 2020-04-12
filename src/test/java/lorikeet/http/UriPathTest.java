package lorikeet.http;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UriPathTest {


    @Test
    public void testMatchesUri() {
        var request = new MockIncomingHttpMsg("/orders/1475/parcels");

        boolean matched = new UriPath(request, "/orders/{id}/parcels")
            .include()
            .success();

        assertThat(matched).isTrue();
    }

    @Test
    public void testNotMatchesUri() {
        var request = new MockIncomingHttpMsg("/orders/1475/updates");

        boolean matched = new UriPath(request, "/orders/{id}/parcels")
            .include()
            .success();

        assertThat(matched).isFalse();
    }

    @Test
    public void testInvalidUriPattern() {
        var request = new MockIncomingHttpMsg("/orders/1475/updates");

        boolean matched = new UriPath(request, "/orders/{id/parcels")
            .include()
            .success();

        assertThat(matched).isFalse();
    }
}