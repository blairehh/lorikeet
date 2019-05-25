package lorikeet.ecosphere.transcript.interpreter;

import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.transcript.NotSupportedValue;
import lorikeet.ecosphere.transcript.Value;

public class Interpreter {

    private static final Seq<ValueInterpreter> INTERPRETERS = Seq.of(
        new NullValueInterpreter(),
        new StringValueInterpreter(),
        new BoolValueInterpreter(),
        new NumberValueInterpreter(),
        new ListValueInterpreter()
    );

    public Value interpret(Object object) {
        for (ValueInterpreter interpreter : INTERPRETERS) {
            final Opt<Value> interpreted = interpreter.interpret(object);
            if (interpreted.isPresent()) {
                return interpreted.orPanic();
            }
        }
        return new NotSupportedValue();
    }

}
