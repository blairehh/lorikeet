package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.HashValue;
import lorikeet.java.JavaUtils;
import org.apache.commons.lang3.StringUtils;

public class HashValueDeserializer implements DatumDeserializer<HashValue> {
    @Override
    public Opt<HashValue> deserialize(String value) {
        if (value.isBlank()) {
            return Opt.empty();
        }

        final String[] parts = value.split("#");
        if (parts.length != 2) {
            return Opt.empty();
        }

        final String className = value.split("#")[0];
        if (!JavaUtils.isValidIdentifier(className)) {
            return Opt.empty();
        }

        final String hashValue = value.split("#")[1];
        if (hashValue.isBlank()) {
            return Opt.empty();
        }

        return Opt.of(new HashValue(className, hashValue));
    }
}
