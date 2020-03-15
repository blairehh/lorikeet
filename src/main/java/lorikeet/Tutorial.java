package lorikeet;

import lorikeet.lobe.DefaultDiskResource;
import lorikeet.lobe.DiskResource;
import lorikeet.lobe.LoggingResource;
import lorikeet.lobe.StdOutLoggingResource;
import lorikeet.lobe.UsesDisk;
import lorikeet.lobe.UsesLogging;
import lorikeet.resource.UndertowResource;
import lorikeet.resource.UsesUndertow;

public class Tutorial implements UsesLogging, UsesDisk, UsesUndertow {

    private final UndertowResource<TutorialTract> undertowResource;

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
    public UndertowResource useUndertow() {
        return this.undertowResource;
    }

    void start(TutorialTract tract) {
        this.undertowResource.start(tract);
    }
}
