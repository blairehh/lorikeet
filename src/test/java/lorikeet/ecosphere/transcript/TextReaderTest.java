package lorikeet.ecosphere.transcript;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TextReaderTest {

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

    @Test
    public void testNextNumber() {
        TextReader textReader = new TextReader("56 '", 0);
        assertThat(textReader.nextNumber().orPanic()).isEqualTo(56.0);
        assertThat(textReader.getCurrentIndex()).isEqualTo(3);
    }

    @Test
    public void testSkip() {
        TextReader textReader = new TextReader("abcdefg", 1);
        textReader.skip();
        assertThat(textReader.getCurrentChar()).isEqualTo('c');
    }

    @Test
    public void testSkipAndSpaces() {
        TextReader textReader = new TextReader("a, b", 1);
        textReader.skip();
        assertThat(textReader.getCurrentChar()).isEqualTo('b');
    }

    @Test
    public void testNextNumberBesideNonNumber() {
        TextReader textReader = new TextReader("1]", 0);
        assertThat(textReader.nextNumber().orPanic()).isEqualTo(1.0);
        assertThat(textReader.getCurrentIndex()).isEqualTo(1);
    }

    @Test
    public void testNextNumberWithFraction() {
        TextReader textReader = new TextReader("1.5/3", 0);
        assertThat(textReader.nextNumber().orPanic()).isEqualTo(1.5);
        assertThat(textReader.getCurrentIndex()).isEqualTo(3);
    }

    @Test
    public void testNextNumberAsNegative() {
        TextReader textReader = new TextReader("-5 ", 0);
        assertThat(textReader.nextNumber().orPanic()).isEqualTo(-5.0);
        assertThat(textReader.getCurrentIndex()).isEqualTo(3);
    }

    @Test
    public void testNextNumberAsNegativeFraction() {
        TextReader textReader = new TextReader("-5.99 ", 0);
        assertThat(textReader.nextNumber().orPanic()).isEqualTo(-5.99);
        assertThat(textReader.getCurrentIndex()).isEqualTo(6);
    }

    @Test
    public void testNextNumberOnlyOneDash() {
        TextReader textReader = new TextReader("-2-2 ", 0);
        assertThat(textReader.nextNumber().orPanic()).isEqualTo(-2.0);
        assertThat(textReader.getCurrentIndex()).isEqualTo(2);
    }

    @Test
    public void testNextAlphaNumericWord() {
        TextReader textReader = new TextReader("fooBar", 0);
        assertThat(textReader.nextAlphaNumericWord(false).orPanic()).isEqualTo("fooBar");
        assertThat(textReader.getCurrentIndex()).isEqualTo(6);
    }

    @Test
    public void testNextAlphaNumericWordWithUnderscore() {
        TextReader textReader = new TextReader("foo_Bar", 0);
        assertThat(textReader.nextAlphaNumericWord(false).orPanic()).isEqualTo("foo_Bar");
        assertThat(textReader.getCurrentIndex()).isEqualTo(7);
    }

    @Test
    public void testNextAlphaNumericWordEndedBySymbol() {
        TextReader textReader = new TextReader("foo@", 0);
        assertThat(textReader.nextAlphaNumericWord(false).orPanic()).isEqualTo("foo");
        assertThat(textReader.getCurrentIndex()).isEqualTo(3);
    }

    @Test
    public void testNextAlphaNumericWordWithNumber() {
        TextReader textReader = new TextReader("foo2", 0);
        assertThat(textReader.nextAlphaNumericWord(false).orPanic()).isEqualTo("foo2");
        assertThat(textReader.getCurrentIndex()).isEqualTo(4);
    }

    @Test
    public void testNextAlphaNumericCanNotHaveNumberAtStart() {
        TextReader textReader = new TextReader("1foo", 0);
        assertThat(textReader.nextAlphaNumericWord(false).isPresent()).isFalse();
    }

    @Test
    public void testNextAlphaNumericCanHaveNumberAtStartIsSpecifiedToDoSo() {
        TextReader textReader = new TextReader("1foo", 0);
        assertThat(textReader.nextAlphaNumericWord(true).orPanic()).isEqualTo("1foo");
    }
}