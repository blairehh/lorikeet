package lorikeet.lang;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Package {

    private final List<String> hierarchy;
    private final String str;

    public Package(List<String> hierarchy) {
        this.hierarchy = hierarchy;
        this.str = toString(this.hierarchy);
    }

    public Package(String... hierarchy) {
        this(Arrays.asList(hierarchy));
    }

    private static String toString(List<String> values) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            builder.append(values.get(i));
            if (i != values.size() - 1) {
                builder.append(".");
            }
        }
        return builder.toString();
    }

    public List<String> getHierarchy() {
        return this.hierarchy;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Package that = (Package)o;

        return Objects.equals(this.getHierarchy(), that.getHierarchy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.hierarchy);
    }

    @Override
    public String toString() {
        return this.str;
    }

}
