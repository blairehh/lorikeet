package test;

import java.util.Objects;
@SuppressWarnings("unchecked")
public class Lk_struct_User implements User {

    public Lk_struct_User() {

    }

    @Override
    public boolean equals(Object v_o) {
        if (v_o == this) {
            return true;
        }

        if (v_o == null || !this.getClass().equals(v_o.getClass())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
