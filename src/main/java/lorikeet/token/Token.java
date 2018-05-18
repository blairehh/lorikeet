package lorikeet.token;


public interface Token {

  public int getLine();
  public String str();
  public TokenType getTokenType();
  public boolean isNewLine();

  default public boolean isKeyword(Keyword keyword) {
      if (this.getTokenType() != TokenType.KEYWORD) {
          return false;
      }
      return ((KeywordToken)this).getKeyword() == keyword;
  }

  default public boolean isSymbol(Symbol symbol) {
      if (this.getTokenType() != TokenType.SYMBOL) {
          return false;
      }
      return ((SymbolToken)this).getSymbol() == symbol;
  }
}
