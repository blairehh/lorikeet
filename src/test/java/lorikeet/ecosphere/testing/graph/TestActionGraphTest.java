package lorikeet.ecosphere.testing.graph;

import lorikeet.Dict;
import lorikeet.Seq;
import lorikeet.ecosphere.CreateUser;
import lorikeet.ecosphere.User;
import lorikeet.ecosphere.testing.TestAxon;
import lorikeet.ecosphere.testing.data.CellValue;
import lorikeet.ecosphere.testing.data.NumberValue;
import lorikeet.ecosphere.testing.data.StringValue;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestActionGraphTest {

    @Test
    public void test() {
        TestAxon action = new TestAxon();
        User user = action.yield(new CreateUser(), "bob@gmail.com", "secret", Seq.of(1, 2));
        assertThat(user.email).isEqualTo("bob@gmail.com");
        assertThat(user.password).isEqualTo("secret");
        assertThat(user.welcomeMessageSentAt).isNotNull();

        CellGraphNode root = action.getCellGraph().getRootNode();
        CellValue cell = root.getCell();
        assertThat(cell.getClassName()).isEqualTo("lorikeet.ecosphere.CreateUser");
        assertThat(cell.getArguments()).containsEntry("email", new StringValue("bob@gmail.com"));
        assertThat(cell.getArguments()).containsEntry("password", new StringValue("secret"));

        List<CellGraphNode> children = root.getChildren();

        assertThat(children).hasSize(3);
        assertThat(children.get(0).getCell().getClassName()).isEqualTo("lorikeet.ecosphere.SendWelcomeMessage");
        assertThat(children.get(0).getCell().getArguments()).containsAllEntriesOf(Dict.of(
            "-0", new StringValue("bob@gmail.com"),
            "message", new StringValue("Hello")
        ));
        assertThat(children.get(1).getCell().getClassName()).isEqualTo("lorikeet.ecosphere.ChargePayment");
        assertThat(children.get(1).getCell().getArguments()).containsAllEntriesOf(Dict.of(
            "currency",new StringValue("USD"),
            "price", new NumberValue(45.0)
        ));
        assertThat(children.get(2).getCell().getClassName()).isEqualTo("lorikeet.ecosphere.OpenAccount");
        assertThat(children.get(2).getCell().getArguments()).containsEntry("email", new StringValue("bob@gmail.com"));
    }

}