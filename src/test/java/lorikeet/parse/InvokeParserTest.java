package lorikeet.parse;

import lorikeet.lang.Package;
import lorikeet.lang.Invoke;
import lorikeet.lang.Type;
import lorikeet.lang.Value;
import lorikeet.lang.Value.BolLiteral;
import lorikeet.lang.Value.IntLiteral;
import lorikeet.lang.Value.Variable;
import lorikeet.token.TokenSeq;
import lorikeet.token.Tokenizer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InvokeParserTest {

    private static final VariableTable variableTable;

    static {
        variableTable = new VariableTable();
        variableTable.add(new Variable(false, "foo", new Type(new Package("lorikeet", "core"), "Bol")));
        variableTable.add(new Variable(false, "bar", new Type(new Package("lorikeet", "core"), "Bol")));
    }

    private final Tokenizer tokenizer = new Tokenizer();
    private final InvokeParser parser = new InvokeParser(
        variableTable,
        new TypeParser(new TypeTable(), new Package("test"))
    );

    Type type(String name, String... packages) {
        return new Type(new Package(packages), name);
    }

    Variable variable(String name, String type, String... packages) {
        return new Variable(false, name, type(type, packages));
    }

    @Test
    public void testNoArgs() {
        final String code = "(foo toStr)";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        Invoke invoke = expect(parser.parse(tokens));
        expect(invoke, variable("foo", "Bol", "lorikeet", "core"));
        expect(invoke, "toStr");
        expect(invoke, 0);
    }

    @Test
    public void test1Arg() {
        final String code = "(foo doh 56)";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        Invoke invoke = expect(parser.parse(tokens));
        expect(invoke, variable("foo", "Bol", "lorikeet", "core"));
        expect(invoke, "doh");
        expect(invoke, 1);
        expect(invoke, 0, new IntLiteral("56"));
    }

    @Test
    public void test3Args() {
        final String code = "(foo doh 56 bar true)";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        Invoke invoke = expect(parser.parse(tokens));
        expect(invoke, variable("foo", "Bol", "lorikeet", "core"));
        expect(invoke, "doh");
        expect(invoke, 3);
        expect(invoke, 0, new IntLiteral("56"));
        expect(invoke, 1, variable("bar", "Bol", "lorikeet", "core"));
        expect(invoke, 2, new BolLiteral("true"));
    }

    Invoke expect(Parse<Invoke> parse) {
        assertThat(parse.succeded()).isTrue();
        return parse.getResult();
    }

    void expect(Invoke invoke, Value value) {
        assertThat(invoke.getSubject()).isEqualTo(value);
    }

    void expect(Invoke invoke, String funcName) {
        assertThat(invoke.getFunctionName()).isEqualTo(funcName);
    }

    void expect(Invoke invoke, int argCount) {
        assertThat(invoke.getArguments().size()).isEqualTo(argCount);
    }

    void expect(Invoke invoke, int index, Value value) {
        assertThat(invoke.getArguments().get(index)).isEqualTo(value);
    }

}
