package lorikeet;

import lorikeet.coding.JsonEncode;
import lorikeet.http.Http200;
import lorikeet.http.HttpEndpoint;
import lorikeet.http.HttpReply;
import lorikeet.http.HttpRouteProvider;
import lorikeet.http.HttpRouter;
import lorikeet.http.IncomingHttpSgnl;
import lorikeet.lobe.Tract;

record RunProgramEndpoint(IncomingHttpSgnl request) implements HttpEndpoint<Tutorial> {
    @Override
    public HttpReply accept(Tract<Tutorial> tract) {
        return new Http200<>("GET REQUEST");
    }
}

record RunProgramPostMsgEndpoint(RunProgramPostMsg runProgramMsg) implements HttpEndpoint<Tutorial> {
    @Override
    public HttpReply accept(Tract<Tutorial> tract) {
        return new Http200<>(new JsonEncode<>(runProgramMsg.getUser()));
    }
}

record RunProgramMsgEndpoint(RunProgramMsg runProgramMsg) implements HttpEndpoint<Tutorial> {
    @Override
    public HttpReply accept(Tract<Tutorial> tract) {
        return new Http200<>("get  " + runProgramMsg.getTimeout());
    }
}


public class RunProgramRouter implements HttpRouteProvider<Tutorial> {
    @Override
    public HttpRouter<Tutorial> router() {
        return new HttpRouter<Tutorial>()
            .get("/run-program", RunProgramEndpoint::new)
            .msg(RunProgramPostMsg.class, RunProgramPostMsgEndpoint::new);
    }

    public HttpReply post(Tract<Tutorial> tract, RunProgramPostMsg runProgramMsg) {
        return new Http200<>(new JsonEncode<>(runProgramMsg.getUser()));
    }

}
