package lorikeet.http;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UriPathTest {


    @Test
    public void testMatchesUri() {
        var msg = new MockIncomingHttpMsg("/orders/1475/parcels");

        boolean matched = new UriPath(msg, "/orders/{id}/parcels")
            .include()
            .success();

        assertThat(matched).isTrue();
    }

    @Test
    public void testNotMatchesUri() {
        var msg = new MockIncomingHttpMsg("/orders/1475/updates");

        boolean matched = new UriPath(msg, "/orders/{id}/parcels")
            .include()
            .success();

        assertThat(matched).isFalse();
    }

    @Test
    public void testInvalidUriPattern() {
        var msg = new MockIncomingHttpMsg("/orders/1475/updates");

        boolean matched = new UriPath(msg, "/orders/{id/parcels")
            .include()
            .success();

        assertThat(matched).isFalse();
    }
}