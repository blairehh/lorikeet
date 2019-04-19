package lorikeet.web;

import lorikeet.spring.PathContainer;
import lorikeet.spring.PathPatternParser;

public class Utils {
    public static boolean uriMatches(String template, String path) {
        return new PathPatternParser().parse(template).matches(PathContainer.parsePath(path));
    }
}
