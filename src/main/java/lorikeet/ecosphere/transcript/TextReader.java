package lorikeet.ecosphere.transcript;

import lorikeet.Err;

public class TextReader {
    private final String text;
    private int index;

    public TextReader(String text, int index) {
        this.text = text;
        this.index = index;
    }

    public String nextToken() {
        this.jumpWhitesapce();
        final StringBuilder token = new StringBuilder();
        for (; this.index < this.text.length(); this.index++) {
            final char character = text.charAt(this.index);
            if (Character.isWhitespace(character)) {
                return token.toString();
            }
            token.append(character);
        }
        return token.toString();
    }

    public Err<String> nextIdentifier() {
        this.jumpWhitesapce();
        StringBuilder className = new StringBuilder();
        for (; this.index < this.text.length(); this.index++) {
            final char character = this.text.charAt(this.index);
            if (isAllowedInName(character)) {
                className.append(character);
                continue;
            }
            return validateName(className.toString());
        }
        return validateName(className.toString());
    }

    private void jumpWhitesapce() {
        for (; this.index < this.text.length(); this.index++) {
            if (!Character.isWhitespace(this.text.charAt(this.index))) {
                return;
            }
        }
    }

    static boolean isAllowedInName(char character) {
        return Character.isLetterOrDigit(character) || character == '.' || character == '_';
    }

    static Err<String> validateName(String className) {
        if (className.trim().isEmpty()) {
            return Err.failure();
        }
        if (className.startsWith(".") || className.endsWith(".")) {
            return Err.failure();
        }
        return Err.of(className);
    }

}
