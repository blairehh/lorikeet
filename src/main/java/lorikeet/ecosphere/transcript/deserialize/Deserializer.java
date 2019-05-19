package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.transcript.TextReader;
import lorikeet.ecosphere.transcript.Value;


public class Deserializer implements DatumDeserializer<Value> {

    private static final Seq<DatumDeserializer<? extends Value>> DESERIALIZERS = Seq.of(
        new BoolValueDeserializer(),
        new HashValueDeserializer(),
        new NullDeserializer(),
        new NumberValueDeserializer(),
        new StringValueDeserializer(),
        new ListDeserializer(),
        new ObjectDeserializer(),
        new MapDeserializer()
    );

    @Override
    public Opt<Value> deserialize(TextReader text) {
        for (DatumDeserializer<? extends Value> deserializer : DESERIALIZERS) {
            final TextReader textReader = text.fork();
            textReader.jumpWhitespace();
            final Opt<? extends Value> result = deserializer.deserialize(textReader);
            if (result.isPresent()) {
                text.resetTo(textReader);
                return Opt.of(result.orPanic());
            }
        }
        return Opt.empty();
    }
}
