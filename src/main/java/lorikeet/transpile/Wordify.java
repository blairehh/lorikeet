package lorikeet.transpile;

public class Wordify {

    private static final String WORDIFABLE = "=!@$%~+^&*-<>|?/\\";

    public static boolean requiresWordify(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (!WORDIFABLE.contains(String.valueOf(value.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static String wordify(String value) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            final char c = value.charAt(i);
            if (c == '=') {
                builder.append("equal");
            } else if (c == '!') {
                builder.append("excl");
            } else if (c == '!') {
                builder.append("excl");
            } else if (c == '@') {
                builder.append("at");
            } else if (c == '$') {
                builder.append("dollar");
            } else if (c == '%') {
                builder.append("percent");
            } else if (c == '~') {
                builder.append("tilda");
            } else if (c == '^') {
                builder.append("power");
            } else if (c == '&') {
                builder.append("amp");
            } else if (c == '*') {
                builder.append("ast");
            } else if (c == '-') {
                builder.append("hypen");
            } else if (c == '<') {
                builder.append("lt");
            } else if (c == '>') {
                builder.append("mt");
            } else if (c == '?') {
                builder.append("quest");
            } else if (c == '|') {
                builder.append("pipe");
            } else if (c == '/') {
                builder.append("forward");
            } else if (c == '\\') {
                builder.append("back");
            } else if (c == '+') {
                builder.append("plus");
            }
        }
        return builder.toString();
    }

}
