package lorikeet;

import lorikeet.http.MockIncomingHttpMsg;
import lorikeet.http.IncomingHttpSgnl;
import lorikeet.resource.HttpMethod;
import org.junit.Test;

public class RunProgramMsgReceptorTest {

    @Test
    public void testRun() {
        IncomingHttpSgnl request = new MockIncomingHttpMsg(HttpMethod.GET, "/foo?timeout=200");
    }
}