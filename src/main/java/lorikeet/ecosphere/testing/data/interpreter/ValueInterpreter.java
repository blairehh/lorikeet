package lorikeet.ecosphere.testing.data.interpreter;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.Value;

public interface ValueInterpreter {
    Opt<Value> interpret(Object value);
}
