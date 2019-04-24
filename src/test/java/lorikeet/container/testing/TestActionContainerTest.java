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
        assertThat(user.welcomeMessageSentAt).isNotNull();

        ContainerGraphNode root = action.getGraph().getRootNode();
        assertThat(root.getName()).isEqualTo("CreateUser");

        List<ContainerGraphNode> children = root.getChildren();
        assertThat(children).hasSize(1);
        assertThat(children.get(0).getName()).isEqualTo("SendWelcomeMessage");
    }

}