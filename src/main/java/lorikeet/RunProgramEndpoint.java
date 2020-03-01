package lorikeet;

import lorikeet.lobe.HttpMessageFilter;
import lorikeet.lobe.HttpReceptor;
import lorikeet.lobe.HttpSignal;
import lorikeet.lobe.HttpSignalFilter;
import lorikeet.lobe.Tract;

public class RunProgramEndpoint implements HttpReceptor<Tutorial> {
    @Override
    public HttpSignalFilter filter() {
        return new HttpMessageFilter();
    }

    public void process(Tract<Tutorial> tract,  HttpSignal signal) {
        tract.invoke(new RunProgram());
    }
}
