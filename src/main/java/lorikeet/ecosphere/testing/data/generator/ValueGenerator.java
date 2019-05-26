package lorikeet.ecosphere.testing.data.generator;

import lorikeet.Err;
import lorikeet.ecosphere.testing.data.Value;

public interface ValueGenerator {
    <T> Err<T> generate(Class<T> classDef, Value value);
}
