package lorikeet.http;

import lorikeet.TestResources;
import lorikeet.coding.InternetMediaType;
import lorikeet.core.Dict;
import lorikeet.core.DictOf;
import lorikeet.core.Seq;
import lorikeet.core.StringInputStream;
import lorikeet.http.annotation.Body;
import lorikeet.http.annotation.Delete;
import lorikeet.http.annotation.Get;
import lorikeet.http.annotation.Header;
import lorikeet.http.annotation.Headers;
import lorikeet.http.annotation.MsgCtor;
import lorikeet.http.annotation.Patch;
import lorikeet.http.annotation.PathVar;
import lorikeet.http.annotation.Post;
import lorikeet.http.annotation.Put;
import lorikeet.http.annotation.Query;
import lorikeet.http.annotation.headers.Authorization;
import lorikeet.http.annotation.headers.ContentType;
import lorikeet.http.annotation.headers.XForwardedFor;
import lorikeet.http.error.MsgTypeDidNotHaveAnnotatedCtor;
import lorikeet.http.error.UnsupportedHeaderValueType;
import lorikeet.http.msg.SampleJson;
import lorikeet.lobe.DefaultTract;
import lorikeet.lobe.Tract;
import lorikeet.lobe.UsesCoding;
import lorikeet.lobe.UsesLogging;
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

@Get("/user/{id}")
class StandardHeaders {
    final String contentType;
    final String authorization;
    final String forwarded;

    @MsgCtor
    public StandardHeaders(
        @ContentType String contentType,
        @Authorization String authorization,
        @XForwardedFor String forwarded
    ) {
        this.contentType = contentType;
        this.authorization = authorization;
        this.forwarded = forwarded;
    }
}


@Post("/users")
class PostWithPayload {
    final SampleJson json;

    @MsgCtor
    public PostWithPayload(
        @Body("application/json") SampleJson json
    ) {
        this.json = json;
    }
}

@Post("/users")
@ContentType("application/json")
class PostWithPayloadAndContentTypeOnClass {
    final SampleJson json;

    @MsgCtor
    public PostWithPayloadAndContentTypeOnClass(@Body SampleJson json) {
        this.json = json;
    }
}

public class HttpMsgTest {

    private final Tract<TestResources> tract = new DefaultTract<>(new TestResources());

    private final IncomingHttpSgnl incoming = new MockIncomingHttpSgnl(
        "/user/786",
        new DictOf<String, String>()
            .push("name", "Bob Doe")
            .push("limit", "10")
            .push("active", "false")
            .push("score", "34.64")
            .push("bad-num", "1a")
            .push("Authorization", "Basic f8eyrf7")
            .push("Content-Type", "application/json")
            .push("X-Forwarded-For", "127.0.0.1")
    );

    private final IncomingHttpSgnl incomingMultiPathVar = new MockIncomingHttpSgnl(
        "/orders/123/product-codes/ABC",
        new DictOf<String, String>()
            .push("name", "Bob Doe")
            .push("limit", "10")
            .push("active", "false")
            .push("score", "34.64")
            .push("bad-num", "1a")
    );

    @SuppressWarnings("unchecked")
    private <RT extends UsesLogging & UsesCoding> Tract<RT> tract() {
        return (Tract<RT>)tract;
    }


    @Test
    public void testInitiateWithOneHeader() {
        SingleHeader msg = new HttpMsg<>(SingleHeader.class)
            .include(incoming, tract())
            .orPanic();

        assertThat(msg.name).isEqualTo("Bob Doe");
    }

    @Test
    public void testTypeMustHaveMsgCtor() {
        Exception error = new HttpMsg<>(SingleHeaderNoCtor.class)
            .include(incoming, tract())
            .errors()
            .first()
            .orElseThrow();

        assertThat(error.getClass()).isEqualTo(MsgTypeDidNotHaveAnnotatedCtor.class);
    }

    @Test
    public void testRejectsUnsupportedHeaderType() {
        Exception error = new HttpMsg<>(UnsupportedHeaderType.class)
            .include(incoming, tract())
            .errors()
            .first()
            .orElseThrow();

        assertThat(error.getClass()).isEqualTo(UnsupportedHeaderValueType.class);
    }

    @Test
    public void testWithMultipleHeaders() {
        MultiHeader msg = new HttpMsg<>(MultiHeader.class)
            .include(incoming, tract())
            .orPanic();

        assertThat(msg.name).isEqualTo("Bob Doe");
        assertThat(msg.limit).isEqualTo(10);
        assertThat(msg.active).isFalse();
        assertThat(msg.score).isEqualTo(34.64);
    }

    @Test
    public void testAllHeaders() {
        AllHeaders msg = new HttpMsg<>(AllHeaders.class)
            .include(incoming, tract())
            .orPanic();

        assertThat(msg.headers.getAny("name")).isEqualTo("Bob Doe");
        assertThat(msg.headers.getAny("limit")).isEqualTo("10");
        assertThat(msg.headers.getAny("active")).isEqualTo("false");
        assertThat(msg.headers.getAny("score")).isEqualTo("34.64");
        assertThat(msg.headers.authorization()).isEqualTo("Basic f8eyrf7");
    }

