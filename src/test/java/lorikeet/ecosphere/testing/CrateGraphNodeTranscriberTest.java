package lorikeet.ecosphere.testing;


import lorikeet.Seq;
import lorikeet.data.DataSerializer;
import lorikeet.data.DataSerializationCapabilityRegistry;
import lorikeet.ecosphere.CreateUser;
import lorikeet.ecosphere.User;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CrateGraphNodeTranscriberTest {

    private DataSerializer serializer = new DataSerializer(DataSerializationCapabilityRegistry.init());

    @Test
    public void test() {
        TestAxon action = new TestAxon();
        User user = action.yield(new CreateUser(), "bob@gmail.com", "secret", Seq.of(1, 2));

        String transcript = new CrateGraphNodeTranscriber(serializer).transcribe(action.getGraph().getRootNode());
        System.out.println(transcript);
    }

}