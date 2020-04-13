package lorikeet.http;


import lorikeet.http.annotation.StatusCode;
import lorikeet.http.error.BadHttpStatusCodeFieldType;
import lorikeet.http.error.StatusCodeAnnotationOnClassMustHaveCodeSpecified;
import lorikeet.http.error.StatusCodeFiledCanNotBeNull;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

@StatusCode(200)
class StatusCodeOnClass {

}

@StatusCode
class CodeMissingOnStatusCode {

}

class StatusCodeAsField {
    @StatusCode private int statusCode;

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}

class StatusCodeAsEnumField {
    @StatusCode private HttpStatus status;

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}

class BadStatusCodeFieldType {
    @StatusCode private String status;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

public class MsgToHttpOutgoingSgnlTest {

    @Test
    public void testMsgWithStatusCodeOnClass() {
        MockOutgoingHttpSgnl outgoing = new MockOutgoingHttpSgnl();

        boolean succedded = new MsgToHttpOutgoingSgnl()
            .toOugoing(new StatusCodeOnClass(), outgoing)
            .success();

        assertThat(succedded).isTrue();
        assertThat(outgoing.statusCode).isEqualTo(200);
    }

    @Test
    public void testMsgWithStatusCodeOnClassMustHaveStatusCode() {
        MockOutgoingHttpSgnl outgoing = new MockOutgoingHttpSgnl();

        Exception exception = new MsgToHttpOutgoingSgnl()
            .toOugoing(new CodeMissingOnStatusCode(), outgoing)
            .errors()
            .first()
            .orElseThrow();

        assertThat(exception.getClass()).isEqualTo(StatusCodeAnnotationOnClassMustHaveCodeSpecified.class);
    }

    @Test
    public void testStatusCodeAsClassField() {
        MockOutgoingHttpSgnl outgoing = new MockOutgoingHttpSgnl();
        StatusCodeAsField msg = new StatusCodeAsField();
        msg.setStatusCode(204);

        boolean succedded = new MsgToHttpOutgoingSgnl()
            .toOugoing(msg, outgoing)
            .success();

        assertThat(succedded).isTrue();
        assertThat(outgoing.statusCode).isEqualTo(204);
    }

    @Test
    public void testStatusCodeAsClassFieldAsEnum() {
        MockOutgoingHttpSgnl outgoing = new MockOutgoingHttpSgnl();
        StatusCodeAsEnumField msg = new StatusCodeAsEnumField();
        msg.setStatus(HttpStatus.CONFLICT);

        boolean succedded = new MsgToHttpOutgoingSgnl()
            .toOugoing(msg, outgoing)
            .success();

        assertThat(succedded).isTrue();
        assertThat(outgoing.statusCode).isEqualTo(409);
    }

    @Test
    public void testBadStatusCodeFieldType() {
        MockOutgoingHttpSgnl outgoing = new MockOutgoingHttpSgnl();
        BadStatusCodeFieldType msg = new BadStatusCodeFieldType();
        msg.setStatus("400");

        Exception exception = new MsgToHttpOutgoingSgnl()
            .toOugoing(msg, outgoing)
            .errors()
            .first()
            .orElseThrow();

        assertThat(exception.getClass()).isEqualTo(BadHttpStatusCodeFieldType.class);
    }

    @Test
    public void testStatusCodeFieldCanNotBeNull() {
        MockOutgoingHttpSgnl outgoing = new MockOutgoingHttpSgnl();
        StatusCodeAsEnumField msg = new StatusCodeAsEnumField();

        Exception exception = new MsgToHttpOutgoingSgnl()
            .toOugoing(msg, outgoing)
            .errors()
            .first()
            .orElseThrow();

        assertThat(exception.getClass()).isEqualTo(StatusCodeFiledCanNotBeNull.class);
    }
}