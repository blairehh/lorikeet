package lorikeet.http;


import lorikeet.http.annotation.StatusCode;
import lorikeet.http.error.StatusCodeAnnotationOnClassMustHaveCodeSpecified;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

@StatusCode(200)
class StatusCodeOnClass {

}

@StatusCode
class CodeMissingOnStatusCode {

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
}