package lorikeet;

import lorikeet.lobe.DefaultDiskResource;
import lorikeet.lobe.DiskResource;
import lorikeet.lobe.LoggingResource;
import lorikeet.lobe.StdOutLoggingResource;
import lorikeet.lobe.UsesDisk;
import lorikeet.lobe.UsesLogging;

public class Tutorial implements UsesLogging, UsesDisk {

    public Tutorial(TutorialConfiguration config) {

    }

    @Override
    public LoggingResource useLogging() {
        return new StdOutLoggingResource();
    }

    @Override
    public DiskResource useDisk() {
        return new DefaultDiskResource();
    }
}
