package lorikeet.lobe.articletesting.data.interpreter;

import lorikeet.Opt;
import lorikeet.lobe.articletesting.data.Value;

public interface ValueInterpreter {
    Opt<Value> interpret(Object value);
}
