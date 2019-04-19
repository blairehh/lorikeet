package lorikeet.web;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WebDispatcherTest {

    static IncomingRequestHandler endpoint = (request, response) -> {};

    @Test
    public void testSingleLevel() {
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
            .path("/")
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

    @Test
    public void testDeepPath() {
        WebDispatcher dispatcher = new WebDispatcher()
            .get("foo", endpoint)
            .path("/bar")
                .post("/baz", endpoint)
                .put("baz2", endpoint)
            .path("/a/b/c/d/e/f/g/h/")
                .get("1", endpoint)
                .get("2", endpoint);

        assertThat(dispatcher.getEndpoints()).hasSize(5);
        assertThat(dispatcher.getEndpoints()).contains(
            new WebEndpoint(HttpMethod.GET,"/foo", endpoint),
            new WebEndpoint(HttpMethod.POST,"/bar/baz", endpoint),
            new WebEndpoint(HttpMethod.PUT,"/bar/baz2", endpoint),
            new WebEndpoint(HttpMethod.GET,"/a/b/c/d/e/f/g/h/1", endpoint),
            new WebEndpoint(HttpMethod.GET,"/a/b/c/d/e/f/g/h/2", endpoint)
        );
    }

    @Test
    public void testRootPath() {
        WebDispatcher dispatcher = new WebDispatcher()
            .get("foo", endpoint)
            .get(endpoint)
            .path("/bar")
                .put("/baz", endpoint)
                .post(endpoint);

        assertThat(dispatcher.getEndpoints()).hasSize(4);
        assertThat(dispatcher.getEndpoints()).contains(
            new WebEndpoint(HttpMethod.GET, "/foo", endpoint),
            new WebEndpoint(HttpMethod.PUT, "/bar/baz", endpoint),
            new WebEndpoint(HttpMethod.GET, "/", endpoint),
            new WebEndpoint(HttpMethod.POST, "/bar", endpoint)
        );
    }

}