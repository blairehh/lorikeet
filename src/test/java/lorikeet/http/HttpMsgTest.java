package lorikeet.http;

import lorikeet.core.Dict;
import lorikeet.core.DictOf;
import lorikeet.core.Seq;
import lorikeet.http.annotation.Delete;
import lorikeet.http.annotation.Get;
import lorikeet.http.annotation.Header;
import lorikeet.http.annotation.Headers;
import lorikeet.http.annotation.MsgCtor;
import lorikeet.http.annotation.Patch;
import lorikeet.http.annotation.PathVar;
import lorikeet.http.annotation.Put;
import lorikeet.http.annotation.Query;
import lorikeet.http.error.MsgTypeDidNotHaveAnnotatedCtor;
import lorikeet.http.error.UnsupportedHeaderValueType;
import org.junit.Test;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@Get("/user/{id}")
class SingleHeader {
    final String name;

    @MsgCtor
    public SingleHeader(@Header("name") String name) {
        this.name = name;
    }
}

@Get("/user/{id}")
class UnsupportedHeaderType {
    final Random name;

    @MsgCtor
    public UnsupportedHeaderType(@Header("name") Random name) {
        this.name = name;
    }
}

@Get("/user/{id}")
class SingleHeaderNoCtor {
    final String name;

    public SingleHeaderNoCtor(@Header("name") String name) {
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
        @Header("name") String name,
        @Header("limit") Integer limit,
        @Header("active") Boolean active,
        @Header("score") Double score
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
    public BadHeaderValue(@Header("bad-num") int number) {
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
    public OneQueryParam(@Query("max") int max) {
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
        @Query("max") int max,
        @Query("active") boolean active,
        @Query("zone") String zone
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
    public MsgWithPathVars(@PathVar("id") long id, @PathVar("code") String productCode) {
        this.id = id;
        this.code = productCode;
    }
}

@Get("/orders/{id}/product-codes/{code}")
class MsgWithMissingPathVars {
    final long id;
    final String reference;

    @MsgCtor
    public MsgWithMissingPathVars(@PathVar("id") long id, @PathVar("reference") String reference) {
        this.id = id;
        this.reference = reference;
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

@Get("/user/{id}")
class AllHeaders {
    final HeaderSet headers;
    @MsgCtor
    public AllHeaders(@Headers HeaderSet headers) {
        this.headers = headers;
    }
}

@Get("/user/{id}")
class AllHeadersAsDict {
    final Dict<String, Seq<String>> headers;
    @MsgCtor
    public AllHeadersAsDict(@Headers Dict<String, Seq<String>> headers) {
        this.headers = headers;
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
            .push("Authorization", "Basic f8eyrf7")
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
    public void testAllHeaders() {
        AllHeaders msg = new HttpMsg<>(incoming, AllHeaders.class)
            .include()
            .orPanic();

        assertThat(msg.headers.getAny("name")).isEqualTo("Bob Doe");
        assertThat(msg.headers.getAny("limit")).isEqualTo("10");
        assertThat(msg.headers.getAny("active")).isEqualTo("false");
        assertThat(msg.headers.getAny("score")).isEqualTo("34.64");
        assertThat(msg.headers.authorization()).isEqualTo("Basic f8eyrf7");
    }

    @Test
    public void testAllHeadersWithInvalidType() {
        boolean failed = new HttpMsg<>(incoming, AllHeadersAsDict.class)
            .include()
            .failure();

        assertThat(failed).isTrue();
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
    public void testMissingPathVars() {
        boolean failed = new HttpMsg<>(incomingMultiPathVar, MsgWithMissingPathVars.class)
            .include()
            .failure();

        assertThat(failed).isTrue();
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