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
            this.exchange.sendResponseHeaders(statusCode, content.length());
            OutputStream os = this.exchange.getResponseBody();
            os.write(content.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
