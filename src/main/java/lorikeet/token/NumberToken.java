package lorikeet.token;

public class NumberToken implements Token {
  private final String value;
  private final int line;

  public NumberToken(String value) {
    this.value = value;
    this.line = 0;
  }

  public NumberToken(String value, int line) {
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
    return TokenType.NUMBER;
  }

}
