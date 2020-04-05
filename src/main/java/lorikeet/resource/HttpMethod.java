package lorikeet.resource;

import lorikeet.core.SeqOf;

import java.util.Optional;

public enum HttpMethod {
    GET,
    POST,
    PATCH,
    DELETE;


    public static Optional<HttpMethod> fromString(String value) {
        return new SeqOf<>(HttpMethod.values())
            .pick((method) -> method.name().equals(value))
            .first();
    }
}
