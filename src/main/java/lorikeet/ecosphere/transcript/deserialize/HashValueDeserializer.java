package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.HashValue;
import lorikeet.ecosphere.transcript.TextReader;
import lorikeet.java.JavaUtils;

public class HashValueDeserializer implements ValueDeserializer<HashValue> {
    @Override
    public Opt<HashValue> deserialize(TextReader text) {
        final String token = text.nextToken();

        if (token.isBlank()) {
            return Opt.empty();
        }

        final String[] parts = token.split("#");
        if (parts.length != 2) {
            return Opt.empty();
        }

        final String className = token.split("#")[0];
        if (!JavaUtils.isValidIdentifier(className)) {
            return Opt.empty();
        }

        final String hashValue = token.split("#")[1];
        if (hashValue.isBlank()) {
            return Opt.empty();
        }

        return Opt.of(new HashValue(className, hashValue));
    }
}
