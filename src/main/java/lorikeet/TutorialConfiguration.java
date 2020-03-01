package lorikeet;

import lorikeet.resource.ConfiguresUndertow;
import lorikeet.resource.UndertowConfig;

public class TutorialConfiguration implements ConfiguresUndertow {

    @Override
    public UndertowConfig configureUndertowServer() {
        return new UndertowConfig(8080, "localhost");
    }
}