package lorikeet;

import lorikeet.lobe.Tract;
import lorikeet.lobe.DefaultTract;

import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException  {
        Tract<Tutorial> tract = new DefaultTract<>(new Tutorial());
//        tract.write(new WriteFile(new URI("file:///home/blair/projects/lorikeet/oi.txt"), "ghi"));
        tract.log("Finished");
    }
}