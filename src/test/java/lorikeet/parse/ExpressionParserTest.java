package lorikeet.parse;

import lorikeet.error.CompileError;
import lorikeet.error.SingularExpressionNotValue;
import lorikeet.error.UnknownToken;
import lorikeet.lang.Expression;
import lorikeet.lang.Package;
import lorikeet.lang.Let;
import lorikeet.lang.Type;
import lorikeet.lang.Value;
import lorikeet.lang.Value.StrLiteral;
import lorikeet.lang.Value.IntLiteral;
import lorikeet.lang.Value.DecLiteral;
import lorikeet.lang.Value.BolLiteral;
import lorikeet.lang.Value.Variable;
import lorikeet.token.TokenSeq;
import lorikeet.token.Tokenizer;

import java.util.Arrays;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpressionParserTest {

    private final VariableTable varTable = new VariableTable();
    private final TypeParser typeParser = new TypeParser(new TypeTable(), new Package("test"));
    private final Tokenizer tokenizer = new Tokenizer();

    Type type(String name, String... packages) {
        return new Type(new Package(packages), name);
    }

    @Test
    public void testSingleCantBeLet() {
        final ExpressionParser parser = new ExpressionParser(varTable, typeParser);
        final String code = "let foo Dec = 0.56";
        final TokenSeq tokens = tokenizer.tokenize(code);
        expect(parser.parse(tokens), SingularExpressionNotValue.class);
    }

    @Test
    public void testStr() {
        final ExpressionParser parser = new ExpressionParser(varTable, typeParser);
        final String code = "\"John Doe\"";
        final TokenSeq tokens = tokenizer.tokenize(code);
        final Expression e = expect(parser.parse(tokens));
        expect(e, 1);
        expect(e, new Type(new Package("lorikeet", "core"), "Str"));
        expect(e, 0, new StrLiteral("\"John Doe\""));
    }

    @Test
    public void testInt() {
        final ExpressionParser parser = new ExpressionParser(varTable, typeParser);
        final String code = "45";
        final TokenSeq tokens = tokenizer.tokenize(code);
        final Expression e = expect(parser.parse(tokens));
        expect(e, 1);
        expect(e, new Type(new Package("lorikeet", "core"), "Int"));
        expect(e, 0, new IntLiteral("45"));
    }

    @Test
    public void testDec() {
        final ExpressionParser parser = new ExpressionParser(varTable, typeParser);
        final String code = "45.45";
        final TokenSeq tokens = tokenizer.tokenize(code);
        final Expression e = expect(parser.parse(tokens));
        expect(e, 1);
        expect(e, new Type(new Package("lorikeet", "core"), "Dec"));
        expect(e, 0, new DecLiteral("45.45"));
    }

    @Test
    public void testBol() {
        final ExpressionParser parser = new ExpressionParser(varTable, typeParser);
        final String code = "true";
        final TokenSeq tokens = tokenizer.tokenize(code);
        final Expression e = expect(parser.parse(tokens));
        expect(e, 1);
        expect(e, new Type(new Package("lorikeet", "core"), "Bol"));
        expect(e, 0, new BolLiteral("true"));
    }

    @Test
    public void testVar() {
        final VariableTable vt = new VariableTable();
        vt.add(new Let("foo", type("Str", "lorikeet", "core"), new StrLiteral("\"a\"")));
        final ExpressionParser parser = new ExpressionParser(vt, typeParser);
        final String code = "foo";
        final TokenSeq tokens = tokenizer.tokenize(code);
        final Expression e = expect(parser.parse(tokens));
        expect(e, 1);
        expect(e, type("Str", "lorikeet", "core"));
        expect(e, 0, new Variable(false, "foo", type("Str", "lorikeet", "core")));
    }

    @Test
    public void testBodyWithOne() {
        final ExpressionParser parser = new ExpressionParser(varTable, typeParser, Arrays.asList("done"));
        final String code = "false\ndone";
        final TokenSeq tokens = tokenizer.tokenize(code);
        final Expression e = expect(parser.parse(tokens));
        expect(e, 1);
        expect(e, type("Bol", "lorikeet", "core"));
        expect(e, 0, new BolLiteral("false"));
    }

    @Test
    public void testBodyFailsWithRandomWord() {
        final ExpressionParser parser = new ExpressionParser(varTable, typeParser, Arrays.asList("done"));
        final String code = "false\nend\ndone";
        final TokenSeq tokens = tokenizer.tokenize(code);
        expect(parser.parse(tokens), UnknownToken.class);
    }

    @Test
    public void testBodyWithLetAndVar() {
        final ExpressionParser parser = new ExpressionParser(varTable, typeParser, Arrays.asList("done"));
        final String code = "let resp Bol = false\nresp\ndone";
        final TokenSeq tokens = tokenizer.tokenize(code);
        final Expression e = expect(parser.parse(tokens));
        expect(e, 2);
        expect(e, type("Bol", "lorikeet", "core"));
        expect(e, 0, new BolLiteral("false"));
    }

    Expression expect(Parse<Expression> parse) {
        assertThat(parse.succeded()).isTrue();
        return parse.getResult();
    }

    void expect(Parse<Expression> parse, Class<? extends CompileError> error) {
        assertThat(parse.succeded()).isFalse();
        assertThat(parse.getErrors(0).isOfType(error)).isTrue();
    }

    void expect(Expression e, int size) {
        assertThat(e.getChildren().size()).isEqualTo(size);
    }

    void expect(Expression e, Type type) {
        assertThat(e.getType()).isEqualTo(type);
    }

    void expect(Expression e, int index, Value value) {
        assertThat(e.getChildren().get(index)).isEqualTo(value);
    }


}
