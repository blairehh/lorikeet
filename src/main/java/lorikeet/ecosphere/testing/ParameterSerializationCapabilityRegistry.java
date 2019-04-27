package lorikeet.ecosphere.testing;

import com.sun.jdi.Value;
import lorikeet.Err;
import lorikeet.Fun;
import lorikeet.error.CouldNotFindParameterSerializerCapability;
import lorikeet.tools.CapabilityRegistry;
import lorikeet.tools.CapabilityRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class ParameterSerializationCapabilityRegistry implements CapabilityRegistry<Class<?>, Fun<Object, String>, Class<?>> {

    private final CapabilityRepository<Class<?>, Fun<Object, String>, Class<?>> repository;

    private ParameterSerializationCapabilityRegistry() {
        this.repository = new CapabilityRepository<>();
    }

    private ParameterSerializationCapabilityRegistry(CapabilityRepository<Class<?>, Fun<Object, String>, Class<?>> repository) {
        this.repository = repository;
    }

    public <ValueType> ParameterSerializationCapabilityRegistry register(Fun<Class<ValueType>, Boolean> match, Fun<ValueType, String> ability) {
        final Fun<Object, String> unsafeWrap = value -> ability.apply((ValueType)value);
        final Fun<Class<?>, Boolean> matchPredicate = value -> match.apply((Class<ValueType>)value);
        return new ParameterSerializationCapabilityRegistry(this.repository.add(matchPredicate, unsafeWrap));
    }

    public <ValueType> ParameterSerializationCapabilityRegistry register(Fun<Class<ValueType>, Boolean> match, Fun<ValueType, String> ability, int rank) {
        final Fun<Object, String> unsafeWrap = value -> ability.apply((ValueType)value);
        final Fun<Class<?>, Boolean> matchPredicate = value -> match.apply((Class<ValueType>)value);
        return new ParameterSerializationCapabilityRegistry(this.repository.add(matchPredicate, unsafeWrap, rank));
    }


    public <ValueType> ParameterSerializationCapabilityRegistry register(Fun<Class<ValueType>, Boolean> match, Fun<ValueType, String> ability,
                                                            Fun<Class<?>, Boolean> contextPredicate, int rank) {
        final Fun<Object, String> unsafeWrap = value -> ability.apply((ValueType)value);
        final Fun<Class<?>, Boolean> matchPredicate = value -> match.apply((Class<ValueType>)value);
        return new ParameterSerializationCapabilityRegistry(this.repository.add(matchPredicate, unsafeWrap, contextPredicate, rank));
    }

    public <ValueType> ParameterSerializationCapabilityRegistry register(Fun<Class<ValueType>, Boolean> match, Fun<ValueType, String> ability,
                                                            Fun<Class<?>, Boolean> contextPredicate) {
        final Fun<Object, String> unsafeWrap = value -> ability.apply((ValueType)value);
        final Fun<Class<?>, Boolean> matchPredicate = value -> match.apply((Class<ValueType>)value);
        return new ParameterSerializationCapabilityRegistry(this.repository.add(matchPredicate, unsafeWrap, contextPredicate));
    }

    @Override
    public Err<Fun<Object, String>> find(Class<?> identifier, Class<?> context) {
        return this.repository
            .find(identifier, context)
            .asErr(new CouldNotFindParameterSerializerCapability(identifier.getCanonicalName(), context.getCanonicalName()));
    }

    public static ParameterSerializationCapabilityRegistry init() {
        final ParameterSerializationCapabilityRegistry scalarTypes = new ParameterSerializationCapabilityRegistry()
            .register(c -> c.equals(String.class), (value) -> String.format("\"%s\"", value))
            .register(c -> Number.class.isAssignableFrom(c), Object::toString)
            .register(c -> c.equals(Boolean.class), Object::toString);


        return scalarTypes.register(c -> List.class.isAssignableFrom(c), (List<?> list) -> serializeList(list, scalarTypes));
    }

    static String serializeList(List<?> list, ParameterSerializationCapabilityRegistry scalarTypes) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < list.size(); i++) {
            final Object item = list.get(i);
            if (item == null) {
                builder.append("null ");
                continue;
            }

            final String value = scalarTypes.find(item.getClass(), String.class)
                .map(stringifier -> stringifier.apply(item))
                .orElse("not-ser");

            builder.append(value);
            if (i != list.size() - 1) {
                builder.append(", ");
            }

        }
        builder.append("]");
        return builder.toString();
    }


}
