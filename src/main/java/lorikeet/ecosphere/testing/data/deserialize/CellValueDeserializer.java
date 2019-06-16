package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Dict;
import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.CellValue;
import lorikeet.ecosphere.testing.data.IdentifierValue;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.ecosphere.testing.data.Value;

/*
* @TODO Cell should no longer be a "value", so this class is no longer needed
 */
public class CellValueDeserializer implements ValueDeserializer<CellValue>  {
    private final Deserializer deserializer = new Deserializer();


    @Override
    public Err<CellValue> deserialize(TextReader reader) {
        reader.jumpWhitespace();
        if (reader.getCurrentChar() != '<') {
            return Err.failure();
        }
        reader.skip();
        final Err<String> className = reader.nextIdentifier();
        String exceptionThrown = null;
        Value returnValue = null;
        Dict<String, Value> arguments = Dict.empty();

        if (!className.isPresent()) {
            return Err.failure();
        }

        while (true) {
            if (reader.isAtEnd()) {
                return Err.failure();
            }

            if (reader.getCurrentChar() == '>') {
                reader.skip();
                return Err.of(new CellValue(className.orPanic(), arguments, exceptionThrown, returnValue));
            }

            if (reader.getCurrentChar() == '-') {
                reader.step();
                final Err<String> identifierErr = identifier(reader);
                if (!identifierErr.isPresent()) {
                    return Err.failure();
                }
                if (reader.getCurrentChar() != '=') {
                    return Err.failure();
                }
                reader.skip();

                Err<Value> valueErr = this.deserializer.deserialize(reader);
                if (!valueErr.isPresent()) {
                    return Err.from(valueErr);
                }

                if (identifierErr.orPanic().matches("\\d+")) {
                    arguments = arguments.push(identifierErr.orPanic(), valueErr.orPanic());
                    continue;
                }

                final String identifier = identifierErr.orPanic();
                final Value value = valueErr.orPanic();

                if (identifier.equalsIgnoreCase("return")) {
                    returnValue = value;
                }
                if (identifier.equalsIgnoreCase("exception")) {
                    if (!(value instanceof IdentifierValue)) {
                        return Err.failure();
                    }
                    exceptionThrown = ((IdentifierValue)value).getIdentifier();
                }
                continue;
            }

            Opt<String> identifier = reader.nextWord();
            if (!identifier.isPresent()) {
                return Err.failure();
            }
            if (reader.getCurrentChar() != '=') {
                return Err.failure();
            }
            reader.skip();
            Err<Value> valueErr = this.deserializer.deserialize(reader);
            if (!valueErr.isPresent()) {
                return Err.from(valueErr);
            }
            arguments = arguments.push(identifier.orPanic(), valueErr.orPanic());
        }
    }

    private Err<String> identifier(TextReader reader) {
        final Opt<Number> number = reader.nextNumber();
        if (number.isPresent()) {
            return Err.of(String.valueOf(number.orPanic().intValue()));
        }
        return reader.nextIdentifier();
    }

}
