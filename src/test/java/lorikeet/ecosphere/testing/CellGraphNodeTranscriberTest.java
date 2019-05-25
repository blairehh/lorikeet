package lorikeet.ecosphere.testing;

import lorikeet.Seq;
import lorikeet.ecosphere.CreateUser;
import lorikeet.ecosphere.User;
import org.junit.Test;


public class CellGraphNodeTranscriberTest {

    private CellGraphNodeTranscriber transcriber = new CellGraphNodeTranscriber();

    @Test
    public void test() {
        TestAxon action = new TestAxon();
        User user = action.yield(new CreateUser(), "bob@gmail.com", "secret", Seq.of(1, 2));

        String transcript = transcriber.transcribe(action.getCellGraph().getRootNode());
        System.out.println(transcript);
    }

}