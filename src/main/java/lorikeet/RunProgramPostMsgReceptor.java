package lorikeet;

import lorikeet.coding.JsonEncode;
import lorikeet.http.Http200;
import lorikeet.http.HttpMsgReceptor;
import lorikeet.http.HttpReply;
import lorikeet.lobe.Tract;

public class RunProgramPostMsgReceptor implements HttpMsgReceptor<Tutorial, RunProgramPostMsg> {
    @Override
    public HttpReply accept(Tract<Tutorial> tract, RunProgramPostMsg runProgramMsg) {
         return new Http200<>(new JsonEncode<>(runProgramMsg.getUser()));
    }
}
