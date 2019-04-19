package lorikeet.web;

import lorikeet.Dict;
import org.apache.commons.lang3.math.NumberUtils;

import java.net.URI;
import java.util.Optional;

public interface IncomingRequest {
    public URI getURI();
    public HttpMethod getMethod();
    public HttpHeaders getHeaders();
    public Dict<String, String> getPathVariables();

    default Optional<String> getPathVariable(String name) {
        return this.getPathVariables().find(name);
    }

    default Optional<Number> getNumberPathVariable(String name) {
        return this.getPathVariable(name)
            .filter(NumberUtils::isCreatable)
            .map(NumberUtils::createNumber);
    }
}
