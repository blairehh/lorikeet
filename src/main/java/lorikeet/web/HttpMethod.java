package lorikeet.web;

import java.util.Optional;
import java.util.stream.Stream;

public enum HttpMethod {
    GET         ("GET"),
    POST        ("POST"),
    PUT         ("PUT"),
    DELETE      ("DELETE");

    private final String value;

    private HttpMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static Optional<HttpMethod> find(String value) {
        if (value == null) {
            return Optional.empty();
        }
        return Stream.of(HttpMethod.values())
            .filter(method -> method.getValue().equals(value.trim().toUpperCase()))
            .findFirst();
    }
}
