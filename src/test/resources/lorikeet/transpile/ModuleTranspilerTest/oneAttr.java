package app;

public class Lk_module_Database {
    private final db.conn.JdbcConnection f_conn;

    public Lk_module_Database(
        db.conn.JdbcConnection p_conn
    ) {
        f_conn = p_conn;
    }
}
