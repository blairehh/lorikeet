package lorikeet.ecosphere.testing;

import java.util.Collection;

public class ParameterSerializerSupport {

    public static String serializeCollection(Collection<?> collection, Class<?> context, ParameterSerializer serializer) {
        if (collection == null) {
            return "null";
        }

        final StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Object item : collection) {
            if (item == null) {
                builder.append("null ");
                continue;
            }

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
}
