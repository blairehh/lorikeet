package lorikeet.db;

public interface DataOrigin {
    boolean isReadOnly();
    DatabaseType getType();
}
