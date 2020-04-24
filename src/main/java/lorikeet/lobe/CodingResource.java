package lorikeet.lobe;

import lorikeet.coding.InternetMediaType;
import lorikeet.core.Fallible;

import java.io.InputStream;

public interface CodingResource {
    <T> Fallible<T> decodeJsonObject(InputStream input, Class<T> type);
    <T> Fallible<String> encodeJsonString(Object object, Class<T> type);

    <T> Fallible<T> decodeMediaType(InternetMediaType mediaType, InputStream inputStream, Class<T> type);
    <T> Fallible<T> decodeMediaType(String mediaType, InputStream inputStream, Class<T> type);
}