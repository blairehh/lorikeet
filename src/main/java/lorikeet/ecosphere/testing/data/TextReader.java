package lorikeet.ecosphere.testing.data;

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
        this.resetTo(reader.getCurrentIndex());
    }

    private void resetTo(int indexAt) {
        this.index = indexAt;
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

    public void step() {
        this.index++;
    }

    public void jumpWhitespace() {
        for (; this.index < this.text.length(); this.index++) {
            if (!Character.isWhitespace(this.text.charAt(this.index))) {
                return;
            }
        }
    }

    public Opt<String> nextWord() {
        final int here = this.index;
        this.jumpWhitespace();
        return this.readWord()
            .ifso(this::jumpWhitespace)
            .ifnot(() -> this.resetTo(here));
    }

    public Opt<String> readWord() {
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
            return Opt.of(word.toString());
        }
        if (word.length() == 0) {
            return Opt.empty();
        }
        return Opt.of(word.toString());
    }

    public Opt<String> nextAlphaNumericWord(boolean allowDigitAtStart) {
        final int here = this.index;
        this.jumpWhitespace();
        return this.readAlphaNumericWord(allowDigitAtStart)
            .ifso(this::jumpWhitespace)
            .ifnot(() -> this.resetTo(here));
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
        final int here = this.index;
        this.jumpWhitespace();
        return this.readNumber()
            .ifso(this::jumpWhitespace)
            .ifnot(() -> this.resetTo(here));
    }

    public Opt<Number> readNumber() {
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
        final int here = this.index;
        this.jumpWhitespace();
        return this.readQuote(quoteMark)
            .ifso(this::jumpWhitespace)
            .ifnot(() -> this.resetTo(here));
    }

    public Err<String> readQuote(char quoteMark) {
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
        final int here = this.index;
        this.jumpWhitespace();
        return this.readIdentifier()
            .ifso(this::jumpWhitespace)
            .ifnot(() -> this.resetTo(here));
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
