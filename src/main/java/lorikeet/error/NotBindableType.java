package lorikeet.error;

import lorikeet.lang.Function;
import lorikeet.token.TokenSeq;
import lorikeet.util.Console;

import java.util.Objects;

public class NotBindableType implements CompileError {

    private final TokenSeq tokenSeq;
    private final Function func;

    public NotBindableType(TokenSeq tokenSeq, Function func) {
        this.tokenSeq = tokenSeq;
        this.func = func;
    }

    public TokenSeq getTokenSeq() {
        return this.tokenSeq;
    }

    public Function getFunction() {
        return this.func;
    }

    @Override
    public void outputTo(Console console) {
        console.print("Error in %s", this.getTokenSeq().getFile().getAbsolutePath());
        console.print(
            "Type '%s' is not a bindble type in attmpt with function %s",
            this.func.getType(),
            this.func.getName()
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

        NotBindableType that = (NotBindableType)o;

        return Objects.equals(this.getFunction(), that.getFunction())
            && Objects.equals(this.getTokenSeq(), that.getTokenSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tokenSeq, this.func);
    }
}
