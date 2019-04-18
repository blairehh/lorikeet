package lorikeet.web;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WebDispatcherTest {

    static WebEndpoint endpoint = (request, response) -> null;

    @Test
    public void testRootPath() {
        WebDispatcher dispatcher = new WebDispatcher()
            .get("foo", endpoint)
            .post("bar", endpoint);

        assertThat(dispatcher.getMappings()).hasSize(2);
        assertThat(dispatcher.getMappings()).contains(
            new Mapping(HttpMethod.GET, "/foo", endpoint),
            new Mapping(HttpMethod.POST, "/bar", endpoint)
        );
    }

    @Test
    public void testSubPath() {
        WebDispatcher dispatcher = new WebDispatcher()
            .get("foo", endpoint)
            .path("/bar")
                .put("/baz", endpoint);

        assertThat(dispatcher.getMappings()).hasSize(2);
        assertThat(dispatcher.getMappings()).contains(
            new Mapping(HttpMethod.GET, "/foo", endpoint),
            new Mapping(HttpMethod.PUT, "/bar/baz", endpoint)
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

        assertThat(dispatcher.getMappings()).hasSize(5);
        assertThat(dispatcher.getMappings()).contains(
            new Mapping(HttpMethod.GET,"/foo", endpoint),
            new Mapping(HttpMethod.POST,"/bar/baz", endpoint),
            new Mapping(HttpMethod.PUT,"/bar/baz2", endpoint),
            new Mapping(HttpMethod.DELETE,"/abba", endpoint),
            new Mapping(HttpMethod.GET,"/bam/bom", endpoint)
        );
    }
}