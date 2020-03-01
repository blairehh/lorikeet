package lorikeet;

import lorikeet.lobe.Tract;
import lorikeet.lobe.DefaultTract;

public class Main {
    public static void main(String[] args)  {
        final Tutorial tutorial = new Tutorial(new TutorialConfiguration());
        Tract<Tutorial> tract = new DefaultTract<>(tutorial);

//        tract.invoke(new RunProgram());

        tutorial.start();
    }
}