package lorikeet.http;

import lorikeet.core.DictOf;
import lorikeet.http.error.MsgTypeDidNotHaveAnnotatedCtor;
import lorikeet.http.error.UnsupportedHeaderValueType;
import lorikeet.lobe.IncomingHttpMsg;
import org.junit.Test;

import javax.ws.rs.HeaderParam;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class SingleHeader {
    String name;

    @MsgCtor
    public SingleHeader(@HeaderParam("name") String name) {
        this.name = name;
    }
}

class UnsupportedHeaderType {
    Random name;

    @MsgCtor
    public UnsupportedHeaderType(@HeaderParam("name") Random name) {
        this.name = name;
    }
}

class SingleHeaderNoCtor {
    String name;

    public SingleHeaderNoCtor(@HeaderParam("name") String name) {
        this.name = name;
    }
}


class MultiHeader {
    String name;
    Integer limit;
    Boolean active;
    Double score;

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

class BadHeaderValue {
    Integer number;

    @MsgCtor
    public BadHeaderValue(@HeaderParam("bad-num") int number) {
        this.number = number;
    }
}

public class HttpMsgOfTest {

    private final IncomingHttpMsg incoming = new MockIncomingHttpMsg(
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
    public void testBestBadHeaderValue() {
        boolean failed = new HttpMsgOf<>(incoming, BadHeaderValue.class)
            .include()
            .failure();

        assertThat(failed).isTrue();
    }
}