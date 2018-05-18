package lorikeet.parse;

import java.util.regex.Pattern;

public class Patterns {

    private static final Pattern TYPE_NAME          = Pattern.compile("^[A-Z][a-zA-Z0-9]*$");
    private static final Pattern PACKAGE_NAME       = Pattern.compile("^[a-z][a-zA-Z0-9]*$");

    public static boolean isTypeName(String value) {
        return TYPE_NAME.matcher(value).matches();
    }

    public static boolean isPackageName(String value) {
        return PACKAGE_NAME.matcher(value).matches();
    }

    public static boolean isAttributeName(String value) {
        return PACKAGE_NAME.matcher(value).matches();
    }

    public static boolean isFunctionName(String value) {
        return PACKAGE_NAME.matcher(value).matches();
    }

    public static boolean isVariableName(String value) {
        return PACKAGE_NAME.matcher(value).matches();
    }

}
