package lorikeet.db;

import lorikeet.Seq;

public class NoResultQueryPlan<ProductType> implements QueryPlan<ProductType> {
    @Override
    public Seq<ProductType> execute() {
        return Seq.empty();
    }
}
