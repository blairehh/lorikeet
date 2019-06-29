package lorikeet.lobe.articletesting.data.serialize;

import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.lobe.articletesting.data.Value;

public class Serializer {

    public static final String NOT_SERIALIZABLE_VALUE = "notser";

    private static final Seq<ValueSerializer> SERIALIZERS = Seq.of(
        new BoolValueSerializer(),
        new HashValueSerializer(),
        new NullValueSerializer(),
        new NumberValueSerializer(),
        new StringValueSerializer(),
        new IdentifierValueSerializer(),
        new ListValueSerializer(),
        new MapValueSerializer(),
        new ObjectValueSerializer()
    );

    public String serialize(Value value) {
        for (ValueSerializer serializer : SERIALIZERS) {
            final Opt<String> serialization = serializer.serialize(value);
            if (serialization.isPresent()) {
                return serialization.orPanic();
            }
        }
        return NOT_SERIALIZABLE_VALUE;
    }
}
