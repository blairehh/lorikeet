package lorikeet.container;

import java.io.Serializable;

public interface Edict3<ReturnType, ParameterType1 extends Serializable, ParameterType2 extends Serializable, ParameterType3 extends Serializable> {
    ReturnType invoke(ParameterType1 parameter1, ParameterType2 parameter2, ParameterType3 parameter3);
    void inject(ActionContainer action);
}