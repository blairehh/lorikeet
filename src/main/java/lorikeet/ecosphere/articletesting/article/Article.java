package lorikeet.ecosphere.articletesting.article;

import lorikeet.Opt;
import lorikeet.Seq;

import java.util.Objects;

public class Article {

    private final String filePath;
    private final String type;
    private final Seq<Stanza> stanzas;

    public Article(String filePath, String type, Seq<Stanza> stanzas) {
        this.filePath = filePath;
        this.type = type;
        this.stanzas = stanzas;
    }

    public Opt<Stanza> findStanza(String name) {
        return this.stanzas.filter(stanza -> stanza.getName().equalsIgnoreCase(name)).first();
    }

    public String getFilePath() {
        return this.filePath;
    }

    public String getType() {
        return this.type;
    }

    public Seq<Stanza> getStanzas() {
        return this.stanzas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Article article = (Article) o;

        return Objects.equals(this.getFilePath(), article.getFilePath())
            && Objects.equals(this.getType(), article.getType())
            && Objects.equals(this.getStanzas(), article.getStanzas());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getFilePath(), this.getStanzas(), this.getType());
    }
}
