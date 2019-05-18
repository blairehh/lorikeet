package lorikeet.ecosphere.transcript;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TextReaderTest {

    @Test
    public void testNextToken() {
        final String text = "abc def ghi jkl mnop";
        assertThat(new TextReader(text, 4).nextToken()).isEqualTo("def");
    }

    @Test
    public void testNextTokenInMiddleOfToken() {
        final String text = "abcdef";
        assertThat(new TextReader(text, 3).nextToken()).isEqualTo("def");
    }

    @Test
    public void testNextTokenAtEnd() {
        final String text = "abc def ghi jkl mnop";
        assertThat(new TextReader(text, 16).nextToken()).isEqualTo("mnop");
    }

    @Test
    public void testNextTokenSkipsWhiespaceAtStart() {
        final String text = "  mnop";
        assertThat(new TextReader(text, 0).nextToken()).isEqualTo("mnop");
    }

    @Test
    public void testExtractNextIdentifier() {
        assertThat(new TextReader("lorikeet.Seq", 0).nextIdentifier().orPanic())
            .isEqualTo("lorikeet.Seq");
        assertThat(new TextReader("lorikeet.Seq <", 0).nextIdentifier().orPanic())
            .isEqualTo("lorikeet.Seq");
    }

    @Test
    public void testNextQuote() {
        TextReader textReader = new TextReader("abc'def'", 3);
        assertThat(textReader.nextQuote('\'').orPanic()).isEqualTo("def");
        assertThat(textReader.getCurrentIndex()).isEqualTo(8);
    }

    @Test
    public void testNextQuoteWithTextAfter() {
        TextReader textReader = new TextReader("abc'def'hij", 3);
        assertThat(textReader.nextQuote('\'').orPanic()).isEqualTo("def");
        assertThat(textReader.getCurrentIndex()).isEqualTo(8);
    }

    @Test
    public void testNextQuoteEscaped() {
        TextReader textReader = new TextReader("abc'de\\'f'", 3);
        assertThat(textReader.nextQuote('\'').orPanic()).isEqualTo("de'f");
    }

    @Test
    public void testNextQuoteDoubleEscaped() {
        TextReader textReader = new TextReader("abc'de\\\'f'", 3);
        assertThat(textReader.nextQuote('\'').orPanic()).isEqualTo("de'f");
    }

    @Test
    public void testNextQuoteNonQuoteMarkEscaped() {
        TextReader textReader = new TextReader("abc'de\tf'", 3);
        assertThat(textReader.nextQuote('\'').orPanic()).isEqualTo("de\tf");
    }

    @Test
    public void testNextQuoteNotThere() {
        TextReader textReader = new TextReader("abc'de\\'f'", 2);
        assertThat(textReader.nextQuote('\'').isPresent()).isFalse();
    }

    @Test
    public void testNextQuoteDoesNotEnd() {
        TextReader textReader = new TextReader("abc'de\\'f", 3);
        assertThat(textReader.nextQuote('\'').isPresent()).isFalse();
    }
}