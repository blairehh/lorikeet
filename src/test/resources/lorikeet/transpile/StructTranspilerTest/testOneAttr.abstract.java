package test;
import java.util.Objects;
public interface User {
    public lorikeet.core.Str getName();
    public <T extends User> T setName(lorikeet.core.Str value);
}
