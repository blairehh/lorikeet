package lorikeet;

import lorikeet.http.MockIncomingHttpMsg;
import lorikeet.lobe.IncomingHttpMsg;
import lorikeet.resource.HttpMethod;
import org.junit.Test;

public class RunProgramMsgReceptorTest {

    @Test
    public void testRun() {
        IncomingHttpMsg request = new MockIncomingHttpMsg(HttpMethod.GET, "/foo?timeout=200");
    }
}