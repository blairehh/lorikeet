package lorikeet.db.impl;

import lorikeet.db.DataOrigin;
import lorikeet.db.DatabaseType;

public class DummyDataOrigin implements DataOrigin {
    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public DatabaseType getType() {
        return new StandardRelationalDatabaseType();
    }
}
