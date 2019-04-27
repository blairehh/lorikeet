package lorikeet.ecosphere.testing;


import lorikeet.Seq;
import lorikeet.ecosphere.CreateUser;
import lorikeet.ecosphere.User;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CrateGraphNodeTranscriberTest {

    @Test
    public void test() {
        TestPlug action = new TestPlug();
        User user = action.yield(new CreateUser(), "bob@gmail.com", "secret", Seq.of(1, 2));

        String transcript = new CrateGraphNodeTranscriber().transcribe(action.getGraph().getRootNode());
        System.out.println(transcript);
    }

}