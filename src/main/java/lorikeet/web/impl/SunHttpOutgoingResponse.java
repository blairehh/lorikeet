package lorikeet.web.impl;

import com.sun.net.httpserver.HttpExchange;
import lorikeet.web.OutgoingResponse;

import java.io.IOException;
import java.io.OutputStream;

public class SunHttpOutgoingResponse implements OutgoingResponse {
    private final HttpExchange exchange;

    public SunHttpOutgoingResponse(HttpExchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public void reply(int statusCode, String content) {
        try {
            System.out.println(content);
            final byte[] body = content.getBytes();
            System.out.println(body.length);
            this.exchange.sendResponseHeaders(statusCode, body.length);
            OutputStream os = this.exchange.getResponseBody();
            os.write(body);
            os.close();
            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
