package lorikeet.lobe.articletesting.data.interpreter;

import lorikeet.Dict;
import lorikeet.Opt;
import lorikeet.lobe.articletesting.data.MapValue;
import lorikeet.lobe.articletesting.data.Value;

import java.util.Map;

public class MapValueInterpreter implements ValueInterpreter {

    private final Interpreter interpreter = new Interpreter();

    @Override
    public Opt<Value> interpret(Object object) {
        if (object == null || !(object instanceof Map)) {
            return Opt.empty();
        }

        Dict<Value, Value> data = Dict.empty();
        final Map<?, ?> map = (Map<?, ?>)object;

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            final Value key = this.interpreter.interpret(entry.getKey());
            final Value value = this.interpreter.interpret(entry.getValue());

            data = data.push(key, value);
        }

        return Opt.of(new MapValue(data));
    }
}
