package lorikeet.container.testing;

import lorikeet.container.CreateUser;
import lorikeet.container.User;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestActionContainerTest {

    @Test
    public void test() {
        TestActionContainer action = new TestActionContainer();
        User user = action.yield(new CreateUser(), "bob@gmail.com", "secret");
        assertThat(user.email).isEqualTo("bob@gmail.com");
        assertThat(user.password).isEqualTo("secret");

//        assertThat(user.welcomeMessageSentAt).isNotNull();
//
        ContainerGraphNode root = action.getGraph().getRootNode();
        assertThat(root.getName()).isEqualTo("lorikeet.container.CreateUser");
        assertThat(root.getParameters()).contains(new ContainerParameter(RenderType.STRING, "email", "bob@gmail.com"));
        assertThat(root.getParameters()).contains(new ContainerParameter(RenderType.STRING,"password", "secret"));

        List<ContainerGraphNode> children = root.getChildren();

        assertThat(children).hasSize(3);
        assertThat(children.get(0).getName()).isEqualTo("lorikeet.container.SendWelcomeMessage");
        assertThat(children.get(0).getParameters()).contains(new ContainerParameter(RenderType.STRING,"email", "bob@gmail.com"));
        assertThat(children.get(1).getName()).isEqualTo("lorikeet.container.ChargePayment");
        assertThat(children.get(1).getParameters()).contains(
            new ContainerParameter(RenderType.STRING,"currency", "USD"),
            new ContainerParameter(RenderType.NUMBER,"price", "45.0")
        );
        assertThat(children.get(2).getName()).isEqualTo("lorikeet.container.OpenAccount");
        assertThat(children.get(2).getParameters()).contains(new ContainerParameter(RenderType.STRING,"email", "bob@gmail.com"));
    }

}