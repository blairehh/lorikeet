package lorikeet.db;

import lorikeet.Dict;
import lorikeet.Fun1;
import lorikeet.IO;
import lorikeet.Seq;
import lorikeet.db.impl.Customer;
import lorikeet.db.impl.DefaultConnectionConfiguration;
import lorikeet.db.impl.DefaultSQLConnection;
import lorikeet.db.impl.DefaultSQLQueryPlanExecutor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryDriverTest {

    private static final String SQL_SCHEMA = IO.readResource("sql/test_db_schema.sql").orPanic();

    private BasicDataSource dataSource;
    private DefaultSQLConnection conn;
    private QueryDriver queryDriver;

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

        DefaultConnectionConfiguration connConfig = new DefaultConnectionConfiguration(conn, Seq.of(conn));

        this.queryDriver = new QueryDriver(Seq.of(connConfig), Dict.of(SqlQueryPlan.class, new DefaultSQLQueryPlanExecutor()));
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

        SqlQueryPlan<Customer> plan = new SqlQueryPlan<>(
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

}