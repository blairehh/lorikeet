package lorikeet.lobe;

import lorikeet.db.QueryPlan;

public interface Query1<ProductType, VariableType> {
    QueryPlan<ProductType> getQuery(VariableType variable);
}
