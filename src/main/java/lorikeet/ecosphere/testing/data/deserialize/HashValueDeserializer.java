package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Err;
import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.HashValue;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.error.HashValueClassNameMustBeFollowedByNumberSign;
import lorikeet.error.HashValueHashMustBeAlphaNumericValue;
import lorikeet.error.HashValueMustStartWithClassName;
import lorikeet.error.UnexpectedEndOfContentWhileParsing;

public class HashValueDeserializer implements ValueDeserializer<HashValue> {
    @Override
    public Err<HashValue> deserialize(TextReader reader) {
        final Err<String> className = reader.nextIdentifier();
        if (!className.isPresent()) {
            return Err.failure(new HashValueMustStartWithClassName());
        }
        if (reader.isAtEnd()) {
            return Err.failure(new UnexpectedEndOfContentWhileParsing());
        }
        if (reader.getCurrentChar() != '#') {
            return Err.failure(new HashValueClassNameMustBeFollowedByNumberSign());
        }
        reader.skip();
        final Opt<String> hash = reader.nextAlphaNumericWord(true);
        if (!hash.isPresent()) {
            return Err.failure(new HashValueHashMustBeAlphaNumericValue());
        }
        return Err.of(new HashValue(className.orPanic(), hash.orPanic()));
    }
}
