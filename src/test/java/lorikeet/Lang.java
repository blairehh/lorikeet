package lorikeet;

import lorikeet.lang.Package;
import lorikeet.lang.Type;
import lorikeet.lang.SpecType;
import lorikeet.lang.Expression;
import lorikeet.lang.Value;
import lorikeet.lang.Value.Variable;

import java.util.List;
import java.util.Arrays;

public class Lang {

    public static SpecType known(Type type) {
        return new SpecType.Known(type);
    }

    public static Type type(String name, String... packages) {
        return new Type(new Package(packages), name);
    }

    public static Expression expression(Value value) {
        return new Expression(Arrays.asList(value), value.getExpressionType().get());
    }

    public static Value literal(int number) {
        return new Value.IntLiteral(String.valueOf(number));
    }

    public static Value literal(String value) {
        return new Value.StrLiteral(value);
    }

    public static Value literal(double number) {
        return new Value.DecLiteral(String.valueOf(number));
    }

    public static Value literal(boolean value) {
        return new Value.BolLiteral(String.valueOf(value));
    }

    public static Value variable(boolean param, String name, SpecType type) {
        return new Variable(param, name, type);
    }

    public static <A> List<A> listOf(A... values) {
        return Arrays.asList(values);
    }

}
