package lorikeet.db;

public interface DataConnection {
    boolean isReadOnly();
    DatabaseType getType();
}
