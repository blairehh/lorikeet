package lorikeet.db;

import lorikeet.Fun1;

import java.util.Objects;

public class SQLQueryPlan<ProductType> implements QueryPlan<ProductType> {

    private final String sql;
    private final Fun1<Intermediate, ProductType> mapper;
    private final Object[] params;

    public SQLQueryPlan(String sql, Fun1<Intermediate, ProductType> mapper, Object[] params) {
        this.sql = sql;
        this.mapper = mapper;
        this.params = params;
    }

    public SQLQueryPlan(String sql, Fun1<Intermediate, ProductType> mapper) {
        this.sql = sql;
        this.mapper = mapper;
        this.params = new Object[0];
    }

    public String getSql() {
        return this.sql;
    }

    public Object[] getParameters() {
        return this.params;
    }

    public Fun1<Intermediate, ProductType> getMapper() {
        return this.mapper;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        SQLQueryPlan<?> that = (SQLQueryPlan<?>) o;

        return Objects.equals(this.getSql(), that.getSql())
            && Objects.equals(this.getParameters(), that.getParameters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSql(), this.getParameters());
    }
}
