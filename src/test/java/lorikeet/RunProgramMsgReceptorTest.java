package lorikeet;

import lorikeet.http.MockIncomingHttpSgnl;
import lorikeet.http.IncomingHttpSgnl;
import lorikeet.http.HttpMethod;
import org.junit.Test;

public class RunProgramMsgReceptorTest {

    @Test
    public void testRun() {
        IncomingHttpSgnl request = new MockIncomingHttpSgnl(HttpMethod.GET, "/foo?timeout=200");
    }
}