package lorikeet.transpile;

import lorikeet.lang.Invoke;
import lorikeet.lang.Expression;
import lorikeet.lang.Package;
import lorikeet.lang.Type;
import lorikeet.lang.SpecType;
import lorikeet.lang.Value;
import lorikeet.lang.Value.Variable;

import java.util.List;
import java.util.Arrays;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InvokeTranspilerTest {

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

    Value literal(String value) {
        return new Value.StrLiteral(value);
    }

    Value literal(double number) {
        return new Value.DecLiteral(String.valueOf(number));
    }

    Value literal(boolean value) {
        return new Value.BolLiteral(String.valueOf(value));
    }

    Value variable(boolean param, String name, SpecType type) {
        return new Variable(param, name, type);
    }

    <A> List<A> listOf(A... values) {
        return Arrays.asList(values);
    }

    private final InvokeTranspiler transpiler = new InvokeTranspiler();

    @Test
    public void testVarNoArgs() {
        final Invoke invoke = new Invoke(
            variable(false, "msg", known(type("Str", "lorikeet", "core"))),
            "toJavaString",
            listOf()
        );
        expect(invoke, "v_msg.m_toJavaString()");
    }

    @Test
    public void testvarWith1Arg() {
        final Invoke invoke = new Invoke(
            variable(false, "msg", known(type("Str", "lorikeet", "core"))),
            "at",
            listOf(
                variable(false, "index", known(type("Int", "lorikeet", "core")))
            )
        );
        expect(invoke, "v_msg.m_at(v_index)");
    }

    @Test
    public void testVarWithVariableArgs() {
        final Invoke invoke = new Invoke(
            variable(false, "msg", known(type("Str", "lorikeet", "core"))),
            "sub",
            listOf(
                variable(false, "start", known(type("Int", "lorikeet", "core"))),
                variable(false, "stop", known(type("Int", "lorikeet", "core")))
            )
        );
        expect(invoke, "v_msg.m_sub(v_start, v_stop)");
    }

    @Test
    public void testLiterals() {
        final Invoke invoke = new Invoke(
            literal("the fox jumped"),
            "foo",
            listOf(literal(true), literal(56), literal(-43.09))
        );
        final String code = "new lorikeet.core.Lk_struct_Str(\"the fox jumped\")"
            + ".m_foo("
            + "new lorikeet.core.Lk_struct_Bol(true), "
            + "new lorikeet.core.Lk_struct_Int(56L), "
            + "new lorikeet.core.Lk_struct_Dec(-43.09)"
            + ")";
        expect(invoke, code);
    }


    void expect(Invoke invoke, String code) {
        assertThat(transpiler.transpile(invoke)).isEqualTo(code);
    }

}
