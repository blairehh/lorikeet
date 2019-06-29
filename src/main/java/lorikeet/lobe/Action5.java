package lorikeet.lobe;

public interface Action5<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4, ParameterType5> extends Cell {
    ReturnType invoke(
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4,
        ParameterType5 parameter5
    );
    void connect(Tract action);
}
