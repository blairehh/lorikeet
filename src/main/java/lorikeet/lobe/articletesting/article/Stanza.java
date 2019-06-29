package lorikeet.lobe.articletesting.article;

import lorikeet.Seq;

import java.util.Objects;

public class Stanza {
    private final String name;
    private final Seq<String> attributes;
    private final String documentation;
    private final String content;

    public Stanza(String name, Seq<String> attributes, String documentation, String content) {
        this.name = name;
        this.attributes = attributes;
        this.documentation = documentation;
        this.content = content;
    }

    public Stanza(StanzaTitle title, String documentation, String content) {
        this.name = title.getName();
        this.attributes = title.getAttributes();
        this.documentation = documentation;
        this.content = content;
    }

    public String getName() {
        return this.name;
    }

    public Seq<String> getAttributes() {
        return this.attributes;
    }

    public String getDocumentation() {
        return this.documentation;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Stanza stanza = (Stanza) o;

        return Objects.equals(this.getName(), stanza.getName())
            && Objects.equals(this.getAttributes(), stanza.getAttributes())
            && Objects.equals(this.getDocumentation(), stanza.getDocumentation())
            && Objects.equals(this.getContent(), stanza.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getAttributes(), this.getDocumentation(), this.getContent());
    }
}
