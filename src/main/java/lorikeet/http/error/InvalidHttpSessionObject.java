package lorikeet.http.error;

import java.util.Objects;

public class InvalidHttpSessionObject extends RuntimeException {
    private final Class<?> agentType;
    private final Class<?> objectType;

    public InvalidHttpSessionObject(Class<?> agentType, Class<?> objectType) {
        super(String.format(
            "Http agent '%s' received invalid session object of type '%s'",
            agentType.getCanonicalName(),
            objectType.getCanonicalName()
        ));
        this.agentType = agentType;
        this.objectType = objectType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        InvalidHttpSessionObject that = (InvalidHttpSessionObject) o;

        return Objects.equals(this.agentType, that.agentType)
            && Objects.equals(this.objectType, that.objectType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.agentType, this.objectType);
    }
}
