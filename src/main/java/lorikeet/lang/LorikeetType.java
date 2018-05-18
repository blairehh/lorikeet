package lorikeet.lang;

import java.util.List;
import java.util.Set;
import java.io.File;

public interface LorikeetType {
    public Origin getOrigin();
    public Type getType();
    public List<Function> getFunctions();
    public Set<Attribute> getAttributes();
}
