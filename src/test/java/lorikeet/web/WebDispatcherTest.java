package lorikeet.web;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WebDispatcherTest {

    static IncomingRequestHandler endpoint = (request, response) -> null;

    @Test
    public void testRootPath() {
        WebDispatcher dispatcher = new WebDispatcher()
            .get("foo", endpoint)
            .post("bar", endpoint);

        assertThat(dispatcher.getEndpoints()).hasSize(2);
        assertThat(dispatcher.getEndpoints()).contains(
            new WebEndpoint(HttpMethod.GET, "/foo", endpoint),
            new WebEndpoint(HttpMethod.POST, "/bar", endpoint)
        );
    }

    @Test
    public void testSubPath() {
        WebDispatcher dispatcher = new WebDispatcher()
            .get("foo", endpoint)
            .path("/bar")
                .put("/baz", endpoint);

        assertThat(dispatcher.getEndpoints()).hasSize(2);
        assertThat(dispatcher.getEndpoints()).contains(
            new WebEndpoint(HttpMethod.GET, "/foo", endpoint),
            new WebEndpoint(HttpMethod.PUT, "/bar/baz", endpoint)
        );
    }

    @Test
    public void testMultipleSubPath() {
        WebDispatcher dispatcher = new WebDispatcher()
            .get("foo", endpoint)
            .path("/bar")
                .post("/baz", endpoint)
                .put("baz2", endpoint)
                .done()
            .delete("abba", endpoint)
            .path("/bam")
                .get("/bom", endpoint);

        assertThat(dispatcher.getEndpoints()).hasSize(5);
        assertThat(dispatcher.getEndpoints()).contains(
            new WebEndpoint(HttpMethod.GET,"/foo", endpoint),
            new WebEndpoint(HttpMethod.POST,"/bar/baz", endpoint),
            new WebEndpoint(HttpMethod.PUT,"/bar/baz2", endpoint),
            new WebEndpoint(HttpMethod.DELETE,"/abba", endpoint),
            new WebEndpoint(HttpMethod.GET,"/bam/bom", endpoint)
        );
    }
}