package lorikeet;

import lorikeet.http.HttpDirective;
import lorikeet.http.HttpResolve;
import lorikeet.http.HttpReceptor;
import lorikeet.http.IncomingHttpSgnl;
import lorikeet.lobe.Tract;


public class RunProgramReceptor implements HttpReceptor<Tutorial> {
    @Override
    public HttpDirective junction(Tract<Tutorial> tract, IncomingHttpSgnl signal) {
        return new HttpResolve(() -> System.out.println("you have hit the run program receptor!!!!!"));
    }
}
