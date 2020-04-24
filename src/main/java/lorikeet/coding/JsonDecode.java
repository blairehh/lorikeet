package lorikeet.coding;

import lorikeet.core.Fallible;
import lorikeet.lobe.DecodeAgent;
import lorikeet.lobe.ResourceInsignia;
import lorikeet.lobe.UsesCoding;
import lorikeet.lobe.UsesLogging;

import java.io.InputStream;

public class JsonDecode<T, R extends UsesLogging & UsesCoding> implements DecodeAgent<R, Fallible<T>> {

    private final InputStream inputStream;
    private final Class<T> type;

    public JsonDecode(InputStream inputStream, Class<T> type) {
        this.inputStream = inputStream;
        this.type = type;
    }

    @Override
    public ResourceInsignia resourceInsignia() {
        return null;
    }

    @Override
    public Fallible<T> junction(R resources) {
        return resources.useCoding().decodeJsonObject(this.inputStream, this.type);
    }
}
