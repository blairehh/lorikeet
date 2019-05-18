package lorikeet.ecosphere.transcript;

import lorikeet.Err;
import lorikeet.Opt;

public class TextReader {
    private final String text;
    private int index;

    public TextReader(String text, int index) {
        this.text = text;
        this.index = index;
    }

    public int getCurrentIndex() {
        return this.index;
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

    public Opt<Number> nextNumber() {
        this.jumpWhitesapce();
        final String token = this.nextToken();
        try {
            final Double decimal = Double.parseDouble(token);
            return Opt.of(decimal);
        } catch (NumberFormatException e) {
            return Opt.empty();
        }
    }

    public Err<String> nextQuote(char quoteMark) {
        final StringBuilder quote = new StringBuilder();
        this.jumpWhitesapce();
        if (this.currentChar() != quoteMark) {
            return Err.failure();
        }
        this.index++;
        for (; this.index < this.text.length(); this.index++) {
            final char current = this.currentChar();
            if (current == '\\' && this.nextChar() == quoteMark) {
                quote.append(quoteMark);
                this.index++;
                continue;
            }
            if (current == quoteMark) {
                this.index++;
                return Err.of(quote.toString());
            }
            quote.append(current);
        }

        return Err.failure();
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

    private int seek(int start, char character) {
        for (int seekIndex = start; seekIndex < this.text.length(); seekIndex++) {
            if (this.text.charAt(seekIndex) == character) {
                return seekIndex;
            }
        }
        return -1;
    }

    private char currentChar() {
        return this.text.charAt(this.index);
    }

    private char nextChar() {
        return this.text.charAt(this.index + 1);
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
