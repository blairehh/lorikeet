package lorikeet.ecosphere;

import java.util.UUID;

public class DefaultPlug implements Plug {

    private final String cid;

    public DefaultPlug() {
        this.cid = UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    public String getCid() {
        return this.cid;
    }

    @Override
    public final <ReturnType, ParameterType> ReturnType yield(Edict1<ReturnType, ParameterType> edict, ParameterType parameter) {
        edict.inject(this);
        return edict.invoke(parameter);
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2> ReturnType yield(
        Edict2<ReturnType, ParameterType1, ParameterType2> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2
    ) {
        edict.inject(this);
        return edict.invoke(parameter1, parameter2);
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3> ReturnType yield(
        Edict3<ReturnType, ParameterType1, ParameterType2, ParameterType3> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3
    ) {
        edict.inject(this);
        return edict.invoke(parameter1, parameter2, parameter3);
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> ReturnType yield(
        Edict4<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4
    ) {
        edict.inject(this);
        return edict.invoke(parameter1, parameter2, parameter3, parameter4);
    }

    @Override
    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4, ParameterType5> ReturnType yield(
        Edict5<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4, ParameterType5> edict,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4,
        ParameterType5 parameter5
    ) {
        edict.inject(this);
        return edict.invoke(parameter1, parameter2, parameter3, parameter4, parameter5);
    }

}
