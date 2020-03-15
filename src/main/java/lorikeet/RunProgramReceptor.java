package lorikeet;

import lorikeet.lobe.HttpReceptor;
import lorikeet.lobe.HttpSignal;
import lorikeet.lobe.HttpLigand;
import lorikeet.lobe.Tract;

public class RunProgramReceptor implements HttpReceptor<Tutorial> {
    @Override
    public HttpLigand ligand() {
        return null;
    }

    @Override
    public void receive(Tract<Tutorial> tract, HttpSignal signal) {
        tract.invoke(new RunProgram());
    }
}
