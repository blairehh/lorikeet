package lorikeet.transpile;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class WordifyTest {

    @Test
    public void testOneSymbolRequires() {
        assertThat(Wordify.requiresWordify("%")).isTrue();
    }

    @Test
    public void testPlusRequires() {
        assertThat(Wordify.requiresWordify("+")).isTrue();
    }

    @Test
    public void testManySymbolRequires() {
        assertThat(Wordify.requiresWordify(">>%|~")).isTrue();
    }

    @Test
    public void testPlainWord() {
        assertThat(Wordify.requiresWordify("foo")).isFalse();
    }

    @Test
    public void testWordify() {
        assertThat(Wordify.wordify("=")).isEqualTo("equal");
        assertThat(Wordify.wordify("!=")).isEqualTo("exclequal");
        assertThat(Wordify.wordify(">>=")).isEqualTo("mtmtequal");
        assertThat(Wordify.wordify("|>")).isEqualTo("pipemt");
        assertThat(Wordify.wordify("$@")).isEqualTo("dollarat");
        assertThat(Wordify.wordify("*+-")).isEqualTo("astplushypen");
    }

}
