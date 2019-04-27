package lorikeet.ecosphere.testing;

import lorikeet.Err;
import lorikeet.Fun;
import lorikeet.tools.CapabilityRegistry;
import lorikeet.tools.CapabilityRepository;

public class ParameterSerializationCapabilityRegistry implements CapabilityRegistry<Class<?>, Fun<?, String>, Class<?>> {

    private final CapabilityRepository<Class<?>, Fun<?, String>, Class<?>> repository;

    private ParameterSerializationCapabilityRegistry() {
        this.repository = new CapabilityRepository<>();
    }

    private ParameterSerializationCapabilityRegistry(CapabilityRepository<Class<?>, Fun<?, String>, Class<?>> repository) {
        this.repository = repository;
    }

    public <ValueType> ParameterSerializationCapabilityRegistry register(Class<ValueType> identity, Fun<ValueType, String> ability) {
        return new ParameterSerializationCapabilityRegistry(this.repository.add(identity, ability));
    }

    public <ValueType> ParameterSerializationCapabilityRegistry register(Class<ValueType> identity, Fun<ValueType, String> ability, int rank) {
        return new ParameterSerializationCapabilityRegistry(this.repository.add(identity, ability, rank));
    }


    public <ValueType> ParameterSerializationCapabilityRegistry register(Class<ValueType> identity, Fun<ValueType, String> ability,
                                                            Fun<Class<?>, Boolean> contextPredicate, int rank) {
        return new ParameterSerializationCapabilityRegistry(this.repository.add(identity, ability, contextPredicate, rank));
    }

    public <ValueType> ParameterSerializationCapabilityRegistry register(Class<ValueType> identity, Fun<ValueType, String> ability,
                                                            Fun<Class<?>, Boolean> contextPredicate) {
        return new ParameterSerializationCapabilityRegistry(this.repository.add(identity, ability, contextPredicate));
    }

    @Override
    public Err<Fun<?, String>> find(Class<?> identifier, Class<?> context) {
        return this.repository
            .find(identifier, context)
            .asErr(new UnableToFindResultSetExtractorCapability(identifier.getCanonicalName(), context.getCanonicalName()));
    }


}
