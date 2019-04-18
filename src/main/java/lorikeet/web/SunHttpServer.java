package lorikeet.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SunHttpServer {

    private final HttpServer server;

    public SunHttpServer(WebServer webserver) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(1111), 0);
        for (Mapping mapping : webserver.getRouter().getDispatcher().getMappings()) {
            this.server.createContext(mapping.getPath(), new NoContentEndpoint());
        }
    }

    public static class NoContentEndpoint implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            os.write("".getBytes());
            os.close();
        }
    }

}
