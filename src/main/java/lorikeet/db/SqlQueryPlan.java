package lorikeet.db;

import lorikeet.Fun;

public class SqlQueryPlan<ProductType> implements QueryPlan<ProductType> {

    private final String sql;
    private final Fun<Intermediate, ProductType> mapper;
    private final Object[] params;

    public SqlQueryPlan(String sql, Fun<Intermediate, ProductType> mapper, Object[] params) {
        this.sql = sql;
        this.mapper = mapper;
        this.params = params;
    }

    public SqlQueryPlan(String sql, Fun<Intermediate, ProductType> mapper) {
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

    public Fun<Intermediate, ProductType> getMapper() {
        return this.mapper;
    }

}
