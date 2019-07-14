package lorikeet.db;

import lorikeet.Dict;
import lorikeet.Err;
import lorikeet.Expr;
import lorikeet.Seq;
import lorikeet.error.CouldNotFindDataConnectionConfiguration;
import lorikeet.error.CouldNotFindQueryPlanExecutorForQueryPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
@TODO should throw on critical here in this class
 */
public class QueryDriver {

    private static final Logger log = LoggerFactory.getLogger(QueryDriver.class);

    private final Seq<Object> connConfigurations;
    private final Dict<Class<? extends QueryPlan>, ? extends QueryPlanExecutor<?, ?, ?>> executors;

    public QueryDriver(Seq<Object> connConfigurations, Dict<Class<? extends QueryPlan>, ? extends QueryPlanExecutor<?, ?, ?>> executors) {
        this.connConfigurations = connConfigurations;
        this.executors = executors;
    }

    public final <ProductType, QueryPlanType extends QueryPlan<ProductType>> Seq<ProductType> query(
        QueryPlanType plan
    ) {

        final Err<Seq<ProductType>> result = Expr.weave(
            this.findExecutor(plan),
            (executor -> this.findConfiguration(executor.getConnectionConfigurationType())),
            (executor, config) -> executor.findConnection(config).asErr(),
            (executor, config, conn) -> executor.run(conn, plan)
        );
        return result.orElse(Seq.empty());
    }

    @SuppressWarnings("unchecked")
    private <QueryPlanType, DataConnectionType, ConnectionConfigurationType> Err<QueryPlanExecutor<QueryPlanType, DataConnectionType, ConnectionConfigurationType>> findExecutor(QueryPlan<?> plan) {
           return (Err<QueryPlanExecutor<QueryPlanType, DataConnectionType, ConnectionConfigurationType>>)this.executors
               .find(plan.getClass())
               .asErr(new CouldNotFindQueryPlanExecutorForQueryPlan(plan.getClass()));
    }

    @SuppressWarnings("unchecked")
    private <ConnectionConfigurationType> Err<ConnectionConfigurationType> findConfiguration(Class<ConnectionConfigurationType> klass) {
        return (Err<ConnectionConfigurationType>)this.connConfigurations
            .filter(config -> config.getClass().equals(klass))
            .first()
            .asErr(new CouldNotFindDataConnectionConfiguration(klass));
    }

}
