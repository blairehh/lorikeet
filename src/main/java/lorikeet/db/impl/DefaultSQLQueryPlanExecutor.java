package lorikeet.db.impl;

import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.db.QueryPlanExecutor;
import lorikeet.db.SQLQueryPlan;

public class DefaultSQLQueryPlanExecutor implements QueryPlanExecutor<SQLQueryPlan, DefaultSQLConnection, DefaultSQLConnectionConfiguration> {

    @Override
    public Opt<DefaultSQLConnection> findConnection(DefaultSQLConnectionConfiguration repository) {
        if (repository.getReadonlyOnlySqlConnections().isEmpty()) {
            return Opt.of(repository.getWriteSqlConnection());
        }
        return repository.getReadonlyOnlySqlConnections().random();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ProductType> Err<Seq<ProductType>> run(DefaultSQLConnection conn, SQLQueryPlan plan) {
        return conn.query(plan.getSql(), plan.getMapper(), plan.getParameters());
    }

    @Override
    public Class<DefaultSQLConnectionConfiguration> getConnectionConfigurationType() {
        return DefaultSQLConnectionConfiguration.class;
    }
}
