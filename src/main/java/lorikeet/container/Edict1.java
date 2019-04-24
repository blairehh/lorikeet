package lorikeet.container;

public interface Edict1<ReturnType, ParameterType> {
    ReturnType invoke(ParameterType parameter);
    void inject(ActionContainer action);
}
