package test;
import java.util.Objects;
public interface User {
    public lorikeet.core.Str getName();
    public test.Role getRole();
    public type.datetime.Date getDob();
    public <T extends User> T setName(lorikeet.core.Str value);
    public <T extends User> T setRole(test.Role value);
    public <T extends User> T setDob(type.datetime.Date value);
}
