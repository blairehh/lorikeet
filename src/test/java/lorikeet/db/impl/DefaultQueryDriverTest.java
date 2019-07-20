package lorikeet.db.impl;

import lorikeet.Dict;
import lorikeet.Fun1;
import lorikeet.IO;
import lorikeet.Seq;
import lorikeet.db.Intermediate;
import lorikeet.db.NoResultQueryPlan;
import lorikeet.db.QueryPlan;
import lorikeet.db.QueryPlanExecutor;
import lorikeet.db.SQLQueryPlan;
import lorikeet.error.CouldNotFindQueryPlanExecutorForQueryPlan;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultQueryDriverTest {

    private static final String SQL_SCHEMA = IO.readResource("sql/test_db_schema.sql").orPanic();

    private BasicDataSource dataSource;
    private DefaultSQLConnection conn;
    private DefaultQueryDriver queryDriver;

    @Before
    public void setUp() {
        if (this.dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setUrl("jdbc:mysql://localhost:33060/lorikeet");
            dataSource.setUsername("root");
            dataSource.setPassword("root");
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

            this.conn = new DefaultSQLConnection(this.dataSource, false);
        }
        this.conn.execute(SQL_SCHEMA, true).orPanic();

        DefaultSQLConnectionConfiguration connConfig = new DefaultSQLConnectionConfiguration(conn, Seq.of(conn));
        Dict<Class<? extends QueryPlan>, QueryPlanExecutor<?, ?, ?>> executors = Dict.empty();
        executors = executors.push(SQLQueryPlan.class, new DefaultSQLQueryPlanExecutor());
        executors = executors.push(NoResultQueryPlan.class, new NoResultQueryPlanExecutor());

        this.queryDriver = new DefaultQueryDriver(Seq.of(connConfig, new DummyDataConnectionConfiguration()), executors);
    }

    @Test
    public void testQuery() {
        conn.insert("INSERT INTO customers(name, telephone, email, address) VALUES ('Joe Doe', '12345678', 'bob@doe.com', '1 Maple Street')");

        Fun1<Intermediate, Customer> mapper = (
            resultSet -> new Customer(
                resultSet.integer("id").orPanic(),
                resultSet.string("name").orPanic(),
                resultSet.string("telephone").orPanic(),
                resultSet.string("email").orPanic(),
                resultSet.string("address").orPanic()
            )
        );

        SQLQueryPlan<Customer> plan = new SQLQueryPlan<>(
            "SELECT * FROM customers",
            mapper,
            null
        );

        Seq<Customer> results = queryDriver.query(plan);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getId()).isEqualTo(1);
        assertThat(results.get(0).getName()).isEqualTo("Joe Doe");
        assertThat(results.get(0).getTelephone()).isEqualTo("12345678");
        assertThat(results.get(0).getEmail()).isEqualTo("bob@doe.com");
        assertThat(results.get(0).getAddress()).isEqualTo("1 Maple Street");
    }

    @Test
    public void testNoResult() {
        Seq<Customer> results = queryDriver.query(new NoResultQueryPlan<>());
        assertThat(results).isEmpty();
    }

    @Test(expected = CouldNotFindQueryPlanExecutorForQueryPlan.class)
    public void testConnectionNotFound() {
        this.queryDriver = new DefaultQueryDriver(Seq.of(new DefaultSQLConnectionConfiguration(conn, Seq.of(conn))), Dict.empty());
        SQLQueryPlan<Customer> plan = new SQLQueryPlan<>(
            "SELECT * FROM customers",
            null,
            null
        );

        queryDriver.query(plan);
    }

}