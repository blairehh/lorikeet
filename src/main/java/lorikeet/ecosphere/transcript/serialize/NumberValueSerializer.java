package lorikeet.ecosphere.transcript.serialize;

import lorikeet.ecosphere.transcript.NumberValue;

public class NumberValueSerializer implements ValueSerializer<NumberValue> {

    @Override
    public String serialize(NumberValue number) {
        final Number value = number.getValue();
        if (value.doubleValue() % 1 == 0) {
            return String.valueOf(value.longValue());
        }
        return String.valueOf(value.doubleValue());
    }
}
