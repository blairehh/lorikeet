package lorikeet.http;

import lorikeet.core.Dict;
import lorikeet.core.DictOf;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;
import lorikeet.http.internal.UriHelper;
import lorikeet.lobe.IncomingHttpMsg;
import lorikeet.resource.HttpMethod;

import java.net.URI;

public class MockIncomingHttpMsg implements IncomingHttpMsg {
    private final URI uri;
    private final Dict<String, Seq<String>> headers;
    private final Dict<String, Seq<String>> queryParameters;

    public MockIncomingHttpMsg(String uri) {
        this.uri = URI.create(uri);
        this.headers = new DictOf<>();
        this.queryParameters = new UriHelper().parseQueryParameters(this.uri);
    }

    public MockIncomingHttpMsg(String uri, Dict<String, String> headers) {
        this.headers = headers.modifyValues(SeqOf::new);
        this.uri = URI.create(uri);
        this.queryParameters = new UriHelper().parseQueryParameters(this.uri);
    }


    @Override
    public HttpMethod method() {
        return HttpMethod.GET;
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
