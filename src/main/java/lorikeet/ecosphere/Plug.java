package lorikeet.ecosphere;


public interface Plug {

    String getCid();

    <ReturnType, ParameterType> ReturnType yield(Edict1<ReturnType, ParameterType> edict, ParameterType parameter);

    <ReturnType, ParameterType1, ParameterType2> ReturnType yield(
        Edict2<ReturnType, ParameterType1, ParameterType2> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2
    );

    <ReturnType, ParameterType1, ParameterType2, ParameterType3> ReturnType yield(
        Edict3<ReturnType, ParameterType1, ParameterType2, ParameterType3> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3
    );


    <ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> ReturnType yield(
        Edict4<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4
    );


    <ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4, ParameterType5> ReturnType yield(
        Edict5<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4, ParameterType5> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4,
        ParameterType5 parameter5
    );

}
