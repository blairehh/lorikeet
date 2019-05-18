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
    public void testNextTokenAtEnd() {
        final String text = "abc def ghi jkl mnop";
        assertThat(new TextReader(text, 16).nextToken()).isEqualTo("mnop");
    }

    @Test
    public void testNextTokenSkipsWhiespaceAtStart() {
        final String text = "  mnop";
        assertThat(new TextReader(text, 0).nextToken()).isEqualTo("mnop");
    }
}