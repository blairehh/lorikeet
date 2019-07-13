package lorikeet.db.impl;

import lorikeet.Err;
import lorikeet.Fun;
import lorikeet.Seq;
import lorikeet.db.DataConnection;
import lorikeet.db.DatabaseType;
import lorikeet.db.Intermediate;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultSQLConnection implements DataConnection {
    private final QueryRunner queryRunner;
    private final boolean isReadOnly;

    public DefaultSQLConnection(DataSource dataSource, boolean isReadOnly) {
        this.queryRunner = new QueryRunner(dataSource);
        this.isReadOnly = isReadOnly;
    }

    @Override
    public boolean isReadOnly() {
        return this.isReadOnly;
    }

    @Override
    public DatabaseType getType() {
        return new StandardRelationalDatabaseType();
    }

    public <T> Err<Number> insertWithAutoID(String statement, Object... params) {
        try {
            return Err.of(this.queryRunner.insert(statement, new ScalarHandler<Number>(), params));
        } catch (SQLException e) {
            return Err.failure(e);
        }
    }

    public Err<Boolean> insert(String statement, Object... params) {
        try {
            this.queryRunner.insert(statement, (a -> ""), params);
            return Err.of(true);
        } catch (SQLException e) {
            return Err.failure(e);
        }
    }

    public Err<Integer> update(String statement, Object... params) {
        try {
            return Err.of(this.queryRunner.update(statement, params));
        } catch (SQLException e) {
            return Err.failure(e);
        }
    }

    public <T> Seq<T> query(String query, Fun<Intermediate, T> map, Object... params) {
        final ResultSetHandler<T> handler = resultSet -> {
            resultSet.next();
            return map.apply(new ResultSetIntermediate(resultSet));
        };
        try {
            return Seq.of(this.queryRunner.execute(query, handler, params));
        } catch (SQLException e) {
            return new Seq<>(e);
        }
    }

    public Err<Integer> execute(String sql, boolean multiStatement) {
        if (multiStatement) {
            final List<String> queries = Arrays.stream(sql.split(";"))
                .map(query -> query.replaceAll("\n", ""))
                .filter(query -> !query.isEmpty())
                .collect(Collectors.toList());

            int tally = 0;
            for (String query : queries) {
                final Err<Integer> result = this.execute(query, false);
                if (!result.isPresent()) {
                    return result;
                }
                tally += result.orPanic();
            }

            return Err.of(tally);
        }
        try {
            return Err.of(this.queryRunner.execute(sql));
        } catch (SQLException e) {
            return Err.failure(e);
        }
    }
}
