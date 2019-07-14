package lorikeet.db;

import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.Seq;

public interface QueryPlanExecutor<QueryPlanType, DataConnectionType, ConnectionConfigurationType> {
    Opt<DataConnectionType> findConnection(ConnectionConfigurationType repository);
    <ProductType> Err<Seq<ProductType>> run(DataConnectionType conn, QueryPlanType plan);
    Class<ConnectionConfigurationType> getConnectionConfigurationType();
}
