package lorikeet.db;

import lorikeet.Err;
import lorikeet.Seq;
import lorikeet.db.impl.StandardRelationalDatabaseType;

public class SqlQueryPlan<ProductType> implements QueryPlan<ProductType> {

    @Override
    public Err<DataOrigin> selectOrigin(Seq<DataOrigin> dataOrigins) {
        final Seq<DataOrigin> relationalDatabases = dataOrigins
            .filter(origin -> origin.getType().equals(new StandardRelationalDatabaseType()));

        return relationalDatabases.filter(DataOrigin::isReadOnly)
            .first() // @TODO change here to random
            .or(relationalDatabases::first)
            .asErr(); // @TODO supply error here
    }

    @Override
    public Seq<ProductType> execute(DataOrigin origin) {

        return null;
    }
}
