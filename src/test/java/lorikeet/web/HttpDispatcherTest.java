package lorikeet.web;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpDispatcherTest {

    @Test
    public void testRootPath() {
        HttpDispatcher dispatcher = new HttpDispatcher()
            .serve("foo")
            .serve("bar");

        assertThat(dispatcher.getMappings()).hasSize(2);
        assertThat(dispatcher.getMappings()).contains(new Mapping("/foo"), new Mapping("/bar"));
    }

    @Test
    public void testSubPath() {
        HttpDispatcher dispatcher = new HttpDispatcher()
            .serve("foo")
            .path("/bar")
                .serve("/baz");

        assertThat(dispatcher.getMappings()).hasSize(2);
        assertThat(dispatcher.getMappings()).contains(new Mapping("/foo"), new Mapping("/bar/baz"));
    }

    @Test
    public void testMultipleSubPath() {
        HttpDispatcher dispatcher = new HttpDispatcher()
            .serve("foo")
            .path("/bar")
                .serve("/baz")
                .serve("baz2")
                .done()
            .serve("abba")
            .path("/bam")
                .serve("/bom");

        assertThat(dispatcher.getMappings()).hasSize(5);
        assertThat(dispatcher.getMappings()).contains(
            new Mapping("/foo"),
            new Mapping("/bar/baz"),
            new Mapping("/bar/baz2"),
            new Mapping("/abba"),
            new Mapping("/bam/bom")
        );
    }
}