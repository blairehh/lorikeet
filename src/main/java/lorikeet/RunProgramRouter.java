package lorikeet;

import lorikeet.coding.JsonEncode;
import lorikeet.http.Http200;
import lorikeet.http.HttpEndpoint;
import lorikeet.http.HttpReply;
import lorikeet.http.HttpRouteProvider;
import lorikeet.http.HttpRouter;
import lorikeet.http.IncomingHttpSgnl;
import lorikeet.lobe.Tract;

class RunProgramEndpoint implements HttpEndpoint<Tutorial, IncomingHttpSgnl> {
    @Override
    public HttpReply accept(Tract<Tutorial> tract, IncomingHttpSgnl signal) {
        return new Http200<>("GET REQUEST");
    }
}

class RunProgramPostMsgEndpoint implements HttpEndpoint<Tutorial, RunProgramPostMsg> {
    @Override
    public HttpReply accept(Tract<Tutorial> tract, RunProgramPostMsg runProgramMsg) {
        return new Http200<>(new JsonEncode<>(runProgramMsg.getUser()));
    }
}

class RunProgramMsgEndpoint implements HttpEndpoint<Tutorial, RunProgramMsg> {
    @Override
    public HttpReply accept(Tract<Tutorial> tract, RunProgramMsg runProgramMsg) {
        return new Http200<>("get  " + runProgramMsg.getTimeout());
    }
}

public class RunProgramRouter implements HttpRouteProvider<Tutorial> {
    @Override
    public HttpRouter<Tutorial> router() {
        return new HttpRouter<Tutorial>()
            //.route(new RunProgramMsgController(), RunProgramMsg.class)
            .get("/run-program", new RunProgramEndpoint())
            .route(new RunProgramPostMsgEndpoint(), RunProgramPostMsg.class);
    }

    public HttpReply post(Tract<Tutorial> tract, RunProgramPostMsg runProgramMsg) {
        return new Http200<>(new JsonEncode<>(runProgramMsg.getUser()));
    }

}
