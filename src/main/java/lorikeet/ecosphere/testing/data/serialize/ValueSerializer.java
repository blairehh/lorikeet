package lorikeet.ecosphere.testing.data.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.Value;

public interface ValueSerializer{
    Opt<String> serialize(Value value);
}
