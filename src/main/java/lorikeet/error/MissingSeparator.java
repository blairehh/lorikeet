package lorikeet.error;

import lorikeet.error.CompileError;
import lorikeet.util.Console;
import lorikeet.token.TokenSeq;

import java.util.Objects;

public class MissingSeparator implements CompileError {
    private final TokenSeq tokenSeq;

    public MissingSeparator(TokenSeq tokenSeq) {
        this.tokenSeq = tokenSeq;
    }

    public TokenSeq getTokenSeq() {
        return this.tokenSeq;
    }

    @Override
    public void outputTo(Console console) {
        console.print("Error in %s", this.getTokenSeq().getFile().getAbsolutePath());
        console.print("Must have a separator have of either ',' or '\\n'");
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        MissingSeparator that = (MissingSeparator)o;

        return Objects.equals(this.getTokenSeq(), that.getTokenSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tokenSeq);
    }
}
