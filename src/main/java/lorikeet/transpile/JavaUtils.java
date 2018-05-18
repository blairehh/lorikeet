package lorikeet.transpile;

import lorikeet.lang.Package;

public class JavaUtils {

    public static String subdirectoryFor(Package pkg) {
        StringBuilder dir = new StringBuilder();
        for (String i : pkg.getHierarchy()) {
            dir.append(i);
            dir.append("/");
        }
        return dir.toString();
    }
}
