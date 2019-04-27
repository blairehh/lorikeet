package lorikeet.ecosphere.testing;

import com.sun.jdi.Value;
import lorikeet.Err;
import lorikeet.Fun;
import lorikeet.Fun2;
import lorikeet.error.CouldNotFindParameterSerializerCapability;
import lorikeet.tools.CapabilityRegistry;
import lorikeet.tools.CapabilityRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public class ParameterSerializationCapabilityRegistry implements CapabilityRegistry<Class<?>, Fun2<Object, Class<?>, String>, Class<?>> {

    private final CapabilityRepository<Class<?>, Fun2<Object, Class<?>, String>, Class<?>> repository;

    private ParameterSerializationCapabilityRegistry() {
        this.repository = new CapabilityRepository<>();
    }

    private ParameterSerializationCapabilityRegistry(CapabilityRepository<Class<?>, Fun2<Object, Class<?>, String>, Class<?>> repository) {
        this.repository = repository;
    }

    public <ValueType> ParameterSerializationCapabilityRegistry register(Fun<Class<ValueType>, Boolean> match, Fun2<ValueType, Class<?>, String> ability) {
        final Fun2<Object, Class<?>, String> unsafeWrap = (value, context) -> ability.apply((ValueType)value, context);
        final Fun<Class<?>, Boolean> matchPredicate = value -> match.apply((Class<ValueType>)value);
        return new ParameterSerializationCapabilityRegistry(this.repository.add(matchPredicate, unsafeWrap));
    }

    public <ValueType> ParameterSerializationCapabilityRegistry register(Fun<Class<ValueType>, Boolean> match, Fun2<ValueType, Class<?>, String> ability, int rank) {
        final Fun2<Object, Class<?>, String> unsafeWrap = (value, context) -> ability.apply((ValueType)value, context);
        final Fun<Class<?>, Boolean> matchPredicate = value -> match.apply((Class<ValueType>)value);
        return new ParameterSerializationCapabilityRegistry(this.repository.add(matchPredicate, unsafeWrap, rank));
    }


    public <ValueType> ParameterSerializationCapabilityRegistry register(Fun<Class<ValueType>, Boolean> match, Fun2<ValueType, Class<?>, String> ability,
                                                            Fun<Class<?>, Boolean> contextPredicate, int rank) {
        final Fun2<Object, Class<?>, String> unsafeWrap = (value, context) -> ability.apply((ValueType)value, context);
        final Fun<Class<?>, Boolean> matchPredicate = value -> match.apply((Class<ValueType>)value);
        return new ParameterSerializationCapabilityRegistry(this.repository.add(matchPredicate, unsafeWrap, contextPredicate, rank));
    }

    public <ValueType> ParameterSerializationCapabilityRegistry register(Fun<Class<ValueType>, Boolean> match, Fun2<ValueType, Class<?>, String> ability,
                                                            Fun<Class<?>, Boolean> contextPredicate) {
        final Fun2<Object, Class<?>, String> unsafeWrap = (value, context) -> ability.apply((ValueType)value, context);
        final Fun<Class<?>, Boolean> matchPredicate = value -> match.apply((Class<ValueType>)value);
        return new ParameterSerializationCapabilityRegistry(this.repository.add(matchPredicate, unsafeWrap, contextPredicate));
    }

    @Override
    public Err<Fun2<Object, Class<?>, String>> find(Class<?> identifier, Class<?> context) {
        return this.repository
            .find(identifier, context)
            .asErr(new CouldNotFindParameterSerializerCapability(identifier.getCanonicalName(), context.getCanonicalName()));
    }

    public static ParameterSerializationCapabilityRegistry init() {
        final ParameterSerializationCapabilityRegistry scalarTypes = new ParameterSerializationCapabilityRegistry()
            .register(c -> c.equals(String.class), (value, context) -> String.format("\"%s\"", value))
            .register(c -> Number.class.isAssignableFrom(c), (value, context) -> value.toString())
            .register(c -> c.equals(Boolean.class), (value, context) -> value.toString());


        return scalarTypes.register(c -> Collection.class.isAssignableFrom(c), (Collection<?> list, Class<?> context) -> serializeList(list, context, scalarTypes));
    }

    static String serializeList(Collection<?> list, Class<?> context, ParameterSerializationCapabilityRegistry scalarTypes) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Object item : list) {
            if (item == null) {
                builder.append("null ");
                continue;
            }

            final String value = scalarTypes.find(item.getClass(), context)
                .map(stringifier -> stringifier.apply(item, context))
                .orElse("not-ser");

            builder.append(value);
            if (i != list.size() - 1) {
                builder.append(", ");
            }
            i++;
        }
        builder.append("]");
        return builder.toString();
    }


}
