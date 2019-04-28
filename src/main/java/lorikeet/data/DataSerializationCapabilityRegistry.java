package lorikeet.data;

import lorikeet.Err;
import lorikeet.Fun;
import lorikeet.Fun2;
import lorikeet.error.CouldNotFindParameterSerializerCapability;
import lorikeet.tools.CapabilityRegistry;
import lorikeet.tools.CapabilityRepository;

import java.util.Collection;
import java.util.Map;

public class DataSerializationCapabilityRegistry implements CapabilityRegistry<Class<?>, Fun2<Object, Class<?>, String>, Class<?>> {

    private final CapabilityRepository<Class<?>, Fun2<Object, Class<?>, String>, Class<?>> repository;

    private DataSerializationCapabilityRegistry() {
        this.repository = new CapabilityRepository<>();
    }

    private DataSerializationCapabilityRegistry(CapabilityRepository<Class<?>, Fun2<Object, Class<?>, String>, Class<?>> repository) {
        this.repository = repository;
    }

    public <ValueType> DataSerializationCapabilityRegistry register(Fun<Class<ValueType>, Boolean> match, Fun2<ValueType, Class<?>, String> ability) {
        final Fun2<Object, Class<?>, String> unsafeWrap = (value, context) -> ability.apply((ValueType)value, context);
        final Fun<Class<?>, Boolean> matchPredicate = value -> match.apply((Class<ValueType>)value);
        return new DataSerializationCapabilityRegistry(this.repository.add(matchPredicate, unsafeWrap));
    }

    public <ValueType> DataSerializationCapabilityRegistry register(Fun<Class<ValueType>, Boolean> match, Fun2<ValueType, Class<?>, String> ability, int rank) {
        final Fun2<Object, Class<?>, String> unsafeWrap = (value, context) -> ability.apply((ValueType)value, context);
        final Fun<Class<?>, Boolean> matchPredicate = value -> match.apply((Class<ValueType>)value);
        return new DataSerializationCapabilityRegistry(this.repository.add(matchPredicate, unsafeWrap, rank));
    }


    public <ValueType> DataSerializationCapabilityRegistry register(Fun<Class<ValueType>, Boolean> match, Fun2<ValueType, Class<?>, String> ability,
                                                                    Fun<Class<?>, Boolean> contextPredicate, int rank) {
        final Fun2<Object, Class<?>, String> unsafeWrap = (value, context) -> ability.apply((ValueType)value, context);
        final Fun<Class<?>, Boolean> matchPredicate = value -> match.apply((Class<ValueType>)value);
        return new DataSerializationCapabilityRegistry(this.repository.add(matchPredicate, unsafeWrap, contextPredicate, rank));
    }

    public <ValueType> DataSerializationCapabilityRegistry register(Fun<Class<ValueType>, Boolean> match, Fun2<ValueType, Class<?>, String> ability,
                                                                    Fun<Class<?>, Boolean> contextPredicate) {
        final Fun2<Object, Class<?>, String> unsafeWrap = (value, context) -> ability.apply((ValueType)value, context);
        final Fun<Class<?>, Boolean> matchPredicate = value -> match.apply((Class<ValueType>)value);
        return new DataSerializationCapabilityRegistry(this.repository.add(matchPredicate, unsafeWrap, contextPredicate));
    }

    @Override
    public Err<Fun2<Object, Class<?>, String>> find(Class<?> identifier, Class<?> context) {
        return this.repository
            .find(identifier, context)
            .asErr(new CouldNotFindParameterSerializerCapability(identifier.getCanonicalName(), context.getCanonicalName()));
    }

    public static DataSerializationCapabilityRegistry init() {
        final DataSerializationCapabilityRegistry registry = new DataSerializationCapabilityRegistry();
        final DataSerializer serializer = new DataSerializer(registry);
        registry.register(c -> c.equals(String.class), (value, context) -> String.format("\"%s\"", value))
            .register(Number.class::isAssignableFrom, (value, context) -> value.toString())
            .register(c -> c.equals(Character.class), (value, context) -> String.format("'%s'", value))
            .register(c -> c.equals(Boolean.class), (value, context) -> value.toString());


        registry.register(
            Collection.class::isAssignableFrom,
            (Collection<?> list, Class<?> context) -> DataSerializationSupport.serializeCollection(list, context, serializer)
        );

        registry.register(
            Map.class::isAssignableFrom,
            (Map<?, ?> map, Class<?> context) -> DataSerializationSupport.serializeMap(map, context, serializer)
        );

        registry.register(
            (c) -> true,
            (Object o, Class<?> context) -> DataSerializationSupport.serializeObject(o, context, serializer),
            -1000
        );

        return registry;
    }


}
