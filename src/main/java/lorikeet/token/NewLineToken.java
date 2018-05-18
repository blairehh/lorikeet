package lorikeet.token;

public class NewLineToken implements Token {

    private final int line;

    public NewLineToken(int line) {
        this.line = line;
    }

    @Override
    public int getLine() {
        return this.line;
    }

    @Override
    public String str() {
        return "\n";
    }

    @Override
    public boolean isNewLine() {
        return true;
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.NEWLINE;
    }

}
