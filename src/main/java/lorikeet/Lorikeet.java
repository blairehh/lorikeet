package lorikeet;

import lorikeet.web.SunHttpServerEngine;

import java.io.IOException;

public final class Lorikeet {
    public static void run(App app) {
        try {
            if (app.getWebServer() != null) {
                SunHttpServerEngine serverEngine = new SunHttpServerEngine(app.getWebServer());
                serverEngine.start();
                System.out.println("waiting");
                while (true) {

                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new RuntimeException(ioe);
        }
    }
}
