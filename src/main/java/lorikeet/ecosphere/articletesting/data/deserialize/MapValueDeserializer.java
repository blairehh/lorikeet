package lorikeet.ecosphere.articletesting.data.deserialize;

import lorikeet.Dict;
import lorikeet.Err;
import lorikeet.ecosphere.articletesting.data.MapValue;
import lorikeet.ecosphere.articletesting.reader.TextReader;
import lorikeet.ecosphere.articletesting.data.Value;
import lorikeet.error.CommaMustComeAfterKeyValuePairInMapValue;
import lorikeet.error.CouldNotDeserializeKeyValueInMapValue;
import lorikeet.error.CouldNotDeserializeValueInMapValue;
import lorikeet.error.InconclusiveError;
import lorikeet.error.LorikeetException;
import lorikeet.error.MapValueKeyMustBeFollowedByColon;
import lorikeet.error.MapValueMustStartWithOpenBraces;
import lorikeet.error.UnexpectedEndOfContentWhileParsing;

public class MapValueDeserializer implements ValueDeserializer<MapValue> {

    private final Deserializer deserializer;
    private final boolean directDeserialization;

    public MapValueDeserializer() {
        this.deserializer = new Deserializer();
        this.directDeserialization = true;
    }

    public MapValueDeserializer(boolean directDeserialization) {
        this.deserializer = new Deserializer();
        this.directDeserialization = directDeserialization;
    }

    @Override
    public Err<MapValue> deserialize(TextReader reader) {
        if (reader.getCurrentChar() != '{') {
            return Err.failure(this.potentiallyInconclusive(new MapValueMustStartWithOpenBraces()));
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

    private LorikeetException potentiallyInconclusive(LorikeetException err) {
        if (this.directDeserialization) {
            return err;
        }
        return new InconclusiveError(err);
    }
}
