package lorikeet.container;

import java.io.Serializable;

public interface Edict2<ReturnType, ParameterType1 extends Serializable, ParameterType2 extends Serializable> {
    ReturnType invoke(ParameterType1 parameter1, ParameterType2 parameter2);
    void inject(ActionContainer action);
}

