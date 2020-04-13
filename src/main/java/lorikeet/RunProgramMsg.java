package lorikeet;

import lorikeet.http.annotation.Get;
import lorikeet.http.annotation.MsgCtor;
import lorikeet.http.annotation.Query;

@Get("/run-program")
public class RunProgramMsg {
    private final int timeout;

    @MsgCtor
    public RunProgramMsg(@Query("timeout") int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return this.timeout;
    }
}
