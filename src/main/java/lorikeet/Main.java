package lorikeet;

import lorikeet.coding.JsonEncode;

public class Main {
    public static void main(String[] args)  {
        final Tutorial tutorial = new Tutorial(new TutorialConfiguration());
        System.out.println(tutorial.provideTract().encode(new User<>("bob")).orPanic());
        //tutorial.start();
    }
}