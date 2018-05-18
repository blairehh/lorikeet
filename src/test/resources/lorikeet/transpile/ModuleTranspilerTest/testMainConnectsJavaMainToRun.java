package app;

public class Lk_module_main {
    private final db.conn.JdbcConnection f_conn;
    private final app.TerminalIO f_io;
    private final logme.Logger f_log;

    public Lk_module_main(
        db.conn.JdbcConnection p_conn,
        app.TerminalIO p_io,
        logme.Logger p_log
    ) {
        f_conn = p_conn;
        f_io = p_io;
        f_log = p_log;
    }

    public lorikeet.core.Int run() {
        return null;
    }

    public static void main(String[] args) {
        new Lk_module_main().run();
    }
}
