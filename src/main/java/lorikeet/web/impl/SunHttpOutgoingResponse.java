package lorikeet.web.impl;

import com.sun.net.httpserver.HttpExchange;
import lorikeet.NinjaException;
import lorikeet.web.OutgoingResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class SunHttpOutgoingResponse implements OutgoingResponse {
    private final HttpExchange exchange;

    public SunHttpOutgoingResponse(HttpExchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public void reply(int statusCode, String content) {
        this.respond(statusCode, content.getBytes(StandardCharsets.UTF_8));
    }

    private void respond(int statusCode, byte[] body) {
        try {
            this.exchange.sendResponseHeaders(statusCode, body.length);
            OutputStream os = this.exchange.getResponseBody();
            os.write(body);
            os.close();
        } catch (IOException e) {
            throw new NinjaException(e);
        }
    }
}
