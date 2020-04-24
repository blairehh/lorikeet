package lorikeet;

import lorikeet.lobe.CodingResource;
import lorikeet.lobe.LoggingResource;
import lorikeet.lobe.StdOutLoggingResource;
import lorikeet.lobe.UsesCoding;
import lorikeet.lobe.UsesLogging;
import lorikeet.resource.DefaultCodingResource;

public class TestResources implements UsesLogging, UsesCoding {

    @Override
    public CodingResource useCoding() {
        return new DefaultCodingResource();
    }

    @Override
    public LoggingResource useLogging() {
        return new StdOutLoggingResource();
    }
}
