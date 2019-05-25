package lorikeet.ecosphere.transcript.interpreter;

import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.transcript.Value;

public class Interpreter {

    private static final Seq<ValueInterpreter> INTERPRETERS = Seq.of(
        new NullValueInterpreter(),
        new StringValueInterpreter(),
        new BoolValueInterpreter(),
        new NumberValueInterpreter()
    );

    public Opt<Value> interpret(Object object) {
        for (ValueInterpreter interpreter : INTERPRETERS) {
            final Opt<Value> interpreted = interpreter.interpret(object);
            if (interpreted.isPresent()) {
                return interpreted;
            }
        }
        return Opt.empty();
    }

}
