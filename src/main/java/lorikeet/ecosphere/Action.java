package lorikeet.ecosphere;

public class Action {
    private Axon axon;

    public void connect(Axon axon) {
        this.axon = axon;
    }

    public void inject(Axon axon) {
        this.axon = axon;
    }

    public String cid() {
        return this.axon.getCid();
    }

    public final <ReturnType, ParameterType> ReturnType yield(Action1<ReturnType, ParameterType> action, ParameterType parameter) {
        return this.axon.yield(action, parameter);
    }

    public final <ReturnType, ParameterType1, ParameterType2> ReturnType yield(
        Action2<ReturnType, ParameterType1, ParameterType2> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2
    ) {
        return this.axon.yield(action, parameter1, parameter2);
    }

    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3> ReturnType yield(
        Action3<ReturnType, ParameterType1, ParameterType2, ParameterType3> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3
    ) {
        return this.axon.yield(action, parameter1, parameter2, parameter3);
    }

    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> ReturnType yield(
        Action4<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4
    ) {
        return this.axon.yield(action, parameter1, parameter2, parameter3, parameter4);
    }

    public final <ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4, ParameterType5> ReturnType yield(
        Action5<ReturnType, ParameterType1, ParameterType2, ParameterType3, ParameterType4, ParameterType5> action,
        ParameterType1 parameter1,
        ParameterType2 parameter2,
        ParameterType3 parameter3,
        ParameterType4 parameter4,
        ParameterType5 parameter5
    ) {
        return this.axon.yield(action, parameter1, parameter2, parameter3, parameter4, parameter5);
    }

}
