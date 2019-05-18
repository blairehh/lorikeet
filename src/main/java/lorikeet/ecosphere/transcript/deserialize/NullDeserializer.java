package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.NullValue;

public class NullDeserializer implements DatumDeserializer<NullValue> {
    public Opt<NullValue> deserialize(String value) {
        if (value.trim().equalsIgnoreCase("null")) {
            return Opt.of(new NullValue());
        }
        return Opt.empty();
    }
}
