package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.transcript.TextReader;
import lorikeet.ecosphere.transcript.Value;


public class Deserializer implements ValueDeserializer<Value> {

    private static final Seq<ValueDeserializer<? extends Value>> DESERIALIZERS = Seq.of(
        new BoolValueDeserializer(),
        new HashValueDeserializer(),
        new NullDeserializer(),
        new NumberValueDeserializer(),
        new StringValueDeserializer(),
        new ListDeserializer(),
        new ObjectDeserializer(),
        new MapDeserializer(),
        new IdentifierValueDeserializer()
    );

    @Override
    public Opt<Value> deserialize(TextReader reader) {
        for (ValueDeserializer<? extends Value> deserializer : DESERIALIZERS) {
            final TextReader textReader = reader.fork();
            textReader.jumpWhitespace();
            final Opt<? extends Value> result = deserializer.deserialize(textReader);
            if (result.isPresent()) {
                reader.resetTo(textReader);
                return Opt.of(result.orPanic());
            }
        }
        return Opt.empty();
    }
}
