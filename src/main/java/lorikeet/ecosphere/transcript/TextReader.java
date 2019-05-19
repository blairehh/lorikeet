package lorikeet.ecosphere.transcript;

import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.Seq;


public class TextReader {

    private final Seq<Character> SYMBOLS = Seq.of(
        ',', '<', '.', '>', '/', '?', '\\', ']', '[', ';', ':', '{', '}', '!', '@', '#', '$', '%', '^', '&', '*',
        '(', ')', '-', '=', '+');


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

    public Opt<String> nextWord() {
        this.jumpWhitespace();
        final StringBuilder word = new StringBuilder();
        for (; this.index < this.text.length(); this.index++) {
            final char character = this.getCurrentChar();
            if (Character.isLetter(character) || character == '_') {
                word.append(character);
                continue;
            }
            if (word.length() == 0) {
                return Opt.empty();
            }
            this.jumpWhitespace();
            return Opt.of(word.toString());
        }
        if (word.length() == 0) {
            return Opt.empty();
        }
        this.jumpWhitespace();
        return Opt.of(word.toString());
    }

    public Opt<String> nextAlphaNumericWord(boolean allowDigitAtStart) {
        this.jumpWhitespace();
        final StringBuilder word = new StringBuilder();
        final int start = this.index;
        for (; this.index < this.text.length(); this.index++) {
            final char character = this.getCurrentChar();
            if (Character.isLetter(character) || character == '_') {
                word.append(character);
                continue;
            }
            if (Character.isDigit(character)) {
                if (start == this.index && !allowDigitAtStart) {
                    if (word.length() == 0) {
                        return Opt.empty();
                    }
                    this.jumpWhitespace();
                    return Opt.of(word.toString());
                } else {
                    word.append(character);
                    continue;
                }
            }
            if (word.length() == 0) {
                return Opt.empty();
            }
            this.jumpWhitespace();
            return Opt.of(word.toString());
        }
        if (word.length() == 0) {
            return Opt.empty();
        }
        this.jumpWhitespace();
        return Opt.of(word.toString());
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
            this.jumpWhitespace();
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
            if (isAllowedInIdentifier(character)) {
                className.append(character);
                continue;
            }
            return validateIdentifier(className.toString());
        }
        return validateIdentifier(className.toString());
    }

    private char nextChar() {
        return this.text.charAt(this.index + 1);
    }

    private static boolean isAllowedInIdentifier(char character) {
        return Character.isLetterOrDigit(character) || character == '.' || character == '_';
    }

    static Err<String> validateIdentifier(String className) {
        if (className.trim().isEmpty()) {
            return Err.failure();
        }
        if (className.startsWith(".") || className.endsWith(".")) {
            return Err.failure();
        }
        return Err.of(className);
    }

}
