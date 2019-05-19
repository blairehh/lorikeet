package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.Dict;
import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.ecosphere.transcript.ObjectValue;
import lorikeet.ecosphere.transcript.TextReader;
import lorikeet.ecosphere.transcript.Value;

public class ObjectDeserializer implements ValueDeserializer<ObjectValue> {

    private final Deserializer deserializer = new Deserializer();

    @Override
    public Opt<ObjectValue> deserialize(TextReader reader) {
        final Err<String> className = reader.nextIdentifier();
        if (!className.isPresent()) {
            return Opt.empty();
        }
        if (reader.getCurrentChar() != '(') {
            return Opt.empty();
        }
        reader.skip();
        Dict<String, Value> data = Dict.empty();
        while (true) {
            if (reader.isAtEnd()) {
                return Opt.empty();
            }
            if (reader.getCurrentChar() == ',') {
                if (!data.isEmpty()) {
                    reader.skip();
                } else {
                    return Opt.empty();
                }
            }
            if (reader.getCurrentChar() == ')') {
                reader.skip();
                return Opt.of(new ObjectValue(className.orPanic(), data));
            }
            final Opt<String> field = reader.nextAlphaNumericWord(false);
            if (!field.isPresent()) {
                return Opt.empty();
            }
            if (reader.getCurrentChar() != ':') {
                return Opt.empty();
            }
            reader.skip();
            final Opt<Value> value = deserializer.deserialize(reader);
            if (!value.isPresent()) {
                return Opt.empty();
            }
            data = data.push(field.orPanic(), value.orPanic());
        }
    }
}
