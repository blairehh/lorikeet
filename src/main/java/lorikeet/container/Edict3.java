package lorikeet.container;

public interface Edict3<ReturnType, ParameterType1, ParameterType2, ParameterType3> extends Container {
    ReturnType invoke(ParameterType1 parameter1, ParameterType2 parameter2, ParameterType3 parameter3);
    void inject(ActionContainer action);
}