package lorikeet.http;

import lorikeet.core.SeqOf;

import java.util.Optional;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE,
    HEAD,
    OPTIONS,
    TRACE;

    public static Optional<HttpMethod> fromString(String value) {
        return new SeqOf<>(HttpMethod.values())
            .pick((method) -> method.name().equalsIgnoreCase(value))
            .first();
    }
}
