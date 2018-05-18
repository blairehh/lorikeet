package lorikeet.token;


public class TextToken implements Token {
  private final String value;
  private final int line;

  public TextToken(String value) {
    this.value = value;
    this.line = 0;
  }

  public TextToken(String value, int line) {
    this.value = value;
    this.line = line;
  }

  @Override
  public int getLine() {
      return this.line;
  }

  @Override
  public String str() {
    return this.value;
  }

  @Override
  public boolean isNewLine() {
      return false;
  }

  @Override
  public String toString() {
    return this.value;
  }

  @Override
  public TokenType getTokenType() {
    return TokenType.TEXT;
  }

}
