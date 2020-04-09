package lorikeet.lobe;

import lorikeet.core.Dict;
import lorikeet.core.Seq;
import lorikeet.resource.HttpMethod;

import java.net.URI;

public interface IncomingHttpMsg {
    HttpMethod method();
    Dict<String, Seq<String>> headers();
    URI uri();
    Dict<String, Seq<String>> queryParameters();
}
