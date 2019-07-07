package lorikeet.db;

import lorikeet.Err;
import lorikeet.Seq;

public interface QueryPlan<ProductType> {
    Err<DataOrigin> selectOrigin(Seq<DataOrigin> dataOrigins);
    Seq<ProductType> execute(DataOrigin origin);
}
