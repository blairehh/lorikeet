package lorikeet;

import lorikeet.coding.JsonEncode;
import lorikeet.http.Http200;
import lorikeet.http.HttpDirective;
import lorikeet.http.HttpMsgReceptor;
import lorikeet.http.HttpNoOp;
import lorikeet.http.HttpReceptor;
import lorikeet.http.HttpReply;
import lorikeet.http.HttpResolve;
import lorikeet.http.HttpRouteProvider;
import lorikeet.http.HttpRouter;
import lorikeet.http.IncomingHttpSgnl;
import lorikeet.lobe.Tract;

class RunProgramReceptor implements HttpReceptor<Tutorial> {
    @Override
    public HttpDirective junction(Tract<Tutorial> tract, IncomingHttpSgnl signal) {
        return new HttpResolve(() -> new HttpNoOp());
    }
}

class RunProgramPostMsgReceptor implements HttpMsgReceptor<Tutorial, RunProgramPostMsg> {
    @Override
    public HttpReply accept(Tract<Tutorial> tract, RunProgramPostMsg runProgramMsg) {
        return new Http200<>(new JsonEncode<>(runProgramMsg.getUser()));
    }
}

class RunProgramMsgReceptor implements HttpMsgReceptor<Tutorial, RunProgramMsg> {
    @Override
    public HttpReply accept(Tract<Tutorial> tract, RunProgramMsg runProgramMsg) {
        return new Http200<>("get  " + runProgramMsg.getTimeout());
    }
}

public class RunProgramRouter implements HttpRouteProvider<Tutorial> {
    @Override
    public HttpRouter<Tutorial> router() {
        return new HttpRouter<Tutorial>()
            .route(new RunProgramMsgReceptor(), RunProgramMsg.class)
            .route(this::post, RunProgramPostMsg.class);
    }

    public HttpReply post(Tract<Tutorial> tract, RunProgramPostMsg runProgramMsg) {
        return new Http200<>(new JsonEncode<>(runProgramMsg.getUser()));
    }

}
