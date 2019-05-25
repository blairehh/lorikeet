package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.ecosphere.transcript.HashValue;
import lorikeet.ecosphere.transcript.TextReader;

public class HashValueDeserializer implements ValueDeserializer<HashValue> {
    @Override
    public Opt<HashValue> deserialize(TextReader reader) {
        final Err<String> className = reader.nextIdentifier();
        if (!className.isPresent()) {
            return Opt.empty();
        }
        if (reader.isAtEnd()) {
            return Opt.empty();
        }
        if (reader.getCurrentChar() != '#') {
            return Opt.empty();
        }
        reader.skip();
        final Opt<String> hash = reader.nextAlphaNumericWord(true);
        if (!hash.isPresent()) {
            return Opt.empty();
        }
        return Opt.of(new HashValue(className.orPanic(), hash.orPanic()));
    }
}
