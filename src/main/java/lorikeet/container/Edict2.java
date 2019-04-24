package lorikeet.container;

public interface Edict2<ReturnType, ParameterType1, ParameterType2> extends Container {
    ReturnType invoke(ParameterType1 parameter1, ParameterType2 parameter2);
    void inject(ActionContainer action);
}

