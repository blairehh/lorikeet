package lorikeet.lobe.articletesting.data.generator;

import lorikeet.Err;
import lorikeet.lobe.articletesting.data.Value;

public interface ValueGenerator {
    <T> Err<T> generate(Class<T> classDef, Value value);
}
