package lorikeet.http.error;

import lorikeet.http.HttpStatus;

import java.util.Objects;

public class BadPathVariableName extends IncomingHttpSgnlError {
    private final String name;

    public BadPathVariableName(String name) {
        super(String.format("Path variable name '%s' is not valid", name));
        this.name = name;
    }

    @Override
    public HttpStatus rejectStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        BadPathVariableName that = (BadPathVariableName) o;

        return Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
