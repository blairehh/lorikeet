package lorikeet.error;

import lorikeet.token.Token;
import lorikeet.token.TokenSeq;
import lorikeet.util.Console;

import java.util.Objects;

public class InvalidTypeName implements CompileError {
    private final String value;
    private final TokenSeq tokenSeq;
    private final Token token;

    public InvalidTypeName(Token token, TokenSeq tokenSeq) {
        this.value = token.str();
        this.tokenSeq = tokenSeq;
        this.token = token;
    }

    public InvalidTypeName(TokenSeq tokenSeq) {
        this.value = tokenSeq.currentStr();
        this.tokenSeq = tokenSeq;
        this.token = tokenSeq.current();
    }

    public String getValue() {
        return this.value;
    }

    public Token getToken() {
        return this.token;
    }

    public TokenSeq getTokenSeq() {
        return this.tokenSeq;
    }

    @Override
    public void outputTo(Console console) {
        console.print("Error in %s", this.getTokenSeq().getFile().getAbsolutePath());
        console.print(
            "Type name '%s' is invalid. A name must adhere to [A-Z][A-Za-z0-9]*",
            this.getValue()
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

        InvalidTypeName that = (InvalidTypeName)o;

        return Objects.equals(this.getValue(), that.getValue())
            && Objects.equals(this.getToken(), that.getToken())
            && Objects.equals(this.getTokenSeq(), that.getTokenSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value, this.tokenSeq, this.token);
    }
}
