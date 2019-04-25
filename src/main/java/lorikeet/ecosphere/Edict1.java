package lorikeet.ecosphere;


public interface Edict1<ReturnType, ParameterType> extends Crate {
    ReturnType invoke(ParameterType parameter);
    void inject(Plug action);
}
