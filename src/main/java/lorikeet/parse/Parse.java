package lorikeet.parse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import lorikeet.error.CompileError;
import lorikeet.token.TokenSeq;
import lorikeet.token.Token;


public class Parse<T> {

  @FunctionalInterface
  public interface ThenFunction<ParameterOne, ParameterTwo, Return> {
    public Return apply(ParameterOne a, ParameterTwo b);
  }

  @FunctionalInterface
  public interface OtherwiseFunction<TS, TK, CE, Return> {
    public Return apply(TS tokenSeq, Token token, CE error);
  }

  private final T result;
  private final TokenSeq tokens;
  private final List<CompileError> compileError;

  public Parse(T result, TokenSeq tokens) {
    this.result = result;
    this.tokens = tokens;
    this.compileError = Collections.emptyList();
  }

  public Parse(CompileError compileError) {
      this.result = null;
      this.tokens = null;
      this.compileError = Collections.singletonList(compileError);
  }

  public Parse(List<CompileError> errors) {
      this.result = null;
      this.tokens = null;
      this.compileError = errors;
  }

  public Parse(Parse<?> failedParse) {
    this.result = null;
    this.tokens = failedParse.getTokenSeq();
    this.compileError = failedParse.getErrors();
  }

  public boolean succeded() {
    return this.result != null;
  }

  public boolean failed() {
    return !this.succeded();
  }

  public T getResult() {
    return this.result;
  }

  public List<CompileError> getErrors() {
      return this.compileError;
  }

  public CompileError getErrors(int index) {
      return this.compileError.get(index);
  }


  public TokenSeq getTokenSeq() {
    return this.tokens;
  }

  public <A> Parse<A> then(ThenFunction<T, TokenSeq, Parse<A>> func) {
    if (this.failed()) {
      return new Parse<A>(this);
    }
    return func.apply(this.result, this.tokens);
  }

}
