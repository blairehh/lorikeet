package lorikeet.http;

import lorikeet.core.DictOf;
import lorikeet.http.error.MsgTypeDidNotHaveAnnotatedCtor;
import lorikeet.http.error.UnsupportedHeaderValueType;
import org.junit.Test;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@Get("/user/{id}")
class SingleHeader {
    final String name;

    @MsgCtor
    public SingleHeader(@HeaderParam("name") String name) {
        this.name = name;
    }
}

@Get("/user/{id}")
class UnsupportedHeaderType {
    final Random name;

    @MsgCtor
    public UnsupportedHeaderType(@HeaderParam("name") Random name) {
        this.name = name;
    }
}

@Get("/user/{id}")
class SingleHeaderNoCtor {
    final String name;

    public SingleHeaderNoCtor(@HeaderParam("name") String name) {
        this.name = name;
    }
}

@Get("/user/{id}")
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

@Get("/user/{id}")
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

@Get("/user/{id}")
class BadHeaderValue {
    final Integer number;

    @MsgCtor
    public BadHeaderValue(@HeaderParam("bad-num") int number) {
        this.number = number;
    }
}

@Get("/user/{id}")
class MsgWithJustPath {
    @MsgCtor
    public MsgWithJustPath() {

    }
}

@Get("/product/{id}")
class MsgWithJustNonMatchingPath {
    @MsgCtor
    public MsgWithJustNonMatchingPath() {

    }
}

@Get("/user/{id}")
class OneQueryParam {
    final int max;

    @MsgCtor
    public OneQueryParam(@QueryParam("max") int max) {
        this.max = max;
    }
}

@Get("/user/{id}")
class MultipleQueryParams {
    final int max;
    final boolean active;
    final String zone;

    @MsgCtor
    public MultipleQueryParams(
        @QueryParam("max") int max,
        @QueryParam("active") boolean active,
        @QueryParam("zone") String zone
    ) {
        this.max = max;
        this.active = active;
        this.zone = zone;
    }
}


@Get("/orders/{id}/product-codes/{code}")
class MsgWithPathVars {
    final long id;
    final String code;

    @MsgCtor
    public MsgWithPathVars(@PathParam("id") long id, @PathParam("code") String productCode) {
        this.id = id;
        this.code = productCode;
    }
}

@Get("/user/{id}")
class GetRequest {
    @MsgCtor
    public GetRequest() {

    }
}

@Put("/user/{id}")
class PutRequest {
    @MsgCtor
    public PutRequest() {

    }
}

@Patch("/user/{id}")
class PatchRequest {
    @MsgCtor
    public PatchRequest() {

    }
}

@Delete("/user/{id}")
class DeleteRequest {
    @MsgCtor
    public DeleteRequest() {

    }
}


public class HttpMsgTest {

    private final IncomingHttpSgnl incoming = new MockIncomingHttpMsg(
        "/user/786",
        new DictOf<String, String>()
            .push("name", "Bob Doe")
            .push("limit", "10")
            .push("active", "false")
            .push("score", "34.64")
            .push("bad-num", "1a")
    );

    private final IncomingHttpSgnl incomingMultiPathVar = new MockIncomingHttpMsg(
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
        SingleHeader msg = new HttpMsg<>(incoming, SingleHeader.class)
            .include()
            .orPanic();

        assertThat(msg.name).isEqualTo("Bob Doe");
    }

    @Test
    public void testTypeMustHaveMsgCtor() {
        Exception error = new HttpMsg<>(incoming, SingleHeaderNoCtor.class)
            .include()
            .errors()
            .first()
            .orElseThrow();

        assertThat(error.getClass()).isEqualTo(MsgTypeDidNotHaveAnnotatedCtor.class);
    }

    @Test
    public void testRejectsUnsupportedHeaderType() {
        Exception error = new HttpMsg<>(incoming, UnsupportedHeaderType.class)
            .include()
            .errors()
            .first()
            .orElseThrow();

        assertThat(error.getClass()).isEqualTo(UnsupportedHeaderValueType.class);
    }

    @Test
    public void testWithMultipleHeaders() {
        MultiHeader msg = new HttpMsg<>(incoming, MultiHeader.class)
            .include()
            .orPanic();

        assertThat(msg.name).isEqualTo("Bob Doe");
        assertThat(msg.limit).isEqualTo(10);
        assertThat(msg.active).isFalse();
        assertThat(msg.score).isEqualTo(34.64);
    }

    @Test
    public void testWithMultipleHeadersWithCustomAnnotationAndPrimitives() {
        MultiHeaderWithCustomAnnotationAndPrimitives msg = new HttpMsg<>(
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
        boolean failed = new HttpMsg<>(incoming, BadHeaderValue.class)
            .include()
            .failure();

        assertThat(failed).isTrue();
    }

    @Test
    public void testPath() {
        boolean succeeded = new HttpMsg<>(incoming, MsgWithJustPath.class)
            .include()
            .success();

        assertThat(succeeded).isTrue();
    }

    @Test
    public void testPathNotMatching() {
        boolean succeeded = new HttpMsg<>(incoming, MsgWithJustNonMatchingPath.class)
            .include()
            .success();

        assertThat(succeeded).isFalse();
    }

    @Test
    public void testPathVars() {
        MsgWithPathVars msg = new HttpMsg<>(incomingMultiPathVar, MsgWithPathVars.class)
            .include()
            .orPanic();

        assertThat(msg.id).isEqualTo(123);
        assertThat(msg.code).isEqualTo("ABC");
    }

    @Test
    public void testOneQueryParam() {
        IncomingHttpSgnl request = new MockIncomingHttpMsg("/user/56?max=100");
        OneQueryParam msg = new HttpMsg<>(request, OneQueryParam.class)
            .include()
            .orPanic();

        assertThat(msg.max).isEqualTo(100);
    }

    @Test
    public void testQueryParamNotFound() {
        IncomingHttpSgnl request = new MockIncomingHttpMsg("/user/56?min=100");
        boolean failed = new HttpMsg<>(request, OneQueryParam.class)
            .include()
            .failure();

        assertThat(failed).isTrue();
    }

    @Test
    public void testMultipleQueryParams() {
        IncomingHttpSgnl request = new MockIncomingHttpMsg("/user/56?max=100&zone=FOO&active=false");
        MultipleQueryParams msg = new HttpMsg<>(request, MultipleQueryParams.class)
            .include()
            .orPanic();

        assertThat(msg.max).isEqualTo(100);
        assertThat(msg.zone).isEqualTo("FOO");
        assertThat(msg.active).isFalse();
    }

    @Test
    public void testHttpMethod() {
        IncomingHttpSgnl request = new MockIncomingHttpMsg(HttpMethod.DELETE, "/user/56");
        boolean success = new HttpMsg<>(request, DeleteRequest.class)
            .include()
            .success();

        assertThat(success).isTrue();

        success = new HttpMsg<>(request, PutRequest.class)
            .include()
            .success();

        assertThat(success).isFalse();

        success = new HttpMsg<>(request, PatchRequest.class)
            .include()
            .success();

        assertThat(success).isFalse();

        success = new HttpMsg<>(request, GetRequest.class)
            .include()
            .success();

        assertThat(success).isFalse();
    }
}