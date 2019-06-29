package lorikeet.lobe.articletesting.data.deserialize;

import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.lobe.articletesting.data.HashValue;
import lorikeet.lobe.articletesting.reader.TextReader;
import lorikeet.error.HashValueClassNameMustBeFollowedByNumberSign;
import lorikeet.error.HashValueHashMustBeAlphaNumericValue;
import lorikeet.error.HashValueMustStartWithClassName;
import lorikeet.error.InconclusiveError;
import lorikeet.error.LorikeetException;
import lorikeet.error.UnexpectedEndOfContentWhileParsing;

public class HashValueDeserializer implements ValueDeserializer<HashValue> {

    private final boolean directDeserialization;

    public HashValueDeserializer() {
        this.directDeserialization = true;
    }

    public HashValueDeserializer(boolean directDeserialization) {
        this.directDeserialization = directDeserialization;
    }

    @Override
    public Err<HashValue> deserialize(TextReader reader) {
        final Err<String> className = reader.nextIdentifier();
        if (!className.isPresent()) {
            return this.err(new HashValueMustStartWithClassName());
        }
        if (reader.isAtEnd()) {
            return this.err(new UnexpectedEndOfContentWhileParsing());
        }
        if (reader.getCurrentChar() != '#') {
            return this.err(new HashValueClassNameMustBeFollowedByNumberSign());
        }
        reader.skip();
        final Opt<String> hash = reader.nextAlphaNumericWord(true);
        if (!hash.isPresent()) {
            return Err.failure(new HashValueHashMustBeAlphaNumericValue());
        }
        return Err.of(new HashValue(className.orPanic(), hash.orPanic()));
    }

    private Err<HashValue> err(LorikeetException error) {
        if (this.directDeserialization) {
            return Err.failure(error);
        }
        return Err.failure(new InconclusiveError(error));
    }
}
