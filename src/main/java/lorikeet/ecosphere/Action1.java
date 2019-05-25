package lorikeet.ecosphere;


public interface Action1<ReturnType, ParameterType> extends Cell {
    ReturnType invoke(ParameterType parameter);
    void inject(Axon action);
}
