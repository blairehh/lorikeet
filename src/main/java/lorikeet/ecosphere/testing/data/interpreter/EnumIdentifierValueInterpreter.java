package lorikeet.ecosphere.testing.data.interpreter;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.IdentifierValue;
import lorikeet.ecosphere.testing.data.Value;

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
