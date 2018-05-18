package lorikeet.error;

import lorikeet.token.TokenSeq;
import lorikeet.util.Console;

import java.util.Objects;

public class VariableNameConflict implements CompileError {

    private final TokenSeq tokenSeq;
    private final String name;

    public VariableNameConflict(TokenSeq tokenSeq, String name) {
        this.tokenSeq = tokenSeq;
        this.name = name;
    }

    public TokenSeq getTokenSeq() {
        return this.tokenSeq;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void outputTo(Console console) {
        console.print("Error in %s", this.getTokenSeq().getFile().getAbsolutePath());
        console.print(
            "Variable name '%s' is already declared for another variable, expr",
            this.getName()
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

        VariableNameConflict that = (VariableNameConflict)o;

        return Objects.equals(this.getTokenSeq(), that.getTokenSeq())
            && Objects.equals(this.getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tokenSeq, this.name);
    }

}
