package lorikeet.parse;

import lorikeet.error.CompileError;
import lorikeet.error.BadSyntax;
import lorikeet.error.InvalidFunctionName;
import lorikeet.error.AttributeNameConflict;
import lorikeet.lang.Attribute;
import lorikeet.lang.Function;
import lorikeet.lang.Package;
import lorikeet.lang.Type;
import lorikeet.lang.Use;
import lorikeet.token.TokenSeq;
import lorikeet.token.Tokenizer;

import org.junit.Test;
import java.util.Set;
import java.util.LinkedHashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctionParserTest {

    private static Tokenizer tokenizer = new Tokenizer();
    private static TypeTable table = new TypeTable();
    private static FunctionParser parser = new FunctionParser(table, new Package("test"));

    static {
        table.add(new Use(new Package("test"), "Foo", "Foo"));
        table.add(new Use(new Package("test"), "Hey", "Hey"));
    }

    @Test
    public void testWithoutParameters() {
        final String code = "func Foo/bar Int =";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Function func = expect(parser.parse(tokens));
        Set<Attribute> attrs = new LinkedHashSet<Attribute>();
        expect(
            func,
            new Type(new Package("test"), "Foo"),
            "bar",
            attrs,
            new Type(new Package("lorikeet", "core"), "Int")
        );
    }

    @Test
    public void testWithoutParametersButWithBracket() {
        final String code = "func Foo/bar () Int =";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Function func = expect(parser.parse(tokens));
        Set<Attribute> attrs = new LinkedHashSet<Attribute>();
        expect(
            func,
            new Type(new Package("test"), "Foo"),
            "bar",
            attrs,
            new Type(new Package("lorikeet", "core"), "Int")
        );
    }


    @Test
    public void testWith1Param() {
        final String code = "func Foo/bar (baz Int) Int =";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Function func = expect(parser.parse(tokens));
        Set<Attribute> attrs = new LinkedHashSet<Attribute>();
        attrs.add(new Attribute("baz", new Type(new Package("lorikeet", "core"), "Int")));
        expect(
            func,
            new Type(new Package("test"), "Foo"),
            "bar",
            attrs,
            new Type(new Package("lorikeet", "core"), "Int")
        );
    }

    @Test
    public void testWith2Params() {
        final String code = "func Foo/bar (baz Hey, max Int) Int =";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Function func = expect(parser.parse(tokens));
        Set<Attribute> attrs = new LinkedHashSet<Attribute>();
        attrs.add(new Attribute("baz", new Type(new Package("test"), "Hey")));
        attrs.add(new Attribute("max", new Type(new Package("lorikeet", "core"), "Int")));
        expect(
            func,
            new Type(new Package("test"), "Foo"),
            "bar",
            attrs,
            new Type(new Package("lorikeet", "core"), "Int")
        );
    }

    @Test
    public void testWith3Params() {
        final String code = "func Foo/bar (baz Hey, max Int, min Int) Int =";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Function func = expect(parser.parse(tokens));
        Set<Attribute> attrs = new LinkedHashSet<Attribute>();
        attrs.add(new Attribute("baz", new Type(new Package("test"), "Hey")));
        attrs.add(new Attribute("max", new Type(new Package("lorikeet", "core"), "Int")));
        attrs.add(new Attribute("min", new Type(new Package("lorikeet", "core"), "Int")));
        expect(
            func,
            new Type(new Package("test"), "Foo"),
            "bar",
            attrs,
            new Type(new Package("lorikeet", "core"), "Int")
        );
    }

    @Test
    public void testNoReturnReturnsVoid() {
        final String code = "func Foo/bar =";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Function func = expect(parser.parse(tokens));
        Set<Attribute> attrs = new LinkedHashSet<Attribute>();
        expect(
            func,
            new Type(new Package("test"), "Foo"),
            "bar",
            attrs,
            new Type(new Package("java", "lang"), "Void")
        );
    }

    @Test
    public void testMainModuleFunction() {
        final String code = "func main/run =";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        final Function func = expect(parser.parse(tokens));
        Set<Attribute> attrs = new LinkedHashSet<Attribute>();
        expect(
            func,
            new Type(new Package("test"), "main"),
            "run",
            attrs,
            new Type(new Package("java", "lang"), "Void")
        );
    }

    @Test
    public void testFailsIfNoSlash() {
        final String code = "func main =";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        expect(parser.parse(tokens), BadSyntax.class, "=");
    }

    @Test
    public void testInvalidFunctionName() {
        final String code = "func main/Foo =";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        expectInvalidFunctionName(parser.parse(tokens), "Foo");
    }

    @Test
    public void testFailsRandomTokenInParameters() {
        final String code = "func main/bar (a Int foo) Int =";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        expect(parser.parse(tokens), BadSyntax.class, "foo");
    }

    @Test
    public void testMustHaveEqual() {
        final String code = "func main/foo () Int ()";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        expect(parser.parse(tokens), BadSyntax.class, "(");
    }

    @Test
    public void testDuplicateAttribute() {
        final String code = "func main/foo (bar Int, bar Int) Int =";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();
        expect(parser.parse(tokens), AttributeNameConflict.class);
    }

    Function expect(Parse<Function> parse) {
        assertThat(parse.succeded()).isTrue();
        assertThat(parse.getTokenSeq().eof()).isTrue();
        return parse.getResult();
    }

    void expect(Parse<Function> parse, Class<? extends CompileError> klass) {
        assertThat(parse.succeded()).isFalse();
        assertThat(parse.getErrors(0).isOfType(klass)).isTrue();
    }

    void expect(Function func, Type type, String name, Set<Attribute> attrs, Type returnType) {
        assertThat(func.getType()).isEqualTo(type);
        assertThat(func.getName()).isEqualTo(name);
        assertThat(func.getAttributes()).isEqualTo(attrs);
        assertThat(func.getReturnType()).isEqualTo(returnType);
    }

    void expect(Parse<Function> parse, Class<BadSyntax> error, String token) {
        assertThat(parse.succeded()).isFalse();
        assertThat(parse.getErrors(0).isOfType(error)).isTrue();
        assertThat(((BadSyntax)parse.getErrors(0)).getFound()).isEqualTo(token);
    }

    void expectInvalidFunctionName(Parse<Function> parse, String token) {
        assertThat(parse.succeded()).isFalse();
        assertThat(parse.getErrors(0).isOfType(InvalidFunctionName.class)).isTrue();
        assertThat(((InvalidFunctionName)parse.getErrors(0)).getValue()).isEqualTo(token);
    }





}
