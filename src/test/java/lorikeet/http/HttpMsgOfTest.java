package lorikeet.http;

import lorikeet.core.DictOf;
import lorikeet.http.error.MsgTypeDidNotHaveAnnotatedCtor;
import lorikeet.http.error.UnsupportedHeaderValueType;
import lorikeet.lobe.IncomingHttpMsg;
import org.junit.Test;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@Path("/user/{id}")
class SingleHeader {
    final String name;

    @MsgCtor
    public SingleHeader(@HeaderParam("name") String name) {
        this.name = name;
    }
}

@Path("/user/{id}")
class UnsupportedHeaderType {
    final Random name;

    @MsgCtor
    public UnsupportedHeaderType(@HeaderParam("name") Random name) {
        this.name = name;
    }
}

@Path("/user/{id}")
class SingleHeaderNoCtor {
    final String name;

    public SingleHeaderNoCtor(@HeaderParam("name") String name) {
        this.name = name;
    }
}

@Path("/user/{id}")
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

@Path("/user/{id}")
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

@Path("/user/{id}")
class BadHeaderValue {
    final Integer number;

    @MsgCtor
    public BadHeaderValue(@HeaderParam("bad-num") int number) {
        this.number = number;
    }
}

@Path("/user/{id}")
class MsgWithJustPath {
    @MsgCtor
    public MsgWithJustPath() {

    }
}

@Path("/product/{id}")
class MsgWithJustNonMatchingPath {
    @MsgCtor
    public MsgWithJustNonMatchingPath() {

    }
}


@Path("/orders/{id}/product-codes/{code}")
class MsgWithPathVars {
    final long id;
    final String code;

    @MsgCtor
    public MsgWithPathVars(@PathParam("id") long id, @PathParam("code") String productCode) {
        this.id = id;
        this.code = productCode;
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

    private final IncomingHttpMsg incomingMultiPathVar = new MockIncomingHttpMsg(
        "/orders/123/product-codes/ABC",
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
    public void testPath() {
        boolean succeeded = new HttpMsgOf<>(incoming, MsgWithJustPath.class)
            .include()
            .success();

        assertThat(succeeded).isTrue();
    }

    @Test
    public void testPathNotMatching() {
        boolean succeeded = new HttpMsgOf<>(incoming, MsgWithJustNonMatchingPath.class)
            .include()
            .success();

        assertThat(succeeded).isFalse();
    }

    @Test
    public void testPathVars() {
        MsgWithPathVars msg = new HttpMsgOf<>(incomingMultiPathVar, MsgWithPathVars.class)
            .include()
            .orPanic();

        assertThat(msg.id).isEqualTo(123);
        assertThat(msg.code).isEqualTo("ABC");
    }
}