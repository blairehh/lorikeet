package lorikeet;

import lorikeet.http.Http200;
import lorikeet.http.HttpMsgReceptor;
import lorikeet.http.HttpReply;
import lorikeet.lobe.Tract;


public class RunProgramMsgReceptor implements HttpMsgReceptor<Tutorial, RunProgramMsg> {
    @Override
    public HttpReply accept(Tract<Tutorial> tract, RunProgramMsg runProgramMsg) {
        return tract.write(new Http200<>("A)run program with timeout " + runProgramMsg.getTimeout()));
        // tract.write(new Http204<>());
        // return new Http200<>("B)run program with timeout " + runProgramMsg.getTimeout());
    }
}
