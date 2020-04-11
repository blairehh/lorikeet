package lorikeet.http;

import lorikeet.lobe.Tract;
import lorikeet.lobe.UsesLogging;

public interface HttpMsgReceptor<R extends UsesLogging, Msg> {
    void accept(Tract<R> tract, Msg msg);
}
