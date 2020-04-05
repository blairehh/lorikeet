package lorikeet;

import lorikeet.lobe.HttpReceptor;
import lorikeet.lobe.IncomingHttpMsg;
import lorikeet.lobe.HttpLigand;
import lorikeet.lobe.Tract;

public class RunProgramReceptor implements HttpReceptor<Tutorial> {
    @Override
    public HttpLigand ligand() {
        return null;
    }

    @Override
    public void receive(Tract<Tutorial> tract, IncomingHttpMsg signal) {
        tract.invoke(new RunProgram());
    }
}
