package lorikeet.token;

public class KeywordToken implements Token {

    private final int line;
    private final Keyword keyword;

    public KeywordToken(Keyword keyword, int line) {
        this.line = line;
        this.keyword = keyword;
    }

    @Override
    public int getLine() {
        return this.line;
    }

    public Keyword getKeyword() {
        return this.keyword;
    }

    @Override
    public boolean isNewLine() {
        return false;
    }

    @Override
    public String str() {
        return this.keyword.toString();
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.KEYWORD;
    }

}
