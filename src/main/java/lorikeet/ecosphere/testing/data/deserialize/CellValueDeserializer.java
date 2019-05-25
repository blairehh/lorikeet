package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Dict;
import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.CellValue;
import lorikeet.ecosphere.testing.data.IdentifierValue;
import lorikeet.ecosphere.testing.data.TextReader;
import lorikeet.ecosphere.testing.data.Value;

public class CellValueDeserializer implements ValueDeserializer<CellValue>  {
    private final Deserializer deserializer = new Deserializer();


    @Override
    public Opt<CellValue> deserialize(TextReader reader) {
        final Err<String> className = reader.nextIdentifier();
        Value exceptionThrown = null;
        Value returnValue = null;
        Dict<String, Value> arguments = Dict.empty();

        if (!className.isPresent()) {
            return Opt.empty();
        }

        while (true) {
            if (reader.isAtEnd()) {
                return Opt.empty();
            }

            if (reader.getCurrentChar() == '>') {
                reader.skip();
                return Opt.of(new CellValue(className.orPanic(), arguments, exceptionThrown, returnValue));
            }

            if (reader.getCurrentChar() == '-') {
                reader.step();
                final Err<String> identifierErr = reader.nextIdentifier();
                if (!identifierErr.isPresent()) {
                    return Opt.empty();
                }
                if (reader.getCurrentChar() != '=') {
                    return Opt.empty();
                }
                reader.skip();

                Opt<Value> valueErr = this.deserializer.deserialize(reader);
                if (!valueErr.isPresent()) {
                    return Opt.empty();
                }

                final String identifier = identifierErr.orPanic();
                final Value value = valueErr.orPanic();

                if (identifier.equalsIgnoreCase("return")) {
                    returnValue = value;
                }
                if (identifier.equalsIgnoreCase("exception")) {
                    if (!(value instanceof IdentifierValue)) {
                        return Opt.empty();
                    }
                    exceptionThrown = value;
                }
                continue;
            }

            Opt<String> identifier = reader.nextWord();
            if (!identifier.isPresent()) {
                return Opt.empty();
            }
            if (reader.getCurrentChar() != '=') {
                return Opt.empty();
            }
            reader.skip();
            Opt<Value> valueErr = this.deserializer.deserialize(reader);
            if (!valueErr.isPresent()) {
                return Opt.empty();
            }
            arguments = arguments.push(identifier.orPanic(), valueErr.orPanic());
        }
    }
}
