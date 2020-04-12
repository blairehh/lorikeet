package lorikeet.http;

import lorikeet.core.Fallible;

import java.net.URI;

public interface HttpWriteResult extends Fallible<Boolean> {
    int statusCode();
}
