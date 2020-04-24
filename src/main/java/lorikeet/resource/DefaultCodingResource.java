package lorikeet.resource;

import com.google.gson.Gson;
import lorikeet.core.Fallible;
import lorikeet.core.Ok;
import lorikeet.lobe.CodingResource;

import java.io.InputStream;
import java.io.InputStreamReader;

public class DefaultCodingResource implements CodingResource {
    private final Gson gson;

    public DefaultCodingResource() {
        this.gson = new Gson();
    }

    public DefaultCodingResource(Gson gson) {
        this.gson = gson;
    }

    // @TODO catch JsonSyntaxException
    @Override
    public <T> Fallible<T> decodeJsonObject(InputStream input, Class<T> type) {
        return new Ok<>(this.gson.fromJson(new InputStreamReader(input), type));
    }

    @Override
    public <T> Fallible<String> encodeJsonString(Object object, Class<T> type) {
        return new Ok<>(this.gson.toJson(object));
    }
}
