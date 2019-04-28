package lorikeet.data;

public class DataSerializer {

    public static final String NULL_VALUE = "null";
    public static final String NOT_SERIALIZABLE_VALUE = "not-ser";
    public static final String FAILED_SERIALIZATION_VALUE = "failed-ser";

    private final DataSerializationCapabilityRegistry capabilityRegistry;

    public DataSerializer(DataSerializationCapabilityRegistry registry) {
        this.capabilityRegistry = registry;
    }

    public String serialize(Object value, Class<?> context) {
        if (value == null) {
            return NULL_VALUE;
        }
        return this.capabilityRegistry.find(value.getClass(), context)
            .map(stringifier -> stringifier.apply(value, context))
            .orElse(NOT_SERIALIZABLE_VALUE);
    }
}
