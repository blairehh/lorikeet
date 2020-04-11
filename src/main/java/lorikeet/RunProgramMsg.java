package lorikeet;

import lorikeet.http.Get;
import lorikeet.http.MsgCtor;

import javax.ws.rs.QueryParam;

@Get("/run-program")
public class RunProgramMsg {
    private final int timeout;

    @MsgCtor
    public RunProgramMsg(@QueryParam("timeout") int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return this.timeout;
    }
}
