package lorikeet.resource;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lorikeet.core.Err;
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

    @Override
    public <T> Fallible<T> decodeJsonObject(InputStream input, Class<T> type) {
        try {
            return new Ok<>(this.gson.fromJson(new InputStreamReader(input), type));
        } catch (JsonSyntaxException e) {
            return new Err<>(e);
        }
    }

    @Override
    public <T> Fallible<String> encodeJsonString(Object object, Class<T> type) {
        return new Ok<>(this.gson.toJson(object));
    }
}
