package lorikeet.ecosphere.transcript.interpreter;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.Value;

public interface ValueInterpreter {
    Opt<Value> interpret(Object value);
}
