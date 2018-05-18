package lorikeet.parse;

import lorikeet.token.TokenSeq;

public interface Parser<T> {
  public Parse<T> parse(TokenSeq tokens);
}
