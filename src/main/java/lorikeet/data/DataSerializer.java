package lorikeet.data;

public class DataSerializer {
    private final DataSerializationCapabilityRegistry capabilityRegistry;

    public DataSerializer(DataSerializationCapabilityRegistry registry) {
        this.capabilityRegistry = registry;
    }

    public String serialize(Object value, Class<?> context) {
        if (value == null) {
            return "null";
        }
        return this.capabilityRegistry.find(value.getClass(), context)
            .map(stringifier -> stringifier.apply(value, context))
            .orElse("not-ser");
    }
}
