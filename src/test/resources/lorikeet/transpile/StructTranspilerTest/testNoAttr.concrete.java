package test;

import java.util.Objects;
@SuppressWarnings("unchecked")
public class Lk_struct_User implements User {

    public Lk_struct_User() {

    }

    @Override
    public boolean equals(Object p_o) {
        if (p_o == this) {
            return true;
        }

        if (p_o == null || !this.getClass().equals(p_o.getClass())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
