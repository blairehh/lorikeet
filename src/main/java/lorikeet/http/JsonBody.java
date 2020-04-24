package lorikeet.http;

import lorikeet.core.FallibleResult;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.http.internal.IncomingHttpSgnlStreamInclude;
import lorikeet.lobe.CodingResource;

public class JsonBody<T> implements IncomingHttpSgnlStreamInclude<T> {

    private final CodingResource coding;
    private final Class<T> type;

    public JsonBody(CodingResource coding, Class<T> type) {
        this.coding = coding;
        this.type = type;
    }

    @Override
    public FallibleResult<T, IncomingHttpSgnlError> include(IncomingHttpSgnl input) {
        this.coding.decodeJsonObject(input.body(), type);
        return null;
    }
}
