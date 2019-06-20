package lorikeet.ecosphere.articletesting.data.generator;

import lorikeet.Err;
import lorikeet.ecosphere.articletesting.data.Value;

public interface ValueGenerator {
    <T> Err<T> generate(Class<T> classDef, Value value);
}
