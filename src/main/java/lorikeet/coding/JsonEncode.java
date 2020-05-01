package lorikeet.coding;

import lorikeet.core.Fallible;
import lorikeet.lobe.EncodeAgent;
import lorikeet.lobe.ResourceInsignia;
import lorikeet.lobe.UsesCoding;
import lorikeet.lobe.UsesLogging;

import java.util.Optional;

public class JsonEncode<R extends UsesLogging & UsesCoding> implements EncodeAgent<R, Fallible<String>> {
    private final Object object;

    public JsonEncode(Object object) {
        this.object = object;
    }

    @Override
    public Optional<String> mediaType() {
        return Optional.of("application/json");
    }

    @Override
    public ResourceInsignia resourceInsignia() {
        return null;
    }

    @Override
    public Fallible<String> junction(R resources) {
        return resources.useCoding().encodeJsonString(object, object.getClass());
    }
}