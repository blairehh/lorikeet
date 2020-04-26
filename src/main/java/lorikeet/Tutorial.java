package lorikeet;

import lorikeet.http.HttpRouter;
import lorikeet.lobe.CodingResource;
import lorikeet.lobe.DefaultDiskResource;
import lorikeet.lobe.DefaultTract;
import lorikeet.lobe.DiskResource;
import lorikeet.lobe.LoggingResource;
import lorikeet.lobe.ProvidesHttpRouter;
import lorikeet.lobe.ProvidesTract;
import lorikeet.lobe.StdOutLoggingResource;
import lorikeet.lobe.Tract;
import lorikeet.lobe.UsesCoding;
import lorikeet.lobe.UsesDisk;
import lorikeet.lobe.UsesLogging;
import lorikeet.resource.DefaultCodingResource;
import lorikeet.resource.UndertowResource;
import lorikeet.resource.UsesUndertow;

public class Tutorial implements
    UsesLogging,
    UsesDisk,
    UsesCoding,
    UsesUndertow<Tutorial, Tutorial>,
    ProvidesHttpRouter<Tutorial>,
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
    public CodingResource useCoding() {
        return new DefaultCodingResource();
    }

    @Override
    public UndertowResource<Tutorial, Tutorial> useUndertow() {
        return this.undertowResource;
    }

    @Override
    public HttpRouter<Tutorial> httpRouter() {
        return new HttpRouter<Tutorial>()
            .include(new RunProgramRouter());
    }

    @Override
    public Tract<Tutorial> provideTract() {
        return new DefaultTract<>(this);
    }

    void start() {
        this.undertowResource.start(this, this);
    }
}
