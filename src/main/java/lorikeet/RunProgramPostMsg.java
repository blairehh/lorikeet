package lorikeet;

import lorikeet.http.annotation.Post;
import lorikeet.http.annotation.MsgCtor;
import lorikeet.http.annotation.Query;

@Post("/run-program")
public class RunProgramPostMsg {
    private final int timeout;

    @MsgCtor
    public RunProgramPostMsg(@Query("timeout") int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return this.timeout;
    }
}
