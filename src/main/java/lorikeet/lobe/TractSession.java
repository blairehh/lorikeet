package lorikeet.lobe;

import java.util.Objects;

public class TractSession {
    private final ResourceInsignia resourceInsignia;
    private final Object sessionObject;

    public TractSession(ResourceInsignia resourceInsignia, Object sessionObject) {
        this.resourceInsignia = resourceInsignia;
        this.sessionObject = sessionObject;
    }

    public ResourceInsignia resourceInsignia() {
        return this.resourceInsignia;
    }

    public Object sessionObject() {
        return this.sessionObject;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        TractSession that = (TractSession) o;

        return Objects.equals(this.resourceInsignia(), that.resourceInsignia())
            && Objects.equals(this.sessionObject(), that.sessionObject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.resourceInsignia(), this.sessionObject());
    }
}
