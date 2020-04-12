package lorikeet.http;

import lorikeet.lobe.Tract;
import lorikeet.lobe.UsesLogging;

public interface HttpMsgReceptor<R extends UsesLogging, Msg> {
    HttpReply accept(Tract<R> tract, Msg msg);
}
