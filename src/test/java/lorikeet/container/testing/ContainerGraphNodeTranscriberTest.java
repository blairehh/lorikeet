package lorikeet.container.testing;


import lorikeet.container.CreateUser;
import lorikeet.container.User;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ContainerGraphNodeTranscriberTest {

    @Test
    public void test() {
        TestActionContainer action = new TestActionContainer();
        User user = action.yield(new CreateUser(), "bob@gmail.com", "secret");

        String transcript = new ContainerGraphNodeTranscriber().transcribe(action.getGraph().getRootNode());
        System.out.println(transcript);
    }

}