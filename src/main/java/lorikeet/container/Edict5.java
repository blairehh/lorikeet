package lorikeet.container;

import java.io.Serializable;

public interface Edict5<
    ReturnType,
    ParameterType1 extends Serializable,
    ParameterType2 extends Serializable,
    ParameterType3 extends Serializable,
    ParameterType4 extends Serializable,
    ParameterType5 extends Serializable
    > {
    ReturnType invoke(
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4,
        ParameterType5 parameter5
    );
    void inject(ActionContainer action);
}
