package lorikeet.http.error;

import lorikeet.http.HttpStatus;

import java.util.Objects;

// @TODO fix this equals styling
public class UnableToDecodeHttpRequestBody extends IncomingHttpSgnlError {
    private final Class<?> bodyType;

    public UnableToDecodeHttpRequestBody(Class<?> bodyType, Throwable cause) {
        super(String.format("Super could not decode request body to type %s", bodyType.getCanonicalName()), cause);
        this.bodyType = bodyType;
    }

    @Override
    public HttpStatus rejectStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnableToDecodeHttpRequestBody that = (UnableToDecodeHttpRequestBody) o;
        return Objects.equals(bodyType, that.bodyType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bodyType);
    }
}
