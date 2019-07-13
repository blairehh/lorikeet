package lorikeet.db;

import lorikeet.Err;
import lorikeet.Seq;
import lorikeet.db.impl.DummyDataConnection;

public class NoResultQueryPlan<ProductType> implements QueryPlan<ProductType> {


    public Err<DataConnection> selectOrigin(Seq<DataConnection> dataConnections) {
        return Err.of(new DummyDataConnection());
    }

}
