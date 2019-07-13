package lorikeet.db;

import lorikeet.Seq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Predicate;

public class QueryDriver {

    private static final Logger log = LoggerFactory.getLogger(QueryDriver.class);

    private final Object connConfigurations;
    private final Seq<QueryPlanExecutor<?, ?, ?>> executors;

    public QueryDriver(Object connConfigurations, Seq<QueryPlanExecutor<?, ?, ?>> executors) {
        this.connConfigurations = connConfigurations;
        this.executors = executors;
    }

    public final <ProductType, QueryPlanType extends QueryPlan<ProductType>, DataConnectionType, ConnectionConfigurationType> Seq<ProductType> query(
        QueryPlanType plan
    ) {
        final Predicate<QueryPlanExecutor<?,?,?>> isApplicableExecutor = (executor ->
            executor.getQueryPlanType().equals(plan.getClass())
                && executor.getConnectionConfigurationType().equals(this.connConfigurations.getClass())
        );

        final Seq<QueryPlanExecutor<?, ?, ?>> applicableExecutors = this.executors
            .filter(isApplicableExecutor);

        if (applicableExecutors.isEmpty()) {
            log.error("could not find executor for QueryPlan {}, returning empty Seq", plan.getClass());
            return Seq.empty(); // @TODO supply error here
        }

        if (applicableExecutors.size() != 1) {
            log.warn("found more than one executor for QueryPlan {}, will use first executor", plan.getClass());
        }

        final QueryPlanExecutor<QueryPlanType, DataConnectionType, ConnectionConfigurationType> executor = (QueryPlanExecutor<QueryPlanType, DataConnectionType, ConnectionConfigurationType>)applicableExecutors.first().orPanic();

        final ConnectionConfigurationType config = executor.getConnectionConfigurationType().cast(this.connConfigurations);
        final DataConnectionType conn = executor.findConnection(config).orPanic();

        return executor.run(conn, plan);
    }
}
