package lorikeet.http;

import lorikeet.core.Dict;
import lorikeet.core.DictOf;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;
import lorikeet.lobe.IncomingHttpMsg;
import lorikeet.resource.HttpMethod;

import java.net.URI;

public class MockIncomingHttpMsg implements IncomingHttpMsg {
    private final URI uri;
    private final Dict<String, Seq<String>> headers;

    public MockIncomingHttpMsg(String uri) {
        this.uri = URI.create(uri);
        this.headers = new DictOf<>();
    }

    public MockIncomingHttpMsg(String uri, Dict<String, String> headers) {
        this.headers = headers.modifyValues(SeqOf::new);
        this.uri = URI.create(uri);
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
}
