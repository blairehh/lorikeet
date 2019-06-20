package lorikeet.ecosphere.articletesting.data.interpreter;

import lorikeet.Opt;
import lorikeet.ecosphere.articletesting.data.Value;

public interface ValueInterpreter {
    Opt<Value> interpret(Object value);
}
