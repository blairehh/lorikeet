package lorikeet;

import lorikeet.http.HttpDirective;
import lorikeet.http.HttpReject;
import lorikeet.lobe.HttpReceptor;
import lorikeet.lobe.IncomingHttpMsg;
import lorikeet.lobe.Tract;


public class RunProgramReceptor implements HttpReceptor<Tutorial> {
    @Override
    public HttpDirective junction(Tract<Tutorial> tract, IncomingHttpMsg signal) {
        return new HttpReject();
    }
}
