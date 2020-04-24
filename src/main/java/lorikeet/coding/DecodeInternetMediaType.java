package lorikeet.coding;

import lorikeet.core.Fallible;
import lorikeet.lobe.DecodeAgent;
import lorikeet.lobe.ResourceInsignia;
import lorikeet.lobe.UsesCoding;
import lorikeet.lobe.UsesLogging;

import java.io.InputStream;

public class DecodeInternetMediaType<T, R extends UsesLogging & UsesCoding> implements DecodeAgent<R, Fallible<T>> {

    private final String mediaType;
    private final InputStream inputStream;
    private final Class<T> type;

    public DecodeInternetMediaType(String mediaType, InputStream inputStream, Class<T> type) {
        this.mediaType = mediaType;
        this.inputStream = inputStream;
        this.type = type;
    }

    public DecodeInternetMediaType(InternetMediaType mediaType, InputStream inputStream, Class<T> type) {
        this.mediaType = mediaType.tree();
        this.inputStream = inputStream;
        this.type = type;
    }

    @Override
    public ResourceInsignia resourceInsignia() {
        return null;
    }

    @Override
    public Fallible<T> junction(R resources) {
        return resources.useCoding().decodeMediaType(this.mediaType, this.inputStream, this.type);
    }
}
