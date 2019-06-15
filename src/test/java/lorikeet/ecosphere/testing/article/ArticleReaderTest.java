package lorikeet.ecosphere.testing.article;

import lorikeet.IO;
import lorikeet.ecosphere.testing.reader.LineReader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticleReaderTest {

    private final ArticleReader reader = new ArticleReader();

    @Test
    public void testOneStanza() {
        String articleContents = IO.readResource("articles/one-stanza.article").orPanic();
        Article article = reader.read("foo.article", new LineReader(articleContents)).orPanic();
        assertThat(article.getType()).isEqualTo("cell");
        assertThat(article.getStanzas()).hasSize(1);
        assertThat(article.getFilePath()).isEqualTo("foo.article");
        assertThat(article.getStanzas().get(0).getName()).isEqualTo("foo");
        assertThat(article.getStanzas().get(0).getAttributes()).isEmpty();
        assertThat(article.getStanzas().get(0).getDocumentation()).isEqualTo("This is a sample documentation section");
        assertThat(article.getStanzas().get(0).getContent()).isEqualTo("    bar\n");
    }

    @Test
    public void testOneStanzaWithoutDoc() {
        String articleContents = IO.readResource("articles/one-stanza-no-doc.article").orPanic();
        Article article = reader.read("foo.article", new LineReader(articleContents)).orPanic();
        assertThat(article.getType()).isEqualTo("cell");
        assertThat(article.getStanzas()).hasSize(1);
        assertThat(article.getStanzas().get(0).getName()).isEqualTo("bar");
        assertThat(article.getStanzas().get(0).getAttributes()).isEmpty();
        assertThat(article.getStanzas().get(0).getDocumentation()).isEmpty();
        assertThat(article.getStanzas().get(0).getContent()).isEqualTo("    foo\n");
    }

    @Test
    public void testTwoStanzas() {
        String articleContents = IO.readResource("articles/two-stanzas.article").orPanic();
        Article article = reader.read("foo.article", new LineReader(articleContents)).orPanic();
        assertThat(article.getType()).isEqualTo("test");
        assertThat(article.getStanzas()).hasSize(2);

        assertThat(article.getStanzas().get(0).getName()).isEqualTo("case");
        assertThat(article.getStanzas().get(0).getAttributes()).isEmpty();
        assertThat(article.getStanzas().get(0).getDocumentation()).isEqualTo("somehting here");
        assertThat(article.getStanzas().get(0).getContent()).isEqualTo("    test subject\n");

        assertThat(article.getStanzas().get(1).getName()).isEqualTo("verify");
        assertThat(article.getStanzas().get(1).getAttributes()).isEmpty();
        assertThat(article.getStanzas().get(1).getDocumentation()).isEqualTo("should be zero");
        assertThat(article.getStanzas().get(1).getContent()).isEqualTo("    0\n");
    }

    @Test
    public void testTwoStanzasNoDoc() {
        String articleContents = IO.readResource("articles/two-stanzas-no-doc.article").orPanic();
        Article article = reader.read("foo.article", new LineReader(articleContents)).orPanic();
        assertThat(article.getType()).isEqualTo("cell");
        assertThat(article.getStanzas()).hasSize(2);

        assertThat(article.getStanzas().get(0).getName()).isEqualTo("verify");
        assertThat(article.getStanzas().get(0).getAttributes()).isEmpty();
        assertThat(article.getStanzas().get(0).getDocumentation()).isEmpty();
            assertThat(article.getStanzas().get(0).getContent()).isEqualTo("    0\n");

        assertThat(article.getStanzas().get(1).getName()).isEqualTo("provide");
        assertThat(article.getStanzas().get(1).getAttributes()).isEmpty();
        assertThat(article.getStanzas().get(1).getDocumentation()).isEmpty();
        assertThat(article.getStanzas().get(1).getContent()).isEqualTo("    nothing\n");
    }

    @Test
    public void testArticleWithMultiLineDoc() {
        String articleContents = IO.readResource("articles/stanza-with-multi-line-doc.article").orPanic();
        Article article = reader.read("foo.article", new LineReader(articleContents)).orPanic();
        assertThat(article.getType()).isEqualTo("cell");
        assertThat(article.getStanzas()).hasSize(1);

        String doc = "This is a sample documentation section\n" +
            "    this is the second line\n" +
            "    this is the third line";

        assertThat(article.getStanzas().get(0).getName()).isEqualTo("baz");
        assertThat(article.getStanzas().get(0).getAttributes()).isEmpty();
        assertThat(article.getStanzas().get(0).getDocumentation()).isEqualTo(doc);
        assertThat(article.getStanzas().get(0).getContent()).isEqualTo("    blimp\n");
    }

    @Test
    public void testArticleWithMultiLineContent() {
        String articleContents = IO.readResource("articles/stanza-with-multi-line-content.article").orPanic();
        Article article = reader.read("foo.article", new LineReader(articleContents)).orPanic();
        assertThat(article.getType()).isEqualTo("sql-query");
        assertThat(article.getStanzas()).hasSize(1);

        String query = "    INSERT INTO users(id, name) VALUES(1, \"Bob\");\n" +
            "    INSERT INTO users(id, name) VALUES(2, \"Jane\");\n";

        assertThat(article.getStanzas().get(0).getName()).isEqualTo("pre");
        assertThat(article.getStanzas().get(0).getAttributes()).isEmpty();
        assertThat(article.getStanzas().get(0).getDocumentation()).isEmpty();
        assertThat(article.getStanzas().get(0).getContent()).isEqualTo(query);
    }
}