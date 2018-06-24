package lorikeet.lang;

import java.util.Optional;

public interface Expressionable {
    public boolean isReturnable();
    public SpecType getExpressionType();
    public void setExpressionType(SpecType.Known type);
}
