package lorikeet.lobe.articletesting.data.serialize;

import lorikeet.Opt;
import lorikeet.lobe.articletesting.data.ObjectValue;
import lorikeet.lobe.articletesting.data.Value;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ObjectValueSerializer implements ValueSerializer {

    private final Serializer serializer = new Serializer();

    @Override
    public Opt<String> serialize(Value value) {
        if (!(value instanceof ObjectValue)) {
            return Opt.empty();
        }

        final ObjectValue objectValue = (ObjectValue)value;
        final int size = objectValue.getData().size();
        final List<Map.Entry<String, Value>> entrySet = objectValue.getData()
            .entrySet()
            .stream()
            .sorted(Comparator.comparing(Map.Entry::getKey))
            .collect(Collectors.toList());

        StringBuilder serialized = new StringBuilder();
        serialized.append(objectValue.getClassName());
        serialized.append("(");

        int i = 0;
        for (Map.Entry<String, Value> entry : entrySet) {
            serialized.append(entry.getKey());
            serialized.append("=");
            serialized.append(serializer.serialize(entry.getValue()));

            if (i != size - 1) {
                serialized.append(", ");
            }
            i++;
        }
        serialized.append(")");

        return Opt.of(serialized.toString());

    }
}
