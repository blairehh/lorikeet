package lorikeet.lobe.articletesting.article;

import lorikeet.Seq;

import java.util.Objects;

public class StanzaTitle {
    private final String name;
    private final Seq<String> attributes;

    public StanzaTitle(String name, Seq<String> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String getName() {
        return this.name;
    }

    public Seq<String> getAttributes() {
        return this.attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        StanzaTitle that = (StanzaTitle) o;

        return Objects.equals(this.getName(), that.getName())
            && Objects.equals(this.getAttributes(), that.getAttributes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getAttributes());
    }
}
