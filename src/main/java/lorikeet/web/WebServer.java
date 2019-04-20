package lorikeet.web;

public interface WebServer {
    WebRouter getRouter();

    default int getPort() {
        return 8080;
    }
}
