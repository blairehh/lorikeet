package lorikeet.db.impl;

import lorikeet.db.DataConnection;
import lorikeet.db.DatabaseType;

public class DummyDataConnection implements DataConnection {
    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public DatabaseType getType() {
        return new StandardRelationalDatabaseType();
    }
}
