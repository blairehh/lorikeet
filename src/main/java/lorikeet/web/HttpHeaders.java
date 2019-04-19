package lorikeet.web;

import com.sun.net.httpserver.Headers;
import lorikeet.Dict;
import lorikeet.Seq;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public final class HttpHeaders {
    private final Dict<String, Seq<String>> headers;

    public HttpHeaders(Headers httpHeaders) {
        final HashMap<String, Seq<String>> map = new HashMap<>();
        httpHeaders.forEach((header, values) -> map.put(header, Seq.of(values)));
        this.headers = Dict.of(map);
    }

    public static HttpHeaders none() {
        return new HttpHeaders(new Headers());
    }

    public boolean has(String header) {
        return this.headers.containsKey(header);
    }

    public Seq<String> find(String header) {
        return this.headers.getOrDefault(header, Seq.empty());
    }

    public Seq<String> findOr(String header, String defaultValue) {
        return this.headers.getOrDefault(header, Seq.of(defaultValue));
    }

    public Optional<String> findFirst(String header) {
        return this.find(header).first();
    }

    public String findFirstOr(String header, String defaultValue) {
        return this.find(header).first().orElse(defaultValue);
    }

    public Dict<String, Seq<String>> asDict() {
        return this.headers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        HttpHeaders that = (HttpHeaders) o;

        return Objects.equals(this.asDict(), that.asDict());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.asDict());
    }
}
