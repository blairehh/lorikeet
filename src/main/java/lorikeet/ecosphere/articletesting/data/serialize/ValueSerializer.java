package lorikeet.ecosphere.articletesting.data.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.articletesting.data.Value;

public interface ValueSerializer{
    Opt<String> serialize(Value value);
}
