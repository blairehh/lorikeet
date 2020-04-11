package lorikeet;


import lorikeet.http.Get;
import lorikeet.http.HttpMsgReceptor;
import lorikeet.http.MsgCtor;
import lorikeet.lobe.Tract;

import javax.ws.rs.QueryParam;

@Get("/foo")
class RunProgramMsg {
    private final int timeout;

    @MsgCtor
    public RunProgramMsg(@QueryParam("timeout") int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return this.timeout;
    }
}

public class RunProgramMsgReceptor implements HttpMsgReceptor<Tutorial, RunProgramMsg> {
    @Override
    public void accept(Tract<Tutorial> tract, RunProgramMsg runProgramMsg) {
        System.out.println(runProgramMsg.getTimeout());
    }
}
