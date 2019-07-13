package lorikeet.db;

import lorikeet.Opt;
import lorikeet.Seq;

public interface QueryPlanExecutor<QueryPlanType, DataConnectionType, ConnectionConfigurationType> {
    Opt<DataConnectionType> findConnection(ConnectionConfigurationType repository);
    <ProductType> Seq<ProductType> run(DataConnectionType conn, QueryPlanType plan);
    Class<QueryPlanType> getQueryPlanType();
    Class<ConnectionConfigurationType> getConnectionConfigurationType();
}
