package lorikeet.lobe.articletesting.data.interpreter;

import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.lobe.articletesting.data.NotSupportedValue;
import lorikeet.lobe.articletesting.data.Value;

public class Interpreter {

    private static final Seq<ValueInterpreter> INTERPRETERS = Seq.of(
        new NullValueInterpreter(),
        new StringValueInterpreter(),
        new BoolValueInterpreter(),
        new NumberValueInterpreter(),
        new ListValueInterpreter(),
        new ObjectValueInterpreter(),
        new MapValueInterpreter(),
        new EnumIdentifierValueInterpreter()
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


    public Value interpretAsHash(Object object) {
        return new HashValueInterpreter().interpret(object);
    }

}
