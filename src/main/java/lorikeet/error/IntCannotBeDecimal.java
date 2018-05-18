package lorikeet.error;

import lorikeet.token.TokenSeq;
import lorikeet.util.Console;

import java.util.Objects;

public class IntCannotBeDecimal implements CompileError {

    private final TokenSeq tokenSeq;

    public IntCannotBeDecimal(TokenSeq tokenSeq) {
        this.tokenSeq = tokenSeq;
    }

    public TokenSeq getTokenSeq() {
        return this.tokenSeq;
    }

    @Override
    public void outputTo(Console console) {
        console.print("Error in %s", this.getTokenSeq().getFile().getAbsolutePath());
        console.print("'%s' is not an integer value", this.tokenSeq.currentStr());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        IntCannotBeDecimal that = (IntCannotBeDecimal)o;

        return Objects.equals(this.getTokenSeq(), that.getTokenSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tokenSeq);
    }
}
