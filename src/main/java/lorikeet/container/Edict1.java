package lorikeet.container;


public interface Edict1<ReturnType, ParameterType> extends Container {
    ReturnType invoke(ParameterType parameter);
    void inject(ActionContainer action);
}
