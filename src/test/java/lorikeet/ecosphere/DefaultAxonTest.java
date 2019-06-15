package lorikeet.ecosphere;

import lorikeet.Seq;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultAxonTest {

    @Test
    public void test() {
        Axon action = new DefaultAxon();
        User user = action.yield(new CreateUser(), "bob@gmail.com", "secret", Seq.of(1, 2));
        assertThat(user.email).isEqualTo("bob@gmail.com");
        assertThat(user.password).isEqualTo("secret");
        assertThat(user.welcomeMessageSentAt).isNotNull();
        assertThat(user.account).isNull();
    }
}
