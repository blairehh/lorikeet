package lorikeet.error;

import lorikeet.lang.Type;
import lorikeet.token.TokenSeq;
import lorikeet.util.Console;

import java.util.Objects;

public class TypeMismatch implements CompileError {

    private final TokenSeq tokenSeq;
    private final Type expected;
    private final Type found;

    public TypeMismatch(TokenSeq tokenSeq, Type expected, Type found) {
        this.tokenSeq = tokenSeq;
        this.expected = expected;
        this.found = found;
    }

    public TokenSeq getTokenSeq() {
        return this.tokenSeq;
    }

    public Type getExpected() {
        return this.expected;
    }

    public Type getFound() {
        return this.found;
    }


    @Override
    public void outputTo(Console console) {
        console.print("Error in %s", this.getTokenSeq().getFile().getAbsolutePath());
        console.print(
            "Type %s is not applicable to type %s",
            this.getExpected(),
            this.getFound()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        TypeMismatch that = (TypeMismatch)o;

        return Objects.equals(this.getTokenSeq(), that.getTokenSeq())
            && Objects.equals(this.getExpected(), that.getExpected())
            && Objects.equals(this.getFound(), that.getFound());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tokenSeq, this.expected, this.found);
    }
}
