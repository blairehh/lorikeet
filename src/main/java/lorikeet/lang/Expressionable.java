package lorikeet.lang;

import java.util.Optional;

public interface Expressionable {
    public Optional<SpecType> getExpressionType();
    public void setExpressionType(SpecType.Known type);
}
