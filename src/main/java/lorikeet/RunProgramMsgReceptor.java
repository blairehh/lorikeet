package lorikeet;

import lorikeet.http.Http200;
import lorikeet.http.HttpMsgReceptor;
import lorikeet.lobe.Tract;

public class RunProgramMsgReceptor implements HttpMsgReceptor<Tutorial, RunProgramMsg> {
    @Override
    public void accept(Tract<Tutorial> tract, RunProgramMsg runProgramMsg) {
        System.out.println("run program with timeout " + runProgramMsg.getTimeout());
        tract.write(new Http200<>());
    }
}
