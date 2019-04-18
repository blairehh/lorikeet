package lorikeet;

import lorikeet.web.WebServer;

public interface App {
    public default WebServer getWebServer() {
        return null;
    }
}
