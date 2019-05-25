package lorikeet.ecosphere.transcript.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.Value;

public interface ValueSerializer{
    Opt<String> serialize(Value value);
}
