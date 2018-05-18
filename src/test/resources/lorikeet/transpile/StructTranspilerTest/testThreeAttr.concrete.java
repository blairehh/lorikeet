package test;

import java.util.Objects;
@SuppressWarnings("unchecked")
public class Lk_struct_User implements User {
    private final lorikeet.core.Str f_name;
    private final test.Role f_role;
    private final type.datetime.Date f_dob;

    public Lk_struct_User(lorikeet.core.Str p_name, test.Role p_role, type.datetime.Date p_dob) {
        f_name = p_name;
        f_role = p_role;
        f_dob = p_dob;
    }

    @Override
    public lorikeet.core.Str getName() {
        return f_name;
    }

    @Override
    public test.Role getRole() {
        return f_role;
    }

    @Override
    public type.datetime.Date getDob() {
        return f_dob;
    }

    @Override
    public Lk_struct_User setName(lorikeet.core.Str p_value) {
        return new Lk_struct_User(p_value, f_role, f_dob);
    }

    @Override
    public Lk_struct_User setRole(test.Role p_value) {
        return new Lk_struct_User(f_name, p_value, f_dob);
    }

    @Override
    public Lk_struct_User setDob(type.datetime.Date p_value) {
        return new Lk_struct_User(f_name, f_role, p_value);
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
            && Objects.equals(f_role, that.f_role)
            && Objects.equals(f_dob, that.f_dob)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(f_name, f_role, f_dob);
    }
}
