package lorikeet.http;

import lorikeet.coding.JsonDecode;
import lorikeet.core.FallibleResult;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.http.error.UnableToDecodeHttpRequestBody;
import lorikeet.http.internal.IncomingHttpSgnlStrop2;
import lorikeet.lobe.Tract;
import lorikeet.lobe.UsesCoding;
import lorikeet.lobe.UsesLogging;

public class JsonBody<T, R extends UsesLogging & UsesCoding> implements IncomingHttpSgnlStrop2<T, R> {

    private final Class<T> type;

    public JsonBody(Class<T> type) {
        this.type = type;
    }

    @Override
    public FallibleResult<T, IncomingHttpSgnlError> include(IncomingHttpSgnl request, Tract<R> tract) {
        return tract.decode(new JsonDecode<>(request.body(), this.type))
            .asResult((error) -> new UnableToDecodeHttpRequestBody(this.type, error));
    }
}
