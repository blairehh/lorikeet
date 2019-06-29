package lorikeet.ecosphere;

public interface Action4<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> extends Cell {
    ReturnType invoke(ParameterType1 parameter1, ParameterType2 parameter2, ParameterType3 parameter3, ParameterType4 parameter4);
    void connect(Tract action);
}
