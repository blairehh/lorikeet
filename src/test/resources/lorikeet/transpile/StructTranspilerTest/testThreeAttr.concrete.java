package test;

import java.util.Objects;
@SuppressWarnings("unchecked")
public class Lk_struct_User implements User {
    private final lorikeet.core.Str f_name;
    private final test.Role f_role;
    private final type.datetime.Date f_dob;

    public Lk_struct_User(lorikeet.core.Str v_name, test.Role v_role, type.datetime.Date v_dob) {
        f_name = v_name;
        f_role = v_role;
        f_dob = v_dob;
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
    public Lk_struct_User setName(lorikeet.core.Str v_value) {
        return new Lk_struct_User(v_value, f_role, f_dob);
    }

    @Override
    public Lk_struct_User setRole(test.Role v_value) {
        return new Lk_struct_User(f_name, v_value, f_dob);
    }

    @Override
    public Lk_struct_User setDob(type.datetime.Date v_value) {
        return new Lk_struct_User(f_name, f_role, v_value);
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
            && Objects.equals(f_role, that.f_role)
            && Objects.equals(f_dob, that.f_dob)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(f_name, f_role, f_dob);
    }
}
