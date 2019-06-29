package lorikeet.lobe.articletesting.data.generator;

import lorikeet.Err;
import lorikeet.Seq;
import lorikeet.lobe.articletesting.data.Value;
import lorikeet.error.CouldNotGenerateValue;

public class Generator {

    private static final Seq<ValueGenerator> GENERATORS = Seq.of(
        new BoolValueGenerator(),
        new NumberValueGenerator(),
        new StringValueGenerator(),
        new EnumIdentifierValueGenerator()
    );

    public <T> Err<T> generate(Class<T> classDef, Value value) {
        for (ValueGenerator generator : GENERATORS) {
            final Err<T> generated = generator.generate(classDef, value);
            if (generated.isPresent()) {
                return generated;
            }
        }
        return Err.failure(new CouldNotGenerateValue());
    }
}
