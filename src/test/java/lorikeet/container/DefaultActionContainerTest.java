package lorikeet.container;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultActionContainerTest {

    @Test
    public void test() {
        ActionContainer action = new DefaultActionContainer();
        User user = action.yield(new CreateUser(), "bob@gmail.com", "secret");
        assertThat(user.email).isEqualTo("bob@gmail.com");
        assertThat(user.password).isEqualTo("secret");
        assertThat(user.welcomeMessageSentAt).isNotNull();
    }
}
