package lorikeet.lobe;

public interface Action2<ReturnType, ParameterType1, ParameterType2> extends Cell {
    ReturnType invoke(ParameterType1 parameter1, ParameterType2 parameter2);
    void connect(Tract action);
}

