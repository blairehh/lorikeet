package lorikeet.db.impl;

import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.db.QueryPlanExecutor;
import lorikeet.db.SqlQueryPlan;

public class DefaultSQLQueryPlanExecutor implements QueryPlanExecutor<SqlQueryPlan, DefaultSQLConnection, DefaultConnectionConfiguration> {

    @Override
    public Opt<DefaultSQLConnection> findConnection(DefaultConnectionConfiguration repository) {
        if (repository.getReadonlyOnlySqlConnections().isEmpty()) {
            return Opt.of(repository.getWriteSqlConnection());
        }
        return repository.getReadonlyOnlySqlConnections().random();
    }

    @Override
    public <ProductType> Seq<ProductType> run(DefaultSQLConnection conn, SqlQueryPlan plan) {
        return conn.query(plan.getSql(), plan.getMapper(), plan.getParameters());
    }

    @Override
    public Class<SqlQueryPlan> getQueryPlanType() {
        return SqlQueryPlan.class;
    }

    @Override
    public Class<DefaultConnectionConfiguration> getConnectionConfigurationType() {
        return DefaultConnectionConfiguration.class;
    }
}
