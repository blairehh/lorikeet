package lorikeet.http.error;

import java.util.Objects;

public class HttpAgentIsMissingSession extends RuntimeException {
    private final Class<?> agentType;

    public HttpAgentIsMissingSession(Class<?> agentType) {
        super(String.format("Http agent '%s' did not have an session", agentType.getCanonicalName()));
        this.agentType = agentType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        HttpAgentIsMissingSession that = (HttpAgentIsMissingSession) o;

        return Objects.equals(this.agentType, that.agentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.agentType);
    }
}
