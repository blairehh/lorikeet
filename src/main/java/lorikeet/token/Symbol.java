package lorikeet.token;

import java.util.Optional;

public enum Symbol {

    DOT                 ("."),
    COMMA               (","),
    SEMICOLON           (";"),
    COLON               (":"),
    HYPHEN              ("-"),
    PLUS                ("+"),
    EQUAL               ("="),
    AT                  ("@"),
    QUESTION_MARK       ("?"),
    PERCENT_SIGN        ("%"),
    DOLLAR_SIGN         ("$"),
    HASH                ("#"),
    ASTERISK            ("*"),
    BACK_SLASH          ("\\"),
    FORWARD_SLASH       ("/"),
    EXCLAMATION         ("!"),
    OPEN_ROUND          ("("),
    CLOSE_ROUND         (")"),
    OPEN_SQUARE         ("["),
    CLOSE_SQUARE        ("]"),
    OPEN_CURLY          ("{"),
    CLOSE_CURLY         ("}"),
    RIGHT_ANGLE         (">"),
    LEFT_ANGLE          ("<"),
    AMPERSAND           ("&"),
    PIPE                ("|"),
    LOGICAL_OR          ("||"),
    LOGICAL_AND         ("&&"),
    EQUALS              ("=="),
    NOT_EQUALS          ("!="),
    LESS_THAN_EQUALS    ("<="),
    MORE_THAN_EQUALS    (">="),
    THIN_ARROW          ("->"),
    CARET               ("^");

    private String value;

    private Symbol(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public boolean isSingle() {
        return this.value.length() == 0;
    }

    public static Optional<Symbol> fromString(String sy) {
        for (Symbol symbol : Symbol.values()) {
            if (symbol.toString().equals(sy)) {
                return Optional.of(symbol);
            }
        }
        return Optional.empty();
    }
}
