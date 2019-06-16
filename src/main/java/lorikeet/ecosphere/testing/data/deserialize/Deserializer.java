package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Err;
import lorikeet.Seq;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.ecosphere.testing.data.Value;
import lorikeet.error.CouldNotDeserializeValue;


public class Deserializer implements ValueDeserializer<Value> {

    private static final Seq<ValueDeserializer<? extends Value>> DESERIALIZERS = Seq.of(
        new BoolValueDeserializer(),
        new HashValueDeserializer(),
        new NullValueDeserializer(),
        new NumberValueDeserializer(),
        new StringValueDeserializer(),
        new ListValueDeserializer(),
        new ObjectValueDeserializer(),
        new MapValueDeserializer(),
        new IdentifierValueDeserializer(),
        new AnyValueDeserializer()
    );

    @Override
    public Err<Value> deserialize(TextReader reader) {
        for (ValueDeserializer<? extends Value> deserializer : DESERIALIZERS) {
            final TextReader textReader = reader.fork();
            textReader.jumpWhitespace();
            final Err<? extends Value> result = deserializer.deserialize(textReader);
            if (result.isPresent()) {
                reader.resetTo(textReader);
                return Err.of(result.orPanic());
            }
        }
        return Err.failure(new CouldNotDeserializeValue());
    }
}
