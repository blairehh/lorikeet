package lorikeet.web;

import java.net.URI;

public interface IncomingRequest {
    public URI getURI();
    public HttpMethod getMethod();
    public HttpHeaders getHeaders();
}
