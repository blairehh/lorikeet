package lorikeet.db;

import lorikeet.Seq;

public interface QueryPlan<ProductType> {
    Seq<ProductType> execute();
}
