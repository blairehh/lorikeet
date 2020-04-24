package lorikeet.http;

import lorikeet.core.Dict;
import lorikeet.core.DictOf;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;
import lorikeet.core.StringInputStream;
import lorikeet.http.internal.UriHelper;

import java.io.InputStream;
import java.net.URI;

public class MockIncomingHttpSgnl implements IncomingHttpSgnl {
    private final HttpMethod method;
    private final URI uri;
    private final HeaderSet headers;
    private final Dict<String, Seq<String>> queryParameters;
    private final InputStream body;

    public MockIncomingHttpSgnl(String uri) {
        this.method = HttpMethod.GET;
        this.uri = URI.create(uri);
        this.headers = new HeaderSet();
        this.queryParameters = new UriHelper().parseQueryParameters(this.uri);
        this.body = new StringInputStream("");
    }


    public MockIncomingHttpSgnl(String uri, InputStream body) {
        this.method = HttpMethod.GET;
        this.uri = URI.create(uri);
        this.headers = new HeaderSet();
        this.queryParameters = new UriHelper().parseQueryParameters(this.uri);
        this.body = body;
    }

    public MockIncomingHttpSgnl(HttpMethod method, String uri) {
        this.method = method;
        this.uri = URI.create(uri);
        this.headers = new HeaderSet();
        this.queryParameters = new UriHelper().parseQueryParameters(this.uri);
        this.body = new StringInputStream("");
    }

    public MockIncomingHttpSgnl(HttpMethod method, String uri, InputStream body) {
        this.method = method;
        this.uri = URI.create(uri);
        this.headers = new HeaderSet();
        this.queryParameters = new UriHelper().parseQueryParameters(this.uri);
        this.body = body;
    }

    public MockIncomingHttpSgnl(String uri, Dict<String, String> headers) {
        this.method = HttpMethod.GET;
        this.headers = new HeaderSet(headers.modifyValues(SeqOf::new));
        this.uri = URI.create(uri);
        this.queryParameters = new UriHelper().parseQueryParameters(this.uri);
        this.body = new StringInputStream("");
    }


    @Override
    public HttpMethod method() {
        return this.method;
    }

    @Override
    public HeaderSet headers() {
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
    public InputStream body() {
        return this.body;
    }
}
