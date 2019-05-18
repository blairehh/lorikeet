package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.BoolValue;

public class BoolValueDeserializer implements DatumDeserializer<BoolValue> {
    @Override
    public Opt<BoolValue> deserialize(String value) {
        if (value.trim().equalsIgnoreCase("true")) {
            return Opt.of(new BoolValue(true));
        }
        if (value.trim().equalsIgnoreCase("false")) {
            return Opt.of(new BoolValue(false));
        }
        return Opt.empty();
    }
}
