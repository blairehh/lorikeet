package lorikeet.error;

import lorikeet.token.Token;
import lorikeet.token.TokenSeq;
import lorikeet.util.Console;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BadSyntax implements CompileError {
    private final String found;
    private final List<String> expected;
    private final TokenSeq tokenSeq;
    private final Token token;

    public BadSyntax(String found, Token token, TokenSeq tokenSeq, String... expected) {
        this.found = found;
        this.expected = Arrays.asList(expected);
        this.tokenSeq = tokenSeq;
        this.token = token;
    }

    public BadSyntax(TokenSeq tokenSeq, String... expected) {
        this.found = tokenSeq.currentStr();
        this.expected = Arrays.asList(expected);
        this.tokenSeq = tokenSeq;
        this.token = tokenSeq.current();
    }

    public BadSyntax(String found, Token token, TokenSeq tokenSeq) {
        this.found = found;
        this.expected = Collections.emptyList();
        this.tokenSeq = tokenSeq;
        this.token = token;
    }


    public String getFound() {
        return this.found;
    }

    public List<String> getExpected() {
        return this.expected;
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
            "Encounted erroneous syntax '%s', maybe you meant %s",
            this.getFound(),
            this.getExpected()
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

        BadSyntax that = (BadSyntax)o;

        return Objects.equals(this.getFound(), that.getFound())
            && Objects.equals(this.getExpected(), that.getExpected())
            && Objects.equals(this.getToken(), that.getToken())
            && Objects.equals(this.getTokenSeq(), that.getTokenSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.found, this.tokenSeq, this.token);
    }
}
