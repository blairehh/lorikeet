package lorikeet.web;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public final class Filter implements Comparable<Filter> {
    private final HttpMethod method;
    private final String path;
    private final int rank;

    public Filter(HttpMethod method, String path, int rank) {
        this.method = method;
        this.path = path;
        this.rank = rank;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public int getRank() {
        return this.rank;
    }

    @Override
    public int compareTo(Filter filter) {
        if (this.getRank() == -1 && filter.getRank() == -1) {
            return 0;
        }
        if (this.getRank() == -1) {
            return -1;
        }
        if (filter.getRank() == -1) {
            return 1;
        }
        return filter.getRank() - this.getRank();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Filter filter = (Filter) o;

        return Objects.equals(this.getRank(), filter.getRank())
            && Objects.equals(this.getMethod(), filter.getMethod())
            && Objects.equals(this.getPath(), filter.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getMethod(), this.getPath(), this.getRank());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("method", this.getMethod())
            .append("path", this.getPath())
            .append("rank", this.getRank())
            .toString();
    }
}
