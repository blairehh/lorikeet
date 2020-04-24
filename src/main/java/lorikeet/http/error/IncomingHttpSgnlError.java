package lorikeet.http.error;

import lorikeet.http.HttpStatus;

public abstract class IncomingHttpSgnlError extends RuntimeException {
    public IncomingHttpSgnlError() {
        super();
    }

    public IncomingHttpSgnlError(String message) {
        super(message);
    }

    public IncomingHttpSgnlError(String message, Throwable cause) {
        super(message, cause);
    }

    public IncomingHttpSgnlError(Throwable cause) {
        super(cause);
    }

    protected IncomingHttpSgnlError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    abstract public HttpStatus rejectStatus();
}
