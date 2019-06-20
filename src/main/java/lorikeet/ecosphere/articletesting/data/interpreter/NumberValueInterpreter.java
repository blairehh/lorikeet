package lorikeet.ecosphere.articletesting.data.interpreter;

import lorikeet.Opt;
import lorikeet.ecosphere.articletesting.data.NumberValue;
import lorikeet.ecosphere.articletesting.data.Value;

public class NumberValueInterpreter implements ValueInterpreter {

    @Override
    public Opt<Value> interpret(Object object) {
        if (object == null || !(object instanceof Number)) {
            return Opt.empty();
        }
        return Opt.of(new NumberValue((Number)object));
    }
}
