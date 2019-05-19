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

    public Character getCurrentChar() {
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
        return this.readAlphaNumericWord(allowDigitAtStart).then((value) -> this.jumpWhitespace());
    }

    private Opt<String> readAlphaNumericWord(boolean allowDigitAtStart) {
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
                    return Opt.of(word.toString());
                } else {
                    word.append(character);
                    continue;
                }
            }
            if (word.length() == 0) {
                return Opt.empty();
            }
            return Opt.of(word.toString());
        }
        if (word.length() == 0) {
            return Opt.empty();
        }
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
        return this.readIdentifier().then(value -> this.jumpWhitespace());
    }

    public Err<String> readIdentifier() {
        final StringBuilder builder = new StringBuilder();

        while (true) {
            if (this.isAtEnd()) {
                break;
            }
            final Opt<String> segment = this.readAlphaNumericWord(false);
            if (!segment.isPresent()) {
                return Err.failure();
            }
            builder.append(segment.orPanic());
            if (this.isAtEnd()) {
                break;
            }
            if (this.getCurrentChar() == '.') {
                this.index++;
                builder.append(".");
                continue;
            }
            break;
        }
        final String className = builder.toString();
        if (className.startsWith(".") || className.endsWith(".") || className.isBlank()) {
            return Err.failure();
        }
        return Err.of(className);
    }

    private char nextChar() {
        return this.text.charAt(this.index + 1);
    }

}
