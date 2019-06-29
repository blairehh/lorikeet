package lorikeet.lobe.articletesting.data.deserialize;

import lorikeet.Err;
import lorikeet.lobe.articletesting.reader.TextReader;
import lorikeet.lobe.articletesting.data.Value;

public interface ValueDeserializer<ValueType extends Value> {
    Err<ValueType> deserialize(TextReader text);
}
