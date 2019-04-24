package lorikeet.container;

import java.io.Serializable;

public interface Edict1<ReturnType, ParameterType extends Serializable> {
    ReturnType invoke(ParameterType parameter);
    void inject(ActionContainer action);
}
