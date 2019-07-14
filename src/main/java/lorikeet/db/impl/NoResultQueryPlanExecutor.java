package lorikeet.db.impl;

import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.db.NoResultQueryPlan;
import lorikeet.db.QueryPlanExecutor;

public class NoResultQueryPlanExecutor implements QueryPlanExecutor<NoResultQueryPlan, DummyDataConnection, DummyDataConnectionConfiguration> {
    @Override
    public Opt<DummyDataConnection> findConnection(DummyDataConnectionConfiguration config) {
        return Opt.of(new DummyDataConnection());
    }

    @Override
    public <ProductType> Err<Seq<ProductType>> run(DummyDataConnection conn, NoResultQueryPlan plan) {
        return Err.of(Seq.empty());
    }

    @Override
    public Class<DummyDataConnectionConfiguration> getConnectionConfigurationType() {
        return DummyDataConnectionConfiguration.class;
    }
}
