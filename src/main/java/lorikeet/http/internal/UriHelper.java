package lorikeet.http.internal;

import lorikeet.core.Dict;
import lorikeet.core.DictOf;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;

import java.net.URI;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

public class UriHelper {

    public Dict<String, Seq<String>> parseQueryParameters(URI uri) {
        final Map<String, Seq<String>> values = this.splitQuery(uri)
            .entrySet()
            .stream()
            .map((entry) -> Map.entry(entry.getKey(), new SeqOf<>(entry.getValue())))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new DictOf<>(values);
    }

    private Map<String, List<String>> splitQuery(URI uri) {
        if (uri.getQuery() == null || uri.getQuery().isEmpty()) {
            return new DictOf<>();
        }

        return Arrays.stream(uri.getQuery().split("&"))
            .map(this::splitQueryParameter)
            .collect(Collectors.groupingBy(Map.Entry::getKey, LinkedHashMap::new, mapping(Map.Entry::getValue, toList())));
    }

    private Map.Entry<String, String> splitQueryParameter(String it) {
        final int idx = it.indexOf("=");
        final String key = idx > 0 ? it.substring(0, idx) : it;
        final String value = idx > 0 && it.length() > idx + 1 ? it.substring(idx + 1) : null;
        return new AbstractMap.SimpleImmutableEntry<>(key, value);
    }
}
