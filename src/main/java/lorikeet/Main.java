package lorikeet;

public class Main {
    public static void main(String[] args)  {
        final Tutorial tutorial = new Tutorial(new TutorialConfiguration());
        TutorialTract tract = new TutorialTract(tutorial);
//        tract.invoke(new RunProgram());

        tutorial.start(tract);
    }
}