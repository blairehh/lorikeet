package lorikeet.ecosphere.testing;

import lorikeet.Err;
import lorikeet.Fun;
import lorikeet.error.CouldNotFindParameterSerializerCapability;
import lorikeet.tools.CapabilityRegistry;
import lorikeet.tools.CapabilityRepository;

public class ParameterSerializationCapabilityRegistry implements CapabilityRegistry<Class<?>, Fun<Object, String>, Class<?>> {

    private final CapabilityRepository<Class<?>, Fun<Object, String>, Class<?>> repository;

    private ParameterSerializationCapabilityRegistry() {
        this.repository = new CapabilityRepository<>();
    }

    private ParameterSerializationCapabilityRegistry(CapabilityRepository<Class<?>, Fun<Object, String>, Class<?>> repository) {
        this.repository = repository;
    }

    public <ValueType> ParameterSerializationCapabilityRegistry register(Class<ValueType> identity, Fun<ValueType, String> ability) {
        final Fun<Object, String> wrap = value -> ability.apply((ValueType)value);
        return new ParameterSerializationCapabilityRegistry(this.repository.add(identity, wrap));
    }

    public <ValueType> ParameterSerializationCapabilityRegistry register(Class<ValueType> identity, Fun<ValueType, String> ability, int rank) {
        final Fun<Object, String> wrap = value -> ability.apply((ValueType)value);
        return new ParameterSerializationCapabilityRegistry(this.repository.add(identity, wrap, rank));
    }


    public <ValueType> ParameterSerializationCapabilityRegistry register(Class<ValueType> identity, Fun<ValueType, String> ability,
                                                            Fun<Class<?>, Boolean> contextPredicate, int rank) {
        final Fun<Object, String> wrap = value -> ability.apply((ValueType)value);
        return new ParameterSerializationCapabilityRegistry(this.repository.add(identity, wrap, contextPredicate, rank));
    }

    public <ValueType> ParameterSerializationCapabilityRegistry register(Class<ValueType> identity, Fun<ValueType, String> ability,
                                                            Fun<Class<?>, Boolean> contextPredicate) {
        final Fun<Object, String> wrap = value -> ability.apply((ValueType)value);
        return new ParameterSerializationCapabilityRegistry(this.repository.add(identity, wrap, contextPredicate));
    }

    @Override
    public Err<Fun<Object, String>> find(Class<?> identifier, Class<?> context) {
        return this.repository
            .find(identifier, context)
            .asErr(new CouldNotFindParameterSerializerCapability(identifier.getCanonicalName(), context.getCanonicalName()));
    }

    public static ParameterSerializationCapabilityRegistry init() {
        return new ParameterSerializationCapabilityRegistry()
            .register(String.class, (value) -> String.format("\"%s\"", value))
            .register(Short.class, Object::toString)
            .register(Integer.class, Object::toString)
            .register(Long.class, Object::toString)
            .register(Float.class, Object::toString)
            .register(Double.class, Object::toString)
            .register(Number.class, Object::toString)
            .register(Boolean.class, Object::toString);
    }


}
