package lorikeet.http.error;

import java.util.Objects;

public class PathVarNotFound extends RuntimeException {
    private final String pathVarName;

    public PathVarNotFound(String pathVarName) {
        super(String.format("Path variable '%s' was not found", pathVarName));
        this.pathVarName = pathVarName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        PathVarNotFound that = (PathVarNotFound) o;

        return Objects.equals(this.pathVarName, that.pathVarName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.pathVarName);
    }
}
