package lorikeet.ecosphere.transcript.interpreter;

import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.transcript.ListValue;
import lorikeet.ecosphere.transcript.Value;

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
