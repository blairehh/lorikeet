package lorikeet;

import lorikeet.http.ReceptorBundle;
import lorikeet.lobe.DefaultDiskResource;
import lorikeet.lobe.DefaultTract;
import lorikeet.lobe.DiskResource;
import lorikeet.lobe.LoggingResource;
import lorikeet.lobe.ProvidesHttpReceptors;
import lorikeet.lobe.ProvidesTract;
import lorikeet.lobe.StdOutLoggingResource;
import lorikeet.lobe.Tract;
import lorikeet.lobe.UsesDisk;
import lorikeet.lobe.UsesLogging;
import lorikeet.resource.UndertowResource;
import lorikeet.resource.UsesUndertow;

public class Tutorial implements
    UsesLogging,
    UsesDisk,
    UsesUndertow<Tutorial, Tutorial>,
    ProvidesHttpReceptors<Tutorial>,
    ProvidesTract<Tutorial>
{

    private final UndertowResource<Tutorial, Tutorial> undertowResource;

    public Tutorial(TutorialConfiguration config) {
        this.undertowResource = new UndertowResource<>(config);
    }

    @Override
    public LoggingResource useLogging() {
        return new StdOutLoggingResource();
    }

    @Override
    public DiskResource useDisk() {
        return new DefaultDiskResource();
    }

    @Override
    public UndertowResource<Tutorial, Tutorial> useUndertow() {
        return this.undertowResource;
    }

    @Override
    public ReceptorBundle<Tutorial> provideHttpReceptors() {
        return new ReceptorBundle<Tutorial>()
            .add(new RunProgramMsgReceptor(), RunProgramMsg.class);
    }

    @Override
    public Tract<Tutorial> provideTract() {
        return new DefaultTract<>(this);
    }

    void start() {
        this.undertowResource.start(this, this);
    }
}
