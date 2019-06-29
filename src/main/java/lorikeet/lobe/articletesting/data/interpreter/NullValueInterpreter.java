package lorikeet.lobe.articletesting.data.interpreter;

import lorikeet.Opt;
import lorikeet.lobe.articletesting.data.NullValue;
import lorikeet.lobe.articletesting.data.Value;

public class NullValueInterpreter implements ValueInterpreter {

    @Override
    public Opt<Value> interpret(Object value) {
        if (value != null) {
            return Opt.empty();
        }
        return Opt.of(new NullValue());
    }
}
