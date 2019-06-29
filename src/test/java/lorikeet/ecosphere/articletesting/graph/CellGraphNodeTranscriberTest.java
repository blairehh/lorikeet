package lorikeet.ecosphere.articletesting.graph;

import lorikeet.Seq;
import lorikeet.ecosphere.CreateUser;
import lorikeet.ecosphere.User;
import lorikeet.ecosphere.articletesting.TestTract;
import org.junit.Test;


public class CellGraphNodeTranscriberTest {

    private CellGraphNodeTranscriber transcriber = new CellGraphNodeTranscriber();

    @Test
    public void test() {
        TestTract action = new TestTract();
        User user = action.yield(new CreateUser(), "bob@gmail.com", "secret", Seq.of(1, 2));

        String transcript = transcriber.transcribe(action.getCellGraph().getRootNode());
    }

}