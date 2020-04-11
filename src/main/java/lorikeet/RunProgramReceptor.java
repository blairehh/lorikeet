package lorikeet;

import lorikeet.core.Fallible;
import lorikeet.lobe.HttpReceptor;
import lorikeet.lobe.IncomingHttpMsg;
import lorikeet.lobe.Tract;


public class RunProgramReceptor implements HttpReceptor<Tutorial> {
    @Override
    public Fallible<Runnable> junction(Tract<Tutorial> tract, IncomingHttpMsg signal) {
        return null;
    }
}
