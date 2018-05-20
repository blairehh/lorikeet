package lorikeet.error;

import lorikeet.token.TokenSeq;
import lorikeet.util.Console;

import java.util.Objects;

public class UnknownVariableOrExpression implements CompileError {
    private final TokenSeq tokenSeq;
    private final String value;

    public UnknownVariableOrExpression(TokenSeq tokenSeq) {
        this.tokenSeq = tokenSeq;
        this.value = tokenSeq.currentStr();
    }

    public TokenSeq getTokenSeq() {
        return this.tokenSeq;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public void outputTo(Console console) {
        console.print("Error in %s", this.getTokenSeq().getFile().getAbsolutePath());
        console.print("Could not find an expression or variable '%s'", this.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        UnknownVariableOrExpression that = (UnknownVariableOrExpression)o;

        return Objects.equals(this.getTokenSeq(), that.getTokenSeq())
            && Objects.equals(this.getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tokenSeq, this.value);
    }

}
