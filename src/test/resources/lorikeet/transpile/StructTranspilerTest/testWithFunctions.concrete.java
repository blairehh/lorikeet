package test;

import java.util.Objects;
@SuppressWarnings("unchecked")
public class Lk_struct_User implements User {
    private final lorikeet.core.Str f_name;

    public Lk_struct_User(lorikeet.core.Str v_name) {
        f_name = v_name;
    }

    @Override
    public lorikeet.core.Str getName() {
        return f_name;
    }

    @Override
    public Lk_struct_User setName(lorikeet.core.Str v_value) {
        return new Lk_struct_User(v_value);
    }

    public lorikeet.core.Str bar() {
        return null;
    }

    @Override
    public boolean equals(Object v_o) {
        if (v_o == this) {
            return true;
        }

        if (v_o == null || !this.getClass().equals(v_o.getClass())) {
            return false;
        }

        Lk_struct_User that = (Lk_struct_User)v_o;

        return (
            Objects.equals(f_name, that.f_name)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(f_name);
    }
}
