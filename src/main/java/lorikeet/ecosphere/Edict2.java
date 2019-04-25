package lorikeet.ecosphere;

public interface Edict2<ReturnType, ParameterType1, ParameterType2> extends Crate {
    ReturnType invoke(ParameterType1 parameter1, ParameterType2 parameter2);
    void inject(Plug action);
}

