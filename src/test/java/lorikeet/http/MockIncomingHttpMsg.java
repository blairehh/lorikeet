package lorikeet.http;

import lorikeet.core.Dict;
import lorikeet.core.DictOf;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;
import lorikeet.http.internal.UriHelper;

import java.net.URI;

public class MockIncomingHttpMsg implements IncomingHttpSgnl {
    private final HttpMethod method;
    private final URI uri;
    private final Dict<String, Seq<String>> headers;
    private final Dict<String, Seq<String>> queryParameters;

    public MockIncomingHttpMsg(String uri) {
        this.method = HttpMethod.GET;
        this.uri = URI.create(uri);
        this.headers = new DictOf<>();
        this.queryParameters = new UriHelper().parseQueryParameters(this.uri);
    }

    public MockIncomingHttpMsg(HttpMethod method, String uri) {
        this.method = method;
        this.uri = URI.create(uri);
        this.headers = new DictOf<>();
        this.queryParameters = new UriHelper().parseQueryParameters(this.uri);
    }

    public MockIncomingHttpMsg(String uri, Dict<String, String> headers) {
        this.method = HttpMethod.GET;
        this.headers = headers.modifyValues(SeqOf::new);
        this.uri = URI.create(uri);
        this.queryParameters = new UriHelper().parseQueryParameters(this.uri);
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
}
