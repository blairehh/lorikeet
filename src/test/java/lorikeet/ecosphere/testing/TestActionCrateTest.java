package lorikeet.ecosphere.testing;

import lorikeet.Seq;
import lorikeet.ecosphere.CreateUser;
import lorikeet.ecosphere.User;
import lorikeet.ecosphere.meta.ParameterMeta;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestActionCrateTest {

    @Test
    public void test() {
        TestPlug action = new TestPlug();
        User user = action.yield(new CreateUser(), "bob@gmail.com", "secret", Seq.of(1, 2));
        assertThat(user.email).isEqualTo("bob@gmail.com");
        assertThat(user.password).isEqualTo("secret");
        assertThat(user.welcomeMessageSentAt).isNotNull();

        CrateGraphNode root = action.getGraph().getRootNode();
        assertThat(root.getName()).isEqualTo("lorikeet.ecosphere.CreateUser");
        assertThat(root.getParameters()).contains(new CrateParameter(new ParameterMeta(0, "email"), "bob@gmail.com"));
        assertThat(root.getParameters()).contains(new CrateParameter(new ParameterMeta(1, "password"), "secret"));

        List<CrateGraphNode> children = root.getChildren();

        assertThat(children).hasSize(3);
        assertThat(children.get(0).getName()).isEqualTo("lorikeet.ecosphere.SendWelcomeMessage");
        assertThat(children.get(0).getParameters()).contains(
            new CrateParameter(new ParameterMeta(0), "bob@gmail.com"),
            new CrateParameter(new ParameterMeta(1, "message"), "Hello")
        );
        assertThat(children.get(1).getName()).isEqualTo("lorikeet.ecosphere.ChargePayment");
        assertThat(children.get(1).getParameters()).contains(
            new CrateParameter(new ParameterMeta(0, "currency"), "USD"),
            new CrateParameter(new ParameterMeta(1, "price"), 45.0)
        );
        assertThat(children.get(2).getName()).isEqualTo("lorikeet.ecosphere.OpenAccount");
        assertThat(children.get(2).getParameters()).contains(new CrateParameter(new ParameterMeta(0, "email"), "bob@gmail.com"));
    }

}