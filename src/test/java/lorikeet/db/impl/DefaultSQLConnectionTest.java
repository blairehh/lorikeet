package lorikeet.db.impl;

import lorikeet.Err;
import lorikeet.IO;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DefaultSQLConnectionTest {

    private static final String SQL_SCHEMA = IO.readResource("sql/test_db_schema.sql").orPanic();

    private BasicDataSource dataSource;
    private DefaultSQLConnection conn;

    @Before
    public void setUp() {
        if (this.dataSource == null) {
            this.dataSource = new BasicDataSource();
            dataSource.setUrl("jdbc:mysql://localhost:33060/lorikeet");
            dataSource.setUsername("root");
            dataSource.setPassword("root");
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

            this.conn = new DefaultSQLConnection(this.dataSource, false);
        }

        this.conn.execute(SQL_SCHEMA, true).orPanic();
    }

    @Test
    public void testInsertWithAutoIDAndQueryWithoutParams() {
        final String insert = "INSERT INTO customers(name, telephone, email, address) VALUES ('Joe Doe', '12345678', 'bob@doe.com', '1 Maple Street')";

        long s = this.conn.insertWithAutoID(insert).orPanic().longValue();

        String street = this.conn.query(
            "SELECT * FROM customers WHERE name = 'Joe Doe'",
            (intermediate -> intermediate.string("address").orPanic())
        ).orPanic();

        assertThat(s).isEqualTo(1);
        assertThat(street).isEqualTo("1 Maple Street");
    }

    @Test
    public void testInsertWithAutoIDGivesBackSpecifiedIDAndQueryWithParams() {
        final String insert = "INSERT INTO customers(id, name, telephone, email, address) VALUES (200, 'Joe Doe', '12345678', 'bob@doe.com', '1 Maple Street')";

        long s = this.conn.insertWithAutoID(insert).orPanic().longValue();

        String street = this.conn.query(
            "SELECT * FROM customers WHERE id = ?",
            (intermediate -> intermediate.string("address").orPanic()),
            200
        ).orPanic();

        assertThat(s).isEqualTo(200);
        assertThat(street).isEqualTo("1 Maple Street");
    }

    @Test
    public void testInsertWithNoAutoGeneratedID() {
        String insert = "INSERT INTO logs(info) VALUES('account opened')";

        Err<Boolean> result = this.conn.insert(insert);

        assertThat(result.orPanic()).isTrue();
    }

    @Test
    public void testUpdate() {
        final String insert = "INSERT INTO customers(name, telephone, email, address) VALUES ('Joe Doe', '12345678', 'bob@doe.com', '1 Maple Street')";

        long customerId = this.conn.insertWithAutoID(insert).orPanic().longValue();

        String update = "UPDATE customers SET telephone = '87654321' WHERE id = ?";
        Err<Integer> updateResult = this.conn.update(update, customerId);

        assertThat(updateResult.orPanic()).isEqualTo(1);

        String telephone = this.conn.query(
            "SELECT * FROM customers WHERE id = ?",
            (intermediate -> intermediate.string("telephone").orPanic()),
            customerId
        ).orPanic();

        assertThat(telephone).isEqualTo("87654321");
    }

}