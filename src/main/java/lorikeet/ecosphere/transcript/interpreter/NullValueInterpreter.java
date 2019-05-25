package lorikeet.ecosphere.transcript.interpreter;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.NullValue;
import lorikeet.ecosphere.transcript.Value;

public class NullValueInterpreter implements ValueInterpreter {

    @Override
    public Opt<Value> interpret(Object value) {
        if (value != null) {
            return Opt.empty();
        }
        return Opt.of(new NullValue());
    }
}
