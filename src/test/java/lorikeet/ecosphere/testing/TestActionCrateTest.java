package lorikeet.ecosphere.testing;

import lorikeet.ecosphere.CreateUser;
import lorikeet.ecosphere.User;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestActionCrateTest {

    @Test
    public void test() {
        TestPlug action = new TestPlug();
        User user = action.yield(new CreateUser(), "bob@gmail.com", "secret");
        assertThat(user.email).isEqualTo("bob@gmail.com");
        assertThat(user.password).isEqualTo("secret");

//        assertThat(user.welcomeMessageSentAt).isNotNull();
//
        CrateGraphNode root = action.getGraph().getRootNode();
        assertThat(root.getName()).isEqualTo("lorikeet.ecosphere.CreateUser");
        assertThat(root.getParameters()).contains(new CrateParameter(RenderType.STRING, "email", "bob@gmail.com"));
        assertThat(root.getParameters()).contains(new CrateParameter(RenderType.STRING,"password", "secret"));

        List<CrateGraphNode> children = root.getChildren();

        assertThat(children).hasSize(3);
        assertThat(children.get(0).getName()).isEqualTo("lorikeet.ecosphere.SendWelcomeMessage");
        assertThat(children.get(0).getParameters()).contains(new CrateParameter(RenderType.STRING,"email", "bob@gmail.com"));
        assertThat(children.get(1).getName()).isEqualTo("lorikeet.ecosphere.ChargePayment");
        assertThat(children.get(1).getParameters()).contains(
            new CrateParameter(RenderType.STRING,"currency", "USD"),
            new CrateParameter(RenderType.NUMBER,"price", "45.0")
        );
        assertThat(children.get(2).getName()).isEqualTo("lorikeet.ecosphere.OpenAccount");
        assertThat(children.get(2).getParameters()).contains(new CrateParameter(RenderType.STRING,"email", "bob@gmail.com"));
    }

}