package lorikeet.parse;

import lorikeet.lang.Package;
import lorikeet.lang.Invoke;
import lorikeet.lang.Value;
import lorikeet.token.TokenSeq;
import lorikeet.token.Tokenizer;
import static lorikeet.Lang.*;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InvokeParserTest {

    private static final VariableTable variableTable;

    static {
        variableTable = new VariableTable();
        variableTable.add(variable("foo", "Bol", "lorikeet", "core"));
        variableTable.add(variable("bar", "Bol", "lorikeet", "core"));
    }

    private final Tokenizer tokenizer = new Tokenizer();
    private final InvokeParser parser = new InvokeParser(
        variableTable,
        new TypeParser(new TypeTable(), new Package("test"))
    );


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
        expect(invoke, 0, literal(56));
    }

    @Test
    public void test3Args() {
        final String code = "(foo doh 56 bar true)";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        Invoke invoke = expect(parser.parse(tokens));
        expect(invoke, variable("foo", "Bol", "lorikeet", "core"));
        expect(invoke, "doh");
        expect(invoke, 3);
        expect(invoke, 0, literal(56));
        expect(invoke, 1, variable("bar", "Bol", "lorikeet", "core"));
        expect(invoke, 2, literal(true));
    }

    @Test
    public void testLiteral() {
        final String code = "(6 + 6)";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        Invoke invoke = expect(parser.parse(tokens));
        expect(invoke, literal(6));
        expect(invoke, "+");
        expect(invoke, 1);
        expect(invoke, 0, literal(6));
    }

    @Test
    public void testSubjectIsInvoke() {
        final String code = "((1 + 5) + 6)";
        final TokenSeq tokens = tokenizer.tokenize(code).jump();

        Invoke invoke = expect(parser.parse(tokens));
        expect(invoke, invocation(literal(1), "+", literal(5)));
        expect(invoke, "+");
        expect(invoke, 1);
        expect(invoke, 0, literal(6));
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
