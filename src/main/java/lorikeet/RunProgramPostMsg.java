package lorikeet;

import lorikeet.http.annotation.Body;
import lorikeet.http.annotation.Post;
import lorikeet.http.annotation.MsgCtor;
import lorikeet.http.annotation.Query;
import lorikeet.http.annotation.headers.ContentType;

@Post("/run-program")
@ContentType("application/json")
public class RunProgramPostMsg {
    private final int timeout;
    private final User user;

    @MsgCtor
    public RunProgramPostMsg(@Query("timeout") int timeout, @Body User user) {
        this.timeout = timeout;
        this.user = user;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public User getUser() {
        return user;
    }
}
