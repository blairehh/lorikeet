package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Dict;
import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.MapValue;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.ecosphere.testing.data.Value;
import lorikeet.error.CommaMustComeAfterKeyValuePairInMapValue;
import lorikeet.error.CouldNotDeserializeKeyValueInMapValue;
import lorikeet.error.CouldNotDeserializeValueInMapValue;
import lorikeet.error.MapValueKeyMustBeFollowedByColon;
import lorikeet.error.MapValueMustStartWithOpenBraces;
import lorikeet.error.UnexpectedEndOfContentWhileParsing;

public class MapValueDeserializer implements ValueDeserializer<MapValue> {

    private final Deserializer deserializer = new Deserializer();

    @Override
    public Err<MapValue> deserialize(TextReader reader) {
        if (reader.getCurrentChar() != '{') {
            return Err.failure(new MapValueMustStartWithOpenBraces());
        }
        reader.skip();
        Dict<Value, Value> data = Dict.empty();
        while (true) {
            if (reader.isAtEnd()) {
                return Err.failure(new UnexpectedEndOfContentWhileParsing());
            }
            if (reader.getCurrentChar() == ',') {
                if (!data.isEmpty()) {
                    reader.skip();
                } else {
                    return Err.failure(new CommaMustComeAfterKeyValuePairInMapValue());
                }
            }
            if (reader.getCurrentChar() == '}') {
                reader.skip();
                return Err.of(new MapValue(data));
            }
            final Err<Value> key = deserializer.deserialize(reader);
            if (!key.isPresent()) {
                return Err.failure(new CouldNotDeserializeKeyValueInMapValue());
            }
            if (reader.getCurrentChar() != ':') {
                return Err.failure(new MapValueKeyMustBeFollowedByColon());
            }
            reader.skip();
            final Err<Value> value = deserializer.deserialize(reader);
            if (!value.isPresent()) {
                return Err.failure(new CouldNotDeserializeValueInMapValue());
            }
            data = data.push(key.orPanic(), value.orPanic());
        }
    }
}
