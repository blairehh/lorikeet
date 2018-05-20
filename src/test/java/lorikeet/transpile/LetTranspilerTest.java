package lorikeet.transpile;

import lorikeet.lang.Let;
import static lorikeet.Lang.*;

import java.util.Arrays;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LetTranspilerTest {

    private final LetTranspiler transpiler = new LetTranspiler();

    @Test
    public void testBasicVariable() {
        final Let let = new Let("foo", expression(literal(1)));
        expect(let, "final lorikeet.core.Int v_foo =");
    }

    void expect(Let let, String value) {
        assertThat(transpiler.transpile(let)).isEqualTo(value);
    }

}
