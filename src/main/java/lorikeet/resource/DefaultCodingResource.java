package lorikeet.resource;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lorikeet.coding.InternetMediaType;
import lorikeet.coding.error.CanNotDecodeOfNullInternetMediaType;
import lorikeet.coding.error.InternetMediaTypeTextPlainMustBeDecodedToString;
import lorikeet.coding.error.UnsupportedInternetMediaType;
import lorikeet.core.Err;
import lorikeet.core.Fallible;
import lorikeet.core.Ok;
import lorikeet.core.StringReader;
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

    @Override
    public <T> Fallible<T> decodeMediaType(InternetMediaType mediaType, InputStream inputStream, Class<T> type) {
        return this.decodeMediaType(mediaType.tree(), inputStream, type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Fallible<T> decodeMediaType(String mediaType, InputStream inputStream, Class<T> type) {
        if (mediaType == null) {
            return new Err<>(new CanNotDecodeOfNullInternetMediaType());
        }
        if (mediaType.equals(InternetMediaType.APPLICATION_JSON.tree())) {
            return this.decodeJsonObject(inputStream, type);
        }
        if (mediaType.equals(InternetMediaType.TEXT_PLAIN.tree())) {
            if (!type.equals(String.class)) {
                return new Err<>(new InternetMediaTypeTextPlainMustBeDecodedToString(type));
            }
            return (Fallible<T>) new StringReader(inputStream)
                .readAll();
        }
        return new Err<>(new UnsupportedInternetMediaType(mediaType));
    }
}
