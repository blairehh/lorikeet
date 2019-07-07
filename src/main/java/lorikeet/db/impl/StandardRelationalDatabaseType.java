package lorikeet.db.impl;

import lorikeet.db.DatabaseType;

public class StandardRelationalDatabaseType implements DatabaseType {
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj.getClass().equals(StandardRelationalDatabaseType.class);
    }
}
