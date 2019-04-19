package lorikeet;

import lorikeet.web.SunHttpServerEngine;

import java.io.IOException;

public final class Lorikeet {
    public static void run(App app) {
        try {
            if (app.getWebServer() != null) {
                SunHttpServerEngine serverEngine = new SunHttpServerEngine(app.getWebServer());
                serverEngine.start();
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

    }
}
