package lorikeet.data;

import java.util.Collection;
import java.util.Map;

public class DataSerializationSupport {

    public static String serializeCollection(Collection<?> collection, Class<?> context, DataSerializer serializer) {
        if (collection == null) {
            return "null";
        }

        final StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Object item : collection) {

            final String value = serializer.serialize(item, context);

            builder.append(value);
            if (i != collection.size() - 1) {
                builder.append(", ");
            }
            i++;
        }
        builder.append("]");
        return builder.toString();
    }

    public static String serializeMap(Map<?, ?> map, Class<?> context, DataSerializer serializer) {
        if (map == null) {
            return "null";
        }
        final StringBuilder builder = new StringBuilder();
        builder.append("{");
        int i = 0;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            builder.append(serializer.serialize(entry.getKey(), context));
            builder.append(": ");
            builder.append(serializer.serialize(entry.getValue(), context));

            if (i != map.size() - 1) {
                builder.append(", ");
            }
            i++;
        }
        builder.append("}");
        return builder.toString();
    }

}
