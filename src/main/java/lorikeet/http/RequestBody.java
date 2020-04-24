package lorikeet.http;

import lorikeet.coding.DecodeInternetMediaType;
import lorikeet.coding.InternetMediaType;
import lorikeet.core.FallibleResult;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.http.error.UnableToDecodeHttpRequestBody;
import lorikeet.http.internal.IncomingHttpSgnlStrop2;
import lorikeet.lobe.Tract;
import lorikeet.lobe.UsesCoding;
import lorikeet.lobe.UsesLogging;

public class RequestBody<T, R extends UsesLogging & UsesCoding> implements IncomingHttpSgnlStrop2<T, R> {
    private final String mediaType;
    private final Class<T> type;

    public RequestBody(String mediaType, Class<T> type) {
        this.mediaType = mediaType;
        this.type = type;
    }

    public RequestBody(InternetMediaType mediaType, Class<T> type) {
        this.mediaType = mediaType.tree();
        this.type = type;
    }

    @Override
    public FallibleResult<T, IncomingHttpSgnlError> include(IncomingHttpSgnl request, Tract<R> tract) {
        return tract.decode(new DecodeInternetMediaType<>(this.mediaType, request.body(), this.type))
            .asResult((error) -> new UnableToDecodeHttpRequestBody(this.type, error));
    }
}
