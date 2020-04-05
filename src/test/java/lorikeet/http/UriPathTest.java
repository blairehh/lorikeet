package lorikeet.http;

import org.junit.Test;

public class UriPathTest {


    @Test
    public void testNotVariables() {

        var msg = new MockIncomingHttpMsg("/orders/1475/fod");

        new UriPath(msg, "/orders/{id}/fo")
            .include();
    }
}