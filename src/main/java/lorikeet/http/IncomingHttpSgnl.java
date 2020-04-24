package lorikeet.http;

import lorikeet.core.Dict;
import lorikeet.core.Seq;

import java.io.InputStream;
import java.net.URI;

public interface IncomingHttpSgnl {
    HttpMethod method();
    HeaderSet headers();
    URI uri();
    Dict<String, Seq<String>> queryParameters();
    InputStream body();
}
