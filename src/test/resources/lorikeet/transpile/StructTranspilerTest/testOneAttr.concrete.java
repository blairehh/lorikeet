package test;

import java.util.Objects;
@SuppressWarnings("unchecked")
public class Lk_struct_User implements User {
    private final lorikeet.core.Str f_name;

    public Lk_struct_User(lorikeet.core.Str p_name) {
        f_name = p_name;
    }

    @Override
    public lorikeet.core.Str getName() {
        return f_name;
    }

    @Override
    public Lk_struct_User setName(lorikeet.core.Str p_value) {
        return new Lk_struct_User(p_value);
    }

    @Override
    public boolean equals(Object p_o) {
        if (p_o == this) {
            return true;
        }

        if (p_o == null || !this.getClass().equals(p_o.getClass())) {
            return false;
        }

        Lk_struct_User that = (Lk_struct_User)p_o;

        return (
            Objects.equals(f_name, that.f_name)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(f_name);
    }
}
