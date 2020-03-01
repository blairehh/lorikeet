package lorikeet.resource;

public class UndertowConfig {
    private final int port;
    private final String host;

    public UndertowConfig(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public int port() {
        return this.port;
    }

    public String host() {
        return this.host;
    }
}
