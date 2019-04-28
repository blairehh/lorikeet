package lorikeet.data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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

    public static String serializeObject(Object object, Class<?> context, DataSerializer serializer) {
        StringBuilder serialized = new StringBuilder();
        serialized.append(object.getClass().getName());
        serialized.append("(");
        final int numberOfFields = object.getClass().getDeclaredFields().length;
        int i = 0;
        for (Field field : object.getClass().getDeclaredFields()) {
            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            try {
                field.setAccessible(true);
                if (!field.canAccess(object)) {
                    continue;
                }
                serialized.append(field.getName());
                serialized.append("=");
                serialized.append(serializer.serialize(field.get(object), context));
            } catch (IllegalAccessException | SecurityException e) {
                e.printStackTrace();
                serialized.append(DataSerializer.FAILED_SERIALIZATION_VALUE);
            }
            if (i != numberOfFields - 1) {
                serialized.append(", ");
            }
            i++;
        }
        serialized.append(")");
        return serialized.toString();
    }

}
