package lorikeet.db;

import lorikeet.Seq;

public interface QueryDriver {
     <ProductType, QueryPlanType extends QueryPlan<ProductType>> Seq<ProductType> query(QueryPlanType plan);
}
