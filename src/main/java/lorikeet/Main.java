package lorikeet;

import lorikeet.lobe.Tract;
import lorikeet.lobe.DefaultTract;

public class Main {
    public static void main(String[] args) {
        Tract<Tutorial> tract = new DefaultTract<>(new Tutorial());
        tract.write(new WriteFile("oi.txt", "welcome"));
        tract.log("Finished");
    }
}