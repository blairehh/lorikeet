package lorikeet.error;

import lorikeet.token.Token;
import lorikeet.token.TokenSeq;
import lorikeet.util.Console;
import java.util.Objects;

public class DuplicatePackageDeclaration implements CompileError {
    private final TokenSeq tokenSeq;
    private final Token token;

    public DuplicatePackageDeclaration(Token token, TokenSeq tokenSeq) {
        this.tokenSeq = tokenSeq;
        this.token = token;
    }

    public DuplicatePackageDeclaration(TokenSeq tokenSeq) {
        this.tokenSeq = tokenSeq;
        this.token = tokenSeq.current();
    }

    public TokenSeq getTokenSeq() {
        return this.tokenSeq;
    }

    public Token getToken() {
        return this.token;
    }

    @Override
    public void outputTo(Console console) {
        console.print("Error in %s", this.getTokenSeq().getFile().getAbsolutePath());
        console.print("There can only be one package declaration in a file");
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        DuplicatePackageDeclaration that = (DuplicatePackageDeclaration)o;

        return Objects.equals(this.getToken(), that.getToken())
            && Objects.equals(this.getTokenSeq(), that.getTokenSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tokenSeq, this.token);
    }
}
