package lorikeet.coding;

import lorikeet.core.Fallible;
import lorikeet.core.Ok;
import lorikeet.lobe.EncodeAgent;
import lorikeet.lobe.ResourceInsignia;
import lorikeet.lobe.UsesCoding;
import lorikeet.lobe.UsesLogging;

import java.util.Optional;

public class TextEncode<R extends UsesLogging & UsesCoding> implements EncodeAgent<R, Fallible<String>> {
    private final Object object;

    public TextEncode(Object object) {
        this.object = object;
    }

    @Override
    public ResourceInsignia resourceInsignia() {
        return null;
    }

    @Override
    public Fallible<String> junction(R resources) {
        return new Ok<>(this.object.toString());
    }

    @Override
    public Optional<String> mediaType() {
        return Optional.of("text/plain");
    }
}
