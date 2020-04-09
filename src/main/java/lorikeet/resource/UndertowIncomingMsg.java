package lorikeet.resource;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import lorikeet.core.Dict;
import lorikeet.core.DictOf;
import lorikeet.core.Seq;
import lorikeet.core.SeqCollector;
import lorikeet.http.internal.UriHelper;
import lorikeet.lobe.IncomingHttpMsg;

import java.net.URI;
import java.util.Objects;

public class UndertowIncomingMsg implements IncomingHttpMsg {
    private final UriHelper uriHelper = new UriHelper();

    private final HttpMethod method;
    private final URI uri;
    private final Dict<String, Seq<String>> headers;
    private final Dict<String, Seq<String>> queryParameters;

    public UndertowIncomingMsg(HttpServerExchange exchange) {
        this.method = HttpMethod.fromString(exchange.getRequestMethod().toString()).orElseThrow();
        this.uri = URI.create(exchange.getRequestURI());
        this.headers = headers(exchange);
        this.queryParameters = uriHelper.parseQueryParameters(this.uri);
    }

    private Dict<String, Seq<String>> headers(HttpServerExchange exchange) {
        Dict<String, Seq<String>> dict = new DictOf<>();
        for (HttpString header : exchange.getRequestHeaders().getHeaderNames()) {
            final Seq<String> values = exchange.getRequestHeaders().get(header)
                .stream()
                .collect(new SeqCollector<>());
            dict = dict.push(header.toString(), values);
        }
        return dict;
    }

    @Override
    public HttpMethod method() {
        return this.method;
    }

    @Override
    public Dict<String, Seq<String>> headers() {
        return this.headers;
    }

    @Override
    public URI uri() {
        return this.uri;
    }

    @Override
    public Dict<String, Seq<String>> queryParameters() {
        return this.queryParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        UndertowIncomingMsg that = (UndertowIncomingMsg) o;

        return Objects.equals(this.method, that.method)
            && Objects.equals(this.uri, that.uri)
            && Objects.equals(this.headers, that.headers)
            && Objects.equals(this.queryParameters, that.queryParameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.method, this.uri, this.headers, this.queryParameters);
    }
}
