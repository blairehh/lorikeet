package lorikeet.db;

import lorikeet.Err;
import lorikeet.Seq;
import lorikeet.db.impl.DummyDataOrigin;

public class NoResultQueryPlan<ProductType> implements QueryPlan<ProductType> {

    @Override
    public Err<DataOrigin> selectOrigin(Seq<DataOrigin> dataOrigins) {
        return Err.of(new DummyDataOrigin());
    }

    @Override
    public Seq<ProductType> execute(DataOrigin origin) {
        return Seq.empty();
    }
}
