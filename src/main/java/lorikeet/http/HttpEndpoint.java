package lorikeet.http;

import lorikeet.lobe.Tract;
import lorikeet.lobe.UsesLogging;

public interface HttpEndpoint<R extends UsesLogging> {
    HttpReply accept(Tract<R> tract);
}
