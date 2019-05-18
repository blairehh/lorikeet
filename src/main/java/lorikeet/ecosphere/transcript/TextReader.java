package lorikeet.ecosphere.transcript;

import lorikeet.Err;
import lorikeet.Opt;

import java.util.Scanner;

public class TextReader {
    private final String text;
    private int index;

    public TextReader(String text, int index) {
        this.text = text;
        this.index = index;
    }

    public TextReader fork() {
        return new TextReader(this.text, this.index);
    }

    public void resetTo(TextReader reader) {
        this.index = reader.getCurrentIndex();
    }

    public int getCurrentIndex() {
        return this.index;
    }

    public char getCurrentChar() {
        return this.text.charAt(this.index);
    }

    public boolean isAtEnd() {
        return this.index >= this.text.length();
    }

    public void skip() {
        this.jumpWhitespace();
        this.index++;
        this.jumpWhitespace();
    }

    public void jumpWhitespace() {
        for (; this.index < this.text.length(); this.index++) {
            if (!Character.isWhitespace(this.text.charAt(this.index))) {
                return;
            }
        }
    }

    public String nextToken() {
        this.jumpWhitespace();
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
        this.jumpWhitespace();
        final StringBuilder number = new StringBuilder();
        final int start = this.index;
        boolean foundPeriod = false;
        for(; this.index < this.text.length(); this.index++) {
            final char current = this.getCurrentChar();
            if (current == '.') {
                if (!foundPeriod) {
                    foundPeriod = true;
                    number.append(current);
                    continue;
                }
                break;
            }
            if (current == '-') {
                if (start == this.index) {
                    number.append(current);
                    continue;
                }
                break;
            }
            if (Character.isDigit(current)) {
                number.append(current);
            } else {
                break;
            }
        }
        try {
            final Double decimal = Double.parseDouble(number.toString());
            return Opt.of(decimal);
        } catch (NumberFormatException e) {
            return Opt.empty();
        }
    }

    public Err<String> nextQuote(char quoteMark) {
        final StringBuilder quote = new StringBuilder();
        this.jumpWhitespace();
        if (this.getCurrentChar() != quoteMark) {
            return Err.failure();
        }
        this.index++;
        for (; this.index < this.text.length(); this.index++) {
            final char current = this.getCurrentChar();
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
        this.jumpWhitespace();
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

    private char nextChar() {
        return this.text.charAt(this.index + 1);
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
