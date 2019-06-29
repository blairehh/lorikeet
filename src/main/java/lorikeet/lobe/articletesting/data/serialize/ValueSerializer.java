package lorikeet.lobe.articletesting.data.serialize;

import lorikeet.Opt;
import lorikeet.lobe.articletesting.data.Value;

public interface ValueSerializer{
    Opt<String> serialize(Value value);
}
