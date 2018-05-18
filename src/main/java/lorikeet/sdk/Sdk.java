package lorikeet.sdk;

import lorikeet.lang.LorikeetType;
import lorikeet.sdk.types.Str;
import lorikeet.sdk.types.Dec;
import lorikeet.sdk.types.Int;
import lorikeet.sdk.types.Bol;

import java.util.Arrays;
import java.util.List;

public class Sdk {
    private static final List<LorikeetType> types;

    static {
        types = Arrays.asList(new Str(), new Dec(), new Int(), new Bol());
    }

    public List<LorikeetType> getTypes() {
        return types;
    }
}
