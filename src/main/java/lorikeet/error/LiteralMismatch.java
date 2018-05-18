package lorikeet.error;

import lorikeet.lang.Type;
import lorikeet.token.Token;
import lorikeet.token.TokenSeq;
import lorikeet.util.Console;

import java.util.Objects;

public class LiteralMismatch implements CompileError {
    private final Token token;
    private final TokenSeq tokenSeq;
    private final Type type;

    public LiteralMismatch(TokenSeq tokenSeq, Type type) {
        this.token = tokenSeq.current();
        this.tokenSeq = tokenSeq;
        this.type = type;
    }

    public TokenSeq getTokenSeq() {
        return this.tokenSeq;
    }

    public Token getToken() {
        return this.token;
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public void outputTo(Console console) {
        console.print("Error in %s", this.getTokenSeq().getFile().getAbsolutePath());
        console.print(
            "Can not assign literal '%s' to type %s",
            this.getToken().str(),
            this.getType().toString()
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

        LiteralMismatch that = (LiteralMismatch)o;

        return Objects.equals(this.getToken(), that.getToken())
            && Objects.equals(this.getTokenSeq(), that.getTokenSeq())
            && Objects.equals(this.getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tokenSeq, this.token, this.type);
    }
}
