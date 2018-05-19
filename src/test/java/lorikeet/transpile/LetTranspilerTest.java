package lorikeet.transpile;

import lorikeet.lang.Expression;
import lorikeet.lang.Let;
import lorikeet.lang.Value;
import lorikeet.lang.Value.Variable;
import lorikeet.lang.Type;
import lorikeet.lang.SpecType;
import lorikeet.lang.Package;

import java.util.Arrays;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LetTranspilerTest {

    private final LetTranspiler transpiler = new LetTranspiler();


    SpecType known(Type type) {
        return new SpecType.Known(type);
    }

    Type type(String name, String... packages) {
        return new Type(new Package(packages), name);
    }

    Expression expression(Value value) {
        return new Expression(Arrays.asList(value), value.getExpressionType().get());
    }

    Value literal(int number) {
        return new Value.IntLiteral(String.valueOf(number));
    }

    @Test
    public void testBasicVariable() {
        final Let let = new Let("foo", expression(literal(1)));
        expect(let, "final lorikeet.core.Int v_foo =");
    }

    void expect(Let let, String value) {
        assertThat(transpiler.transpile(let)).isEqualTo(value);
    }

}
