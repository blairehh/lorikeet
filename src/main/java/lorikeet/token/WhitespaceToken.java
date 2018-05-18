package lorikeet.token;

public class WhitespaceToken implements Token {

    private final int line;

    public WhitespaceToken(int line) {
        this.line = line;
    }

    @Override
    public int getLine() {
        return this.line;
    }

    @Override
    public boolean isNewLine() {
        return false;
    }

    @Override
    public String str() {
        return " ";
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.WHITESPACE;
    }
}
