package lorikeet.resource;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import lorikeet.http.HeaderField;
import lorikeet.http.OutgoingHttpSgnl;

import java.io.OutputStreamWriter;
import java.util.Objects;

public class UndertowOutgoingSgnl implements OutgoingHttpSgnl {
    private final HttpServerExchange exchange;

    public UndertowOutgoingSgnl(HttpServerExchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public OutgoingHttpSgnl statusCode(int statusCode) {
        this.exchange.setStatusCode(statusCode);
        return this;
    }

    @Override
    public OutgoingHttpSgnl header(String name, String value) {
        this.exchange.getResponseHeaders().put(HttpString.tryFromString(name), value);
        return this;
    }

    @Override
    public OutgoingHttpSgnl header(HeaderField header, String value) {
        return this.header(header.key(), value);
    }

    @Override
    public OutgoingHttpSgnl writeBody(String content) {
        this.exchange.getResponseSender().send(content);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        UndertowOutgoingSgnl that = (UndertowOutgoingSgnl) o;

        return Objects.equals(this.exchange, that.exchange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.exchange);
    }
}
