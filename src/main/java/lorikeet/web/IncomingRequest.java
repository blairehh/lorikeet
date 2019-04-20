package lorikeet.web;

import lorikeet.Dict;
import lorikeet.Opt;
import lorikeet.Seq;
import org.apache.commons.lang3.math.NumberUtils;

import java.net.URI;

public interface IncomingRequest {
    URI getURI();
    HttpMethod getMethod();
    Dict<String, Seq<String>> getHeaders();
    Dict<String, String> getPathVariables();

    default boolean hasHeader(String header) {
        return this.getHeaders().containsKey(header);
    }

    default Seq<String> findHeader(String header) {
        return this.getHeaders().getOrDefault(header, Seq.empty());
    }

    default Seq<String> findHeaderOr(String header, String defaultValue) {
        return this.getHeaders().getOrDefault(header, Seq.of(defaultValue));
    }

    default Opt<String> findFirstHeader(String header) {
        return this.findHeader(header).first();
    }

    default String findFirstHeaderOr(String header, String defaultValue) {
        return this.findFirstHeader(header).orElse(defaultValue);
    }


    default Opt<String> findPathVariable(String name) {
        return this.getPathVariables().find(name);
    }

    default Opt<Number> findNumberPathVariable(String name) {
        return this.findPathVariable(name)
            .filter(NumberUtils::isCreatable)
            .map(NumberUtils::createNumber);
    }
}
