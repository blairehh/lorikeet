package lorikeet;

import lorikeet.core.Seq;
import lorikeet.core.SeqOf;
import lorikeet.http.ReceptorBundle;
import lorikeet.lobe.DefaultTract;
import lorikeet.lobe.HttpReceptor;
import lorikeet.lobe.LorikeetAction;
import lorikeet.lobe.ProvidesHttpReceptors;
import lorikeet.lobe.Tract;
import lorikeet.lobe.WriteAgent;

public class TutorialTract implements Tract<Tutorial>, ProvidesHttpReceptors<Tutorial> {
    private final Tract<Tutorial> tract;

    public TutorialTract(Tutorial tutorial) {
        this.tract = new DefaultTract<>(tutorial);
    }

    @Override
    public <O> O write(WriteAgent<Tutorial, O> write) {
        return tract.write(write);
    }

    @Override
    public <O> O invoke(LorikeetAction<Tutorial, O> action) {
        return this.tract.invoke(action);
    }

    @Override
    public void log(String format, Object... vars) {
        this.tract.log(format, vars);
    }

    @Override
    public ReceptorBundle<Tutorial> provideHttpReceptors() {
        return new ReceptorBundle<Tutorial>()
            .add(new RunProgramReceptor());
    }
}
