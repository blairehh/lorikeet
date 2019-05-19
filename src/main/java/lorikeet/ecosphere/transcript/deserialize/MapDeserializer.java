package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.Dict;
import lorikeet.Opt;
import lorikeet.ecosphere.transcript.MapValue;
import lorikeet.ecosphere.transcript.TextReader;
import lorikeet.ecosphere.transcript.Value;

public class MapDeserializer implements ValueDeserializer<MapValue> {

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
