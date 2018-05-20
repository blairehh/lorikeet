package app;

public class Lk_module_main {
    private final db.conn.JdbcConnection f_conn;
    private final app.TerminalIO f_io;
    private final logme.Logger f_log;

    public Lk_module_main(
        db.conn.JdbcConnection v_conn,
        app.TerminalIO v_io,
        logme.Logger v_log
    ) {
        f_conn = v_conn;
        f_io = v_io;
        f_log = v_log;
    }

    public lorikeet.core.Int run() {
        return null;
    }

    public static void main(String[] args) {
        new Lk_module_main().run();
    }
}
