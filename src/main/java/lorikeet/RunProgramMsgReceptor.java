package lorikeet;

import lorikeet.http.Http200;
import lorikeet.http.HttpMsgReceptor;
import lorikeet.http.HttpReply;
import lorikeet.lobe.Tract;


public class RunProgramMsgReceptor implements HttpMsgReceptor<Tutorial, RunProgramMsg> {
    @Override
    public HttpReply accept(Tract<Tutorial> tract, RunProgramMsg runProgramMsg) {
         return new Http200<>("get  " + runProgramMsg.getTimeout());
    }
}
