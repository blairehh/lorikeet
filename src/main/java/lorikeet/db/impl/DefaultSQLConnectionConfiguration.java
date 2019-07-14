package lorikeet.db.impl;

import lorikeet.Seq;

public class DefaultSQLConnectionConfiguration {

    private final DefaultSQLConnection writeSqlConnection;
    private final Seq<DefaultSQLConnection> readonlyOnlySqlConnections;

    public DefaultSQLConnectionConfiguration(DefaultSQLConnection writeSqlConnection, Seq<DefaultSQLConnection> readonlyOnlySqlConnections) {
        this.writeSqlConnection = writeSqlConnection;
        this.readonlyOnlySqlConnections = readonlyOnlySqlConnections;
    }

    public DefaultSQLConnection getWriteSqlConnection() {
        return this.writeSqlConnection;
    }

    public Seq<DefaultSQLConnection> getReadonlyOnlySqlConnections() {
        return this.readonlyOnlySqlConnections;
    }
}
