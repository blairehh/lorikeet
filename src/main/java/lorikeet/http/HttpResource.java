package lorikeet.http;

import lorikeet.lobe.Resource;
import lorikeet.lobe.ResourceInsignia;

public interface HttpResource extends Resource {
    @Override
    default ResourceInsignia insignia() {
        return new HttpServerInsignia();
    }
}