    @Test
    public void testStandardHeaders() {
        StandardHeaders msg = new HttpMsg<>(StandardHeaders.class)
            .include(incoming, tract())
            .orPanic();

        assertThat(msg.contentType).isEqualTo("application/json");
        assertThat(msg.authorization).isEqualTo("Basic f8eyrf7");
        assertThat(msg.forwarded).isEqualTo("127.0.0.1");
    }

    @Test
    public void testAllHeadersWithInvalidType() {
        boolean failed = new HttpMsg<>(AllHeadersAsDict.class)
            .include(incoming, tract())
            .failure();

        assertThat(failed).isTrue();
    }

    @Test
    public void testWithMultipleHeadersWithCustomAnnotationAndPrimitives() {
        MultiHeaderWithCustomAnnotationAndPrimitives msg = new HttpMsg<>(
            MultiHeaderWithCustomAnnotationAndPrimitives.class
        )
            .include(incoming, tract())
            .orPanic();

        assertThat(msg.name).isEqualTo("Bob Doe");
        assertThat(msg.limit).isEqualTo(10);
        assertThat(msg.active).isFalse();
        assertThat(msg.score).isEqualTo(34.64);
    }

    @Test
    public void testBestBadHeaderValue() {
        boolean failed = new HttpMsg<>(BadHeaderValue.class)
            .include(incoming, tract())
            .failure();

        assertThat(failed).isTrue();
    }

    @Test
    public void testPath() {
        boolean succeeded = new HttpMsg<>(MsgWithJustPath.class)
            .include(incoming, tract())
            .success();

        assertThat(succeeded).isTrue();
    }

    @Test
    public void testPathNotMatching() {
        boolean succeeded = new HttpMsg<>(MsgWithJustNonMatchingPath.class)
            .include(incoming, tract())
            .success();

        assertThat(succeeded).isFalse();
    }

    @Test
    public void testPathVars() {
        MsgWithPathVars msg = new HttpMsg<>(MsgWithPathVars.class)
            .include(incomingMultiPathVar, tract())
            .orPanic();

        assertThat(msg.id).isEqualTo(123);
        assertThat(msg.code).isEqualTo("ABC");
    }

    @Test
    public void testMissingPathVars() {
        boolean failed = new HttpMsg<>(MsgWithMissingPathVars.class)
            .include(incomingMultiPathVar, tract())
            .failure();

        assertThat(failed).isTrue();
    }

    @Test
    public void testOneQueryParam() {
        IncomingHttpSgnl request = new MockIncomingHttpSgnl("/user/56?max=100");
        OneQueryParam msg = new HttpMsg<>(OneQueryParam.class)
            .include(request, tract())
            .orPanic();

        assertThat(msg.max).isEqualTo(100);
    }

    @Test
    public void testQueryParamNotFound() {
        IncomingHttpSgnl request = new MockIncomingHttpSgnl("/user/56?min=100");
        boolean failed = new HttpMsg<>(OneQueryParam.class)
            .include(request, tract())
            .failure();

        assertThat(failed).isTrue();
    }

    @Test
    public void testMultipleQueryParams() {
        IncomingHttpSgnl request = new MockIncomingHttpSgnl("/user/56?max=100&zone=FOO&active=false");
        MultipleQueryParams msg = new HttpMsg<>(MultipleQueryParams.class)
            .include(request, tract())
            .orPanic();

        assertThat(msg.max).isEqualTo(100);
        assertThat(msg.zone).isEqualTo("FOO");
        assertThat(msg.active).isFalse();
    }

    @Test
    public void testHttpMethod() {
        IncomingHttpSgnl request = new MockIncomingHttpSgnl(HttpMethod.DELETE, "/user/56");
        boolean success = new HttpMsg<>(DeleteRequest.class)
            .include(request, tract())
            .success();

        assertThat(success).isTrue();

        success = new HttpMsg<>( PutRequest.class)
            .include(request, tract())
            .success();

        assertThat(success).isFalse();

        success = new HttpMsg<>(PatchRequest.class)
            .include(request, tract())
            .success();

        assertThat(success).isFalse();

        success = new HttpMsg<>(GetRequest.class)
            .include(request, tract())
            .success();

        assertThat(success).isFalse();
    }

    @Test
    public void testPostWithJson() {
        IncomingHttpSgnl request = new MockIncomingHttpSgnl(
            HttpMethod.POST,
            "/users",
            new StringInputStream("{\"id\": 1, \"name\": \"Bob\", \"active\": true}")
        );

        PostWithPayload result = new HttpMsg<>(PostWithPayload.class)
            .include(request, tract())
            .orPanic();

        assertThat(result.json.id).isEqualTo(1);
        assertThat(result.json.name).isEqualTo("Bob");
        assertThat(result.json.active).isTrue();
    }

    @Test
    public void testPostWithJsonAndContentTypeOnClass() {
        IncomingHttpSgnl request = new MockIncomingHttpSgnl(
            HttpMethod.POST,
            "/users",
            new StringInputStream("{\"id\": 1, \"name\": \"Bob\", \"active\": true}")
        );

        PostWithPayloadAndContentTypeOnClass result = new HttpMsg<>(PostWithPayloadAndContentTypeOnClass.class)
            .include(request, tract())
            .orPanic();

        assertThat(result.json.id).isEqualTo(1);
        assertThat(result.json.name).isEqualTo("Bob");
        assertThat(result.json.active).isTrue();
    }
}