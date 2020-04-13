package lorikeet.http;

import lorikeet.lobe.ResourceInsignia;
import lorikeet.lobe.UsesHttpServer;
import lorikeet.lobe.WriteAgent;

public interface HttpWrite<R extends UsesHttpServer> extends WriteAgent<R, HttpWriteResult>, HttpReply {
    @Override
    default ResourceInsignia resourceInsignia() {
        return new HttpServerInsignia();
    }
}
