package lorikeet.container;


import java.io.Serializable;

public interface ActionContainer {

    String getCid();

    <ReturnType, ParameterType extends Serializable> ReturnType yield(Edict1<ReturnType, ParameterType> edict, ParameterType parameter);

    <ReturnType, ParameterType1 extends Serializable, ParameterType2 extends Serializable> ReturnType yield(
        Edict2<ReturnType, ParameterType1, ParameterType2> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2
    );

    <ReturnType, ParameterType1 extends Serializable, ParameterType2 extends Serializable, ParameterType3 extends Serializable> ReturnType yield(
        Edict3<ReturnType, ParameterType1, ParameterType2, ParameterType3> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3
    );


    <ReturnType,
        ParameterType1 extends Serializable,
        ParameterType2 extends Serializable,
        ParameterType3 extends Serializable,
        ParameterType4 extends Serializable
        > ReturnType yield(
        Edict4<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4
    );


    <ReturnType,
        ParameterType1 extends Serializable,
        ParameterType2 extends Serializable,
        ParameterType3 extends Serializable,
        ParameterType4 extends Serializable,
        ParameterType5 extends Serializable
        > ReturnType yield(
        Edict5<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4, ParameterType5> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4,
        ParameterType5 parameter5
    );

}
