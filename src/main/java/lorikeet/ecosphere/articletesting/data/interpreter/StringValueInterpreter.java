package lorikeet.ecosphere.articletesting.data.interpreter;

import lorikeet.Opt;
import lorikeet.ecosphere.articletesting.data.StringValue;
import lorikeet.ecosphere.articletesting.data.Value;

public class StringValueInterpreter implements ValueInterpreter {


    @Override
    public Opt<Value> interpret(Object value) {
        if (value == null) {
            return Opt.empty();
        }

        if (!(value instanceof CharSequence) && !(value instanceof Character)) {
            return Opt.empty();
        }

        return Opt.of(new StringValue(value.toString()));
    }

}
