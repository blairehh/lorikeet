package lorikeet.http;

import lorikeet.core.DictOf;
import lorikeet.http.error.MsgTypeDidNotHaveAnnotatedCtor;
import lorikeet.http.error.UnsupportedHeaderValueType;
import lorikeet.lobe.IncomingHttpMsg;
import org.junit.Test;

import javax.ws.rs.HeaderParam;
import java.net.URI;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SingleHeader {
    final String name;

    @MsgCtor
    public SingleHeader(@HeaderParam("name") String name) {
        this.name = name;
    }
}

class UnsupportedHeaderType {
    final Random name;

    @MsgCtor
    public UnsupportedHeaderType(@HeaderParam("name") Random name) {
        this.name = name;
    }
}

class SingleHeaderNoCtor {
    final String name;

    public SingleHeaderNoCtor(@HeaderParam("name") String name) {
        this.name = name;
    }
}


class MultiHeader {
    final String name;
    final Integer limit;
    final Boolean active;
    final Double score;

    @MsgCtor
    public MultiHeader(
        @HeaderParam("name") String name,
        @HeaderParam("limit") Integer limit,
        @HeaderParam("active") Boolean active,
        @HeaderParam("score") Double score
    ) {
        this.name = name;
        this.limit = limit;
        this.active = active;
        this.score = score;
    }
}


class MultiHeaderWithCustomAnnotationAndPrimitives {
    final String name;
    final int limit;
    final boolean active;
    final double score;

    @MsgCtor
    public MultiHeaderWithCustomAnnotationAndPrimitives(
        @Header("name") String name,
        @Header("limit") int limit,
        @Header("active") boolean active,
        @Header("score") double score
    ) {
        this.name = name;
        this.limit = limit;
        this.active = active;
        this.score = score;
    }
}

class BadHeaderValue {
    final Integer number;

    @MsgCtor
    public BadHeaderValue(@HeaderParam("bad-num") int number) {
        this.number = number;
    }
}

class PathField {
    final URI uri;

    @MsgCtor
    public PathField(@Path("/user/{id}") URI uri) {
        this.uri = uri;
    }
}

class PathFieldAsString {
    final String uri;

    @MsgCtor
    public PathFieldAsString(@Path("/user/{id}") String uri) {
        this.uri = uri;
    }
}

class PathFieldInvalidType {
    final UUID uri;

    @MsgCtor
    public PathFieldInvalidType(@Path("/user/{id}") UUID uri) {
        this.uri = uri;
    }
}


public class HttpMsgOfTest {

    private final IncomingHttpMsg incoming = new MockIncomingHttpMsg(
        "/user/786",
        new DictOf<String, String>()
            .push("name", "Bob Doe")
            .push("limit", "10")
            .push("active", "false")
            .push("score", "34.64")
            .push("bad-num", "1a")
    );

    @Test
    public void testInitiateWithOneHeader() {
        SingleHeader msg = new HttpMsgOf<>(incoming, SingleHeader.class)
            .include()
            .orPanic();

        assertThat(msg.name).isEqualTo("Bob Doe");
    }

    @Test
    public void testTypeMustHaveMsgCtor() {
        Exception error = new HttpMsgOf<>(incoming, SingleHeaderNoCtor.class)
            .include()
            .errors()
            .first()
            .orElseThrow();

        assertThat(error.getClass()).isEqualTo(MsgTypeDidNotHaveAnnotatedCtor.class);
    }

    @Test
    public void testRejectsUnsupportedHeaderType() {
        Exception error = new HttpMsgOf<>(incoming, UnsupportedHeaderType.class)
            .include()
            .errors()
            .first()
            .orElseThrow();

        assertThat(error.getClass()).isEqualTo(UnsupportedHeaderValueType.class);
    }

    @Test
    public void testWithMultipleHeaders() {
        MultiHeader msg = new HttpMsgOf<>(incoming, MultiHeader.class)
            .include()
            .orPanic();

        assertThat(msg.name).isEqualTo("Bob Doe");
        assertThat(msg.limit).isEqualTo(10);
        assertThat(msg.active).isFalse();
        assertThat(msg.score).isEqualTo(34.64);
    }

    @Test
    public void testWithMultipleHeadersWithCustomAnnotationAndPrimitives() {
        MultiHeaderWithCustomAnnotationAndPrimitives msg = new HttpMsgOf<>(
            incoming, MultiHeaderWithCustomAnnotationAndPrimitives.class
        )
            .include()
            .orPanic();

        assertThat(msg.name).isEqualTo("Bob Doe");
        assertThat(msg.limit).isEqualTo(10);
        assertThat(msg.active).isFalse();
        assertThat(msg.score).isEqualTo(34.64);
    }

    @Test
    public void testBestBadHeaderValue() {
        boolean failed = new HttpMsgOf<>(incoming, BadHeaderValue.class)
            .include()
            .failure();

        assertThat(failed).isTrue();
    }

    @Test
    public void testUriPath() {
        PathField msg = new HttpMsgOf<>(incoming, PathField.class)
            .include()
            .orPanic();

        assertThat(msg.uri.toASCIIString()).isEqualTo("/user/786");
    }

    @Test
    public void testUriPathAsString() {
        PathFieldAsString msg = new HttpMsgOf<>(incoming, PathFieldAsString.class)
            .include()
            .orPanic();

        assertThat(msg.uri).isEqualTo("/user/786");
    }

    @Test
    public void testUriPathInvalidType() {
        boolean failed = new HttpMsgOf<>(incoming, PathFieldInvalidType.class)
            .include()
            .failure();

        assertThat(failed).isTrue();
    }

}