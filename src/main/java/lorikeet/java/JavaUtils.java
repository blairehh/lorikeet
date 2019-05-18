package lorikeet.java;

import java.util.stream.Stream;

public class JavaUtils {



    public static boolean isValidIdentifier(String value) {
        if (value.endsWith(".")) {
            return false;
        }
        return Stream.of(value.split("\\."))
            .allMatch(JavaUtils::isValidName);
    }

    private static boolean isValidName(String name) {
        if (name.matches(".*\\\\s+.*")) {
            return false;
        }
        if (name.isBlank()) {
            return false;
        }

        final char first = name.charAt(0);
        if (!Character.isLetter(first)) {
            return false;
        }

        return name.matches("[a-zA-Z0-9_]+");
    }
}
