package lorikeet;

import lorikeet.lobe.DiskWrite;
import lorikeet.lobe.LorikeetAction;
import lorikeet.lobe.Tract;

import java.net.URI;
import java.net.URISyntaxException;

public class RunProgram implements LorikeetAction<Tutorial, Boolean> {
    @Override
    public Boolean junction(Tract<Tutorial> tract) {
        try {
            return tract.write(new DiskWrite<>(new URI("file:///home/blair/projects/lorikeet/oi.txt"), "mno"))
                .success();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
