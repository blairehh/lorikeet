package lorikeet;

import lorikeet.lobe.DiskWrite;
import lorikeet.lobe.Tract;
import lorikeet.lobe.DefaultTract;

import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException  {
        Tract<Tutorial> tract = new DefaultTract<>(new Tutorial());
        tract.write(new DiskWrite<>(new URI("file:///home/blair/projects/lorikeet/oi.txt"), "stq"));
        tract.log("Finished");
    }
}