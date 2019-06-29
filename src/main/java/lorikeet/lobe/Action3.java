package lorikeet.lobe;

public interface Action3<ReturnType, ParameterType1, ParameterType2, ParameterType3> extends Cell {
    ReturnType invoke(ParameterType1 parameter1, ParameterType2 parameter2, ParameterType3 parameter3);
    void connect(Tract action);
}