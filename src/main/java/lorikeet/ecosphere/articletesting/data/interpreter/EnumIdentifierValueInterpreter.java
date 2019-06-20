package lorikeet.ecosphere.articletesting.data.interpreter;

import lorikeet.Opt;
import lorikeet.ecosphere.articletesting.data.IdentifierValue;
import lorikeet.ecosphere.articletesting.data.Value;

public class EnumIdentifierValueInterpreter implements ValueInterpreter {
    @Override
    public Opt<Value> interpret(Object value) {
        if (!(value instanceof Enum)) {
            return Opt.empty();
        }
        Enum<?> enumValue = (Enum<?>)value;
        final String identifier = enumValue.getClass().getName() + "." + enumValue.name();
        return Opt.of(new IdentifierValue(identifier));
    }
}
