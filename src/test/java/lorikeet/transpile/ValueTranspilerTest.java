package lorikeet.transpile;

import static lorikeet.Lang.*;
import static lorikeet.Expect.*;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueTranspilerTest {

    ValueTranspiler transpiler = new ValueTranspiler();

    @Test
    public void testStrLiteral() {
        expect(
            transpiler.transpile(literal("Hello")),
            "new lorikeet.core.Lk_struct_Str(\"Hello\")"
        );
    }

    @Test
    public void testIntLiteral() {
        expect(
            transpiler.transpile(literal(6777)),
            "new lorikeet.core.Lk_struct_Int(6777L)"
        );
    }

    @Test
    public void testDecLiteral() {
        expect(
            transpiler.transpile(literal(-0.5)),
            "new lorikeet.core.Lk_struct_Dec(-0.5)"
        );
    }

    @Test
    public void testBolLiteral() {
        expect(
            transpiler.transpile(literal(true)),
            "new lorikeet.core.Lk_struct_Bol(true)"
        );
    }

    @Test
    public void testVar() {
        expect(
            transpiler.transpile(variable(false, "foo", known(type("Str", "lorikeet", "core")))),
            "v_foo"
        );
    }


}
