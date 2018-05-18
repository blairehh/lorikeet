package lorikeet.token;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class TokenizerTest {


    @Test
    public void simpleWords() {
        Tokenizer tokenizer = new Tokenizer();
        TokenSeq tokens = tokenizer.tokenize("the quick brown fox jumped 0ver the f3nce 678");
        check(tokens, 17);
        checkTx(tokens, 0, "the", 1);
        checkWs(tokens, 1, 1);
        checkTx(tokens, 2, "quick", 1);
        checkWs(tokens, 3, 1);
        checkTx(tokens, 4, "brown", 1);
        checkWs(tokens, 5, 1);
        checkTx(tokens, 6, "fox", 1);
        checkWs(tokens, 7, 1);
        checkTx(tokens, 8, "jumped", 1);
        checkWs(tokens, 9, 1);
        checkTx(tokens, 10, "0ver", 1);
        checkWs(tokens, 11, 1);
        checkTx(tokens, 12, "the", 1);
        checkWs(tokens, 13, 1);
        checkTx(tokens, 14, "f3nce", 1);
        checkWs(tokens, 15, 1);
        checkNm(tokens, 16, "678", 1);
  }

    @Test
    public void withNewLines() {
        Tokenizer tokenizer = new Tokenizer();
        TokenSeq tokens = tokenizer.tokenize("my poem\nthis is an awful poem\nthe end");
        check(tokens, 17);
        checkTx(tokens, 0, "my", 1);
        checkWs(tokens, 1, 1);
        checkTx(tokens, 2, "poem", 1);
        checkNl(tokens, 3, 1);
        checkTx(tokens, 4, "this", 2);
        checkWs(tokens, 5, 2);
        checkTx(tokens, 6, "is", 2);
        checkWs(tokens, 7, 2);
        checkTx(tokens, 8, "an", 2);
        checkWs(tokens, 9, 2);
        checkTx(tokens, 10, "awful", 2);
        checkWs(tokens, 11, 2);
        checkTx(tokens, 12, "poem", 2);
        checkNl(tokens, 13, 2);
        checkTx(tokens, 14, "the", 3);
        checkWs(tokens, 15, 3);
        checkTx(tokens, 16, "end", 3);
    }

    @Test
    public void withSomeSymbolsAndTabs() {
        Tokenizer tokenizer = new Tokenizer();
        TokenSeq tokens = tokenizer.tokenize("expr struct Foo {\n:algebraic int bar;\n^package Foo(int a, int b){}\n}");
        check(tokens, 34);
        checkKw(tokens, 0, Keyword.EXPR, 1);
        checkWs(tokens, 1, 1);
        checkKw(tokens, 2, Keyword.STRUCT, 1);
        checkWs(tokens, 3, 1);
        checkTx(tokens, 4, "Foo", 1);
        checkWs(tokens, 5, 1);
        checkSy(tokens, 6, Symbol.OPEN_CURLY, 1);
        checkNl(tokens, 7, 1);
        checkSy(tokens, 8, Symbol.COLON, 2);
        checkKw(tokens, 9, Keyword.ALGEBRAIC, 2);
        checkWs(tokens, 10, 2);
        checkTx(tokens, 11, "int", 2);
        checkWs(tokens, 12, 2);
        checkTx(tokens, 13, "bar", 2);
        checkSy(tokens, 14, Symbol.SEMICOLON, 2);
        checkNl(tokens, 15, 2);
        checkSy(tokens, 16, Symbol.CARET, 3);
        checkKw(tokens, 17, Keyword.PACKAGE, 3);
        checkWs(tokens, 18, 3);
        checkTx(tokens, 19, "Foo", 3);
        checkSy(tokens, 20, Symbol.OPEN_ROUND, 3);
        checkTx(tokens, 21, "int", 3);
        checkWs(tokens, 22, 3);
        checkTx(tokens, 23, "a", 3);
        checkSy(tokens, 24, Symbol.COMMA, 3);
        checkWs(tokens, 25, 3);
        checkTx(tokens, 26, "int", 3);
        checkWs(tokens, 27, 3);
        checkTx(tokens, 28, "b", 3);
        checkSy(tokens, 29, Symbol.CLOSE_ROUND, 3);
        checkSy(tokens, 30, Symbol.OPEN_CURLY, 3);
        checkSy(tokens, 31, Symbol.CLOSE_CURLY, 3);
        checkNl(tokens, 32, 3);
        checkSy(tokens, 33, Symbol.CLOSE_CURLY, 4);
    }

    @Test
    public void specialSymbols() {
        Tokenizer tokenizer = new Tokenizer();
        TokenSeq tokens = tokenizer.tokenize("a == b b != c c >= d d <= (-> f&& ||g");
        check(tokens, 30);
        checkTx(tokens, 0, "a", 1);
        checkWs(tokens, 1, 1);
        checkSy(tokens, 2, Symbol.EQUALS, 1);
        checkWs(tokens, 3, 1);
        checkTx(tokens, 4, "b", 1);
        checkWs(tokens, 5, 1);
        checkTx(tokens, 6, "b", 1);
        checkWs(tokens, 7, 1);
        checkSy(tokens, 8, Symbol.NOT_EQUALS, 1);
        checkWs(tokens, 9, 1);
        checkTx(tokens, 10, "c", 1);
        checkWs(tokens, 11, 1);
        checkTx(tokens, 12, "c", 1);
        checkWs(tokens, 13, 1);
        checkSy(tokens, 14, Symbol.MORE_THAN_EQUALS, 1);
        checkWs(tokens, 15, 1);
        checkTx(tokens, 16, "d", 1);
        checkWs(tokens, 17, 1);
        checkTx(tokens, 18, "d", 1);
        checkWs(tokens, 19, 1);
        checkSy(tokens, 20, Symbol.LESS_THAN_EQUALS, 1);
        checkWs(tokens, 21, 1);
        checkSy(tokens, 22, Symbol.OPEN_ROUND, 1);
        checkSy(tokens, 23, Symbol.THIN_ARROW, 1);
        checkWs(tokens, 24, 1);
        checkTx(tokens, 25, "f", 1);
        checkSy(tokens, 26, Symbol.LOGICAL_AND, 1);
        checkWs(tokens, 27, 1);
        checkSy(tokens, 28, Symbol.LOGICAL_OR, 1);
        checkTx(tokens, 29, "g", 1);
    }

    @Test
    public void withQuote() {
        Tokenizer tokenizer = new Tokenizer();
        TokenSeq tokens = tokenizer.tokenize("String name = 'Bob John';");
        check(tokens, 8);
        checkTx(tokens, 0, "String", 1);
        checkWs(tokens, 1, 1);
        checkTx(tokens, 2, "name", 1);
        checkWs(tokens, 3, 1);
        checkSy(tokens, 4, Symbol.EQUAL, 1);
        checkWs(tokens, 5, 1);
        checkQt(tokens, 6, "'", "Bob John", 1);
        checkSy(tokens, 7, Symbol.SEMICOLON, 1);
    }

    @Test
    public void quoteOverTwoLines() {
        Tokenizer tokenizer = new Tokenizer();
        TokenSeq tokens = tokenizer.tokenize("foo \"hello\nworld\"baz");
        check(tokens, 4);
        checkTx(tokens, 0, "foo", 1);
        checkWs(tokens, 1, 1);
        checkQt(tokens, 2, "\"", "hello\nworld", 1);
        checkTx(tokens, 3, "baz", 2);
    }

    @Test
    public void testTwoTypesOfQuotes() {
        Tokenizer tokenizer = new Tokenizer();
        TokenSeq tokens = tokenizer.tokenize("foo \"hello world\"baz'my name'``");
        check(tokens, 6);

        checkTx(tokens, 0, "foo", 1);
        checkWs(tokens, 1, 1);
        checkQt(tokens, 2, "\"", "hello world", 1);
        checkTx(tokens, 3, "baz", 1);
        checkQt(tokens, 4, "'", "my name", 1);
        checkQt(tokens, 5, "`", "", 1);
    }

    @Test
    public void testQuoteWithInQuoteIsNotAnother() {
        Tokenizer tokenizer = new Tokenizer();
        TokenSeq tokens = tokenizer.tokenize("foo = 'foo = `bar`'");
        check(tokens, 5);

        checkTx(tokens, 0, "foo", 1);
        checkWs(tokens, 1, 1);
        checkSy(tokens, 2, Symbol.EQUAL, 1);
        checkWs(tokens, 3, 1);
        checkQt(tokens, 4, "'", "foo = `bar`", 1);
    }

  @Test
  public void testComment() {
      Tokenizer tokenizer = new Tokenizer();
      TokenSeq tokens = tokenizer.tokenize("foo = bar #<the is a comment >#ee");
      check(tokens, 7);

      checkTx(tokens, 0, "foo", 1);
      checkWs(tokens, 1, 1);
      checkSy(tokens, 2, Symbol.EQUAL, 1);
      checkWs(tokens, 3, 1);
      checkTx(tokens, 4, "bar", 1);
      checkWs(tokens, 5, 1);
      checkTx(tokens, 6, "ee", 1);
  }

    @Test
    public void testSkipsConsecutiveWhitespacesOrAfterNewline() {
        Tokenizer tokenizer = new Tokenizer();
        TokenSeq tokens = tokenizer.tokenize(" a   b c     \n    d   e  e f");

        check(tokens, 15);
        checkWs(tokens, 0, 1);
        checkTx(tokens, 1, "a", 1);
        checkWs(tokens, 2, 1);
        checkTx(tokens, 3, "b", 1);
        checkWs(tokens, 4, 1);
        checkTx(tokens, 5, "c", 1);
        checkWs(tokens, 6, 1);
        checkNl(tokens, 7, 1);
        checkTx(tokens, 8, "d", 2);
        checkWs(tokens, 9, 2);
        checkTx(tokens, 10, "e", 2);
        checkWs(tokens, 11, 2);
        checkTx(tokens, 12, "e", 2);
        checkWs(tokens, 13, 2);
        checkTx(tokens, 14, "f", 2);
    }

    @Test
    public void testSkipsConsecutiveNewLines() {
        Tokenizer tokenizer = new Tokenizer();
        TokenSeq tokens = tokenizer.tokenize("a\nb\n\n\n\nc");
        check(tokens, 5);
        checkTx(tokens, 0, "a", 1);
        checkNl(tokens, 1, 1);
        checkTx(tokens, 2, "b", 2);
        checkNl(tokens, 3, 2);
        checkTx(tokens, 4, "c", 6);
    }

    @Test
    public void testSkipsConsecutiveNewLinesWithWhitespace() {
        Tokenizer tokenizer = new Tokenizer();
        TokenSeq tokens = tokenizer.tokenize("a\nb\n\n         \n  c  \n \n  d");
        check(tokens, 8);
        checkTx(tokens, 0, "a", 1);
        checkNl(tokens, 1, 1);
        checkTx(tokens, 2, "b", 2);
        checkNl(tokens, 3, 2);
        checkTx(tokens, 4, "c", 5);
        checkWs(tokens, 5, 5);
        checkNl(tokens, 6, 5);
        checkTx(tokens, 7, "d", 7);
    }

    @Test
    public void testNumbers() {
        Tokenizer tokenizer = new Tokenizer();
        TokenSeq tokens = tokenizer.tokenize("45 67.9 -5 -56.454 5G 56.78.554 ");
        check(tokens, 12);
        checkNm(tokens, 0, "45", 1);
        checkWs(tokens, 1, 1);
        checkNm(tokens, 2, "67.9", 1);
        checkWs(tokens, 3, 1);
        checkNm(tokens, 4, "-5", 1);
        checkWs(tokens, 5, 1);
        checkNm(tokens, 6, "-56.454", 1);
        checkWs(tokens, 7, 1);
        checkTx(tokens, 8, "5G", 1);
        checkWs(tokens, 9, 1);
        checkTx(tokens, 10, "56.78.554", 1);
        checkWs(tokens, 11, 1);
    }

    @Test
    public void testToNextDeclaration() {
        Tokenizer tokenizer = new Tokenizer();
        TokenSeq tokens = tokenizer.tokenize("Foo.bar(a Int, b Str) Foo = (System.out println: 6) func Foo.baz");

        TokenSeq moved = tokens.toNextDeclaration();
        assertThat(moved.currentStr()).isEqualTo("func");
    }


    void check(TokenSeq seq, int count) {
        assertThat(seq.count()).isEqualTo(count);
    }

    void checkTx(TokenSeq seq, int index, String value, int line) {
        assertThat(seq.at(index).getTokenType()).isEqualTo(TokenType.TEXT);
        assertThat(seq.at(index).str()).isEqualTo(value);
        assertThat(seq.at(index).getLine()).isEqualTo(line);
    }

    void checkNm(TokenSeq seq, int index, String value, int line) {
        assertThat(seq.at(index).getTokenType()).isEqualTo(TokenType.NUMBER);
        assertThat(seq.at(index).str()).isEqualTo(value);
        assertThat(seq.at(index).getLine()).isEqualTo(line);
    }

    void checkWs(TokenSeq seq, int index, int line) {
        assertThat(seq.at(index).getTokenType()).isEqualTo(TokenType.WHITESPACE);
        assertThat(seq.at(index).getLine()).isEqualTo(line);
    }

    void checkNl(TokenSeq seq, int index, int line) {
        assertThat(seq.at(index).getTokenType()).isEqualTo(TokenType.NEWLINE);
        assertThat(seq.at(index).getLine()).isEqualTo(line);
    }

    void checkKw(TokenSeq seq, int index, Keyword kw, int line) {
        assertThat(seq.at(index).getTokenType()).isEqualTo(TokenType.KEYWORD);
        assertThat(seq.at(index).isKeyword(kw)).isTrue();
        assertThat(seq.at(index).getLine()).isEqualTo(line);
    }

    void checkSy(TokenSeq seq, int index, Symbol sy, int line) {
        assertThat(seq.at(index).getTokenType()).isEqualTo(TokenType.SYMBOL);
        assertThat(seq.at(index).isSymbol(sy)).isTrue();
        assertThat(seq.at(index).getLine()).isEqualTo(line);
    }

    void checkQt(TokenSeq seq, int index, String quote, String quotation, int line) {
        assertThat(seq.at(index).getTokenType()).isEqualTo(TokenType.QUOTATION);
        QuotationToken token = (QuotationToken)seq.at(index);
        assertThat(token.getLine()).isEqualTo(line);
        assertThat(token.getQuote()).isEqualTo(quote);
        assertThat(token.getQuotation()).isEqualTo(quotation);
    }

}
