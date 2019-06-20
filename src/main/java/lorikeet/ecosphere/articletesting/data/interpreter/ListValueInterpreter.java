package lorikeet.ecosphere.articletesting.data.interpreter;

import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.articletesting.data.ListValue;
import lorikeet.ecosphere.articletesting.data.Value;

import java.util.Collection;

public class ListValueInterpreter implements ValueInterpreter {

    private final Interpreter interpreter = new Interpreter();

    @Override
    public Opt<Value> interpret(Object value) {
        if (value == null || !(value instanceof Collection)) {
            return Opt.empty();
        }

        final Collection<?> collection = (Collection<?>)value;
        Seq<Value> values = Seq.empty();

        for (Object item : collection) {
            values = values.push(this.interpreter.interpret(item));
        }
        return Opt.of(new ListValue(values));
    }
}
