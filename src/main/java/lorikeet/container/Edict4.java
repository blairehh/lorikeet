package lorikeet.container;

import java.io.Serializable;

public interface Edict4<
    ReturnType,
    ParameterType1 extends Serializable,
    ParameterType2 extends Serializable,
    ParameterType3 extends Serializable,
    ParameterType4 extends Serializable
    > {
    ReturnType invoke(ParameterType1 parameter1, ParameterType2 parameter2, ParameterType3 parameter3, ParameterType4 parameter4);
    void inject(ActionContainer action);
}
