package lorikeet.ecosphere.testing;

public class ParameterSerializer {
    private final ParameterSerializationCapabilityRegistry capabilityRegistry;

    public ParameterSerializer(ParameterSerializationCapabilityRegistry registry) {
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
