package lorikeet.transpile;

import lorikeet.lang.Let;
import lorikeet.lang.Package;
import lorikeet.lang.Value.StrLiteral;
import lorikeet.lang.Value.IntLiteral;
import lorikeet.lang.Value.BolLiteral;
import lorikeet.lang.Value.DecLiteral;
import lorikeet.lang.Type;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LetTranspilerTest {

    private LetTranspiler transpiler = new LetTranspiler();

    @Test
    public void testStr() {
        final Let let = new Let(
            "name",
            new Type(new Package("lorikeet", "core"), "Str"),
            new StrLiteral("\"Blair\"")
        );

        assertThat(transpiler.transpile(let))
            .isEqualTo("final lorikeet.core.Str v_name = new lorikeet.core.Str(\"Blair\");");
    }

    @Test
    public void testInt() {
        final Let let = new Let(
            "num",
            new Type(new Package("lorikeet", "core"), "Int"),
            new IntLiteral("567")
        );

        assertThat(transpiler.transpile(let))
            .isEqualTo("final lorikeet.core.Int v_num = new lorikeet.core.Int(567L);");
    }

    @Test
    public void testBol() {
        final Let let = new Let(
            "allowed",
            new Type(new Package("lorikeet", "core"), "Bol"),
            new BolLiteral("true")
        );

        assertThat(transpiler.transpile(let))
            .isEqualTo("final lorikeet.core.Bol v_allowed = new lorikeet.core.Bol(true);");
    }

    @Test
    public void testDec() {
        final Let let = new Let(
            "max",
            new Type(new Package("lorikeet", "core"), "Dec"),
            new BolLiteral("-56.8065")
        );

        assertThat(transpiler.transpile(let))
            .isEqualTo("final lorikeet.core.Dec v_max = new lorikeet.core.Dec(-56.8065);");
    }

}
