package lorikeet.token;

public class SymbolToken implements Token {

    private final int line;
    private final Symbol symbol;

    public SymbolToken(Symbol symbol, int line) {
        this.line = line;
        this.symbol = symbol;
    }

    @Override
    public int getLine() {
        return this.line;
    }

    public Symbol getSymbol() {
        return this.symbol;
    }

    @Override
    public boolean isNewLine() {
        return false;
    }

    @Override
    public String str() {
        return this.symbol.toString();
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.SYMBOL;
    }

}
