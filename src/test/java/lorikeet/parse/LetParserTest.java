package lorikeet.parse;

import lorikeet.error.CompileError;
import lorikeet.error.BadSyntax;
import lorikeet.error.InvalidVariableName;
import lorikeet.error.TypeMismatch;
import lorikeet.error.VariableNameConflict;
import lorikeet.lang.Let;
import lorikeet.lang.Package;
import lorikeet.lang.Type;
import lorikeet.lang.Value;
import lorikeet.lang.Value.StrLiteral;
import lorikeet.lang.Value.IntLiteral;
import lorikeet.lang.Value.DecLiteral;
import lorikeet.lang.Value.BolLiteral;
import lorikeet.token.TokenSeq;
import lorikeet.token.Tokenizer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LetParserTest {

    private final VariableTable varTable = new VariableTable();
    private final Tokenizer tokenizer = new Tokenizer();
    private final LetParser parser = new LetParser(
        varTable,
        new TypeTable(),
        new Package("test")
    );

    @Test
    public void testStr() {
        final String code = "let name Str = \"Bob\"";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Let let = expect(parser.parse(tokens));
        expect(let, "name");
        expect(let, new Type(new Package("lorikeet", "core"), "Str"));
        expect(let, new StrLiteral("\"Bob\""));
    }

    @Test
    public void testBolTrue() {
        final String code = "let isAwesome Bol = true";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Let let = expect(parser.parse(tokens));
        expect(let, "isAwesome");
        expect(let, new Type(new Package("lorikeet", "core"), "Bol"));
        expect(let, new BolLiteral("true"));
    }

    @Test
    public void testBolFalse() {
        final String code = "let isAwful Bol = false";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Let let = expect(parser.parse(tokens));
        expect(let, "isAwful");
        expect(let, new Type(new Package("lorikeet", "core"), "Bol"));
        expect(let, new BolLiteral("false"));
    }

    @Test
    public void testOnlyOneValue() {
        final String code = "let isAwful Bol = false true";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Let let = expect(parser.parse(tokens), "true");
        expect(let, "isAwful");
        expect(let, new Type(new Package("lorikeet", "core"), "Bol"));
        expect(let, new BolLiteral("false"));
    }

    @Test
    public void testFailsBadName() {
        final String code = "let Name Str = \"Bob\"";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();
        expect(parser.parse(tokens), InvalidVariableName.class);
    }

    @Test
    public void testInt() {
        final String code = "let max Int = 100";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Let let = expect(parser.parse(tokens));
        expect(let, "max");
        expect(let, new Type(new Package("lorikeet", "core"), "Int"));
        expect(let, new IntLiteral("100"));
    }

    @Test
    public void testDec() {
        final String code = "let max Dec = -100.200";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Let let = expect(parser.parse(tokens));
        expect(let, "max");
        expect(let, new Type(new Package("lorikeet", "core"), "Dec"));
        expect(let, new DecLiteral("-100.200"));
    }

    @Test
    public void testFailsIfTypeMismatch() {
        final String code = "let min Dec = \"1.0\"";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();
        expect(parser.parse(tokens), TypeMismatch.class);
    }

    @Test
    public void testIntCannotBeDec() {
        final String code = "let min Int = 0.56";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();
        expect(parser.parse(tokens), TypeMismatch.class);
    }

    @Test
    public void testCannotUsingConflictingName() {
        varTable.add(new Let("foo", new Type(new Package("testorg"), "Foo"), null));
        final String code = "let foo Dec = 0.56";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();
        expect(parser.parse(tokens), VariableNameConflict.class);
    }

    Let expect(Parse<Let> parse) {
        assertThat(parse.succeded()).isTrue();
        return parse.getResult();
    }

    Let expect(Parse<Let> parse, String token) {
        assertThat(parse.succeded()).isTrue();
        assertThat(parse.getTokenSeq().currentStr()).isEqualTo(token);
        return parse.getResult();
    }

    void expect(Parse<Let> parse, Class<? extends CompileError> error) {
        assertThat(parse.succeded()).isFalse();
        assertThat(parse.getErrors(0).isOfType(error)).isTrue();
    }

    void expect(Let let, String name) {
        assertThat(let.getName()).isEqualTo(name);
    }

    void expect(Let let, Type type) {
        assertThat(let.getType()).isEqualTo(type);
    }

    void expect(Let let, Value value) {
        assertThat(let.getExpression().getChildren().get(0)).isEqualTo(value);
    }
}
