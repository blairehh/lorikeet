package lorikeet.ecosphere.testing.article;

import lorikeet.Seq;

import java.util.Objects;

public class Article {

    private final Seq<Stanza> stanzas;

    public Article(Seq<Stanza> stanzas) {
        this.stanzas = stanzas;
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

        return Objects.equals(this.getStanzas(), article.getStanzas());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getStanzas());
    }
}
