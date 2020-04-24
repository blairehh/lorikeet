package lorikeet.lobe;

import lorikeet.core.Fallible;

import java.io.InputStream;

public interface CodingResource {
    <T> Fallible<T> decodeJsonObject(InputStream input, Class<T> type);
    <T> Fallible<String> encodeJsonString(Object object, Class<T> type);
}