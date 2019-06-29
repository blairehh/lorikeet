package lorikeet.lobe.articletesting.graph;

import lorikeet.Seq;
import lorikeet.lobe.CreateUser;
import lorikeet.lobe.User;
import lorikeet.lobe.articletesting.TestTract;
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