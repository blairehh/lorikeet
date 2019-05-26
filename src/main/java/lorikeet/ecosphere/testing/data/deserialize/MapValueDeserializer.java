package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Dict;
import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.MapValue;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.ecosphere.testing.data.Value;

public class MapValueDeserializer implements ValueDeserializer<MapValue> {

    private final Deserializer deserializer = new Deserializer();

    @Override
    public Opt<MapValue> deserialize(TextReader reader) {
        if (reader.getCurrentChar() != '{') {
            return Opt.empty();
        }
        reader.skip();
        Dict<Value, Value> data = Dict.empty();
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
            if (reader.getCurrentChar() == '}') {
                reader.skip();
                return Opt.of(new MapValue(data));
            }
            final Opt<Value> key = deserializer.deserialize(reader);
            if (!key.isPresent()) {
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
            data = data.push(key.orPanic(), value.orPanic());
        }
    }
}
