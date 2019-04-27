package lorikeet.ecosphere.testing;

import com.sun.jdi.Value;
import lorikeet.Err;
import lorikeet.Fun;
import lorikeet.error.CouldNotFindParameterSerializerCapability;
import lorikeet.tools.CapabilityRegistry;
import lorikeet.tools.CapabilityRepository;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ParameterSerializationCapabilityRegistry implements CapabilityRegistry<Class<?>, Fun<Object, String>, Class<?>> {

    private final CapabilityRepository<Class<?>, Fun<Object, String>, Class<?>> repository;

    private ParameterSerializationCapabilityRegistry() {
        this.repository = new CapabilityRepository<>();
    }

    private ParameterSerializationCapabilityRegistry(CapabilityRepository<Class<?>, Fun<Object, String>, Class<?>> repository) {
        this.repository = repository;
    }

    public <ValueType> ParameterSerializationCapabilityRegistry register(Fun<Class<ValueType>, Boolean> identity, Fun<ValueType, String> ability) {
        final Fun<Object, String> unsafeWrap = value -> ability.apply((ValueType)value);
        final Fun<Class<?>, Boolean> identityPredicate = value -> identity.apply((Class<ValueType>)value);
        return new ParameterSerializationCapabilityRegistry(this.repository.add(identityPredicate, unsafeWrap));
    }

    public <ValueType> ParameterSerializationCapabilityRegistry register(Fun<Class<ValueType>, Boolean> identity, Fun<ValueType, String> ability, int rank) {
        final Fun<Object, String> unsafeWrap = value -> ability.apply((ValueType)value);
        final Fun<Class<?>, Boolean> identityPredicate = value -> identity.apply((Class<ValueType>)value);
        return new ParameterSerializationCapabilityRegistry(this.repository.add(identityPredicate, unsafeWrap, rank));
    }


    public <ValueType> ParameterSerializationCapabilityRegistry register(Fun<Class<ValueType>, Boolean> identity, Fun<ValueType, String> ability,
                                                            Fun<Class<?>, Boolean> contextPredicate, int rank) {
        final Fun<Object, String> unsafeWrap = value -> ability.apply((ValueType)value);
        final Fun<Class<?>, Boolean> identityPredicate = value -> identity.apply((Class<ValueType>)value);
        return new ParameterSerializationCapabilityRegistry(this.repository.add(identityPredicate, unsafeWrap, contextPredicate, rank));
    }

    public <ValueType> ParameterSerializationCapabilityRegistry register(Fun<Class<ValueType>, Boolean> identity, Fun<ValueType, String> ability,
                                                            Fun<Class<?>, Boolean> contextPredicate) {
        final Fun<Object, String> unsafeWrap = value -> ability.apply((ValueType)value);
        final Fun<Class<?>, Boolean> identityPredicate = value -> identity.apply((Class<ValueType>)value);
        return new ParameterSerializationCapabilityRegistry(this.repository.add(identityPredicate, unsafeWrap, contextPredicate));
    }

    @Override
    public Err<Fun<Object, String>> find(Class<?> identifier, Class<?> context) {
        return this.repository
            .find(identifier, context)
            .asErr(new CouldNotFindParameterSerializerCapability(identifier.getCanonicalName(), context.getCanonicalName()));
    }

    public static ParameterSerializationCapabilityRegistry init() {
        return new ParameterSerializationCapabilityRegistry()
            .register(c -> c.equals(String.class), (value) -> String.format("\"%s\"", value))
            .register(c -> Number.class.isAssignableFrom(c), Object::toString)
            .register(c -> c.equals(Boolean.class), Object::toString);
    }


}
