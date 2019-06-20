package lorikeet.ecosphere.articletesting.data.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.articletesting.data.MapValue;
import lorikeet.ecosphere.articletesting.data.Value;

import java.util.Map;

public class MapValueSerializer implements ValueSerializer {

    private final Serializer serializer = new Serializer();

    @Override
    public Opt<String> serialize(Value value) {
        if (!(value instanceof MapValue)) {
            return Opt.empty();
        }

        final MapValue mapValue = (MapValue)value;
        final Map<Value, Value> map = mapValue.getData();

        final StringBuilder builder = new StringBuilder();
        builder.append("{");
        int i = 0;
        for (Map.Entry<Value, Value> entry : map.entrySet()) {
            builder.append(serializer.serialize(entry.getKey()));
            builder.append(": ");
            builder.append(serializer.serialize(entry.getValue()));

            if (i != map.size() - 1) {
                builder.append(", ");
            }
            i++;
        }
        builder.append("}");

        return Opt.of(builder.toString());
    }
}
