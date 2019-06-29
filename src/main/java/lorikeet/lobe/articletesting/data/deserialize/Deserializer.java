package lorikeet.lobe.articletesting.data.deserialize;

import lorikeet.Err;
import lorikeet.Seq;
import lorikeet.lobe.articletesting.reader.TextReader;
import lorikeet.lobe.articletesting.data.Value;
import lorikeet.error.CouldNotDeserializeValue;
import lorikeet.error.InconclusiveError;


public class Deserializer implements ValueDeserializer<Value> {

    private static final Seq<ValueDeserializer<? extends Value>> DESERIALIZERS = Seq.of(
        new BoolValueDeserializer(false),
        new HashValueDeserializer(false),
        new NullValueDeserializer(false),
        new NumberValueDeserializer(false),
        new StringValueDeserializer(false),
        new ListValueDeserializer(false),
        new ObjectValueDeserializer(false),
        new MapValueDeserializer(false),
        new IdentifierValueDeserializer(false),
        new AnyValueDeserializer(false),
        new NotValueDeserializer(false)
    );

    @Override
    public Err<Value> deserialize(TextReader reader) {
        Err<? extends Value> error = null;
        for (ValueDeserializer<? extends Value> deserializer : DESERIALIZERS) {
            final TextReader textReader = reader.fork();
            textReader.jumpWhitespace();
            final Err<? extends Value> result = deserializer.deserialize(textReader);
            if (result.isPresent()) {
                reader.resetTo(textReader);
                return Err.of(result.orPanic());
            }
            if (!result.failedWith(InconclusiveError.class)) {
                error = result;
            }
        }

        if (error == null) {
            return Err.failure(new CouldNotDeserializeValue());
        }
        return Err.from(error);
    }
}
