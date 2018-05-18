package lorikeet.error;

import lorikeet.lang.Attribute;
import lorikeet.token.TokenSeq;
import lorikeet.util.Console;

import java.util.Objects;

public class AttributeNameConflict implements CompileError {
    private final TokenSeq tokenSeq;
    private final Attribute attribute;

    public AttributeNameConflict(TokenSeq tokenSeq, Attribute attribute) {
        this.attribute = attribute;
        this.tokenSeq = tokenSeq;
    }

    public TokenSeq getTokenSeq() {
        return this.tokenSeq;
    }

    public Attribute getAttribute() {
        return this.attribute;
    }

    @Override
    public void outputTo(Console console) {
        console.print("Error in %s", this.getTokenSeq().getFile().getAbsolutePath());
        console.print(
            "Attribute with name '%s' has already been declared",
            this.getAttribute().getName()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        AttributeNameConflict that = (AttributeNameConflict)o;

        return Objects.equals(this.getTokenSeq(), that.getTokenSeq())
            && Objects.equals(this.getAttribute(), that.getAttribute());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tokenSeq, this.attribute);
    }

}
