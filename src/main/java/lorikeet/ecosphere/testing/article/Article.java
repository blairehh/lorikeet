package lorikeet.ecosphere.testing.article;

import lorikeet.Seq;

import java.util.Objects;

public class Article {

    private final String type;
    private final Seq<Stanza> stanzas;

    public Article(String type, Seq<Stanza> stanzas) {
        this.type = type;
        this.stanzas = stanzas;
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

        return Objects.equals(this.getType(), article.getType())
            && Objects.equals(this.getStanzas(), article.getStanzas());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getStanzas(), this.getType());
    }
}
