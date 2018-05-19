package lorikeet.parse;

import lorikeet.lang.Attribute;
import lorikeet.lang.Function;
import lorikeet.lang.Let;
import lorikeet.lang.Package;
import lorikeet.lang.Type;
import lorikeet.lang.SpecType;
import lorikeet.lang.Value.Variable;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Arrays;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VariableTableTest {

    <T> Set<T> setOf(T... items) {
        return new LinkedHashSet(Arrays.asList(items));
    }

    SpecType known(Type type) {
        return new SpecType.Known(type);
    }

    Type type(String name) {
        return new Type(new Package("test"), name);
    }

    Attribute attr(String name, Type type) {
        return new Attribute(name, type);
    }

    Let let(String name, Type type) {
        return new Let(name, known(type), null);
    }

    @Test
    public void testFromFunc() {
        Function func = new Function(
            type("Player"),
            "hit",
            setOf(attr("amount", type("Hit")), attr("power", type("Power"))),
            type("Player")
        );
        VariableTable vt = new VariableTable(func);
        vt.add(let("calc", type("Int")));
        vt.add(let("dod", type("YesNo")));

        expect(vt.get("amount").get(), "amount", known(type("Hit")), true);
        expect(vt.get("power").get(), "power", known(type("Power")), true);
        expect(vt.get("calc").get(), "calc", known(type("Int")), false);
        expect(vt.get("dod").get(), "dod", known(type("YesNo")), false);
    }


    @Test
    public void testFromVariableTable() {
        Function func = new Function(
            type("Player"),
            "hit",
            setOf(attr("amount", type("Hit")), attr("power", type("Power"))),
            type("Player")
        );
        VariableTable parent = new VariableTable(func);
        parent.add(let("calc", type("Int")));
        parent.add(let("dod", type("YesNo")));

        VariableTable vt = new VariableTable(parent);
        vt.add(let("foo", type("Bar")));

        expect(vt.get("amount").get(), "amount", known(type("Hit")), true);
        expect(vt.get("power").get(), "power", known(type("Power")), true);
        expect(vt.get("calc").get(), "calc", known(type("Int")), false);
        expect(vt.get("dod").get(), "dod", known(type("YesNo")), false);
        expect(vt.get("foo").get(), "foo", known(type("Bar")), false);
    }

    void expect(Variable var, String name, SpecType type, boolean param) {
        assertThat(var.getName()).isEqualTo(name);
        assertThat(var.getType()).isEqualTo(type);
        assertThat(var.isParameter()).isEqualTo(param);
    }
}
