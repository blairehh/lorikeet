package lorikeet;

import lorikeet.lobe.ProvidesDisk;
import lorikeet.lobe.LoggingResource;
import lorikeet.lobe.ProvidesLogging;
import lorikeet.lobe.StdOutLoggingResource;

public class Tutorial implements ProvidesLogging, ProvidesDisk {

    @Override
    public LoggingResource provideLogging() {
        return new StdOutLoggingResource();
    }
}