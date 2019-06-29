package lorikeet.lobe.articletesting.data.interpreter;

import lorikeet.Opt;
import lorikeet.lobe.articletesting.data.NumberValue;
import lorikeet.lobe.articletesting.data.Value;

public class NumberValueInterpreter implements ValueInterpreter {

    @Override
    public Opt<Value> interpret(Object object) {
        if (object == null || !(object instanceof Number)) {
            return Opt.empty();
        }
        return Opt.of(new NumberValue((Number)object));
    }
}
