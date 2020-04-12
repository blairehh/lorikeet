package lorikeet.http;

import lorikeet.core.Fallible;

public interface HttpWriteResult extends Fallible<Boolean>, HttpReply {
    int statusCode();
}
