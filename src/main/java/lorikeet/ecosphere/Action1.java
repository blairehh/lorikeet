package lorikeet.ecosphere;


public interface Action1<ReturnType, ParameterType> extends Crate {
    ReturnType invoke(ParameterType parameter);
    void inject(Axon action);
}
