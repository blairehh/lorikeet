package lorikeet.container;

public interface Edict4<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> extends Container {
    ReturnType invoke(ParameterType1 parameter1, ParameterType2 parameter2, ParameterType3 parameter3, ParameterType4 parameter4);
    void inject(ActionContainer action);
}
